# Video API Checker - 海康/萤石 API Key 泄露利用验证工具

## 简介

这是一个用于验证海康威视（HikVision ISC平台）和萤石（YingShi）API Key泄露的工具。通过该工具可以快速检测泄露的 API Key 是否有效，并获取相关视频资源信息。

⚠️ **声明：本工具仅供安全研究和授权测试使用，请勿用于非法用途。**

## 功能特性

- **海康威视 ISC 平台检测**
  - 验证 Artemis API Key 有效性
  - 搜索摄像头资源列表
  - 获取摄像头预览地址
  - 支持 V1/V2 版本 API

- **萤石云平台检测**
  - 验证 appKey/appSecret 有效性
  - 获取 accessToken
  - 搜索视频资源列表

- **响应式 UI**
  - 窗口大小可自由调整
  - 结果区域自动扩展
  - 支持文本自动换行

## 二开版本说明

本版本为二次开发版本，主要适配高版本 Java：

| 改动 | 说明 |
|------|------|
| Java 版本 | 从 Java 8 升级至 Java 11+ |
| JavaFX | 使用 OpenJFX 17.0.8（独立依赖） |
| 项目结构 | 从 JAR 包重构为 Maven 项目 |
| HTTP 客户端 | 更新废弃 API 为现代实现 |
| 响应式布局 | 实现 UI 自动跟随窗口缩放 |

## 环境要求

- Java 11 或更高版本（推荐 Java 17/21）
- Maven 3.6+（可选，已内置）

## 快速开始

### 方式一：使用启动脚本

```bash
# Linux/Mac
./run.sh

# Windows
run.bat
```

### 方式二：使用 Maven

```bash
# 编译
mvn compile

# 运行
mvn javafx:run

# 打包
mvn package
```

## 使用方法

1. **海康威视检测**
   - 输入 ArtemisConfig.host（如 `218.xxx.xxx.xxx:443`）
   - 输入 appKey 和 appSecret
   - 点击「检测」验证 API 有效性
   - 若有效，可点击「搜索」获取摄像头列表
   - 输入 cameraIndexCode 获取预览地址

2. **萤石检测**
   - 输入 appKey 和 appSecret
   - 点击「检测」获取 accessToken
   - 使用 accessToken 进行后续操作

## API 接口说明

### 海康威视 API

| API | 路径 | 说明 |
|-----|------|------|
| 区域查询 | `/api/resource/v1/regions/root` | 验证 API 有效性 |
| 摄像头搜索 V1 | `/api/resource/v1/cameras` | ISC < 1.4 版本 |
| 摄像头搜索 V2 | `/api/resource/v2/camera/search` | ISC >= 1.4 版本 |
| 预览地址 V1 | `/api/video/v1/cameras/previewURLs` | 获取 RTSP 地址 |
| 预览地址 V2 | `/api/video/v2/cameras/previewURLs` | 新版预览接口 |

### 萤石 API

| API | 路径 | 说明 |
|-----|------|------|
| 获取 Token | `/api/lapp/token/get` | 验证并获取 accessToken |
| 视频列表 | `/api/lapp/live/video/list` | 获取设备列表 |

## 项目结构

```
video-api/
├── pom.xml              # Maven 配置
├── README.md            # 项目说明
├── run.sh / run.bat     # 启动脚本
├── src/main/java/
│   ├── Main.java        # 主入口
│   ├── controller/      # UI 控制器
│   ├── plugins/         # HikVision/YingShi 插件
│   ├── utils/           # 工具类
│   └── com/hikvision/   # 海康 SDK
└── src/main/resources/
    ├── main.fxml        # UI 布局
    ├── style.css        # 样式
    └── logo.jpg         # 图标
```

## 原始版本

原始版本基于 Java 8 编写，由于 OpenJDK 8 不包含 JavaFX 运行时，导致无法直接运行。本二开版本解决了以下问题：

- JavaFX 从 JDK 11+ 移除后的依赖问题
- HttpClient 废弃 API 的兼容性问题
- UI 响应式布局缺失问题

## License

本项目仅供学习和授权安全测试使用。

## 作者

- 原版：zp857@玄甲实验室
- 二开版本：适配高版本 Java