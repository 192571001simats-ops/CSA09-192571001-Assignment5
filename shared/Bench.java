import java.util.*;
import java.nio.file.*;
import java.io.*;
import java.lang.management.ManagementFactory;
public final class Bench {
    public static volatile long sink;
    public static void require(boolean condition, String label) {
        if (!condition) throw new AssertionError(label);
        System.out.println("PASS " + label);
    }
    public static void measure(String caseName, int size, Runnable action) {
        for (int i=0;i<2;i++) action.run();
        for (int trial=1;trial<=5;trial++) {
            long memoryBefore=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
            long start=System.nanoTime(); action.run(); long elapsed=System.nanoTime()-start;
            long memoryAfter=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
            String row=caseName+","+size+","+trial+","+elapsed+","+(memoryAfter-memoryBefore)+"\n";
            try {
                Path p=Path.of("results.csv");
                if (!Files.exists(p)) Files.writeString(p,"case,size,trial,nanoseconds,heap_delta_bytes\n");
                Files.writeString(p,row,StandardOpenOption.APPEND);
            } catch(IOException e) {throw new UncheckedIOException(e);}
        }
        System.out.println("MEASURED " + caseName + " size="+size+" trials=5");
    }
}
