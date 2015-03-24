package edu.eetac.dsa.igonzalez.libros_api;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

 

public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {
@Override
public Response toResponse(WebApplicationException exception) {
LibroError error = new LibroError(
		exception.getResponse().getStatus(), exception.getMessage());
return Response.status(error.getStatus()).entity(error)
		.type(MediaType.LIBROS_API_ERROR).build();
}

}