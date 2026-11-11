import java.math.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;

public class BusPassValidation {
  public static void main(String[] args) throws Exception {
    Path path = Path.of("validation-passes.csv");
    Files.deleteIfExists(path);
    BusPassStore store = new BusPassStore(path);
    Bench.require(store.find("UNKNOWN").isEmpty(), "missing file is an empty lookup store");
    var first =
        new BusPassStore.Pass(
            "V1",
            "R1",
            "Athisaya, U",
            12,
            "Anna Nagar",
            LocalDate.of(2027, 1, 31),
            new BigDecimal("4500.00"));
    store.add(first);
    Bench.require(
        store.find("V1").orElseThrow().equals(first),
        "normal add find and quoted field preservation");
    Bench.require(store.find("UNKNOWN").isEmpty(), "nonexistent pass ID returns empty");
    Bench.require(
        store.route(12).size() == 1 && store.route(7).isEmpty(),
        "route match and route without records");
    try {
      store.add(first);
      throw new AssertionError();
    } catch (IllegalArgumentException e) {
      System.out.println("PASS duplicate pass ID rejected");
    }
    Files.writeString(path.resolveSibling("abandoned-write.tmp"), "INCOMPLETE");
    Bench.require(
        store.find("V1").orElseThrow().equals(first),
        "abandoned temporary write leaves canonical record intact");
    Files.writeString(path, "DAMAGED\n", StandardOpenOption.APPEND);
    List<String> rejected = new ArrayList<>();
    Bench.require(
        store.read(rejected).size() == 1 && rejected.size() == 1,
        "damaged line reported without losing valid row");
    try {
      store.add(
          new BusPassStore.Pass(
              "V2",
              "R2",
              "Divya",
              7,
              "Campus",
              LocalDate.of(2027, 1, 31),
              new BigDecimal("3800.00")));
      throw new AssertionError();
    } catch (java.io.IOException e) {
      System.out.println("PASS update refuses to overwrite damaged canonical store");
    }
  }
}
