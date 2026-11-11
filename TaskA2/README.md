# TaskA2

Athisaya U  
192571001  
Programming in Java CSA09  
OpenJDK 21.0.13

## Compile and run

From the repository root on Linux or macOS:

```bash
mkdir -p build/TaskA2
javac -encoding UTF-8 -d build/TaskA2 $(find shared TaskA2 -name "*.java")
cd TaskA2
java -cp ../build/TaskA2 BufferedDesign
cd ..
```

Windows PowerShell uses `Get-ChildItem shared,TaskA2 -Recurse -Filter *.java` to collect sources before calling javac.

## Execution evidence

The captured output is in output.txt. Raw repeated measurements, where required, are in results.csv; summary.csv reports the mean, median, range and standard deviation. Heap deltas reflect heap occupancy and are not allocated-byte counts.
