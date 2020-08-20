package com.ant;

public class Entity {
	private String name;
	private String age;

	public Entity(){
		System.err.println("create");
	}

	public String getName() {
		return name;
	}

	public String getAge() {
		return age;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(String age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Entity{" +
				"name='" + name + '\'' +
				", age='" + age + '\'' +
				'}';
	}
}
