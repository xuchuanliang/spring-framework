package com.flowChart;

import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Profile;

import java.lang.reflect.Method;

public class Main {
	public static void main(String[] args) {
//		runCglib();
		runSpring();
	}


	public static void runSpring(){
		AnnotationConfigApplicationContext a = new AnnotationConfigApplicationContext(Config.class);
		a.getBean(MyService.class).query();
	}

	/*
	cglib生成的代理类关键代码：
	此处我们可以看出cglib是通过继承目标类来实现代理的

		public class MyService$$EnhancerByCGLIB$$31c4c78d extends MyService implements Factory {

		final void CGLIB$query$0() {
			super.query();
		}

		//在我们调用代理类的query方法时，会判断是否有MethodInterceptor；如果没有则会直接调用父类的query方法，如果有，则会调用MethodInterceptor的方法进行代理拦截，MethodInterceptor的方法体是我们自己实现的
		public final void query() {
			MethodInterceptor var10000 = this.CGLIB$CALLBACK_0;
			if (var10000 == null) {
				CGLIB$BIND_CALLBACKS(this);
				var10000 = this.CGLIB$CALLBACK_0;
			}

			if (var10000 != null) {
				var10000.intercept(this, CGLIB$query$0$Method, CGLIB$emptyArgs, CGLIB$query$0$Proxy);
			} else {
				super.query();
			}
		}

}
	 */
	public static void runCglib(){
		//加上此行代码，会将生成的代理类的class文件输出到指定文件夹下，例如我们最终输出的文件在D:\code\com\flowChart\MyService$$EnhancerByCGLIB$$31c4c78d
		System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,"D:\\code");

		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(MyService.class);
		enhancer.setCallback(new MethodInterceptor() {
			/**
			 *
			 * @param o 目标对象
			 * @param method 目标方法
			 * @param objects 目标方法的参数
			 * @param methodProxy 被代理类的方法
			 * @return
			 * @throws Throwable
			 */
			@Override
			public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
				System.out.println("before invoke");
				Object r = methodProxy.invokeSuper(o,objects);
				System.out.println("after invoke");
				return r;
			}
		});
		MyService myService = (MyService) enhancer.create();
		myService.query();
	}
}
