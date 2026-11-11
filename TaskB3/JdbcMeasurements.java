import java.sql.*;import java.util.*;
public class JdbcMeasurements {
 static List<String[]> rows(int n){List<String[]> rows=new ArrayList<>();for(int i=0;i<n;i++)rows.add(new String[]{"P"+i,"R"+i,"Student"+i,"12","4500.00"});return rows;}
 static void clear()throws SQLException{try(Connection c=Db.open();Statement s=c.createStatement()){s.executeUpdate("DELETE FROM bus_pass");}}
 static void insert(List<String[]> rows,int mode,boolean auto)throws SQLException{
  try(Connection c=Db.open()){c.setAutoCommit(auto);try{
   if(mode==0)try(Statement s=c.createStatement()){for(String[] p:rows)s.executeUpdate("INSERT INTO bus_pass(pass_id,reg_no,name,route_no,amount) VALUES('"+p[0]+"','"+p[1]+"','"+p[2]+"',12,4500.00)");}
   else try(PreparedStatement p=c.prepareStatement("INSERT INTO bus_pass(pass_id,reg_no,name,route_no,amount) VALUES(?,?,?,?,?)")){int count=0;for(String[] row:rows){p.setString(1,row[0]);p.setString(2,row[1]);p.setString(3,row[2]);p.setInt(4,12);p.setDouble(5,4500);if(mode==1)p.executeUpdate();else{p.addBatch();if(++count%1000==0)p.executeBatch();}}if(mode==2)p.executeBatch();}
   if(!auto)c.commit();
  }catch(SQLException|RuntimeException e){if(!auto)c.rollback();throw e;}}
 }
 static void measured(String name,int n,Runnable action){Bench.measure(name,n,action);}
 public static void main(String[] args)throws Exception{
  Db.init();for(int mode=0;mode<3;mode++){clear();insert(rows(10),mode,false);Bench.require(Db.count("bus_pass")==10,"ten records correct method "+mode);}
  try(Connection c=Db.open();Statement s=c.createStatement();ResultSet r=s.executeQuery("SELECT pass_id,reg_no,name,route_no,amount FROM bus_pass ORDER BY pass_id")){ResultSetMetaData m=r.getMetaData();for(int i=1;i<=m.getColumnCount();i++)System.out.println("COLUMN "+m.getColumnName(i)+" "+m.getColumnTypeName(i));}
  for(int n:new int[]{1000,10000,100000}){
   List<String[]> data=rows(n);
   for(int mode=0;mode<3;mode++){final int kind=mode;
    measured(new String[]{"Statement_transaction","Prepared_transaction","Batch_transaction"}[mode],n,()->{try{clear();insert(data,kind,false);if(Db.count("bus_pass")!=n)throw new AssertionError();}catch(SQLException e){throw new RuntimeException(e);}});
   }
   measured("Batch_autocommit",n,()->{try{clear();insert(data,2,true);if(Db.count("bus_pass")!=n)throw new AssertionError();}catch(SQLException e){throw new RuntimeException(e);}});
   clear();try(Connection c=Db.open()){Bench.require(PassLoader.loadAll(c,data)==n,"provided PassLoader rows "+n);}
  }
  List<String[]> failed=rows(10000);failed.get(5000)[4]="INVALID";clear();try(Connection c=Db.open()){try{PassLoader.loadAll(c,failed);throw new AssertionError();}catch(NumberFormatException e){Bench.require(Db.count("bus_pass")==0&&c.getAutoCommit(),"invalid amount rolls back all batches and restores auto commit");}}
  for(boolean auto:new boolean[]{true,false}){
   clear();try(Connection c=Db.open()){c.setAutoCommit(auto);try(PreparedStatement p=c.prepareStatement("INSERT INTO bus_pass(pass_id,reg_no,name,route_no,amount) VALUES(?,?,?,?,?)")){try{for(int i=0;i<10000;i++){if(i==5000)throw new SQLException("Injected failure after five batches");p.setString(1,"F"+i);p.setString(2,"R"+i);p.setString(3,"Student");p.setInt(4,12);p.setDouble(5,4500);p.addBatch();if((i+1)%1000==0)p.executeBatch();}if(!auto)c.commit();}catch(SQLException failure){if(!auto)c.rollback();}}}
   int remain=Db.count("bus_pass");System.out.println("Failure halfway autoCommit="+auto+" rows remaining="+remain);Bench.require(remain==(auto?5000:0),"partial commit versus complete rollback");
  }
  System.out.println("Benchmark includes identical table reset, connection and validation overhead for every mode; generated records exist before timing");
 }
}
