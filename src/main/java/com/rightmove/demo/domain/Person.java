package com.rightmove.demo.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public final class Person {
	private final String firstName;
	private final String lastName;
	private final String address;
	private final String emailAddress;
	private final long age;
}
