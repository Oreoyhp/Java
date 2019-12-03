一、生成jar包

​	执行 `mvn install ` 生成jar包，上传到Linux服务器上

二、启动项目

​	① 切换到jar包所在目录执行 `java -jar xxx.jar` 命令

​		缺点：此方案只要关闭命令窗口，程序运行便结束。推荐使用第二种方案

​	② 使用管道方案实现运行

​		使用vim创建start.sh

​	`vim start.sh` 

​	`java -jar xxx.sh` 

​	执行wq

​	给文件授权 `chmod 777 start.sh` 

​	最后执行命令 `nohup ./start.sh &` 

停止项目：`kill -9 PID` 



netstat -anp | grep 80     : 查看80端口

kill sid                                :关闭sid的端口

cat start.sh                        :查看start.sh 文件的内容



可能出现的错误：

1、eclipse生成jar包报错

​		Maven Install报错：Perhaps you are running on JRE rather than a JDK?

​	原因：eclipse默认在jre上运行，maven install编译需要在jdk中的编译器

​	解决方案：window—>Perferences—>Insttalled JREs—>add;添加jdk路径

2、Linux查看端口号

centos默认没有安装netstat,需要执行 `yum install -y net-tools`  



https://blog.csdn.net/qq_36107346/article/details/86686345