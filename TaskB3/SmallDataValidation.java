import java.math.BigDecimal;
import java.sql.*;

public class SmallDataValidation {
  public static void main(String[] args) throws Exception {
    Db.init();
    for (int mode = 0; mode < 3; mode++) {
      JdbcMeasurements.clear();
      JdbcMeasurements.insert(JdbcMeasurements.rows(10), mode, false);
      try (Connection c = Db.open();
          Statement s = c.createStatement();
          ResultSet r =
              s.executeQuery(
                  "SELECT pass_id,reg_no,name,route_no,amount FROM bus_pass ORDER BY pass_id")) {
        int i = 0;
        while (r.next()) {
          boolean correct =
              r.getString(1).equals("P" + i)
                  && r.getString(2).equals("R" + i)
                  && r.getString(3).equals("Student" + i)
                  && r.getInt(4) == 12
                  && r.getBigDecimal(5).compareTo(new BigDecimal("4500.00")) == 0;
          if (!correct)
            throw new AssertionError("row content mismatch mode " + mode + " index " + i);
          System.out.println(
              "ROW mode="
                  + mode
                  + " "
                  + r.getString(1)
                  + " "
                  + r.getString(2)
                  + " "
                  + r.getString(3)
                  + " "
                  + r.getInt(4)
                  + " "
                  + r.getBigDecimal(5));
          i++;
        }
        Bench.require(i == 10, "all five fields of ten rows verified mode " + mode);
      }
    }
  }
}
