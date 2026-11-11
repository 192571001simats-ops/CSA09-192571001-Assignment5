# TaskA1

Athisaya U  
192571001  
Programming in Java CSA09  
OpenJDK 21.0.13

## Compile and run

From the repository root on Linux or macOS:

```bash
mkdir -p build/TaskA1
javac -encoding UTF-8 -cp lib/mysql-connector-j-9.2.0.jar -d build/TaskA1 $(find shared TaskA1 -name "*.java")
cd TaskA1
java -cp ../build/TaskA1:../lib/mysql-connector-j-9.2.0.jar RecordLayouts
cd ..
```

Windows PowerShell uses `Get-ChildItem shared,TaskA1 -Recurse -Filter *.java` to collect sources before calling javac.

## Execution evidence

The captured output is in output.txt. Raw repeated measurements, where required, are in results.csv; summary.csv reports the mean, median, range and standard deviation. Heap deltas reflect heap occupancy and are not allocated-byte counts.

The root README and schema.sql define the isolated MySQL setup. The Windows run_all.ps1 uses the Windows classpath separator.

Implementation source and recorded output appear in implementation-screenshot.png. Full execution and compilation records are retained in implementation-run.txt and implementation-compile.txt.
