package org.acme.simulator;

import java.util.concurrent.ThreadLocalRandom;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

@ApplicationScoped
public class LoadSimulator {

    @ConfigProperty(name = "simulator.load.value")
    double load;

    @ConfigProperty(name = "simulator.duration.value")
	double duration;

    @ConfigProperty(name = "simulator.load.deviation")
    double deviation4load;

    @ConfigProperty(name = "simulator.duration.deviation")
    double deviation4duration;

    private static final Logger LOG = Logger.getLogger(LoadSimulator.class);

    LoadSimulator() {
        LOG.info("load: " + load);
        LOG.info("duration: " + duration);
        LOG.info("load deviation: " + deviation4load);
        LOG.info("duration deviation: " + deviation4duration);
    }

    public void throttle() throws Exception {
        // TODO: It would be possible to make the load dependent on query.
        // TODO: and also to simulate IO wait.
        final long startTime = System.currentTimeMillis();
		final double factualDuration =  ThreadLocalRandom.current().nextGaussian() * deviation4duration + duration;
		final double factualLoad = ThreadLocalRandom.current().nextGaussian() * deviation4load + load;
        try {
            // Loop for the given duration
            while (System.currentTimeMillis() - startTime < factualDuration) {
                // Every 100ms, sleep for the percentage of not loaded time
                if (System.currentTimeMillis() % 100 == 0) {
                    Thread.sleep((long) Math.floor((1 - factualLoad) * 100));
                }
            }
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
	}
}