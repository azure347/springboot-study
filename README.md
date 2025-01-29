## SpringBoot学习

### SpringBoot 自动配置原理

***简单的说，自动配置就是会根据在类路径中的jar、类自动配置Bean。Spring Boot将所有的功能场景都抽取出来，做成一个个的starter（启动器），只需要在项目里面引入这些starter，相关场景的所有依赖都会导入进来。***

- **自动配置就是基于三个重要的注解实现的（实际就是 @SpringBootApplication 注解）**

```java
// @SpringBootConfiguration：我们点进去以后可以发现底层是Configuration注解，其实就是支持JavaConfig的方式来进行配置(使用Configuration配置类等同于XML文件)
// @EnableAutoConfiguration：这个注解用来开启自动配置，是自动配置实现的核心注解
// @ComponentScan：这个注解，学过Spring的同学应该对它不会陌生，就是扫描注解，默认是扫描当前类下的package。将@Controller/@Service/@Component/@Repository等注解加载到IOC容器中
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan
public @interface SpringBootApplication {
}
```

- **@EnableAutoConfiguration源码**

```java
// @AutoConfigurationPackage：自动配置包
// @Import：给IOC容器导入组件
@AutoConfigurationPackage
@Import(AutoConfigurationImportSelector.class)
public @interface EnableAutoConfiguration {
}
```

- **@AutoConfigurationPackage 源码**

```java
@Import(AutoConfigurationPackages.Registrar.class)
public @interface AutoConfigurationPackage

public void registerBeanDefinitions(AnnotationMetadata metadata,
                BeanDefinitionRegistry registry) {
  register(registry, new PackageImport(metadata).getPackageName())
        }
// 很容易可以看到，它的作用就是将主配置类(@SpringBootApplication)的所在包及其子包里边的组件扫描到Spring容器中
```

- **@Import(AutoConfigurationImportSelector.class) 源码**

```java
public String[] selectImports(AnnotationMetadata annotationMetadata) {
        if (!isEnabled(annotationMetadata)) {
            return NO_IMPORTS;
        }
        AutoConfigurationMetadata autoConfigurationMetadata = AutoConfigurationMetadataLoader
                .loadMetadata(this.beanClassLoader);
        AutoConfigurationEntry autoConfigurationEntry = getAutoConfigurationEntry(
                autoConfigurationMetadata, annotationMetadata);

// 可以得到了很多配置信息
protected AutoConfigurationEntry getAutoConfigurationEntry(...) {
    AnnotationAttributes attributes = getAttributes(annotationMetadata);
    List<String> configurations = getCandidateConfigurations(annotationMetadata, attributes);

// 配置信息从这里来
protected List<String> getCandidateConfigurations(...) {
    List<String> configurations = SpringFactoriesLoader.loadFactoryNames(...);

// 配置加载的位置
public static List<String> loadFactoryNames(...) {
    String factoryClassName = factoryClass.getName();
    return loadSpringFactories(classLoader)...;
}

private static Map<String, List<String>> loadSpringFactories(...) {
    Enumeration<URL> urls = (classLoader != null ?
        classLoader.getResources(FACTORIES_RESOURCE_LOCATION) :
        Properties properties = PropertiesLoaderUtils.loadProperties(resource);
        for (Map.Entry<?, ?> entry : properties.entrySet()) {
            result.add(factoryClassName, factoryName.trim());

// 这个方法也就是自动配置的核心实现了，主要是三点内容：
// FACTORIES_RESOURCE_LOCATION的值是META-INF/spring.factories
// Spring启动的时候会扫描所有jar路径下的META-INF/spring.factories，将其文件包装成Properties对象
// 从Properties对象获取到key值为EnableAutoConfiguration的数据，然后添加到容器里边。
```

### SpringBoot 配置文件

- **同一个目录下的 application 和 bootstrap**
    - **bootstrap 优先级高于 application，优先被加载**
    - **bootstrap 用于应用程序上下文的引导阶段，由父 ApplicationContext 加载**
    - **bootstrap 是系统级别的配置（不变的参数），application 是应用级别的配置**
- **不同位置的配置文件加载顺序（优先级）**
    - **file：./config/ - 优先级最高（项目根路径下的 config）**
    - **file：./ - 优先级第二（项目根路径下）**
    - **classpath:/config/ - 优先级第三（项目 resources/config 下）**
    - **classpath:/ - 优先级第四（项目 resources 目录下）**
    - **高优先级覆盖低优先级相同配置、多个配置文件互补**

### 配置注入的方式

- **直接使用 @Value**
- **使用 @ConfigurationProperties + prefix 的方式**

### 定时任务

- **@EnableScheduling：允许当前的应用开启定时任务**
- **@Scheduled：指定定时任务的运行规则**

### 异步任务

***通常代码都是顺序执行（一行一行的执行），这也就是同步调用。但是异步编程却没有这样的限制，代码执行并不是阻塞的。可以直接调用不用等待返回，而是在某一个想要获取结果的时间点再去获取结果。***

- **引入spring-boot-starter-web依赖**
- **在SpringBoot入口类上加上 @EnableAsync 注解，开启异步支持**
- **只需要在方法上加上 @Async 注解，则当前方法就是异步方法**

***默认情况下的异步线程池配置使得线程不能被重用，每次调用异步方法都会新建一个线程，我们可以自己定义异步线程池来优化。***

### 单元测试

***编写单元测试可以帮助开发人员编写高质量的代码，提升代码质量，减少Bug，便于重构。SpringBoot提供了一些实用程序和注解，用来帮助我们测试应用程序，在SpringBoot中开启单元测试只需引入spring-boot-starter-test即可，其包含了一些主流的测试库。***

**一个标准的SpringBoot测试用例应该包含两个注解：**

- **@SpringBootTest：意思是带有 SpringBoot 支持的引导程序，其中提供了可以指定 Web 环境的参数**
- **@RunWith(SpringRunner.class)：告诉JUnit运行使用Spring的测试支持。SpringRunner是SpringJUnit4ClassRunner的新名字，这个名字只是让名字看起来简单些**



