package com.rightmove.demo.controller;

import com.rightmove.demo.domain.Person;
import lombok.Builder;

@Builder
public record PersonDto(String firstName, String lastName, String address, String emailAddress, long age) {
	public Person toPerson(long age) {
		Person person = new Person();
		person.setFirstName(firstName);
		person.setLastName(lastName);
		person.setAddress(address);
		person.setEmailAddress(emailAddress);
		person.setAge(age);
		return person;
	}

	public static PersonDto fromPerson(Person person) {
		return PersonDto.builder()
				.address(person.getAddress())
				.emailAddress(person.getEmailAddress())
				.firstName(person.getFirstName())
				.lastName(person.getLastName())
				.age(person.getAge())
				.build();
	}
}
