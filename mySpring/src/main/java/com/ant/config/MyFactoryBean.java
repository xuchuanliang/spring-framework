package com.ant.config;

import com.ant.entity.EntityByFactoryBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class MyFactoryBean implements FactoryBean<EntityByFactoryBean> {
	@Override
	public EntityByFactoryBean getObject() throws Exception {
		return new EntityByFactoryBean();
	}

	@Override
	public Class<?> getObjectType() {
		return EntityByFactoryBean.class;
	}
}
