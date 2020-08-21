package com.ant.entity;

public class Body {
	private String bodyName;
	private Entity entity;

	public String getBodyName() {
		return bodyName;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setBodyName(String bodyName) {
		this.bodyName = bodyName;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	@Override
	public String toString() {
		return "Body{" +
				"bodyName='" + bodyName + '\'' +
				", entity=" + entity +
				'}';
	}
}
