package com.rightmove.demo.client;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AgifyClient {

	public static final String AGIFY_URI = "https://api.agify.io/?name=%s";
	@Inject
	ObjectMapper objectMapper;

	private final HttpClient httpClient = HttpClient.newBuilder()
			.version(HttpClient.Version.HTTP_2)
			.build();

	public Long getAgeForName(String name) throws IOException, InterruptedException {
		var uri = String.format(AGIFY_URI, URLEncoder.encode(name, StandardCharsets.UTF_8));
		var response = this.httpClient
				.send(
						HttpRequest.newBuilder()
								.GET()
								.uri(URI.create(uri))
								.build()
						,
						HttpResponse.BodyHandlers.ofString()
				);

		AgifyResponse agifyResponse = objectMapper.readValue(response.body(), AgifyResponse.class);
		return agifyResponse.age();
	}
}
