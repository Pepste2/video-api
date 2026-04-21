@echo off
REM Video API Checker - Windows 启动脚本
REM 直接运行 JAR 包（包含所有依赖）

cd /d "%~dp0"

REM 检查 Java 版本
java -version >nul 2>&1
if errorlevel 1 (
    echo 错误: 未找到 Java，请安装 Java 11 或更高版本
    pause
    exit /b 1
)

REM 运行程序
java -jar video-api-checker.jar