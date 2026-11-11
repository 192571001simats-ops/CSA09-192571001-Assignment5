import java.sql.*;
public final class Db {
 public static Connection open()throws SQLException{
  String url=System.getenv().getOrDefault("JAVA_ASSIGNMENT_DB_URL","jdbc:mysql://127.0.0.1:3306/java_assignments?rewriteBatchedStatements=true");
  return DriverManager.getConnection(url,System.getenv().getOrDefault("JAVA_ASSIGNMENT_DB_USER","root"),System.getenv().getOrDefault("JAVA_ASSIGNMENT_DB_PASSWORD",""));
 }
 public static void init()throws SQLException{
  try(Connection c=open();Statement s=c.createStatement()){
   s.executeUpdate("CREATE TABLE IF NOT EXISTS bus_pass(pass_id VARCHAR(30) PRIMARY KEY,reg_no VARCHAR(20) NOT NULL,name VARCHAR(100) NOT NULL,route_no INT NOT NULL,amount DECIMAL(12,2) NOT NULL CHECK(amount>=0),boarding_stop VARCHAR(100) NOT NULL DEFAULT 'Unspecified',valid_date DATE NULL)");
   s.executeUpdate("CREATE TABLE IF NOT EXISTS users_lab5(id VARCHAR(30) PRIMARY KEY,pwd VARCHAR(100) NOT NULL)");
   s.executeUpdate("CREATE TABLE IF NOT EXISTS wallets_lab5(id INT PRIMARY KEY,balance DECIMAL(12,2) NOT NULL CHECK(balance>=0))");
   s.executeUpdate("CREATE TABLE IF NOT EXISTS students_lab5(reg_no VARCHAR(20) PRIMARY KEY,name VARCHAR(100) NOT NULL)");
   s.executeUpdate("CREATE TABLE IF NOT EXISTS fees_assignment4(id BIGINT AUTO_INCREMENT PRIMARY KEY,reg_no VARCHAR(20) NOT NULL,amount DECIMAL(12,2) NOT NULL CHECK(amount>0))");
   s.executeUpdate("CREATE TABLE IF NOT EXISTS registrations_assignment4(id BIGINT AUTO_INCREMENT PRIMARY KEY,reg_no VARCHAR(20) NOT NULL,name VARCHAR(100) NOT NULL,event_name VARCHAR(60) NOT NULL,created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,UNIQUE KEY student_event(reg_no,event_name))");
  }
 }
 public static int count(String table)throws SQLException{
  if(!java.util.Set.of("bus_pass","students_lab5","registrations_assignment4").contains(table))throw new IllegalArgumentException();
  try(Connection c=open();Statement s=c.createStatement();ResultSet r=s.executeQuery("SELECT COUNT(*) FROM "+table)){r.next();return r.getInt(1);}
 }
}
