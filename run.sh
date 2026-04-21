#!/bin/bash
# Video API Checker 启动脚本
# 支持 Java 11+ 运行

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
MVN_DIR="$SCRIPT_DIR/apache-maven-3.9.6"

if [ ! -d "$MVN_DIR" ]; then
    echo "正在下载 Maven..."
    curl -L -o "$SCRIPT_DIR/mvn.zip" "https://archive.apache.org/dist/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.zip"
    unzip -q "$SCRIPT_DIR/mvn.zip" -d "$SCRIPT_DIR"
fi

echo "正在启动 Video API Checker..."
"$MVN_DIR/bin/mvn" javafx:run