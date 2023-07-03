package com.rightmove.demo.controller;

import com.rightmove.demo.domain.Person;
import lombok.Builder;

@Builder
public record PersonDto(String firstName, String lastName, String address, String emailAddress, long age) {
	public Person toPerson(long age) {
		return Person.builder()
				.address(this.address())
				.emailAddress(this.emailAddress())
				.firstName(this.firstName())
				.lastName(this.lastName())
				.age(age)
				.build();
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
