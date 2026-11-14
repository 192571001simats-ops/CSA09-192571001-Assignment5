import java.sql.*;
import java.util.*;

interface Dao<T, K> {
  void insert(T value) throws SQLException;

  Optional<T> findById(K key) throws SQLException;

  List<T> findAll() throws SQLException;

  void update(T value) throws SQLException;

  boolean delete(K key) throws SQLException;
}

record StudentRecord(String regNo, String name) {}

class StudentDao implements Dao<StudentRecord, String> {
  public void insert(StudentRecord s) throws SQLException {
    write("INSERT INTO students_lab5(reg_no,name) VALUES(?,?)", s.regNo(), s.name());
  }

  public Optional<StudentRecord> findById(String id) throws SQLException {
    try (Connection c = Db.open();
        PreparedStatement p =
            c.prepareStatement("SELECT reg_no,name FROM students_lab5 WHERE reg_no=?")) {
      p.setString(1, id);
      try (ResultSet r = p.executeQuery()) {
        return r.next()
            ? Optional.of(new StudentRecord(r.getString(1), r.getString(2)))
            : Optional.empty();
      }
    }
  }

  public List<StudentRecord> findAll() throws SQLException {
    List<StudentRecord> rows = new ArrayList<>();
    try (Connection c = Db.open();
        Statement s = c.createStatement();
        ResultSet r = s.executeQuery("SELECT reg_no,name FROM students_lab5 ORDER BY reg_no")) {
      while (r.next()) rows.add(new StudentRecord(r.getString(1), r.getString(2)));
    }
    return rows;
  }

  public void update(StudentRecord s) throws SQLException {
    write("UPDATE students_lab5 SET name=? WHERE reg_no=?", s.name(), s.regNo());
  }

  public boolean delete(String id) throws SQLException {
    try (Connection c = Db.open();
        PreparedStatement p = c.prepareStatement("DELETE FROM students_lab5 WHERE reg_no=?")) {
      p.setString(1, id);
      return p.executeUpdate() == 1;
    }
  }

  private void write(String sql, String a, String b) throws SQLException {
    try (Connection c = Db.open();
        PreparedStatement p = c.prepareStatement(sql)) {
      p.setString(1, a);
      p.setString(2, b);
      if (p.executeUpdate() != 1) throw new SQLException("Expected one changed row");
    }
  }
}

public class StudentDaoDemo {
  public static void main(String[] args) throws Exception {
    Db.init();
    StudentDao dao = new StudentDao();
    dao.delete("192571001");
    dao.insert(new StudentRecord("192571001", "Athisaya U"));
    Bench.require(dao.findById("192571001").isPresent(), "DAO insert and primary key lookup");
    dao.update(new StudentRecord("192571001", "Athisaya U Updated"));
    Bench.require(dao.findById("192571001").orElseThrow().name().endsWith("Updated"), "DAO update");
    Bench.require(
        !dao.findAll().isEmpty() && dao.delete("192571001"), "DAO ordered list and delete");
    Bench.require(dao.findById("missing").isEmpty(), "DAO absent primary key");
  }
}
