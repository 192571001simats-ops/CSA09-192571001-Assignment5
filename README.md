# File Persistence and MySQL JDBC

Athisaya U  
192571001  
CSA09 Programming in Java  
Assignment 5

This is my Java Assignment 5 on file handling and JDBC. It has 9 tasks covering records, serialization, transactions, bus pass storage and database migration.

The task folders contain the Java programs, saved output and screenshots. Input files or generators and timing CSV files are included where needed.

## How to run

Install a full JDK 21, then open a terminal in this repository.

- Windows PowerShell: `./run_all.ps1`
- Linux or macOS: `bash run_all.sh`

The scripts compile and run each task separately. New output and timing summaries go into `runs/TaskNN`, so the saved results in the task folders stay unchanged. For Assignment 5, the folders use names such as `TaskA1` and `TaskB1`.

## Database setup

The database setup is in `schema.sql`. The default connection is:

`jdbc:mysql://127.0.0.1:3306/java_assignments?rewriteBatchedStatements=true`

Set `JAVA_ASSIGNMENT_DB_URL`, `JAVA_ASSIGNMENT_DB_USER` and `JAVA_ASSIGNMENT_DB_PASSWORD` for your local database. The code defaults to `root` with an empty password, so use a configured local account instead. The run scripts download MySQL Connector/J from Maven Central and check its SHA-256 hash.

Use a separate assignment database. The tests create and reset the assignment tables. The runtime recheck passed with MariaDB using MySQL Connector/J; Oracle MySQL 8.4 was not tested in that recheck.

## Tasks

| Folder | Programs to run |
| --- | --- |
| TaskA1 | RecordLayouts |
| TaskA2 | BufferedDesign |
| TaskA3 | StudentDaoDemo |
| TaskA4 | SerializationStudy |
| TaskA5 | TransactionSafety |
| TaskB1 | PassStore, FileMeasurements, BusPassValidation |
| TaskB2 | AdversarialFiles |
| TaskB3 | JdbcMeasurements, SmallDataValidation |
| TaskB4 | Migration |

## Timing results

Where timing is needed, the results use two warmup runs and five measured runs. The report tables use milliseconds, while the raw CSV files use nanoseconds. Input generation is outside the timed part.

The heap value is a change in memory usage, not the total bytes allocated. Timing can vary with JVM warmup, garbage collection and other work on the computer, so small differences may not repeat exactly.

## Report

[PDF report](report/Athisaya_U_Java_Assignment5_Completed.pdf)  

## Output and screenshots

Each task has a `real-execution.png` terminal screenshot and a `terminal-run.txt` file with the compiler and program output.
