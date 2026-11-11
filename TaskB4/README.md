# TaskB4

Athisaya U  
192571001  
Programming in Java CSA09  
OpenJDK 21.0.13

## Compile and run

From the repository root on Linux or macOS:

```bash
mkdir -p build/TaskB4
javac -encoding UTF-8 -cp lib/mysql-connector-j-9.2.0.jar -d build/TaskB4 $(find shared TaskB4 -name "*.java")
cd TaskB4
java -cp ../build/TaskB4:../lib/mysql-connector-j-9.2.0.jar Migration
cd ..
```

Windows PowerShell uses `Get-ChildItem shared,TaskB4 -Recurse -Filter *.java` to collect sources before calling javac.

## Execution evidence

The captured output is in output.txt. Raw repeated measurements, where required, are in results.csv; summary.csv reports the mean, median, range and standard deviation. Heap deltas reflect heap occupancy and are not allocated-byte counts.

The root README and schema.sql define the isolated MySQL setup. The Windows run_all.ps1 uses the Windows classpath separator.

The actual cloud terminal execution is captured in real-execution.png. The complete compiler and program output is retained in terminal-run.txt.
