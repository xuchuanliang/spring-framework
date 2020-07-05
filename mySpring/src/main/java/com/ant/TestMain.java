package com.ant;


import com.ant.config.Config;
import com.ant.service.IndexDao;
import com.ant.service2.Entity2;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestMain {
	public static void main(String[] args) throws InterruptedException {
		/**
		 * 记录spring中的扩展点：
		 * 1.BeanPostProcessor
		 * 2.BeanFactoryPostProcessor
		 * 3.BeanDefinitionRegistryPostProcessor,备注：实现了BeanFactoryPostProcessor接口，并且扩展了新的方法postProcessBeanDefinitionRegistry，
		 *   跟进到spring的内部类ConfigurationClassPostProcessor了解到的知识点，其中ConfigurationClassPostProcessor是解析我们注解配置类非常重要的一个类
		 * 4.通过@Import注解，注解用的类实现ImportSelector接口或ImportBeanDefinitionRegistrar接口
		 * 5.
		 *
		 *
		 *
		 *
		 */


		/**
		 * 记录向spring容器中添加bean的方式
		 * 1.通过手动注册的方式：annotationConfigApplicationContext.register(Config.class);---此种方式是通过一个类注入，由spring帮我们实例化
		 * 2.通过xml定义一个<bean><bean/>---此种方式是通过一个类注入，由spring帮我们实例化
		 * 3.通过@Component/@Service/@Controller/@Repository---此种方式是通过一个类注入，由spring帮我们实例化
		 * 4.通过@Import()中增加实现ImportBeanDefinitionRegistrar的类---此种方式spring是暴露了一个bd注册器给spring，允许自己构建bd交给spring容器，mybatis的mapperScan就是通过这种方式实现的，此种方式允许用户能够插手beanDefinition加入spring的过程
		 * 5.通过@Bean方式---此种方式是通过一个对象注入，我们可以查收bean实例化的过程
		 * 6.通过FactoryBean---此种方式是通过一个对象注入，我们可以查收bean实例化的过程
		 *
		 *  java -classpath C:\Program Files\Java\jdk1.8.0_231\lib\sa-jdi.jar "sun.jvm.hotspot.HSDB"
		 *  https://www.cnblogs.com/dengrong/p/10622594.html
		 *
		 *
		 *
		 *	分析完invokeBeanFactoryPostProcessors总结：
		 *  BeanFactoryPostProcessor接口：实现该接口之后，可以参与spring容器对beanDefinition的建设，在实例化bean之前所有的BeanFactoryPostProcessor实现类的回调
		 *  方法会被调用，经典应用场景有：ConfigurationClassPostProcessor，针对所有标注有@Configuration注解的配置类，实现cglib的动态代理，并且该类的bd中的class
		 *  类型替换成代理类的类型，从而达到在后面实例化该配置类的时候实际上实例化的是代理类
		 *
		 *  BeanDefinitionRegistryPostProcessor接口：该接口继承了BeanFactoryPostProcessor接口，是BeanFactoryProcessor接口的唯一一个子接口，
		 *  所有实现了该接口的类也实现了BeanPostProcessor，该接口的回调是在spring回调所有的BeanFactoryProcessor接口的大方法(invokeBeanFactoryPostProcessors(beanFactory))中统一执行的，
		 *  BeanDefinitionRegistryPostProcessor接口的回调会在BeanFactoryProcessor接口的回调之前执行，在invokeBeanFactoryPostProcessors(beanFactory)这个大方法中，将BeanFactoryPostProcessor
		 *  分为了4类，分别是用户自定义的BeanDefinitionRegistryPostProcessor实现类，spring内置的BeanDefinitionRegistryPostProcessor实现类，用户自定义的BeanDefinitionRegistryPostProcessor实现类，
		 *  spring内置的BeanFactoryPostProcessor实现类【注意：此处指的用户自定义的BeanDefinitionRegistryPostProcessor或BeanFactoryPostProcessor都是指用户手动调用annotationConfigApplicationContext.addBeanFactoryPostProcessor();】
		 *  方法，手动向容器中add的BeanFactoryPostProcessor，经典应用场景：ConfigurationClassPostProcessor：包含对带有@Configuration的配置类或没加的区分为fullConfigurationClass或liteConfigurationClass，
		 *  对@ComponentScan注解进行扫描所有的bd，对@Import注解中的普通类，实现了ImportSelector或ImportBeanDefinitionRegistrar接口的类进行回调和特殊处理，对@Bean的扫描等。
		 *
		 * @Import 中的实现ImportSelector和ImportBeanDefinitionRegistrar接口的区别：
		 * 实现ImportSelector接口，spring会回调实现的方法，并将注解元数据注解信息AnnotationMetadata通过参数回传给该方法，该方法返回一个String[]，在这个方法中可以返回一个包含多个类全限定名的字符串数组，spring会帮助我们将该数组中
		 * 的所有class全限定名解析封装成BeanDefinition，并在后面的过程中加载实例化称为bean，放在spring容器中，是一个非常重要的扩展点，但是缺点是我们无法参与到bd的创建过程，bd的创建过程完全有spring来控制。
		 * 实现ImportBeanDefinitionRegistrar接口，spring会回调实现的方法，并将元数据AnnotationMetadata以及spring的bd注册器BeanDefinitionRegistr回传给该方法，在该方法中，我们就可以使用spring的bd注册器，
		 * 根据自己的业务需求，来扫描加载bd，将bd注册到spring容器中，目前常见的很多自定义注解均是使用这种方式来完成特定的功能，如mybatis的@MapperScan，@Enablexxxx，均是采用这种方式，这种方式的好处是用户完全控制
		 * bd的创建过程。
		 *
		 *  BeanPostProcessor：实现该接口之后，可以插手spring容器实例化bean的过程，在spring容器对bean实例化之前以及之后，会回调所有的的BeanPostProcessor接口
		 *  的实现方法，经典应用场景有：bean的生命周期回调，如@PostConstruct、@PreDestroy；AOP代理
		 */
		AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
		annotationConfigApplicationContext.register(Config.class);
		annotationConfigApplicationContext.refresh();
		annotationConfigApplicationContext.getBean(IndexDao.class).test();
		System.out.println(annotationConfigApplicationContext.getBean(Entity2.class));

//		Enhancer enhancer = new Enhancer();
//		enhancer.setSuperclass(IndexDao.class);
	}
}
