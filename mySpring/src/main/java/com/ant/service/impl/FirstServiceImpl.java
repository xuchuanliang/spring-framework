package com.ant.service.impl;

import com.ant.service.FirstService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FirstServiceImpl implements FirstService {
	@Resource
	private SecondServiceImpl secondService;

	@Override
	public void query(){
		System.err.println(secondService);
	}
}
