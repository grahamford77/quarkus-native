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
		Person personEntity = Person.builder()
				.lastName("Doe")
				.address("1 High Street")
				.firstName("John")
				.emailAddress("john.doe@example.com")
				.build();

		String documentId = "abcd1234";
		when(firestore.collection(PersonRepository.PERSONS_COLLECTION_NAME)).thenReturn(collectionReference);
		when(collectionReference.add(personEntity)).thenReturn(apiFuture);
		when(apiFuture.get()).thenReturn(mock(DocumentReference.class));
		when(apiFuture.get().getId()).thenReturn(documentId);

		// When
		String result = personRepository.store(personEntity);

		// Then
		assertEquals(documentId, result);
		verify(firestore, times(1)).collection(PersonRepository.PERSONS_COLLECTION_NAME);
		verify(collectionReference, times(1)).add(personEntity);
		verify(apiFuture, times(2)).get();
	}
}
