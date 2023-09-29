package com.rightmove.demo.controller;

import java.net.URI;
import java.util.concurrent.ExecutionException;

import com.rightmove.demo.repository.ObjectNotFoundException;
import com.rightmove.demo.service.PersonService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/person")
public class PersonResource {

	private static final String GET_PERSON_BY_ID_URL = "/person/%s";

	@Inject
	PersonService personService;

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPerson(@PathParam("id") String id) {
		try {
			PersonDto personDto = personService.getPerson(id);
			return Response.ok(personDto).build();
		} catch (ObjectNotFoundException e) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} catch (ExecutionException e) {
			return Response.serverError().build();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return Response.serverError().build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response storePerson(PersonDto personDto) {
		try {
			String id = personService.storePerson(personDto);
			return Response.created(URI.create(String.format(GET_PERSON_BY_ID_URL, id))).build();
		} catch (ExecutionException e) {
			return Response.serverError().build();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return Response.serverError().build();
		}
	}
}
