package com.rightmove.demo.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.rightmove.demo.domain.Person;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PersonRepository {
	private static final Map<String, Person> PERSON_STORE = new HashMap<>();

	public Person getById(String id) {
		Person person = PERSON_STORE.get(id);

		if (person != null) {
			return person;
		}

		throw new ObjectNotFoundException();
	}

	public String store(Person person) {
		String uuid = UUID.randomUUID().toString();
		PERSON_STORE.put(uuid, person);
		return uuid;
	}
}
