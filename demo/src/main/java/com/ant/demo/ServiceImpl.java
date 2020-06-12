package com.ant.demo;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ServiceImpl implements Service{
	@Override
	public String eat() throws InterruptedException {
		System.err.println("吃饭了。。。。。");
		TimeUnit.SECONDS.sleep(new Random().nextInt(10));
		return "来呀";
	}
}
