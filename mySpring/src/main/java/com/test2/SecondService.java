package com.test2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecondService {
	private FirstService firstService;
	public SecondService(){
		System.out.println("=================secondService");
	}

	public void setFirstService(FirstService firstService) {
		System.out.println("=================>"+firstService);
		this.firstService = firstService;
	}
}
