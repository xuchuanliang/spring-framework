package com.ant.service.impl;

import com.ant.service.FirstService;
import com.ant.service.SecondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

@Service
public class FirstServiceImpl implements FirstService {
	@Resource
	private SecondServiceImpl secondService;
	@Autowired
	private SecondServiceImpl secondService2;

	@Override
	public void query() {
		System.err.println(secondService);
	}

	@PostConstruct
	public void postConstruct(){
		System.out.println("postConstruct");
	}

	@PreDestroy
	public void preDestory(){
		System.out.println("preDestory");
	}

	@Resource
	public void setSecondService1(SecondService secondService){
		System.out.println("@Resource "+secondService);
	}

	@Autowired
	public void setSecondService2(SecondService secondService2){
		System.out.println("@Autowired "+secondService2);
	}

	public FirstServiceImpl(){
		System.out.println("firstServiceImpl construct");
	}
}
