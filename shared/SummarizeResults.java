import java.nio.file.*;
import java.util.*;

public final class SummarizeResults {
  public static void main(String[] args) throws Exception {
    Path input = Path.of("results.csv");
    if (!Files.exists(input)) return;
    Map<String, List<Double>> groups = new LinkedHashMap<>();
    for (String line : Files.readAllLines(input).subList(1, Files.readAllLines(input).size())) {
      String[] f = line.split(",");
      groups
          .computeIfAbsent(f[0] + "," + f[1], key -> new ArrayList<>())
          .add(Double.parseDouble(f[3]) / 1000000.0);
    }
    StringBuilder output =
        new StringBuilder("case,size,trials,mean_ms,median_ms,min_ms,max_ms,stdev_ms\n");
    for (var entry : groups.entrySet()) {
      List<Double> values = entry.getValue();
      double mean = values.stream().mapToDouble(Double::doubleValue).average().orElseThrow();
      List<Double> sorted = new ArrayList<>(values);
      Collections.sort(sorted);
      int n = sorted.size();
      double median =
          n % 2 == 0 ? (sorted.get(n / 2 - 1) + sorted.get(n / 2)) / 2 : sorted.get(n / 2);
      double variance = 0;
      for (double x : values) variance += (x - mean) * (x - mean);
      double sd = n > 1 ? Math.sqrt(variance / (n - 1)) : 0;
      output
          .append(entry.getKey())
          .append(',')
          .append(n)
          .append(
              String.format(
                  Locale.ROOT,
                  ",%.6f,%.6f,%.6f,%.6f,%.6f\n",
                  mean,
                  median,
                  sorted.get(0),
                  sorted.get(n - 1),
                  sd));
    }
    Files.writeString(Path.of("summary.csv"), output);
    System.out.println("Summarized " + groups.size() + " measured cases");
  }
}
