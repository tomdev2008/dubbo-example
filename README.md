# 项目结构

    采用IntelliJ IDEA作为ide，多模块开发，分为三个模块：
    
## fansz-members-api

    API接口、参数和返回结果模型定义；
     
## fansz-members-provider
 会员服务实现，采用**dubbo + spring + spring data rest + mybatis**技术实现,采用**maven**作为代码构建工具；
 
1. 项目相关的环境配置信息放在**src/main/resources/conf**目录下，请开发人员根据  
    开发环境进行修改；
2. dubbo服务注册服务器采用zookeeper,另外项目运行需要依赖**redis****mysql**;
  
3. 打包命令:
    目前发布包的结构如下:
    * bin:启动和关闭shell脚本；
    * conf:环境相关的信息
    * logs:运行日志；
    * lib:相关相关的jar包；
 
  通过mvn package,产生打包文件；
     
    
4. mybatis插件：
     项目采用mybatis访问数据库，当数据库表建立好之后，可以通过差距生成相关的数据库表映射对象和dao;具体的请参见pom.xml和mybatis-config.xml;
     mvn mybatis-generator:generate;   

## fansz-members-consumber

   客户端demo,通过dubbo访问api服务.
