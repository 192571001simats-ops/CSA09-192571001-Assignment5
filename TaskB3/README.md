# TaskB3

Athisaya U  
192571001  
Programming in Java CSA09  
OpenJDK 21.0.13

## Compile and run

From the repository root on Linux or macOS:

```bash
mkdir -p build/TaskB3
javac -encoding UTF-8 -cp lib/mysql-connector-j-9.2.0.jar -d build/TaskB3 $(find shared TaskB3 -name "*.java")
cd TaskB3
java -cp ../build/TaskB3:../lib/mysql-connector-j-9.2.0.jar JdbcMeasurements
cd ..
```

Windows PowerShell uses `Get-ChildItem shared,TaskB3 -Recurse -Filter *.java` to collect sources before calling javac.

## Execution evidence

The captured output is in output.txt. Raw repeated measurements, where required, are in results.csv; summary.csv reports the mean, median, range and standard deviation. Heap deltas reflect heap occupancy and are not allocated-byte counts.

The root README and schema.sql define the isolated MySQL setup. The Windows run_all.ps1 uses the Windows classpath separator.

Implementation source and recorded output appear in implementation-screenshot.png. Full execution and compilation records are retained in implementation-run.txt and implementation-compile.txt.
