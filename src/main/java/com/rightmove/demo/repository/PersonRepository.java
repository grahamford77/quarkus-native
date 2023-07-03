package com.rightmove.demo.repository;

import java.util.concurrent.ExecutionException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.common.annotations.VisibleForTesting;
import com.rightmove.demo.domain.Person;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PersonRepository {
	@VisibleForTesting
	public static final String PERSONS_COLLECTION_NAME = "persons";

	@Inject
	Firestore firestore;

	@Inject
	ObjectMapper objectMapper;

	public Person getById(String id) throws ExecutionException, InterruptedException {
		DocumentSnapshot document = firestore.collection(PERSONS_COLLECTION_NAME).document(id).get().get();
		if (document.exists()) {
			return objectMapper.convertValue(document.getData(), Person.class);
		}

		throw new ObjectNotFoundException();
	}

	public String store(Person person) throws ExecutionException, InterruptedException {
		CollectionReference persons = firestore.collection(PERSONS_COLLECTION_NAME);
		ApiFuture<DocumentReference> result = persons.add(person);
		return result.get().getId();
	}
}
