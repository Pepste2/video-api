#!/bin/bash
# Video API Checker - Linux/Mac 启动脚本
# 直接运行 JAR 包（包含所有依赖）

cd "$(dirname "$0")"

# 检查 Java 版本
if ! command -v java &> /dev/null; then
    echo "错误: 未找到 Java，请安装 Java 11 或更高版本"
    exit 1
fi

# 运行程序
java -jar video-api-checker.jar