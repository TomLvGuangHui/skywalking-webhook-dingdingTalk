# skywalking-webhook-dingdingTalk
创建dingding机器人地址请参看
http://ju.outofmemory.cn/entry/325910

下载该github上的java代码
修改/src/main/resources/application.properties文件中 com.dudu.dingtalkURL的值为 dingding机器人地址，注意带上token的值

进入pom.xml所在目录  
运行 mvn clean package 
之后 在target目录中，可以看到一个jar包，，java -jar 运行即可

和skywalking结合的时候，修改
 webhooks 的地址为 - http://YOU IP 地址:8080/add/
 即可
