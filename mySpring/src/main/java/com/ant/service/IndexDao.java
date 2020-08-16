package com.ant.service;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.beans.ConstructorProperties;

@Service
public class IndexDao  {

	@Resource
	private IndexService indexService;

	public void test(){
		System.out.println("test");
	}
}
