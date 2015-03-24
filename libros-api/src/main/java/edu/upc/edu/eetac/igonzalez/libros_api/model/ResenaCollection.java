package edu.upc.edu.eetac.igonzalez.libros_api.model;

import java.util.ArrayList;
import java.util.List;

public class ResenaCollection {

	private List<Libro> resenas;
	
	public ResenaCollection() {
		super();
		resenas = new ArrayList<>();
	}
 

	public List<Libro> getResenas() {
		return resenas;
	}

	public void setResenas(List<Libro> resenas) {
		this.resenas = resenas;
	}
	
}
