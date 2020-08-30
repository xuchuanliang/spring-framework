package com.ant.service.impl;

import com.ant.service.SecondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SecondServiceImpl implements SecondService {

	@Autowired
	private FirstServiceImpl firstService;
	@Override
	public void query(){
		System.err.println(firstService);
	}

	public SecondServiceImpl(){
		System.out.println("secondServiceImpl construct");
	}
}
