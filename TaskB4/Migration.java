import java.sql.*;import java.nio.file.*;import java.util.*;import java.time.*;import java.math.*;
public class Migration {
 public static void main(String[] args)throws Exception{
  Db.init();Path file=Path.of("legacy-passes.csv");StringBuilder input=new StringBuilder();for(int i=0;i<100;i++)input.append(new BusPassStore.Pass("C01-P"+i,"R"+i,"Student "+i,12,"Anna Nagar",LocalDate.of(2027,1,31),new BigDecimal("4500.00")).csv()).append('\n');input.append("DAMAGED\n");Files.writeString(file,input);
  List<String> failures=new ArrayList<>();List<BusPassStore.Pass> passes=new BusPassStore(file).read(failures);Set<String> ids=new HashSet<>();
  try(Connection c=Db.open()){c.setAutoCommit(false);try(Statement clear=c.createStatement();PreparedStatement p=c.prepareStatement("INSERT INTO bus_pass(pass_id,reg_no,name,route_no,amount,boarding_stop,valid_date) VALUES(?,?,?,?,?,?,?)")){
   clear.executeUpdate("DELETE FROM bus_pass");int count=0;
   for(var pass:passes){if(!ids.add(pass.id())){failures.add("duplicate,"+pass.id());continue;}p.setString(1,pass.id());p.setString(2,pass.reg());p.setString(3,pass.name());p.setInt(4,pass.route());p.setBigDecimal(5,pass.amount());p.setString(6,pass.stop());p.setDate(7,java.sql.Date.valueOf(pass.validity()));p.addBatch();if(++count%1000==0)p.executeBatch();}p.executeBatch();c.commit();
  }catch(SQLException|RuntimeException e){c.rollback();throw e;}}
  Files.writeString(Path.of("migration-rejected.csv"),"line,error\n"+String.join("\n",failures)+"\n");Bench.require(Db.count("bus_pass")==100&&failures.size()==1,"100 valid migrated; one damaged row quarantined");
  try(Connection c=Db.open()){
   DatabaseMetaData meta=c.getMetaData();System.out.println("Database: "+meta.getDatabaseProductName()+" "+meta.getDatabaseProductVersion());System.out.println("Driver: "+meta.getDriverName()+" "+meta.getDriverVersion());
   try(ResultSet tables=meta.getTables(c.getCatalog(),null,"%",new String[]{"TABLE"})){while(tables.next())System.out.println("TABLE "+tables.getString("TABLE_NAME"));}
   try(Statement s=c.createStatement();ResultSet r=s.executeQuery("SELECT boarding_stop,valid_date FROM bus_pass WHERE pass_id='C01-P0'")){r.next();Bench.require(r.getString(1).equals("Anna Nagar")&&r.getDate(2).toLocalDate().equals(LocalDate.of(2027,1,31)),"migration preserves every record field");}
  }
 }
}
