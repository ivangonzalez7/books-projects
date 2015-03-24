package edu.eetac.dsa.igonzalez.libros_api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import edu.eetac.dsa.rgonzalez.libros_api.LibroRootAPI;

@Path("/")
public class LibrosRootAPIResource {
	@GET
	public LibroRootAPI getRootAPI() {
		LibroRootAPI api = new LibroRootAPI();
		return api;
	}
}

