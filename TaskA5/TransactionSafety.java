import java.sql.*;import java.math.*;
public class TransactionSafety {
 static void reset()throws SQLException{try(Connection c=Db.open();Statement s=c.createStatement()){s.executeUpdate("DELETE FROM wallets_lab5");s.executeUpdate("INSERT INTO wallets_lab5 VALUES(1,1000.00),(2,0.00)");s.executeUpdate("DELETE FROM users_lab5");s.executeUpdate("INSERT INTO users_lab5 VALUES('student','example-only')");}}
 static void transfer(boolean force)throws SQLException{
  try(Connection c=Db.open()){boolean old=c.getAutoCommit();c.setAutoCommit(false);
   try(PreparedStatement debit=c.prepareStatement("UPDATE wallets_lab5 SET balance=balance-? WHERE id=? AND balance>=?");PreparedStatement credit=c.prepareStatement("UPDATE wallets_lab5 SET balance=balance+? WHERE id=?")){
    debit.setBigDecimal(1,new BigDecimal("500.00"));debit.setInt(2,1);debit.setBigDecimal(3,new BigDecimal("500.00"));if(debit.executeUpdate()!=1)throw new SQLException("Insufficient funds");
    if(force)throw new SQLException("Injected failure between updates");credit.setBigDecimal(1,new BigDecimal("500.00"));credit.setInt(2,2);if(credit.executeUpdate()!=1)throw new SQLException("Missing receiver");c.commit();
   }catch(SQLException|RuntimeException e){try{c.rollback();}catch(SQLException rollback){e.addSuppressed(rollback);}throw e;}finally{c.setAutoCommit(old);}
  }
 }
 static String balances()throws SQLException{try(Connection c=Db.open();Statement s=c.createStatement();ResultSet r=s.executeQuery("SELECT id,balance FROM wallets_lab5 ORDER BY id")){StringBuilder b=new StringBuilder();while(r.next())b.append(r.getInt(1)).append(':').append(r.getBigDecimal(2)).append(' ');return b.toString().trim();}}
 static boolean login(String id,String pwd,boolean safe)throws SQLException{
  try(Connection c=Db.open()){
   if(safe)try(PreparedStatement p=c.prepareStatement("SELECT id FROM users_lab5 WHERE id=? AND pwd=?")){p.setString(1,id);p.setString(2,pwd);try(ResultSet r=p.executeQuery()){return r.next();}}
   try(Statement s=c.createStatement();ResultSet r=s.executeQuery("SELECT id FROM users_lab5 WHERE id='"+id+"' AND pwd='"+pwd+"'")){return r.next();}
  }
 }
 public static void main(String[] args)throws Exception{
  Db.init();reset();String before=balances();try{transfer(true);throw new AssertionError();}catch(SQLException e){System.out.println(e.getMessage());}
  Bench.require(before.equals(balances()),"rollback preserves both wallets");transfer(false);System.out.println("After transfer: "+balances());Bench.require(balances().equals("1:500.00 2:500.00"),"successful transfer preserves Rs1000 total");
  String attack="' OR '1'='1' -- ";Bench.require(login(attack,"invalid",false),"unsafe local test login bypass reproduced");Bench.require(!login(attack,"invalid",true),"prepared query treats attack as literal data");
 }
}
