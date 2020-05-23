package org.acme.rest.json;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.acme.simulator.LoadSimulator;

@Path("/fruits")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FruitResource {

    private Set<Fruit> fruits = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    @Inject
    LoadSimulator simulator;

    public FruitResource() throws Exception {
        fruits.add(new Fruit("Apple", "Winter fruit"));
        fruits.add(new Fruit("Pineapple", "Tropical fruit"));
    }

    @GET
    public Response list() throws Exception {
        simulator.throttle();
        return Response.ok(fruits).build();
    }

    @POST
    public Response add(Fruit fruit) throws Exception {
        simulator.throttle();
        fruits.add(fruit);
        return Response.ok(fruits).build();
    }

    @DELETE
    public Response delete(Fruit fruit) throws Exception {
        simulator.throttle();
        fruits.removeIf(existingFruit -> existingFruit.name.contentEquals(fruit.name));
        return Response.ok(fruits).build();
    }
}
