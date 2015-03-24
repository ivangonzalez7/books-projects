package edu.eetac.dsa.igonzalez.libros_api;

public class LibroError {
	private int status;
	private String message;
 
	public LibroError() {
		super();
	}
 
	public LibroError(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
 
	public int getStatus() {
		return status;
	}
 
	public void setStatus(int status) {
		this.status = status;
	}
 
	public String getMessage() {
		return message;
	}
 
	public void setMessage(String message) {
		this.message = message;
	}
}
