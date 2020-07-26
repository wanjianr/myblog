## 博客问答系统

#### 集成MyBatis
   -  引入依赖
   ```xml
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.1.3</version>
</dependency>
```
- 配置数据库连接池
```properties
# 先引入连接池依赖
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
# 配置文件
spring.datasource.url=jdbc:h2:~/myblog
spring.datasource.username=sa
spring.datasource.password=123
spring.datasource.driver-class-name=org.h2.Driver
```
- 创建mapper包，编写映射类
```java
@Repository
@Mapper
public interface UserMapper {
    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified) values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);
}
```
- 注入mapper,并插入，将token写入cookie中
```text
@Autowired
private UserMapper userMapper;
userMapper.insert(user);
response.addCookie(new Cookie("token",token));
```

#### 集成flywaydb
- 官网: https://flywaydb.org/getstarted/
- 引入依赖
```xml
<plugin>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-maven-plugin</artifactId>
    <version>6.5.1</version>
    <configuration>
        <url>jdbc:h2:~/myblog</url>
        <user>douye</user>
        <password>1041</password>
    </configuration>
    <dependencies>
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <version>1.4.197</version>
        </dependency>
    </dependencies>
</plugin>
```

#### 连接mysql
- 引入依赖
```xml
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.0.1</version>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.45</version>
</dependency>
```
- 配置文件
```properties
spring.datasource.url=jdbc:mysql://39.106.227.33:3306/myblog?useUnicode=true&characterEncoding=UTF-8&useSSL=true
spring.datasource.username=root
spring.datasource.password=Mysql5.7
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
```


#### 完成问题发布功能
- 建表
```mysql
create table question
(
	id int auto_increment,
	title varchar(60) null,
	description text null,
	gmt_create bigint null,
	gmt_modified bigint null,
	creator int null,
	comment_count int default 0 null,
	view_count int default 0 null,
	like_count int default 0 null,
	tag varchar(256) null,
	constraint question_pk
		primary key (id)
);
```
- 新建Question模型（model）

- 编写持久层（mapper）

- 编写控制层（controller）
    - 空值判断
    - 登陆验证
    - 存储数据
    
#### mybatis驼峰命名设置
```properties
mybatis.configuration.map-underscore-to-camel-case=true
```
#### 首页问题显示
- 添加用户图片字段
- 首页问题列表显示
    - dto中创建类QuestionDTO，将user信息关联至Question
    - 创建服务层QuestionService，通过操作持久层将问题内容封装至QuestionDTO
    - 编写控制层逻辑，将从service中拿到的内容加入到model，并返回给页面
    - 编写html页面，解析后端传来的model中的信息
    
#### 分页功能
- 首页的控制层接收页数信息，通过页数信息查询数据
    - 利用`@RequestParam(name = "page", defaultValue = "1") Integer page`获取页面传的页数信息
    - 将参数传至service层，在service层计算页面的偏移量，并传至持久层用于数据查询
    - 编写paginationDTO，用于封装查询到的问题列表和分页信息
    
#### 抽取html中的公共代码块
```html
<!--定义-->
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
  <body>
    <div th:fragment="copy">
      &copy; 2011 The Good Thymes Virtual Grocery
    </div>
  </body>
</html>

<!--调用-->
<body>
  ...
  <div th:insert="~{footer :: copy}"></div>
</body>
```

#### 展示个人页面
- （控制层）首先完成登陆验证，若用户已登陆则获取user对象，否则重定向至首页（通过`HttpServletRequest request`获取cookie信息来验证）
- 验证成功后，根据用户id查询question数据表
    - （服务层）根据前端请求的页数据，计算分页数据、根据页码查询question数据表，并封装成paginationDTO对象，返回给控制层
    - （持久层）根据服务层提供的偏移量及显示条数信息查询数据库，并返回值服务层
    
#### 拦截器（完成登陆验证功能）
- 官方文档1.11.5: `https://docs.spring.io/spring/docs/5.2.8.RELEASE/spring-framework-reference/web.html#mvc-config-interceptors`

#### 修复登录问题，增加登出功能
- 登陆逻辑
    - 查询accountId，如果有则更新user信息，否则插入user
- 登出逻辑
    - 删除request请求中的`user`属性（session中的）
    - 新建一个key为`token`值为`null`的cookie对象
    - 设置最大时间为0
    - 在response对象中添加该cookie
    
#### 问题详情页
- 编辑功能
    - 根据问题id来判断问题是更新还是创建 