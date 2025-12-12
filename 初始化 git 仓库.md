用户中心笔记

## 初始化 git 仓库

1. 在 github 上新建一个仓库

   ![image-20251211194259435](assets/image-20251211194259435.png)



2. 本地远程仓库关联

   ![](assets/image-20251211194350297.png)

这里注意，使用 ssh 的方式， 如果不知道 ssh 是啥的同学可以搜索查看





任何一个系统，都有用户系统，学习完当前项目，那么其余的管理系统项目都是一样的，**crud**



## 企业做项目流程



需求分析 =》 设计（概要设计、详细设计）=》 技术选型 =》初始化/引入需要的技术 =》写demo =》 写代码（实现业务逻辑） =》测试（单元测试，测试之后再上线） =》代码提交 =》代码评审  =》 部署 =》 发布



## 需求分析

不同的系统，可以公用一套用户系统

1. 用户注册
2. 用户管理（仅管理员可见） 
3. 用户校验



## 技术选型



前端： 三件套 vue + ant design

后端： java + spring + springMvc + mybatis-plus + springboot + mysql

部署： 服务器docker



## 后端初始化

使用 idea 的 springboot init 项目初始化工具

![image-20251211200027883](assets/image-20251211200027883.png)



修改配置文件  application.yml

```yml
server:
  port: 8080
# 数据库配置
spring:
  datasource:
    # 数据库连接 URL
    # 请将 your_database_name 替换为你的数据库名
    # serverTimezone=Asia/Shanghai 是为了避免时区问题
    url: jdbc:mysql://localhost:3306/user_center
    # 数据库用户名 (请替换)
    username: root
    # 数据库密码 (请替换)
    password: 123456
    # 数据库驱动类 (推荐使用 MySQL 8.x 版本的驱动)
    driver-class-name: com.mysql.cj.jdbc.Driver
```



配置好数据库



## 引入 mybatis-plus

加入当前依赖的坐标

```xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.5.15</version>
</dependency>
```

去除掉mybatis的依赖坐标，重复了

```xml
<!--        <dependency>-->
<!--            <groupId>org.mybatis.spring.boot</groupId>-->
<!--            <artifactId>mybatis-spring-boot-starter</artifactId>-->
<!--            <version>2.2.2</version>-->
<!--        </dependency>-->
```

