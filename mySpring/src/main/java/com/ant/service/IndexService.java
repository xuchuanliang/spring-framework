package com.ant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public class IndexService {
	@Autowired
	private IndexDao indexDao;
	public void test(){
		System.out.println("test");
	}
}
