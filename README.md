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