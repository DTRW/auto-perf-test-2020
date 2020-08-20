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

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Timed;

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
    @Counted(name = "fridge", description = "How many times you opened the fridge.")
    @Timed(name = "fridgeTimer", description = "A measure of how long it took to see what is in the fridge.", unit = MetricUnits.MILLISECONDS)
    public Response list() throws Exception {
        simulator.throttle();
        return Response.ok(fruits).build();
    }

    @POST
    @Counted(name = "shopping", description = "How many times you have filled the fridge.")
    @Timed(name = "shoppingTimer", description = "A measure of how long it took to do the grocery shopping.", unit = MetricUnits.MILLISECONDS)
    public Response add(Fruit fruit) throws Exception {
        simulator.throttle();
        fruits.add(fruit);
        return Response.ok(fruits).build();
    }

    @DELETE
    @Counted(name = "snacks", description = "How many times you had a fruit.")
    @Timed(name = "snackTimer", description = "A measure of how long it took to swallow a fruit.", unit = MetricUnits.MILLISECONDS)
    public Response delete(Fruit fruit) throws Exception {
        simulator.throttle();
        fruits.removeIf(existingFruit -> existingFruit.name.contentEquals(fruit.name));
        return Response.ok(fruits).build();
    }

    @Gauge(name = "inventory", unit = MetricUnits.NONE, description = "Number of fruits in the fridge.")
    public int highestPrimeNumberSoFar() {
        return fruits.size();
    }
}
