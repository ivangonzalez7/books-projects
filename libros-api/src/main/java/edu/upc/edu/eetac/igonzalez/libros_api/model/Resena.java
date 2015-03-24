package edu.upc.edu.eetac.igonzalez.libros_api.model;



import java.sql.Date;

public class Resena {
	
	private int idres;
	private String username;
	private Date fecha;
	private String texto;
	private int idlibro;
	
	public int getIdres() {
		return idres;
	}
	public void setIdres(int idres) {
		this.idres = idres;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public int getIdlibro() {
		return idlibro;
	}
	public void setIdlibro(int idlibro) {
		this.idlibro = idlibro;
	}
	public void add(Resena resena) {
		// TODO Auto-generated method stub
		
	}
	
	
}
