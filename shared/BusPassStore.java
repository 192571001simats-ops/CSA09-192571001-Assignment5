import java.io.*;
import java.math.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;

public class BusPassStore {
  public record Pass(
      String id,
      String reg,
      String name,
      int route,
      String stop,
      LocalDate validity,
      BigDecimal amount) {
    public Pass {
      for (String value : List.of(id, reg, name, stop))
        if (value.isBlank() || value.contains("\n") || value.contains("\r"))
          throw new IllegalArgumentException("Invalid text field");
      if (route <= 0 || amount == null || amount.signum() < 0 || amount.scale() > 2)
        throw new IllegalArgumentException("Invalid route or amount");
      Objects.requireNonNull(validity);
    }

    public String csv() {
      return String.join(
          ",",
          quote(id),
          quote(reg),
          quote(name),
          Integer.toString(route),
          quote(stop),
          validity.toString(),
          amount.toPlainString());
    }
  }

  private final Path file;

  public BusPassStore(Path file) {
    this.file = file;
  }

  public static String quote(String s) {
    return "\"" + s.replace("\"", "\"\"") + "\"";
  }

  public static List<String> fields(String line) {
    List<String> out = new ArrayList<>();
    StringBuilder cell = new StringBuilder();
    boolean quoted = false, closed = false;
    for (int i = 0; i < line.length(); i++) {
      char c = line.charAt(i);
      if (quoted) {
        if (c == '"') {
          if (i + 1 < line.length() && line.charAt(i + 1) == '"') {
            cell.append('"');
            i++;
          } else {
            quoted = false;
            closed = true;
          }
        } else cell.append(c);
      } else if (c == ',') {
        out.add(cell.toString());
        cell.setLength(0);
        closed = false;
      } else if (c == '"' && cell.length() == 0 && !closed) quoted = true;
      else if (closed || c == '"') throw new IllegalArgumentException("Invalid CSV quote");
      else cell.append(c);
    }
    if (quoted) throw new IllegalArgumentException("Unclosed quote");
    out.add(cell.toString());
    return out;
  }

  public static Pass parse(String line) {
    List<String> f = fields(line);
    if (f.size() != 7) throw new IllegalArgumentException("Expected seven fields");
    return new Pass(
        f.get(0),
        f.get(1),
        f.get(2),
        Integer.parseInt(f.get(3)),
        f.get(4),
        LocalDate.parse(f.get(5)),
        new BigDecimal(f.get(6)));
  }

  public List<Pass> read(List<String> failures) throws IOException {
    List<Pass> passes = new ArrayList<>();
    if (!Files.exists(file)) return passes;
    int lineNo = 0;
    try (BufferedReader r = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
      String line;
      while ((line = r.readLine()) != null) {
        lineNo++;
        try {
          passes.add(parse(line));
        } catch (IllegalArgumentException | java.time.DateTimeException e) {
          failures.add(lineNo + "," + e.getMessage());
        }
      }
    }
    return passes;
  }

  public Optional<Pass> find(String id) throws IOException {
    if (!Files.exists(file)) return Optional.empty();
    try (BufferedReader r = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
      String line;
      while ((line = r.readLine()) != null)
        try {
          Pass p = parse(line);
          if (p.id().equals(id)) return Optional.of(p);
        } catch (IllegalArgumentException | java.time.DateTimeException e) {
        }
    }
    return Optional.empty();
  }

  public List<Pass> route(int route) throws IOException {
    return read(new ArrayList<>()).stream().filter(p -> p.route() == route).toList();
  }

  public void add(Pass pass) throws IOException {
    Path absolute = file.toAbsolutePath();
    Files.createDirectories(absolute.getParent());
    try (FileChannel guard =
            FileChannel.open(
                absolute.resolveSibling(absolute.getFileName() + ".lock"),
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE);
        FileLock lock = guard.lock()) {
      List<String> damaged = new ArrayList<>();
      List<Pass> all = read(damaged);
      if (!damaged.isEmpty()) throw new IOException("Repair damaged store before update");
      if (all.stream().anyMatch(p -> p.id().equals(pass.id())))
        throw new IllegalArgumentException("Duplicate pass ID");
      all.add(pass);
      Path temp = Files.createTempFile(absolute.getParent(), "passes-", ".tmp");
      boolean installed = false;
      try {
        try (FileChannel writer = FileChannel.open(temp, StandardOpenOption.WRITE)) {
          for (Pass p : all) {
            ByteBuffer row = StandardCharsets.UTF_8.encode(p.csv() + "\n");
            while (row.hasRemaining()) writer.write(row);
          }
          writer.force(true);
        }
        Files.move(
            temp, absolute, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
        installed = true;
      } finally {
        if (!installed) Files.deleteIfExists(temp);
      }
    }
  }
}
