package edu.eetac.dsa.igonzalez.libros_api;

import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class DemoApplication extends ResourceConfig {
	public DemoApplication ( ) {
		super( );
		register(DeclarativeLinkingFeature.class) ;
		}
		}
