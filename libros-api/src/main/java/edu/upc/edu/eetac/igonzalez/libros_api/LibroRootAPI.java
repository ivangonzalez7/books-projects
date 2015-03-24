package edu.eetac.dsa.igonzalez.libros_api;


import java.util.List;
import javax.ws.rs.core.Link;
public class LibroRootAPI {
	
	private List<Link> links;
	 
	public List<Link> getLinks() {
		return links;
	}
 
	public void setLinks(List<Link> links) {
		this.links = links;
	}

}
