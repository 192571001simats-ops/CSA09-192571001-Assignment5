set -euo pipefail
cd "$(dirname "$0")"
ROOT="$PWD"
mkdir -p build
mkdir -p lib
if [ ! -f lib/mysql-connector-j-9.2.0.jar ]; then
  curl --fail --location https://repo.maven.apache.org/maven2/com/mysql/mysql-connector-j/9.2.0/mysql-connector-j-9.2.0.jar --output lib/mysql-connector-j-9.2.0.jar
fi
printf "%s  %s\n" "7e9941bbdcca244d878ea95bfff788fd9ba6a65af757f24be6c632930d61c7ed" "lib/mysql-connector-j-9.2.0.jar" | sha256sum --check

mkdir -p "build/TaskA1"
javac -g -encoding UTF-8 -cp "$ROOT/lib/mysql-connector-j-9.2.0.jar" -d "build/TaskA1" $(find shared TaskA1 -name "*.java")
TASK_BUILD="TaskA1"
mkdir -p "runs/TaskA1"
cd "runs/TaskA1"
rm -f results.csv
: > output.txt
printf "RecordLayouts\n" | tee -a output.txt
java -Xms128m -Xmx512m -cp "$ROOT/build/TaskA1:$ROOT/lib/mysql-connector-j-9.2.0.jar" RecordLayouts 2>&1 | tee -a output.txt
java -cp "$ROOT/build/$TASK_BUILD" SummarizeResults
cd "$ROOT"

mkdir -p "build/TaskA2"
javac -g -encoding UTF-8 -cp "$ROOT/lib/mysql-connector-j-9.2.0.jar" -d "build/TaskA2" $(find shared TaskA2 -name "*.java")
TASK_BUILD="TaskA2"
mkdir -p "runs/TaskA2"
cd "runs/TaskA2"
rm -f results.csv
: > output.txt
printf "BufferedDesign\n" | tee -a output.txt
java -Xms128m -Xmx512m -cp "$ROOT/build/TaskA2:$ROOT/lib/mysql-connector-j-9.2.0.jar" BufferedDesign 2>&1 | tee -a output.txt
java -cp "$ROOT/build/$TASK_BUILD" SummarizeResults
cd "$ROOT"

mkdir -p "build/TaskA3"
javac -g -encoding UTF-8 -cp "$ROOT/lib/mysql-connector-j-9.2.0.jar" -d "build/TaskA3" $(find shared TaskA3 -name "*.java")
TASK_BUILD="TaskA3"
mkdir -p "runs/TaskA3"
cd "runs/TaskA3"
rm -f results.csv
: > output.txt
printf "StudentDaoDemo\n" | tee -a output.txt
java -Xms128m -Xmx512m -cp "$ROOT/build/TaskA3:$ROOT/lib/mysql-connector-j-9.2.0.jar" StudentDaoDemo 2>&1 | tee -a output.txt
java -cp "$ROOT/build/$TASK_BUILD" SummarizeResults
cd "$ROOT"

mkdir -p "build/TaskA4"
javac -g -encoding UTF-8 -cp "$ROOT/lib/mysql-connector-j-9.2.0.jar" -d "build/TaskA4" $(find shared TaskA4 -name "*.java")
TASK_BUILD="TaskA4"
mkdir -p "runs/TaskA4"
cd "runs/TaskA4"
rm -f results.csv
: > output.txt
printf "SerializationStudy\n" | tee -a output.txt
java -Xms128m -Xmx512m -cp "$ROOT/build/TaskA4:$ROOT/lib/mysql-connector-j-9.2.0.jar" SerializationStudy 2>&1 | tee -a output.txt
java -cp "$ROOT/build/$TASK_BUILD" SummarizeResults
cd "$ROOT"

mkdir -p "build/TaskA5"
javac -g -encoding UTF-8 -cp "$ROOT/lib/mysql-connector-j-9.2.0.jar" -d "build/TaskA5" $(find shared TaskA5 -name "*.java")
TASK_BUILD="TaskA5"
mkdir -p "runs/TaskA5"
cd "runs/TaskA5"
rm -f results.csv
: > output.txt
printf "TransactionSafety\n" | tee -a output.txt
java -Xms128m -Xmx512m -cp "$ROOT/build/TaskA5:$ROOT/lib/mysql-connector-j-9.2.0.jar" TransactionSafety 2>&1 | tee -a output.txt
java -cp "$ROOT/build/$TASK_BUILD" SummarizeResults
cd "$ROOT"

mkdir -p "build/TaskB1"
javac -g -encoding UTF-8 -cp "$ROOT/lib/mysql-connector-j-9.2.0.jar" -d "build/TaskB1" $(find shared TaskB1 -name "*.java")
TASK_BUILD="TaskB1"
mkdir -p "runs/TaskB1"
cd "runs/TaskB1"
rm -f results.csv
: > output.txt
printf "PassStore\n" | tee -a output.txt
java -Xms128m -Xmx512m -cp "$ROOT/build/TaskB1:$ROOT/lib/mysql-connector-j-9.2.0.jar" PassStore 2>&1 | tee -a output.txt
printf "FileMeasurements\n" | tee -a output.txt
java -Xms128m -Xmx512m -cp "$ROOT/build/TaskB1:$ROOT/lib/mysql-connector-j-9.2.0.jar" FileMeasurements 2>&1 | tee -a output.txt
printf "BusPassValidation\n" | tee -a output.txt
java -Xms128m -Xmx512m -cp "$ROOT/build/TaskB1:$ROOT/lib/mysql-connector-j-9.2.0.jar" BusPassValidation 2>&1 | tee -a output.txt
java -cp "$ROOT/build/$TASK_BUILD" SummarizeResults
cd "$ROOT"

mkdir -p "build/TaskB2"
javac -g -encoding UTF-8 -cp "$ROOT/lib/mysql-connector-j-9.2.0.jar" -d "build/TaskB2" $(find shared TaskB2 -name "*.java")
TASK_BUILD="TaskB2"
mkdir -p "runs/TaskB2"
cd "runs/TaskB2"
rm -f results.csv
: > output.txt
printf "AdversarialFiles\n" | tee -a output.txt
java -Xms128m -Xmx512m -cp "$ROOT/build/TaskB2:$ROOT/lib/mysql-connector-j-9.2.0.jar" AdversarialFiles 2>&1 | tee -a output.txt
java -cp "$ROOT/build/$TASK_BUILD" SummarizeResults
cd "$ROOT"

mkdir -p "build/TaskB3"
javac -g -encoding UTF-8 -cp "$ROOT/lib/mysql-connector-j-9.2.0.jar" -d "build/TaskB3" $(find shared TaskB3 -name "*.java")
TASK_BUILD="TaskB3"
mkdir -p "runs/TaskB3"
cd "runs/TaskB3"
rm -f results.csv
: > output.txt
printf "JdbcMeasurements\n" | tee -a output.txt
java -Xms128m -Xmx512m -cp "$ROOT/build/TaskB3:$ROOT/lib/mysql-connector-j-9.2.0.jar" JdbcMeasurements 2>&1 | tee -a output.txt
printf "SmallDataValidation\n" | tee -a output.txt
java -Xms128m -Xmx512m -cp "$ROOT/build/TaskB3:$ROOT/lib/mysql-connector-j-9.2.0.jar" SmallDataValidation 2>&1 | tee -a output.txt
java -cp "$ROOT/build/$TASK_BUILD" SummarizeResults
cd "$ROOT"

mkdir -p "build/TaskB4"
javac -g -encoding UTF-8 -cp "$ROOT/lib/mysql-connector-j-9.2.0.jar" -d "build/TaskB4" $(find shared TaskB4 -name "*.java")
TASK_BUILD="TaskB4"
mkdir -p "runs/TaskB4"
cd "runs/TaskB4"
rm -f results.csv
: > output.txt
printf "Migration\n" | tee -a output.txt
java -Xms128m -Xmx512m -cp "$ROOT/build/TaskB4:$ROOT/lib/mysql-connector-j-9.2.0.jar" Migration 2>&1 | tee -a output.txt
java -cp "$ROOT/build/$TASK_BUILD" SummarizeResults
cd "$ROOT"
