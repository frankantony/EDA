package br.ufc.crateus.eda.utils;

import java.util.Date;

public class Person {
	private long cpf;
	private String name;
	Date birthDate;
	
	public Person(long cpf, String name, Date birthDate) {
		this.cpf = cpf;
		this.name = name;
		this.birthDate = birthDate;
	}
	public long getCpf() {
		return cpf;
	}
	
	public String getName() {
		return name;
	}
	
	public Date getBirthDate() {
		return birthDate;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (cpf ^ (cpf >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (cpf != other.cpf)
			return false;
		return true;
	}
	
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj) return true;
//		
//		if (obj == null) return false;
//		
//		if (this.getClass() != obj.getClass())
//			return false;
//		
//		Person p = (Person) obj;
//		if (p.cpf != this.cpf)
//			return false;
//		return true;
//	}
	
	
}
