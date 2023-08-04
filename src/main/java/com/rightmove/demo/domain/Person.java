package com.rightmove.demo.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public final class Person implements Serializable {
	public String firstName;
	public String lastName;
	public String address;
	public String emailAddress;
	public long age;
}
