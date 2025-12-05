## 一、整体概述

本项目是在ChatGPT辅助下完成的一个 Android 聊天 Demo，包含聊天列表页和聊天界面。  
界面采用 RecyclerView 构建消息列表，数据使用 SharedPreferences + Gson 存储。

---

## 二、代码结构与模块说明
```
app/
└── src/
    └── main/
        ├── AndroidManifest.xml  // Android应用配置清单
        ├── java/
        │   └── com/example/chatappbygpt/
        │       ├── MainActivity.kt       // 聊天列表页（主界面）
        │       ├── ChatActivity.kt       // 聊天对话界面
        │       ├── ChatAdapter.kt        // 消息气泡列表的Adapter（聊天界面用）
        │       ├── ChatListAdapter.kt    // 主界面聊天列表的Adapter
        │       ├── models.kt             // 消息的数据结构定义
        │       └── StorageHelper.kt      // 用SharedPreferences存储消息的工具类
        └── res/  // 资源文件目录
            ├── layout/  // 界面布局文件
            │   ├── activity_main.xml      // 聊天列表页的UI布局
            │   ├── activity_chat.xml      // 聊天对话界面的UI布局
            │   ├── item_chat.xml          // 主界面聊天列表的单项布局
            │   ├── item_message_left.xml  // 左侧消息气泡的布局（对方消息）
            │   └── item_message_right.xml // 右侧消息气泡的布局（自己消息）
            ├── drawable/  // 图形/背景资源
            │   ├── bg_msg_left.xml   // 左侧消息气泡的背景样式
            │   └── bg_msg_right.xml  // 右侧消息气泡的背景样式
            └── menu/  // 菜单资源
                └── menu_chat.xml  // 聊天界面右上角的菜单布局              
```

### 1. Activity 层

| 文件               | 作用                         |
| ---------------- | -------------------------- |
| **MainActivity** | 展示所有聊天对象                   |
| **ChatActivity** | 聊天界面：显示消息、输入框、发送按钮，加载与保存消息 |


### 2. Adapter 层

ChatAdapter和ChatListAdapter，据说是聊天软件都在用的双适配器结构。两个Adapter通过Activity进行连接。

| 文件                  | 作用            |
| ------------------- | ------------- |
| **ChatAdapter**     | 展示聊天列表消息预览    |
| **ChatListAdapter** | 聊天界面消息布局，显示时间 |

### 3. 数据/存储层

| 文件                | 作用                                              |
| ----------------- | ----------------------------------------------- |
| **models.kt**     | 定义 `Chat` 和 `Message` 数据结构。                     |
| **StorageHelper** | 使用 SharedPreferences + Gson 持久化消息列表，每个聊天对象独立存储。 |

### 4. 布局文件

| 文件                     | 作用        |
| ---------------------- | --------- |
| activity_main.xml      | 聊天列表界面    |
| activity_chat.xml      | 聊天界面      |
| item_message_left.xml  | 对方消息气泡    |
| item_message_right.xml | 自己消息气泡    |
| item_chat.xml          | 单个聊天的缩略显示 |

---

## 三、设计思路

### 1. 使用 RecyclerView 作为消息列表

原因：

- 性能高
- 自动复用 item
- 能无限扩展
- 支持自定义 item 布局

消息本质上就是 一行一个 item，重复拼成消息记录
### 2. 消息发送流程设计

1. editText 拿到输入内容
2. 构造 Message
3. 放入 msgList
4. 保存消息（SharedPreferences）
5. RecyclerView 通知更新
6. 自动滚动到底部

### 3. 数据持久化方案

使用 SharedPreferences + Gson 的原因：

- 简单
- 无需数据库
- 小项目足够
