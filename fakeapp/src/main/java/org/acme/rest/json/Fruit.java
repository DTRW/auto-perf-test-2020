package org.acme.rest.json;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Fruit {

    public String name;
    public String description;

    public Fruit() {
    }

    public Fruit(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
