# TaskB2

Athisaya U  
192571001  
Programming in Java CSA09  
OpenJDK 21.0.13

## Compile and run

From the repository root on Linux or macOS:

```bash
mkdir -p build/TaskB2
javac -encoding UTF-8 -d build/TaskB2 $(find shared TaskB2 -name "*.java")
cd TaskB2
java -cp ../build/TaskB2 AdversarialFiles
cd ..
```

Windows PowerShell uses `Get-ChildItem shared,TaskB2 -Recurse -Filter *.java` to collect sources before calling javac.

## Execution evidence

The captured output is in output.txt. Raw repeated measurements, where required, are in results.csv; summary.csv reports the mean, median, range and standard deviation. Heap deltas reflect heap occupancy and are not allocated-byte counts.
