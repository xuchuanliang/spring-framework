package com.ant.service;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class IndexService {

	@Resource
	private IndexDao indexDao;

	public void test() {
		System.out.println("test");
	}
}
