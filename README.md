# File Persistence and MySQL JDBC

Athisaya U  
192571001  
CSA09 Programming in Java  
Assignment 5

Java 21 implementations and captured execution evidence for 9 tasks. Every task folder contains its Java files, input fixtures or generators, output.txt and output screenshots. Measurements also have results.csv and summary.csv.

## Execution

A full JDK 21 is required. Windows PowerShell: `./run_all.ps1`. Linux or macOS: `bash run_all.sh`. These scripts compile each task independently and run its validation entry points. New execution logs and timing summaries are written under runs/TaskNN, keeping the captured task-folder evidence together with its report. Supplied code completions are separate from intentionally incorrect reference variants.

MySQL setup is in schema.sql. The default lab connection is jdbc:mysql://127.0.0.1:3306/java_assignments?rewriteBatchedStatements=true. JAVA_ASSIGNMENT_DB_URL, JAVA_ASSIGNMENT_DB_USER and JAVA_ASSIGNMENT_DB_PASSWORD select the local lab database. The default user is root and the default password is empty; a local configured account can be supplied through those environment variables. The driver is downloaded from Maven Central and checked against SHA-256. Tests create and reset only the named assignment tables.

## Tasks

| Folder | Executed entry points |
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

## Measurements

Two warmups and five measured trials are retained where timing is requested. Tables use milliseconds; the raw CSV stores nanoseconds and heap occupancy delta. Input generation is outside timed sections. Heap occupancy is not an allocated-byte count. JVM warmup, garbage collection, caching and shared-machine scheduling limit small timing comparisons. The report separates actual observations from asymptotic analysis.

## Report

[Completed report PDF](report/Athisaya_U_Java_Assignment5_Completed.pdf)  
[Editable Word report](report/Athisaya_U_Java_Assignment5_Completed.docx)
