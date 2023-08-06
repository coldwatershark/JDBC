# JDBC
JDBC快速入门教程 事务、预编译、driud连接池


下面是学习笔记



# JDBC概念

Java数据库连接，（Java Database Connectivity，简称JDBC）是Java语言中用来规范客户端程序如何来访问数据库的应用程序接口，提供了诸如查询和更新数据库中数据的方法。通俗易懂说：jdbc就是java操作数据库

jdbc的本质就是：java官方提供的一套规范接口，用于帮助程序员开发者操作不同的关系型数据库(mysql/Oracle/SQLServer)

# jdbc快速入门

1.创建一张表

2.在java官方只是提供JDBC规范的接口，如果需要连接到具体的数据库 例如
mysql ，我们就需要导入mysql的依赖jar包，具体实现是有不同的数据库
产商实现的。
1.导入mysql驱动jar包;
2.注册驱动 javase 反射机制Class.forName()
3.获取数据库连接
4.获取执行者对象
5.执行sql语句并获取返回结果
6.对结果进行处理
7.释放jdbc资源

jdbc驱动依赖下载：http://note.youdao.com/noteshare?id=61e2cc939390acc9c7e5017907e98044&sub=DAABBA2F350445D2AC6879CCC3F46EA7

```java
/**

- @author 余胜军
- @ClassName JdbcDemo01
- @qq 644064779
- @addres [www.mayikt.com](http://www.mayikt.com/)
- 微信:yushengjun644
*/
public class JdbcDemo01 {
public static void main(String[] args) throws ClassNotFoundException, SQLException {
// 1.导入jar包
// 2.注册驱动
Class.forName("com.mysql.jdbc.Driver");
// 3.获取执行者
Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mayikt?serverTimezone=UTC",
"root", "root");
// 4.获取执行者对象
Statement statement = connection.createStatement();
// 5.执行sql语句获取返回结果
ResultSet resultSet = statement.executeQuery("select * from mayikt_users");
// 6.对返回结果进行处理
while (resultSet.next()) {
System.out.println(resultSet.getInt("id") + "," + resultSet.getString("name") + "," +
resultSet.getString("pwd"));
}
// 7.释放资源
connection.close();
statement.close();
}
}
```

需要在jdbc连接后面加上该参数jdbc:mysql://127.0.0.1:3306/mayikt?serverTimezone=UTC
在Java中使用JDBC操作数据库，该数据库版本为8.0.15属于高版本(如果是低版本的话，通常是不会出现这些问题的)

### 常见问题

1.Exception in thread "main" java.lang.ClassNotFoundException: com.mysql.jdbc.Driver

原因是没有引入mysql 驱动jar包

2. No suitable driver found for jdbc:mysql//127.0.0.1:3306/mayikt1?serverTimezone=UTC

原因jdbc地址填写是错误的

3. Access denied for user 'root1'@'localhost' (using password: YES)

at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.jav

mysql 连接账户和密码 错误

4. Table 'mayikt.mayikt_users11' doesn't exist 在我们的mayikt数据库

是没有mayikt_users11(数据库中没有该表)

# jdbcAPI详解

DriverManager   驱动程序管理器是负责管理驱动程序的，驱动注册以后，会保存在DriverManager中的已注册列表中后续的处理就可以对这个列表进行操作.

DriverManager   驱动程序管理器是负责管理驱动程序的，驱动注册以后，会保存在DriverManager中的已注册列表中后续的处理就可以对这个列表进行操作.

注册驱动方式

1.DriverManager.registerDriver();

2.写代码实现

Class.*forName*("com.mysql.jdbc.Driver");

3.com.mysql.jdbc.Driver类中存在静态代码快

```java
public class Driver extends NonRegisteringDriver implements java.sql.Driver {
//
// Register ourselves with the DriverManager
//
static {
try {
java.sql.DriverManager.registerDriver(new Driver());
} catch (SQLException E) {
throw new RuntimeException("Can't register driver!");
}
}
```

4.开发者是不需要调用DriverManager.registerDriver();方法，因为我们在使用class.*forName 会加载到我们的*

com.mysql.jdbc.Driver 通过Driver静态代码快 注册我们的Driver驱动。

5.mysql5之后，在jar包中存在一个java.sql.Driver配置文件，文件指定加载com.mysql.cj.jdbc.Driver

通过SPI机制实现。

类加载器、spi 反射技术 javase进阶基础

6.获取连接Connection 连接对象

Connection connection = DriverManager.*getConnection*("数据库连接地址",        "用户名称", "用户密码");

参数：指定连接的路径 语法：jdbc://mysql://ip地址:端口号码/数据库名称

user:用户名称

pwd:用户的密码

ResultSet结果集对象

1.判断结果集是否有数据: boolean next();

1.1.有数据返回true 并将索引向下移动一行

1.2.没有数据返回false

2.获取结果集中的数据：xxx.getxx(列名称)  注意与 数据库数据类型需要对应

# jdbc案例

### 需求讨论与分层架构

使用jdbc技术访问到mysql数据库 对 student学生数据做增删改查操作

1.初始化数据库

2.定义实体类层

数据库中的数据类型需要与db对应  实体类中的 基本数据类型 建议用包装类 默认是为null

分层架构
com.mayikt.entity---实体类----创建实体类与数据库表结构字段一一对应的
com.mayikt.dao----数据库访问层----db打交道
com.mayikt.serivce---业务逻辑层
com.mayikt.controller---控制层
如果在db数据类型是为varchar 对应 string
如果在db数据类型是为int对应 Integer

**相关代码
实体类层**

```java
public class StudentEntity {
/**
* 学生对象
* 在java中定义数据库实体类层
* 不建议使用基本数据类型 使用包装类
*/
/**
* 学生的id
*/
private Long id; // 默认值null
/**
* 学生姓名
*/
private String name;
/**
* 学生年龄
*/
private Integer age;
/**
* 学生的地址
*/
private String address;
public StudentEntity(Long id, String name, Integer age, String address) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.address = address;
}
public StudentEntity( String name, Integer age, String address) {
    this.name = name;
    this.age = age;
    this.address = address;
}

public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public Integer getAge() {
    return age;
}

public void setAge(Integer age) {
    this.age = age;
}

public String getAddress() {
    return address;
}

public void setAddress(String address) {
    this.address = address;
}

@Override
public String toString() {
    return "StudentEntity{" +
        "id=" + id +
        ", name='" + name + '\\'' +
        ", age=" + age +
        ", address='" + address + '\\'' +
        '}';
}
}
```

**Dao层**

见JDBC01

### jdbc工具类封装

1.编写配置文件

在src目录下创建config.properties配置文件

```java
driverClass=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://127.0.0.1:3306/mayikt?serverTimezone=UTC
user=root
password=root
```

2.编写jdbc工具 获取连接 释放连接

### 封装主菜单系统

```java
System.out.println("欢迎来到我们每特教育蚂蚁课堂学生管理系统");
System.out.println("1.查询所有的学生信息");
System.out.println("2.根据学生id查询学生信息");
System.out.println("3.新增学生信息");
System.out.println("4.根据学生id修改学生信息");
System.out.println("5.根据学生id删除学生信息");
System.out.println("请选择对应序号:");
```

in.nextLine();不能放在in.nextInt();代码段后面
他不是跳过你了，而是他已经有内容了，内容就是‘\n’。因为nextInt();接收一个整型字符,不会读取\n，nextline();读入一行文本，会读入"\n"字符
解决办法in.nextInt() 中间(in.nextLine();)  in.nextLine();

# 什么是JDBC SQL注入漏洞

什么是SQL注入攻击：

**就是利用SQL语句的漏洞实现对系统攻击**

底层原理就是 通过传递参数(or 1=1 )拼接的SQL语句 导致其成立可以查询到数据。

登录SQL语句：

select * from mayikt_users where phone='18140668751' and pwd='12';

但是黑客传递参数：

黑客传递的参数 ' or 1='1

select * from mayikt_users where phone='' and pwd='' or 1='1';

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/5b97d841-d078-439d-abcb-21fa58257bca/Untitled.png)

如果我们使用sql语句拼接的情况下 很容易导致 被黑客sql注入

# 如何解决SQL注入漏洞

解决办法：

**使用PreparedStatemnet（预编译执行者对象）**

在sql语句执行之前，将sql语句进行提前编译，明确SQL语句的格式后，传递的参数 就是参数 不会拼接sql语句。

用？占位符赋值的方式 setxxx(参数1，参数2)

xxx: 数据类型

参数1：？的位置从编号1开始

参数2：？的实际参数

```java
String loginSql = "select * from mayikt_users where phone=? and pwd=?;";
statement = connection.prepareStatement(loginSql);
statement.setString(1, userEntity.getPhone());
statement.setString(2, userEntity.getPwd());
```

# jdbc事务管理器

### mysql中的事务

事务是必须满足4个条件（ACID）：原子性（Atomicity，或称不可分割性）、一致性（Consistency）、隔离性（Isolation，又称独立性）、持久性（Durability）。

1.原子性：一个事务（transaction）中的所有操作，要么全部完成，要么全部不完成，不会结束在中间某个环节。事务在执行过程中发生错误，会被回滚（Rollback）到事务开始前的状态，就像这个事务从来没有执行过一样。

2.一致性：在事务开始之前和事务结束以后，数据库的完整性没有被破坏。这表示写入的数据必须完全符合所有的预设规则，这包含数据的精确度、串联性以及后续数据库可以自发性地完成预定的工作。

3.隔离性：数据库允许多个并发事务同时对其数据进行读写和修改的能力，隔离性可以防止多个事务并发执行时由于交叉执行而导致数据的不一致。事务隔离分为不同级别，包括读未提交（Read uncommitted）、读提交（read committed）、可重复读（repeatable read）和串行化（Serializable）。

4.持久性：事务处理结束后，对数据的修改就是永久的，即便系统故障也不会丢失。

开启事务-----
{
sql写的操作
update、insert、delete
}
提交事务或者回滚事务
提交事务----事务里面做的写操作 可以查询到 写完之后的数据
回滚事务----事务里面做的写操作  直接回滚了 反悔了  查询不到

如果开启了事务，但是没有提交或者回滚事务  我们是查询不到未提交的数据。
回滚事务----查询不到数据

1.直接用 SET 来改变 MySQL 的自动提交模式:
SET AUTOCOMMIT=0 禁止自动提交
SET AUTOCOMMIT=1 开启自动提交
2.用 BEGIN, ROLLBACK, COMMIT来实现
BEGIN 开始一个事务
ROLLBACK 事务回滚
COMMIT 事务确认
演示：

### 事务如果不提交、不回滚，会发生哪些问题？

我们在使用手动提交事务，如果不提交数据也不回滚数据 容易引发行锁问题 导致该行数据一直被锁住，无法被其他线程修改。

select * from information_schema.innodb_trx

kill 17; 手动释放行锁
kill 19;手动释放行锁

在mysql InnoDB存储引擎中 多个线程如果同时修改同一行数据 最终只会有一个线程修改成功。
InnoDB存储引擎---行锁

### jdbc中的事务管理器

同一事务中所有的操作，都在使用同一个Connection对象。

①JDBC中的事务

Connection的三个方法与事务有关：

setAutoCommit（boolean）:设置是否为自动提交事务，如果true（默认值为true）表示自动提交，也就是每条执行的SQL语句都是一个单独的事务，如果设置为false，那么相当于开启了事务了；con.setAutoCommit(false) 表示开启事务。

commit（）：提交结束事务。

rollback（）：回滚结束事务。

jdbc整合mysql中事务呢？手动事务呢？

事务呢？

**增加、删除、修改数据---写操作 加上事务**

查询操作---不需要加上事务的？ — 不需要

开启事务

提交事务

回滚事务

# MySQL多表联合查询操作

# jdbc数据库连接池

### 什么是数据库连接池

1.我们在JDBC编程中，**每次创建和断开Connection对象都会消耗一定的时间和IO资源**，如果需要频繁的与数据库打交道，该过程效率非常低。因为在Java程序与数据库之间建立连接时，数据库端要验证用户名和密码，并且要为这个连接分配资源,Java程序则要把代表连接的java.sql.Connection对象等加载到内存中,所以建立数据库连接的开销很大。

2.为了避免频繁的创建数据库连接，与时我们可以通过**数据库连接池负责分配、管理和释放数据库连接**,它允许应用程序**重复使用现有的数据库连接**，而不是重新建立。

3.数据库连接池大致实现原理：

数据库连接池在初始化时将创建一定数量的数据库连接放到连接池中，当应用程序访问数据库时并不是直接创建Connection,而是向连接池“**申请”一个Connection**。如果连接池中有空闲的Connection,则将其返回，否则创建新的Connection。使用完毕后,连接池会将该Connection回收，并交付其他的线程复用使用，以减少创建和断开数据库连接的次数，提高数据库的访问效率。

### 整合c3p0数据库连接池

### 快速入门

1.导入依赖Jar包

c3p0-0.9.5.2

mchange-commons-java-0.2.12.jar

mysql-connector-java-8.0.13.jar

jar包相关下载：

链接：http://note.youdao.com/noteshare?id=61e2cc939390acc9c7e5017907e98044&sub=DAABBA2F350445D2AC6879CCC3F46EA7

```java
ComboPooledDataSource pool = new ComboPooledDataSource();// 创建c3p0数据库连接池
pool.setUser("root");// 用户名称
pool.setPassword("root");// 用户密码
pool.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/mayikt?zeroDateTimeBehavior=convertToNull&serverTimezone=Asia/Shanghai");// MySQL数据库连接url
pool.setDriverClass("com.mysql.jdbc.Driver"); // 加载驱动
```

### 改成配置文件形式

1.在src目录创建c3p0.properties 或者c3p0-config.xml

2.特别的名称要求，程序会自动去找寻这个配置文件

```xml
<c3p0-config>
<!-- 使用默认的配置读取连接池对象 -->
<default-config>
<!--  连接参数 -->
<!--需要修改自己数据库路径、用户账号、密码-->
<property name="driverClass">com.mysql.jdbc.Driver</property>
<property name="jdbcUrl">jdbc:mysql://127.0.0.1:3306/mayikt?serverTimezone=UTC</property>
<property name="user">root</property>
<property name="password">root</property>
</default-config>
</c3p0-config>
```

自动就可以找到c3p0-config.xml
我们也可以在c3p0-config.xml  配置多个jdbc数据库连接信息
代码指定使用数据库连接配置信息
ComboPooledDataSource pool = new ComboPooledDataSource("mayikt-otherc3p0");
不指定就使用默认的。

### 核心配置文件说明

1.连接池核心参数

```xml
<!--初始化申请的连接数量-->
<property name="initialPoolSize">5</property>
<!--最大的连接数量-->
<property name="maxPoolSize">10</property>
<!--超时时间(单位毫秒)-->
<property name="checkoutTimeout">3000</property>
```

2.基本原理是在内部对象池中维护一定数量的数据库连接，并对外暴露数据库连接获取和返回方法。用户可通过getConnection()方法获取连接，使用完毕后再通过Connection.close()方法将连接返回，注意此时连接并没有关闭，而是由连接池管理器回收，并为下一次使用做好准备。
数据库连接池的好处：

1. 节约资源
2. 用户访问高效

### 整合druid数据库连接池

Druid（德鲁伊）：数据库连接池实现技术，由阿里巴巴提供的， 与C3P0数据库连接池 底层实现原理一样。

1.需要导入druid-1.2.8.jar 依赖jar包

依赖jar包下载：http://note.youdao.com/noteshare?id=61e2cc939390acc9c7e5017907e98044&sub=DAABBA2F350445D2AC6879CCC3F46EA7

2.定义配置文件

是properties形式的

可以叫任意名称，可以放在任意目录下。程序不会自动寻找，因此在使用时需要手动加载相应的配置文件。

druid.properties

3.加载配置文件。使用Properties集合
4.获取数据库连接池对象：通过工厂来获取 DruidDataSourceFactory
5.获取连接：getConnection()
