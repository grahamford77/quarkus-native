package com.rightmove.demo.client;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record AgifyResponse(Long count, String name, long age) {
}
