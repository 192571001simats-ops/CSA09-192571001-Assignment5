import java.nio.file.*;
import java.sql.*;
import java.util.*;

public class AdversarialFiles {
  public static void main(String[] args) throws Exception {
    Db.init();
    try (Connection c = Db.open();
        Statement s = c.createStatement()) {
      s.executeUpdate("DELETE FROM users_lab5");
      s.executeUpdate("INSERT INTO users_lab5 VALUES('student','example-only')");
    }
    String payload = "' OR '1'='1' -- ";
    try (Connection c = Db.open()) {
      try (Statement s = c.createStatement();
          ResultSet r =
              s.executeQuery(
                  "SELECT id FROM users_lab5 WHERE id='" + payload + "' AND pwd='bad'")) {
        Bench.require(r.next(), "local unsafe query accepted adversarial input");
      }
      try (PreparedStatement p =
          c.prepareStatement("SELECT id FROM users_lab5 WHERE id=? AND pwd=?")) {
        p.setString(1, payload);
        p.setString(2, "bad");
        try (ResultSet r = p.executeQuery()) {
          Bench.require(!r.next(), "parameter binding rejected same adversarial input");
        }
      }
    }
    Path file = Path.of("damaged-passes.csv");
    Files.writeString(
        file,
        "wrong,fields\n\n"
            + "P2,R2,Divya,7,Campus,2027-01-31,NOT_A_NUMBER\n"
            + "\"P3\",\"R3\",\"Arun\",12,\"Campus\",2027-01-31,4500.00\n");
    var store = new BusPassStore(file);
    List<String> failures = new ArrayList<>();
    var good = store.read(failures);
    Bench.require(
        good.size() == 1 && failures.size() == 3,
        "three damaged lines reported; later valid record survives");
    Files.writeString(Path.of("rejected.csv"), "line,error\n" + String.join("\n", failures) + "\n");
    for (String f : failures) System.out.println("REJECT " + f);
  }
}
