package edu.eetac.dsa.igonzalez.libros_api;

import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class LibroApplication extends ResourceConfig {
	public LibroApplication() {
		super();
		register(DeclarativeLinkingFeature.class);
	}
}

