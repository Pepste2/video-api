@echo off
REM Video API Checker 启动脚本 (Windows)
REM 支持 Java 11+ 运行

set SCRIPT_DIR=%~dp0
set MVN_DIR=%SCRIPT_DIR%apache-maven-3.9.6

if not exist "%MVN_DIR%" (
    echo 正在下载 Maven...
    curl -L -o "%SCRIPT_DIR%mvn.zip" "https://archive.apache.org/dist/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.zip"
    unzip -q "%SCRIPT_DIR%mvn.zip" -d "%SCRIPT_DIR%"
)

echo 正在启动 Video API Checker...
call "%MVN_DIR%\bin\mvn.cmd" javafx:run