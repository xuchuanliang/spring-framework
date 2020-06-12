package com.ant.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Test {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		Entity bean = classPathXmlApplicationContext.getBean(Entity.class);
		System.err.println(bean);
	}

	public void test() throws InterruptedException {

		Service service = getProxy();
		service.eat();
	}

	private Service getProxy(){
		Service service = new ServiceImpl();
		return (Service) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{Service.class}, new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				if(method.getName() == "eat"){
					System.err.println("begin");
					method.invoke(service, args);
					System.err.println("end");
					return "comon";
				}else{
					return method.invoke(service,args);
				}
			}
		});
	}
}
