package com.test2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecondService {
	@Autowired
	private FirstService firstService;
	public SecondService(){
		System.out.println("=================secondService");
	}
	public void test(){
		System.out.print("secondService.test()============="+firstService);
	}
}
