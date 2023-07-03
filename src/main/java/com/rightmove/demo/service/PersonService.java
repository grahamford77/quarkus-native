package com.rightmove.demo.service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.rightmove.demo.client.AgifyClient;
import com.rightmove.demo.controller.PersonDto;
import com.rightmove.demo.domain.Person;
import com.rightmove.demo.repository.PersonRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class PersonService {
	public static final String AGIFY_EXCEPTION_MESSAGE = "Unable to get age, using to 999";
	@Inject
	PersonRepository personRepository;

	@Inject
	AgifyClient agifyClient;

	public PersonDto getPerson(String id) throws ExecutionException, InterruptedException {
		Person person = personRepository.getById(id);
		return PersonDto.fromPerson(person);
	}

	public String storePerson(PersonDto personDto) throws ExecutionException, InterruptedException {
		Long age;
		try {
			age = agifyClient.getAgeForName(personDto.firstName());
		} catch (IOException e) {
			age = 999L;
			log.warn(AGIFY_EXCEPTION_MESSAGE, e);
		} catch (InterruptedException e) {
			age = 999L;
			log.warn(AGIFY_EXCEPTION_MESSAGE, e);
			Thread.currentThread().interrupt();
		}

		Person personEntity = personDto.toPerson(age);
		return personRepository.store(personEntity);
	}
}
