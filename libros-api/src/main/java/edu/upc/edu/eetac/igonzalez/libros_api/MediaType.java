package edu.eetac.dsa.igonzalez.libros_api;

public interface MediaType {
	public final static String LIBROS_API_USER = "application/vnd.libros.api.user+json";
	public final static String LIBROS_API_USER_COLLECTION = "application/vnd.libros.api.user.collection+json";
	public final static String LIBROS_API_RESENA = "application/vnd.libros.api.resena+json"; //reseñas
	public final static String LIBROS_API_RESENA_COLLECTION = "application/vnd.libros.api.resena.collection+json";
	public final static String LIBROS_API_LIBRO = "application/vnd.libros.api.libro+json";  //libros
	public final static String LIBROS_API_LIBRO_COLLECTION = "application/vnd.libros.api.libros.collection+json"; //coleccion libros
	public final static String LIBROS_API_ERROR = "application/vnd.libros.error+json";
}
