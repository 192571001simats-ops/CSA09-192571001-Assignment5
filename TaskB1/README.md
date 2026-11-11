# TaskB1

Athisaya U  
192571001  
Programming in Java CSA09  
OpenJDK 21.0.13

## Compile and run

From the repository root on Linux or macOS:

```bash
mkdir -p build/TaskB1
javac -encoding UTF-8 -cp lib/mysql-connector-j-9.2.0.jar -d build/TaskB1 $(find shared TaskB1 -name "*.java")
cd TaskB1
java -cp ../build/TaskB1:../lib/mysql-connector-j-9.2.0.jar PassStore
cd ..
cd TaskB1
java -cp ../build/TaskB1:../lib/mysql-connector-j-9.2.0.jar FileMeasurements
cd ..
```

Windows PowerShell uses `Get-ChildItem shared,TaskB1 -Recurse -Filter *.java` to collect sources before calling javac.

## Execution evidence

The captured output is in output.txt. Raw repeated measurements, where required, are in results.csv; summary.csv reports the mean, median, range and standard deviation. Heap deltas reflect heap occupancy and are not allocated-byte counts.

The root README and schema.sql define the isolated MySQL setup. The Windows run_all.ps1 uses the Windows classpath separator.
