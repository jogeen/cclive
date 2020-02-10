# cclive
针对直播间上课名单检查的小功能代码
### 使用方式
1. 获取源码git@github.com:jogeen/cclive.git
2. 在/resource/name.txt中添加班级学员名称
3. 在com.jogeen.cclive.NameCheck类的main方法中修改以下参数:
    - useid="XXXX";//用户id
    - roomid="XXXX";//直播间id
    - 注：以上参数包含在 每天晚上20:00 企业微信推送的系统消息中。
4. 运行main方法。  
