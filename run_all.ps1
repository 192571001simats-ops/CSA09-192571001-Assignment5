$ErrorActionPreference = 'Stop'
Set-Location $PSScriptRoot
$repoRoot = $PSScriptRoot
New-Item -ItemType Directory -Path build -Force | Out-Null
New-Item -ItemType Directory -Path lib -Force | Out-Null
if (!(Test-Path lib/mysql-connector-j-9.2.0.jar)) {
  Invoke-WebRequest 'https://repo.maven.apache.org/maven2/com/mysql/mysql-connector-j/9.2.0/mysql-connector-j-9.2.0.jar' -OutFile lib/mysql-connector-j-9.2.0.jar
}
if ((Get-FileHash 'lib/mysql-connector-j-9.2.0.jar' -Algorithm SHA256).Hash.ToLower() -ne '7e9941bbdcca244d878ea95bfff788fd9ba6a65af757f24be6c632930d61c7ed') { throw 'JDBC driver checksum mismatch' }

$task = 'TaskA1'
$buildPath = Join-Path $repoRoot ("build/" + $task)
New-Item -ItemType Directory -Path $buildPath -Force | Out-Null
$sources = @((Get-ChildItem shared,$task -Recurse -Filter *.java).FullName)
& javac -g -encoding UTF-8 -cp "lib/mysql-connector-j-9.2.0.jar" -d $buildPath @sources
if ($LASTEXITCODE -ne 0) { throw "Compilation failed: $task" }
$runPath = Join-Path $repoRoot ("runs/" + $task)
New-Item -ItemType Directory -Path $runPath -Force | Out-Null
Push-Location $runPath
Remove-Item results.csv -ErrorAction SilentlyContinue
Set-Content output.txt ""
$classPath = $buildPath + ";" + (Join-Path $repoRoot "lib/mysql-connector-j-9.2.0.jar")
& java -Xms128m -Xmx512m -cp $classPath RecordLayouts 2>&1 | Tee-Object -FilePath output.txt -Append
if ($LASTEXITCODE -ne 0) { throw "Execution failed: $task" }
& java -cp $buildPath SummarizeResults
if ($LASTEXITCODE -ne 0) { throw "Summary failed: $task" }
Pop-Location

$task = 'TaskA2'
$buildPath = Join-Path $repoRoot ("build/" + $task)
New-Item -ItemType Directory -Path $buildPath -Force | Out-Null
$sources = @((Get-ChildItem shared,$task -Recurse -Filter *.java).FullName)
& javac -g -encoding UTF-8 -cp "lib/mysql-connector-j-9.2.0.jar" -d $buildPath @sources
if ($LASTEXITCODE -ne 0) { throw "Compilation failed: $task" }
$runPath = Join-Path $repoRoot ("runs/" + $task)
New-Item -ItemType Directory -Path $runPath -Force | Out-Null
Push-Location $runPath
Remove-Item results.csv -ErrorAction SilentlyContinue
Set-Content output.txt ""
$classPath = $buildPath + ";" + (Join-Path $repoRoot "lib/mysql-connector-j-9.2.0.jar")
& java -Xms128m -Xmx512m -cp $classPath BufferedDesign 2>&1 | Tee-Object -FilePath output.txt -Append
if ($LASTEXITCODE -ne 0) { throw "Execution failed: $task" }
& java -cp $buildPath SummarizeResults
if ($LASTEXITCODE -ne 0) { throw "Summary failed: $task" }
Pop-Location

$task = 'TaskA3'
$buildPath = Join-Path $repoRoot ("build/" + $task)
New-Item -ItemType Directory -Path $buildPath -Force | Out-Null
$sources = @((Get-ChildItem shared,$task -Recurse -Filter *.java).FullName)
& javac -g -encoding UTF-8 -cp "lib/mysql-connector-j-9.2.0.jar" -d $buildPath @sources
if ($LASTEXITCODE -ne 0) { throw "Compilation failed: $task" }
$runPath = Join-Path $repoRoot ("runs/" + $task)
New-Item -ItemType Directory -Path $runPath -Force | Out-Null
Push-Location $runPath
Remove-Item results.csv -ErrorAction SilentlyContinue
Set-Content output.txt ""
$classPath = $buildPath + ";" + (Join-Path $repoRoot "lib/mysql-connector-j-9.2.0.jar")
& java -Xms128m -Xmx512m -cp $classPath StudentDaoDemo 2>&1 | Tee-Object -FilePath output.txt -Append
if ($LASTEXITCODE -ne 0) { throw "Execution failed: $task" }
& java -cp $buildPath SummarizeResults
if ($LASTEXITCODE -ne 0) { throw "Summary failed: $task" }
Pop-Location

$task = 'TaskA4'
$buildPath = Join-Path $repoRoot ("build/" + $task)
New-Item -ItemType Directory -Path $buildPath -Force | Out-Null
$sources = @((Get-ChildItem shared,$task -Recurse -Filter *.java).FullName)
& javac -g -encoding UTF-8 -cp "lib/mysql-connector-j-9.2.0.jar" -d $buildPath @sources
if ($LASTEXITCODE -ne 0) { throw "Compilation failed: $task" }
$runPath = Join-Path $repoRoot ("runs/" + $task)
New-Item -ItemType Directory -Path $runPath -Force | Out-Null
Push-Location $runPath
Remove-Item results.csv -ErrorAction SilentlyContinue
Set-Content output.txt ""
$classPath = $buildPath + ";" + (Join-Path $repoRoot "lib/mysql-connector-j-9.2.0.jar")
& java -Xms128m -Xmx512m -cp $classPath SerializationStudy 2>&1 | Tee-Object -FilePath output.txt -Append
if ($LASTEXITCODE -ne 0) { throw "Execution failed: $task" }
& java -cp $buildPath SummarizeResults
if ($LASTEXITCODE -ne 0) { throw "Summary failed: $task" }
Pop-Location

$task = 'TaskA5'
$buildPath = Join-Path $repoRoot ("build/" + $task)
New-Item -ItemType Directory -Path $buildPath -Force | Out-Null
$sources = @((Get-ChildItem shared,$task -Recurse -Filter *.java).FullName)
& javac -g -encoding UTF-8 -cp "lib/mysql-connector-j-9.2.0.jar" -d $buildPath @sources
if ($LASTEXITCODE -ne 0) { throw "Compilation failed: $task" }
$runPath = Join-Path $repoRoot ("runs/" + $task)
New-Item -ItemType Directory -Path $runPath -Force | Out-Null
Push-Location $runPath
Remove-Item results.csv -ErrorAction SilentlyContinue
Set-Content output.txt ""
$classPath = $buildPath + ";" + (Join-Path $repoRoot "lib/mysql-connector-j-9.2.0.jar")
& java -Xms128m -Xmx512m -cp $classPath TransactionSafety 2>&1 | Tee-Object -FilePath output.txt -Append
if ($LASTEXITCODE -ne 0) { throw "Execution failed: $task" }
& java -cp $buildPath SummarizeResults
if ($LASTEXITCODE -ne 0) { throw "Summary failed: $task" }
Pop-Location

$task = 'TaskB1'
$buildPath = Join-Path $repoRoot ("build/" + $task)
New-Item -ItemType Directory -Path $buildPath -Force | Out-Null
$sources = @((Get-ChildItem shared,$task -Recurse -Filter *.java).FullName)
& javac -g -encoding UTF-8 -cp "lib/mysql-connector-j-9.2.0.jar" -d $buildPath @sources
if ($LASTEXITCODE -ne 0) { throw "Compilation failed: $task" }
$runPath = Join-Path $repoRoot ("runs/" + $task)
New-Item -ItemType Directory -Path $runPath -Force | Out-Null
Push-Location $runPath
Remove-Item results.csv -ErrorAction SilentlyContinue
Set-Content output.txt ""
$classPath = $buildPath + ";" + (Join-Path $repoRoot "lib/mysql-connector-j-9.2.0.jar")
& java -Xms128m -Xmx512m -cp $classPath PassStore 2>&1 | Tee-Object -FilePath output.txt -Append
if ($LASTEXITCODE -ne 0) { throw "Execution failed: $task" }
$classPath = $buildPath + ";" + (Join-Path $repoRoot "lib/mysql-connector-j-9.2.0.jar")
& java -Xms128m -Xmx512m -cp $classPath FileMeasurements 2>&1 | Tee-Object -FilePath output.txt -Append
if ($LASTEXITCODE -ne 0) { throw "Execution failed: $task" }
$classPath = $buildPath + ";" + (Join-Path $repoRoot "lib/mysql-connector-j-9.2.0.jar")
& java -Xms128m -Xmx512m -cp $classPath BusPassValidation 2>&1 | Tee-Object -FilePath output.txt -Append
if ($LASTEXITCODE -ne 0) { throw "Execution failed: $task" }
& java -cp $buildPath SummarizeResults
if ($LASTEXITCODE -ne 0) { throw "Summary failed: $task" }
Pop-Location

$task = 'TaskB2'
$buildPath = Join-Path $repoRoot ("build/" + $task)
New-Item -ItemType Directory -Path $buildPath -Force | Out-Null
$sources = @((Get-ChildItem shared,$task -Recurse -Filter *.java).FullName)
& javac -g -encoding UTF-8 -cp "lib/mysql-connector-j-9.2.0.jar" -d $buildPath @sources
if ($LASTEXITCODE -ne 0) { throw "Compilation failed: $task" }
$runPath = Join-Path $repoRoot ("runs/" + $task)
New-Item -ItemType Directory -Path $runPath -Force | Out-Null
Push-Location $runPath
Remove-Item results.csv -ErrorAction SilentlyContinue
Set-Content output.txt ""
$classPath = $buildPath + ";" + (Join-Path $repoRoot "lib/mysql-connector-j-9.2.0.jar")
& java -Xms128m -Xmx512m -cp $classPath AdversarialFiles 2>&1 | Tee-Object -FilePath output.txt -Append
if ($LASTEXITCODE -ne 0) { throw "Execution failed: $task" }
& java -cp $buildPath SummarizeResults
if ($LASTEXITCODE -ne 0) { throw "Summary failed: $task" }
Pop-Location

$task = 'TaskB3'
$buildPath = Join-Path $repoRoot ("build/" + $task)
New-Item -ItemType Directory -Path $buildPath -Force | Out-Null
$sources = @((Get-ChildItem shared,$task -Recurse -Filter *.java).FullName)
& javac -g -encoding UTF-8 -cp "lib/mysql-connector-j-9.2.0.jar" -d $buildPath @sources
if ($LASTEXITCODE -ne 0) { throw "Compilation failed: $task" }
$runPath = Join-Path $repoRoot ("runs/" + $task)
New-Item -ItemType Directory -Path $runPath -Force | Out-Null
Push-Location $runPath
Remove-Item results.csv -ErrorAction SilentlyContinue
Set-Content output.txt ""
$classPath = $buildPath + ";" + (Join-Path $repoRoot "lib/mysql-connector-j-9.2.0.jar")
& java -Xms128m -Xmx512m -cp $classPath JdbcMeasurements 2>&1 | Tee-Object -FilePath output.txt -Append
if ($LASTEXITCODE -ne 0) { throw "Execution failed: $task" }
$classPath = $buildPath + ";" + (Join-Path $repoRoot "lib/mysql-connector-j-9.2.0.jar")
& java -Xms128m -Xmx512m -cp $classPath SmallDataValidation 2>&1 | Tee-Object -FilePath output.txt -Append
if ($LASTEXITCODE -ne 0) { throw "Execution failed: $task" }
& java -cp $buildPath SummarizeResults
if ($LASTEXITCODE -ne 0) { throw "Summary failed: $task" }
Pop-Location

$task = 'TaskB4'
$buildPath = Join-Path $repoRoot ("build/" + $task)
New-Item -ItemType Directory -Path $buildPath -Force | Out-Null
$sources = @((Get-ChildItem shared,$task -Recurse -Filter *.java).FullName)
& javac -g -encoding UTF-8 -cp "lib/mysql-connector-j-9.2.0.jar" -d $buildPath @sources
if ($LASTEXITCODE -ne 0) { throw "Compilation failed: $task" }
$runPath = Join-Path $repoRoot ("runs/" + $task)
New-Item -ItemType Directory -Path $runPath -Force | Out-Null
Push-Location $runPath
Remove-Item results.csv -ErrorAction SilentlyContinue
Set-Content output.txt ""
$classPath = $buildPath + ";" + (Join-Path $repoRoot "lib/mysql-connector-j-9.2.0.jar")
& java -Xms128m -Xmx512m -cp $classPath Migration 2>&1 | Tee-Object -FilePath output.txt -Append
if ($LASTEXITCODE -ne 0) { throw "Execution failed: $task" }
& java -cp $buildPath SummarizeResults
if ($LASTEXITCODE -ne 0) { throw "Summary failed: $task" }
Pop-Location
