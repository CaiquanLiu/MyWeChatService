# 基于Tomcat+Servlet的个人微信公众号服务后台
个人微信公众号“才权的AI小助手”后台服务

## 博客地址：
<https://caiquanliu.github.io/tags/Eddy%E7%9A%84AI%E5%AD%A6%E4%B9%A0%E4%B9%8B%E6%97%85/>

## 实现功能：
* 完成微信个人公众号的服务器地址绑定；
* 完成微信个人公众号的消息解析；
* 支持微信的文本输入；
* 支持微信的语言输入；
* 接入Turing机器人接口；
* 完成Log4j的集成；
* 实现基于HtmlParser的web页面爬取和内容分析；
* 添加基于Ant的Jenkins自动化编译；

## 环境/依赖：
* 运行主机：阿里云ECS；
* 宿主系统：Ubuntu 16.04；
* JDK：1.8；
* Web Server：Tomcat；
* Log组件：Log4j；
* Html页面解析组件：HtmlParser；
* 自动化编译：Jenkins+Ant；

## 测试公众号:
![](./assets/MyAI_Wechat_QRC.PNG)

## 测试方法：

### Turing接口测试
* 文本输入“今天天气怎么样？”

### 语音输入测试
* 语音输入“今天天气怎么样？”

### 自定义指令测试（web页面内容爬取）
* 文本或语音输入“我的简书文章有哪些？”
