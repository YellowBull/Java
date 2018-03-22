# Spring 基础知识点
## Spring 框架的作用
* 轻量：Spring是轻量级的，基本版本大小2M
* [控制反转(IoC)](http://blog.csdn.net/u012561176/article/details/45974315)：Spring通过控制反转实现了松耦合，对象自身给出他们的依赖关系，而不是创建或查找依赖的对象们。
* [面向切面编程(AOP)](https://www.cnblogs.com/kangsir/p/6653245.html)：Spring支持面向切面编程，把业务逻辑层与系统服务分开。
* 容器：Spring包含并管理了应用程序中的对象的生命周期和配置。
* MVC框架：springMVC
* [事务管理](http://www.mamicode.com/info-detail-1248286.html)：spring提供一个持续的事务管理接口，可以拓展上至本地的事务，下至全局事务JTA。
* 异常处理：Spring提供方便的技术相关的异常API。
## Spring 的组成
![](http://img.blog.csdn.net/20160512174600469)<br/>
Spring 由七个模块组成
* Spring core：核心容易提供Spring框架的基本功能。核心容器的主要组建是BeanFactory，他是由工厂模式实现。BeanFactory是用控制反转模式将应用程序的配置
和依赖性规范与实际应用程序代码分离。
* Spring Context：Spring Context是一个配置文件，向spring提供上下文信息。Spring Context包括应用程序context，UI支持，
企业服务，例如 JNDI、EJB、电子邮件、国际化、校验和调度功能。
* Spring AOP：通过配置管理特性，Spring AOP 模块直接将面向切面的编程功能集成到了 Spring 框架中。所以，可以很容易地使 Spring 框架管理的任何对象支持 AOP。Spring AOP 模块为基于 Spring 的应用程序中的对象提供了事务管理服务。通过使用 Spring AOP，不用依赖 EJB 组件，就可以将声明性事务管理集成到应用程序中。
* Spring DAO：JDBC DAO 抽象层提供了有意义的异常层次结构，可用该结构来管理异常处理和不同数据库供应商抛出的错误消息。异常层次结构简化了错误处理，并且极大地降低了需要编写的异常代码数量（例如打开和关闭连接）。Spring DAO 的面向 JDBC 的异常遵从通用的 DAO 异常层次结构。
* Spring ORM：Spring 框架插入了若干个 ORM 框架，从而提供了 ORM 的对象关系工具，其中包括 JDO、Hibernate 和 iBatis SQL Map。所有这些都遵从 Spring 的通用事务和 DAO 异常层次结构。
* Spring Web 模块：Web 上下文模块建立在应用程序上下文模块之上，为基于 Web 的应用程序提供了上下文。所以，Spring 框架支持与 Jakarta Struts 的集成。Web 模块还简化了处理多部分请求以及将请求参数绑定到域对象的工作。
* Spring MVC 框架：MVC 框架是一个全功能的构建 Web 应用程序的 MVC 实现。通过策略接口，MVC 框架变成为高度可配置的，MVC 容纳了大量视图技术，其中包括 JSP、Velocity、Tiles、iText 和 POI。
## Spring 容器
Sping的容器可以分为两种类型
1. BeanFactory：（org.springframework.beans.factory.BeanFactory接口定义）是最简答的容器，提供了基本的DI支持。最常用的BeanFactory实现就是XmlBeanFactory类，它根据XML文件中的定义加载beans，该容器从XML文件读取配置元数据并用它去创建一个完全配置的系统或应用。<br/>
2. ApplicationContext应用上下文：（org.springframework.context.ApplicationContext）基于BeanFactory之上构建，并提供面向应用的服务。<br/>
## ApplicationContext通常的实现
ClassPathXmlApplicationContext：从类路径下的XML配置文件中加载上下文定义，把应用上下文定义文件当做类资源。<br/>
FileSystemXmlApplicationContext：读取文件系统下的XML配置文件并加载上下文定义。<br/>
XmlWebApplicationContext：读取Web应用下的XML配置文件并装载上下文定义。<br/>
```Java
ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
```
## IOC & DI
Inversion of Control， 一般分为两种类型：依赖注入DI(Dependency Injection)和依赖查找（Dependency Lookup）.依赖注入应用比较广泛。<br/>
Spring IOC负责创建对象，管理对象（DI），装配对象，配置对象，并且管理这些对象的整个生命周期。<br/>
优点：把应用的代码量降到最低。容器测试，最小的代价和最小的侵入性使松散耦合得以实现。IOC容器支持加载服务时的饿汉式初始化和懒加载。<br/>
DI依赖注入是IOC的一个方面，是个通常的概念，它有多种解释。这概念是说你不用创建对象，而只需要描述它如何被创建。你不在代码里直接组装你的组件和服务，但是要在配置文件里描述组件需要哪些服务，之后一个IOC容器辅助把他们组装起来。<br/>
IOC的注入方式：1. 构造器依赖注入；2. Setter方法注入。<br/>
## 如何给spring容器提供配置元数据
XML配置文件<br/>
基于注解的配置<br/>
基于Java的配置@Configuration, @Bean<br/>
## bean标签中的属性：
id<br/>
name<br/>
class<br/>
init-method：Bean实例化后会立刻调用的方法<br/>
destory-method:Bean从容器移除和销毁前，会调用的方法<br/>
factory-method:运行我们调用一个指定的静态方法，从而代替构造方法来创建一个类的实例。<br/>
scope：Bean的作用域，包括singleton(默认)，prototype(每次调用都创建一个实例), request,session, global-session（注意spring中的单例bean不是线程安全的）<br/>
autowired:自动装配 byName, byType, constructor, autodetect(首先阐释使用constructor自动装配，如果没有发现与构造器相匹配的Bean时，Spring将尝试使用byType自动装配)<br/>
## beans标签中相关属性
default-init-method<br/>
default-destory-method<br/>
default-autowire：默认为none，应用于Spring配置文件中的所有Bean，注意这里不是指Spring应用上下文，因为你可以定义多个配置文件<br/>
## Bean的生命周期
Bean的构造<br/>
调用setXXX()方法设置Bean的属性<br/>
调用BeanNameAware的setBeanName()<br/>
调用BeanFactoryAware的setBeanFactory()方法<br/>
调用BeanPostProcessor的postProcessBeforeInitialization()方法<br/>
调用InitializingBean的afterPropertiesSet()方法<br/>
调用自定义的初始化方法<br/>
调用BeanPostProcessor类的postProcessAfterInitialization()方法<br/>
调用DisposableBean的destroy()方法<br/>
调用自定义的销毁方法<br/>
## Spring中注入集合
允许值相同<br/>
不允许值相同<br/>
键和值都可以为任意类型，key, key-ref, value-ref, value可以任意搭配<br/>
XXX键和值都只能是String类型<br/>
## 装配空值
```XML
<property name="xxx"><null/></property>
```
## 自动装配(autowiring)
有助于减少甚至消除配置和元素，让Spring自动识别如何装配Bean的依赖关系。<context:annotation-config/>与之对应的是：自动检测(autodiscovery)，比自动装配更近了一步，让Spring能够自动识别哪些类需要被配置成SpringBean，从而减少对元素的使用。<context:component-scan> <br/>
## 注解
Spring容器默认禁用注解装配。最简单的开启方式<context:annotation-config/>。Spring支持的几种不同的用于自动装配的注解：<br/>
Spring自带的@Autowired注解<br/>
JSR-330的@Inject注解<br/>
JSR-250的@Resource注解<br/>
## @Autowired
@Autowired具有强契约特征，其所标注的属性或参数必须是可装配的。如果没有Bean可以装配到@Autowired所标注的属性或参数中，自动装配就会失败，抛出NoSuchBeanDefinitionException.属性不一定非要装配，null值也是可以接受的。在这种场景下可以通过设置@Autowired的required属性为false来配置自动装配是可选的，如：<br/>
```Java
@Autowired(required=false)
private Object obj;
```
注意required属性可以用于@Autowired注解所使用的任意地方。但是当使用构造器装配时，只有一个构造器可以将@Autowired的required属性设置为true。其他使用@Autowired注解所标注的[构造器](https://www.cnblogs.com/grl214/p/5895854.html)只能将required属性设置为false。此外，当使用@Autowired标注多个构造器时，Spring就会从所有满足装配条件的构造器中选择入参最多的那个构造器。可以使用@Qualifier明确指定要装配的Bean.如下：<br/>
```Java
@Autowired@Qualifier("objName")
private Object obj;
```
## 自定义的限定器
@Target({ElementType.FIELF, ElementType.PARAMETER, ElementType.TYPE})<br/>
@Retention(RetentionPolicy.RUNTIME)<br/>
@Qualifierpublic @Interface SpecialQualifier{}<br/>
此时，可以通过自定义的@SpecialQualifier注解来代替@Qualifier来标注，也可以和@Autowired一起使用：<br/>
@Autowired@SpecialQualifierprivate Object obj;<br/>
此时,Spring会把自动装配的范围缩小到被@SpecialQualifier标注的Bean中。如果被@SpecialQualifier标注的Bean有多个，我们还可以通过自定义的另一个限定器@SpecialQualifier2来进一步缩小范围。<br/>
## @Autowired优缺点
Spring的@Autowired注解是减少Spring XML配置的一种方式。但是它的类会映入对Spring的特定依赖（即使依赖只是一个注解）。<br/>
## @Inject
和@Autowired注解一样，@Inject可以用来自动装配属性、方法和构造器；与@Autowired不同的是，@Inject没有required属性。因此@Inject注解所标注的依赖关系必须存在，如果不存在，则会抛出异常。<br/>
## @Named
相对于@Autowired对应的Qualifier，@Inject所对应的是@Named注解。<br/>
```Java
@Inject@Named("objName")
private Object obj;
```
## SpEL表达式
语法形式在#{}中使用表达式,如：<br/>
```XML
<property name="count" value="#{5}"/>
```
## @Value
@Value是一个新的装配注解，可以让我们使用注解装配String类型的值和基本类型的值，如int, boolean。我们可以通过@Value直接标注某个属性，方法或者方法参数，并传入一个String类型的表达式来装配属性，如：<br/>
```Java
@Value("Eruption")
private String song;
```
@Value可以配合SpEL表达式一起使用，譬如有些情况下需要读取properties文件中的内容，可以使用：<br/>
```Java
@Value("#{configProperties['ora_driver']}")
```
## 自动检测Bean
<context:component-scan>元素除了完成与<context:annotation-config>一样的工作，还允许Spring自动检测Bean和定义Bean.<context:component-scan>元素会扫描指定的包和其所有子包，如下：<br/>
```XML
<context:component-scan base-package="com.zzh.dao" />
```
## 为自动检测标注Bean
默认情况下，查找使用构造型（stereotype）注解所标注的类，这些特殊的注解如下：<br/>
- @Component：通用的构造型注解，标志此类为Spring组件<br/>
- @Controller：标识将该类定义为SpringMVC controller<br/>
- @Repository：标识将该类定义为数据仓库<br/>
- @Service：标识将该类定义为服务<br/>
以@Component为例：<br/>
```Java
@Componentpublic class Guitar implements Intrument{}
```
这里@Component会自动注册Guitar 为Spring Bean，并设置默认的Bean的Id为guitar，首字母大写变小写。注意如果第一个和第二个字母都是大写，默认的Bean的id会有特殊处理。也可以指定Bean的Id如：<br/>
```Java
@Component("guitarOne")
public class Guitar implements Intrument{}
```
## AOP
面向切面的编程AOP，是一种编程技术，允许程序模块化横向切割关注点，或横切典型的责任划分，如日志和事务管理。<br/>
AOP的核心是切面，它将多个类的通用行为封装成可重用的模块，该模块含有一组API提供横切功能。比如，一个日志模块可以被称作日志的AOP切面。根据需求的不同，一个应用程序可以有若干切面。在SpringAOP中，切面通过带有@Aspect注解的类实现。<br/>
关注点是应用中的一个模块的行为，一个关注点可能会被定义成一个我们想实现的一个功能。<br/>
横切关注点一个关注点，此关注点是整个应用都会使用的功能，并影响整个应用，比如日志，安全和数据传输，几乎应用的每个模块都需要的功能。因此这些都属于横切关注点。<br/>
连接点代表一个应用程序的某个位置，在这个位置我们可以插入一个AOP切面，它实际上是个应用程序执行Spring AOP的位置。<br/>
切点是一个或一组连接点，通知将在这些位置执行。可以通过表达式或匹配的方式指明切入点。<br/>
引入运行我们在已存在的类中添加新的方法和属性。<br/>
## AOP通知
通知是个在方法执行前后要做的动作，实际上是程序执行时要通过SpringAOP框架触发的代码<br/>
Spring切面可以应用五种类型的通知：<br/>
before：前置通知，在一个方法执行前被调用。@Before<br/>
after: 在方法执行之后调用的通知，无论方法执行是否成功。@After<br/>
after-returning: 仅当方法成功完成后执行的通知。@AfterReturning<br/>
after-throwing: 在方法抛出异常退出时执行的通知。@AfterThrowing<br/>
around: 在方法执行之前和之后调用的通知。@Around<br/>
## Spring的事务类型
编程式事务管理：这意味你通过编程的方式管理事务，给你带来极大的灵活性，但是难维护。声明式事务管理：这意味着你可以将业务代码和事务管理分离，你只需用注解和XML配置来管理事务。<br/>
## ACID
Atomic原子性：事务是由一个或多个活动所组成的一个工作单元。原子性确保事务中的所有操作全部发生或者全部不发生。<br/>
Consistent一致性：一旦事务完成，系统必须确保它所建模的业务处于一致的状态<br/>
Isolated隔离线：事务允许多个用户对象头的数据进行操作，每个用户的操作不会与其他用户纠缠在一起。<br/>
Durable持久性：一旦事务完成，事务的结果应该持久化，这样就能从任何的系统崩溃中恢复过来。<br/>
## JDBC事务
如果在应用程序中直接使用JDBC来进行持久化，譬如博主采用的是Mybatis，DataSourceTransactionManager会为你处理事务边界。譬如
```XML
<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource"destroy-method="close">
  <property name="driverClassName" value="${driver}" />
  <property name="url" value="${url}" />
  <property name="username" value="zzh" />
  <property name="password" value="zzh" />
  <property name="validationQuery" value="SELECT 1"/>
</bean>
<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
  <property name="dataSource" ref="dataSource"/>
</bean>
```
## JTA事务
如果你的事务需要跨多个事务资源（例如：两个或多个数据库；或者如Sping+ActiveMQ整合 需要将ActiveMQ和数据库的事务整合起来），就需要使用JtaTransactionManager:
```XML
<bean id="jtaTransactionManager"class="org.springframework.transaction.jta.JtaTransactionManager"/>
```
JtaTransactionManager将事务管理的职责委托给了一个JTA的实现。JTA规定了应用程序与一个或多个数据源之间协调事务的标准API。transactionManagerName属性指明了要在JNDI上查找的JTA事务管理器。JtaTransactionManager将事务管理的职责委托给javax.transaction.UserTransaction和javax.transaction.TransactionManager对象。通过UserTransaction.commit()方法来提交事务。类似地，如果事务失败，UserTransaction的rollback()方法将会被调用。<br/>
## 声明式事务
尽管Spring提供了多种声明式事务的机制，但是所有的方式都依赖这五个参数来控制如何管理事务策略。因此，如果要在Spring中声明事务策略，就要理解这些参数。(@Transactional)<br/>
1. 传播行为(propagation)<br/>
ISOLATION_DEFAULT: 使用底层数据库预设的隔离层级<br/>
ISOLATION_READ_COMMITTED: 允许事务读取其他并行的事务已经送出（Commit）的数据字段，可以防止Dirty read问题<br/>
ISOLATION_READ_UNCOMMITTED: 允许事务读取其他并行的事务还没送出的数据，会发生Dirty、Nonrepeatable、Phantom read等问题<br/>
ISOLATION_REPEATABLE_READ: 要求多次读取的数据必须相同，除非事务本身更新数据，可防止Dirty、Nonrepeatable read问题<br/>
ISOLATION_SERIALIZABLE: 完整的隔离层级，可防止Dirty、Nonrepeatable、Phantom read等问题，会锁定对应的数据表格，因而有效率问题<br/>
2. 隔离级别(isolation)<br/>
PROPAGATION_REQUIRED–支持当前事务，如果当前没有事务，就新建一个事务。这是最常见的选择。<br/>
PROPAGATION_SUPPORTS–支持当前事务，如果当前没有事务，就以非事务方式执行。<br/>
PROPAGATION_MANDATORY–支持当前事务，如果当前没有事务，就抛出异常。<br/>
PROPAGATION_REQUIRES_NEW–新建事务，如果当前存在事务，把当前事务挂起。<br/>
PROPAGATION_NOT_SUPPORTED–以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。<br/>
PROPAGATION_NEVER–以非事务方式执行，如果当前存在事务，则抛出异常。<br/>
PROPAGATION_NESTED–如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则进行与PROPAGATION_REQUIRED类似的操作。<br/>
3. 只读(read-only)<br/>
如果事务只进行读取的动作，则可以利用底层数据库在只读操作时发生的一些最佳化动作，由于这个动作利用到数据库在只读的事务操作最佳化，因而必须在事务中才有效，也就是说要搭配传播行为PROPAGATION_REQUIRED、PROPAGATION_REQUIRES_NEW、PROPAGATION_NESTED来设置。<br/>
4. 事务超时(timeout)<br/>
有的事务操作可能延续很长一段的时间，事务本身可能关联到数据表的锁定，因而长时间的事务操作会有效率上的问题，对于过长的事务操作，考虑Roll back事务并要求重新操作，而不是无限时的等待事务完成。 可以设置事务超时期间，计时是从事务开始时，所以这个设置必须搭配传播行为PROPAGATION_REQUIRED、PROPAGATION_REQUIRES_NEW、PROPAGATION_NESTED来设置。<br/>
5. 回滚规则(rollback-for, no-rollback-for)：rollback-for指事务对于那些检查型异常应当回滚而不提交；no-rollback-for指事务对于那些异常应当继续运行而不回滚。默认情况下，Spring声明事务对所有的运行时异常都进行回滚。<br/>
```XML
<tx:advice id="txAdvice" transaction-manager="transactionManager"><tx:attributes><tx:method name="*" /></tx:attributes></tx:advice>
```
