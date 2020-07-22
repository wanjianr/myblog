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