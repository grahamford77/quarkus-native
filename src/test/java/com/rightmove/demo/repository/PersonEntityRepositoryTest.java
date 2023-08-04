package com.rightmove.demo.repository;

import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.rightmove.demo.domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PersonEntityRepositoryTest {

	@Mock
	private Firestore firestore;

	@Mock
	private CollectionReference collectionReference;

	@Mock
	private ApiFuture<DocumentReference> apiFuture;

	@InjectMocks
	private PersonRepository personRepository;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testStore() throws ExecutionException, InterruptedException {
		// Given
		Person person = new Person();
		person.setFirstName("John");
		person.setLastName("Doe");
		person.setAddress("1 High Street");
		person.setEmailAddress("john.doe@example.com");
		person.setAge(23);

		String documentId = "abcd1234";
		when(firestore.collection(PersonRepository.PERSONS_COLLECTION_NAME)).thenReturn(collectionReference);
		when(collectionReference.add(person)).thenReturn(apiFuture);
		when(apiFuture.get()).thenReturn(mock(DocumentReference.class));
		when(apiFuture.get().getId()).thenReturn(documentId);

		// When
		String result = personRepository.store(person);

		// Then
		assertEquals(documentId, result);
		verify(firestore, times(1)).collection(PersonRepository.PERSONS_COLLECTION_NAME);
		verify(collectionReference, times(1)).add(person);
		verify(apiFuture, times(2)).get();
	}
}
