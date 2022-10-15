package no.kristiania.server;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import jakarta.inject.Singleton;
import java.util.HashMap;
import java.util.Map;


public class ShopConfig extends ResourceConfig {
    public ShopConfig() {
        register(MovieResources.class);

        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(MovieRepositoryImpl.class).to(MovieRepository.class).in(Singleton.class);

            }
        });

        Map<String, String> props = new HashMap<>();
        props.put("jersey.config.server.wadl.disableWadl", "true");
        setProperties(props);



    }
}
