<img src="https://s1.ax1x.com/2023/03/10/ppu2EAP.png" align="right" alt="Todo Icon" width="200" height="200">

# LazyChat-Server 📨

[LazyChat](https://github.com/Coder-Liuu/LazyChat) 是一款基于 TUI的用户聊天软件，它具有很棒的用户界面！

该项目是LazyChat的服务器端。

# Features 🌟

- 基于Netty的练手项目
- 💩一样的源代码

尽管有很多bug，但是我也不会修复 :)

# Structure

- dao：负责与数据库交互，进行数据的CRUD操作，负责数据的持久化。
- service：负责协调多个DAO进行业务处理。
- handler：负责与业务逻辑相关的代码，如登陆业务、私聊业务、通知业务。
- message：包含用于定义消息结构的类和接口。
- protocol：包含与协议相关的代码，如定义编码和解码消息等。
- session：包含与会话相关的代码，如管理用户的登录状态等。
- ChatServer.java：包含实现聊天服务器功能的主程序，包括创建服务器、监听客户端连接、启动线程等。

# Contribution 🤝

始终对PR开放：）

此外本项目受到了[@kraanzu](https://github.com/kraanzu) [@lazygit](https://github.com/jesseduffield/lazygit)的项目启发，感谢他们这么好的创意！
