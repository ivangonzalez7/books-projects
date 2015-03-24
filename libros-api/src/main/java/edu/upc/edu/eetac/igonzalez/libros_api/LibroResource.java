package edu.eetac.dsa.igonzalez.libros_api;

import javax.sql.DataSource; 
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import edu.eetac.dsa.rgonzalez.libros.api.model.Autor;
import edu.eetac.dsa.rgonzalez.libros.api.model.Libro;
import edu.eetac.dsa.rgonzalez.libros.api.model.LibrosCollection;
import edu.eetac.dsa.rgonzalez.libros.api.model.Resena;
import edu.eetac.dsa.rgonzalez.libros_api.DataSourceSPA;
import edu.eetac.dsa.rgonzalez.libros.api.model.ResenaCollection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.List;

@Path("/libros")
public class LibroResource {

	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	@Context
	private SecurityContext security;

	// //muestra la BD de libros
	private String GET_LIBROS_QUERY = "SELECT * FROM libros ";

	@GET
	@Produces(MediaType.LIBROS_API_LIBRO_COLLECTION)
	public LibrosCollection getLibro() {

		System.out.println("no conectados a la BD");
		LibrosCollection libros = new LibrosCollection();

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		System.out.println("conectados a la BD");

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_LIBROS_QUERY);
			ResultSet rs = stmt.executeQuery();

			System.out.println(stmt);
			while (rs.next()) {

				Libro libro = new Libro();
				libro.setId(rs.getInt("id"));
				libro.setAutor(rs.getString("autor"));
				libro.setTitulo(rs.getString("titulo"));
				libro.setLengua(rs.getString("lengua"));
				libro.setEdicion(rs.getString("edicion"));
				libro.setFecha_ed(rs.getDate("fecha_ed"));
				libro.setFecha_imp(rs.getDate("fecha_imp"));
				libro.setEditorial(rs.getString("editorial"));
				
				libros.add(libro);

			}

		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println(libros);
		return libros;
	}

	/*
	 * @GET
	 * 
	 * @Path("/{id}")
	 * 
	 * @Produces(MediaType.LIBROS_API_LIBRO) public Response
	 * getSting(@PathParam("id") String id,
	 * 
	 * @Context Request request) { // Create CacheControl CacheControl cc = new
	 * CacheControl();
	 * 
	 * Libro libro = getLibroFromDatabase(id);
	 * 
	 * // Calculate the ETag on last modified date of user resource EntityTag
	 * eTag = new EntityTag(Long.toString(libro.getLastModified()));
	 * 
	 * // Verify if it matched with etag available in http request
	 * Response.ResponseBuilder rb = request.evaluatePreconditions(eTag);
	 * 
	 * // If ETag matches the rb will be non-null; // Use the rb to return the
	 * response without any further processing if (rb != null) { return
	 * rb.cacheControl(cc).tag(eTag).build(); }
	 * 
	 * // If rb is null then either it is first time request; or resource is //
	 * modified // Get the updated representation and return with Etag attached
	 * to it rb = Response.ok(libro).cacheControl(cc).tag(eTag);
	 * 
	 * return rb.build(); }
	 */
	// //búsqueda para un usuario de autor y titulo o individual
/*	private String GET_LIBROS_AUTOR = "SELECT * FROM libros where autor LIKE ? ;";
	private String GET_LIBROS_TITULO = "SELECT * FROM libros where titulo LIKE ? ;";
	private String GET_LIBROS_SEARCH = "SELECT * FROM libros where titulo LIKE ? and autor LIKE ? ;";
*/
	
	private String GET_LIBROS_AUTOR = "SELECT * FROM libros where autor LIKE ? ;";
	private String GET_LIBROS_TITULO = "SELECT * FROM libros where titulo LIKE ? ;";
	private String GET_LIBROS_SEARCH = "SELECT * FROM libros where titulo LIKE ? and autor LIKE ? ;";
	private String GET_RESENA = " SELECT * from resena where idlibro LIKE ? ";
	
	@GET
	@Path("/search")
	@Produces(MediaType.LIBROS_API_LIBRO)
	public LibrosCollection SearchLibros(@QueryParam("titulo") String titulo,
			@QueryParam("autor") String autor) {
		LibrosCollection libros = new LibrosCollection();

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the databes",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		System.out.println("datos: " + titulo);
		System.out.println("datos: " + autor);

		PreparedStatement stmt = null;

		try {

			if (titulo != null && autor != null) {
				stmt = conn.prepareStatement(GET_LIBROS_SEARCH);
				stmt.setString(1, titulo);
				stmt.setString(2, autor);

			} else if (titulo != null && autor == null) {
				stmt = conn.prepareStatement(GET_LIBROS_TITULO);
				stmt.setString(1, titulo);
			}

			else if (titulo == null && autor != null) {
				stmt = conn.prepareStatement(GET_LIBROS_AUTOR);
				stmt.setString(1, autor);
			}

			ResultSet rs = stmt.executeQuery();
			System.out.println(stmt);
				while (rs.next()) {
					Libro libro = new Libro();
					libro.setId(rs.getInt("id"));
					libro.setAutor(rs.getString("autor"));
					libro.setTitulo(rs.getString("titulo"));
					libro.setAutor(rs.getString("autor"));
					libro.setLengua(rs.getString("lengua"));
					libro.setEdicion(rs.getString("edicion"));
					libro.setFecha_ed(rs.getDate("fecha_ed"));
					libro.setFecha_imp(rs.getDate("fecha_imp"));
					libro.setEditorial(rs.getString("editorial"));
					
					System.out.println("Query salida: " + stmt);
			/*PreparedStatement stmt2 = null;
			stmt2 = conn.prepareStatement(GET_RESENA);
			stmt2.setInt(1, libro.getId()); //conseguimos la id 
			ResultSet rs2 = stmt.executeQuery();
			
			while (rs2.next()) {
				//List<Resena> resena = libro.getResena();
				Resena resena = new Resena();
				resena.setIdres(rs2.getInt("idres"));
		        resena.setUsername(rs2.getString("username"));
				resena.setFecha(rs2.getDate("fecha"));
				resena.setTexto(rs2.getString("texto"));
				resena.setIdlibro(rs2.getInt("idlibro"));
				
				
				libro.addResena(resena);
			}
			*/		libros.add(libro);
				}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println(libros);
		return libros;
	}

	// ///búsqueda libro por id concreto
	/*private String GET_LIBRO_BY_ID = "SELECT * FROM libros where idlibro = ?";
	private String GET_AUTORES_LIBRO = "select la.idautor, a.name from libros_autores la, autores a where la.idautor=a.idautor and la.idlibro=?";
	private String GET_RESENAS_LIBRO = "select * from resenas where idlibro=?";
    */
	private String GET_LIBRO_BY_ID = "SELECT * FROM libros where id = ?";

	@GET
	@Path("/{id}")
	@Produces(MediaType.LIBROS_API_LIBRO)
	public Libro getLibro(@PathParam("id") int id) {
		
		Libro libro = new Libro();
	    Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement(GET_LIBRO_BY_ID);
			stmt.setInt(1, Integer.valueOf(id));
			System.out.println(stmt);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				libro.setId(rs.getInt("id"));
				libro.setTitulo(rs.getString("titulo"));
				libro.setAutor(rs.getString("autor"));
				libro.setLengua(rs.getString("lengua"));
				libro.setEdicion(rs.getString("edicion"));
				libro.setFecha_ed(rs.getDate("fecha_ed"));
				libro.setFecha_imp(rs.getDate("fecha_imp"));
				libro.setEditorial(rs.getString("editorial"));
			}
			else{
				throw new NotFoundException("There's no libro with id ="
						+ id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			}
		    System.out.println(libro);
			return libro;
		
	}

	// /////Cacheable
	/*
	 * @GET
	 * 
	 * @Path("/{id}")
	 * 
	 * @Produces(MediaType.LIBROS_API_LIBRO) public Response
	 * getBook(@PathParam("id") String id,@Context Request request) {
	 * 
	 * //Creamos CacheControl CacheControl cc= new CacheControl();
	 * 
	 * //Sacamos un book de la base de datos Libro libro =
	 * getLibroFromDatabase(id);
	 * 
	 * //Calculamos ETag de la ultima modificación de la reseña
	 * 
	 * String s= libro.getResena()+libro.getAutor()+libro.getEdicion()+"21";
	 * 
	 * 
	 * EntityTag eTag = new EntityTag(Long.toString(s.hashCode()));
	 * 
	 * 
	 * //Comparamos el eTag creado con el que viene de la peticiOn HTTP
	 * Response.ResponseBuilder rb = request.evaluatePreconditions(eTag);//
	 * comparamos
	 * 
	 * if (rb != null) {// Si el resultado no es nulo, significa que no ha sido
	 * modificado el contenido ( o es la 1º vez ) return
	 * rb.cacheControl(cc).tag(eTag).build(); }
	 * 
	 * 
	 * // Si es nulo construimos la respuesta de cero. rb =
	 * Response.ok(libro).cacheControl(cc).tag(eTag);
	 * 
	 * return rb.build();
	 * 
	 * }
	 */

	private String GET_RESENA_BY_ID = " SELECT * from resenas where idlibro = ?;";

	private Libro getLibroFromDatabase(String id) {
		Libro libro = new Libro();

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		// PreparedStatement stmt2 = null;
		try {
			// cojemos libros
			stmt = conn.prepareStatement(GET_LIBRO_BY_ID);
			stmt.setInt(1, Integer.valueOf(id));
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				libro.setId(rs.getInt("id"));
				libro.setTitulo(rs.getString("titulo"));
				libro.setAutor(rs.getString("autor"));
				libro.setLengua(rs.getString("lengua"));
				libro.setEdicion(rs.getString("edicion"));
				libro.setFecha_ed(rs.getDate("fecha_ed"));
				libro.setFecha_imp(rs.getDate("fecha_imp"));
				libro.setEditorial(rs.getString("editorial"));
			} else {
				throw new NotFoundException(
						"No se encuentra ningun libro con ID =" + id);
			}
			/*
			 * //cojemos resenas stmt2=conn.prepareStatement(GET_RESENA_BY_ID);
			 * stmt2.setInt(1, Integer.valueOf(id)); ResultSet rs2 =
			 * stmt2.executeQuery();
			 * 
			 * while(rs2.next()) { Resena resena = new Resena();
			 * resena.setIdres(rs.getInt("idres"));
			 * resena.setIdlibros(rs.getInt("idlibro"));
			 * resena.setUsername(rs.getString("username"));
			 * resena.setFecha(rs.getDate("fecha"));
			 * resena.setTexto(rs.getString("texto"));
			 * 
			 * }
			 */

		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}

		return libro;
	}

	// admin crear una ficha de libro
	/*private String INSERT_LIBRO = "INSERT into libros (titulo, lengua, edicion, fecha_ed, fecha_imp, editorial) values (?,?,?,?,?,?);";
	private final static String INSERT_LIBRO_AUTOR = "insert into libros_autores (idlibro, idautor) values (?,?)";
    */
	
	private String INSERT_LIBRO = "INSERT into libros (titulo, autor, lengua, edicion, fecha_ed, fecha_imp, editorial) values (?,?,?,?,?,?,?);";

	@POST
	@Consumes(MediaType.LIBROS_API_LIBRO)
	@Produces(MediaType.LIBROS_API_LIBRO)

	public Libro CreateLibro(Libro libro)
	{
		
	//solo puede el admin
	if (!security.isUserInRole("admin"))
	throw new ForbiddenException("No se le permite crear un autor");

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		
		PreparedStatement stmt = null;
		try{
			stmt = conn.prepareStatement(INSERT_LIBRO);
			
			stmt.setString(1, libro.getTitulo());
			stmt.setString(2, libro.getAutor());
			stmt.setString(3, libro.getLengua());
			stmt.setString(4, libro.getEdicion());
			stmt.setDate(5, new Date(Calendar.getInstance().getTime().getTime()));
            stmt.setDate(6, new Date(Calendar.getInstance().getTime().getTime()));
            stmt.setString(7, libro.getEditorial());
			stmt.executeUpdate();
			System.out.println(stmt);
			ResultSet rs = stmt.getGeneratedKeys();
			
			if (rs.next()) {
				int id = rs.getInt(1);
	 
				libro = getLibroFromDatabase(Integer.toString(id));
				System.out.println("libro creado");
				
			} else {
				// Something has failed...
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
			


	return libro;

	}
	// /////admin elimina la ficha de un libro

	private String DELETE_LIBRO = "DELETE FROM libros Where id = ?;";

	@DELETE
	@Path("/{id}")
	public void DeleteLibro(@PathParam("id") String id) {
		// solo puede el admin
		if (!security.isUserInRole("admin"))
			throw new ForbiddenException("You are not allowed to delete a book");

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(DELETE_LIBRO, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, Integer.valueOf(id));
			System.out.println(stmt);

			int rows = stmt.executeUpdate();
			if (rows == 0) {
				throw new NotFoundException("No hay un libro con este nombre"
						+ id);// Updating inexistent libro
			} else {
				System.out.println("Autor eliminado");
			}
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}

		// no retorna nada el delete, es un void!
	}

	// ////admin actualiza ficha de libro

	private String UPDATE_LIBRO = "UPDATE libro set titulo=ifnull(?, titulo), autor=ifnull(?, autor), lengua=ifnull(?, lengua), edicion=ifnull(?,edicion), fecha_ed=ifnull(?, fecha_ed), fecha_imp=ifnull(?, fecha_imp), editorial=ifnull(?, editorial) where idautor =?;";

	@PUT
	@Path("/id")
	@Consumes(MediaType.LIBROS_API_LIBRO)
	@Produces(MediaType.LIBROS_API_LIBRO)
	public Libro UpdateLibro(@PathParam("id") String id, Libro libro) {

		// solo puede admin
		if (!security.isUserInRole("admin"))
			throw new ForbiddenException("No se le permite actualizar libro ");

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(UPDATE_LIBRO);

			stmt.setString(1, libro.getTitulo());
			stmt.setString(2, libro.getAutor());
			stmt.setString(3, libro.getLengua());
			stmt.setString(4, libro.getEdicion());
			stmt.setDate(5, libro.getFecha_ed());
			stmt.setDate(6, libro.getFecha_imp());
			stmt.setString(7, libro.getEditorial());
			stmt.setInt(8, Integer.valueOf(id)); // lo pone tambien en Atenea
			stmt.executeUpdate();
			System.out.println(stmt);
			int rows = stmt.executeUpdate();
			if (rows == 1)
				libro = getLibroFromDatabase(id);
			else {
				throw new NotFoundException("No hay un libro con este nombre"
						+ id);// Updating inexistent libro
			}

		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}

		return libro;

	}

	// /////admin crea una ficha de autor
	private String INSERT_AUTOR = "insert into autor (name) values (?);";

	@POST
	@Path("/autor")
	@Consumes(MediaType.LIBROS_API_LIBRO)
	@Produces(MediaType.LIBROS_API_LIBRO)
	public Autor CreateAutor(Autor autor) {
		// solo puede el admin
		if (!security.isUserInRole("admin"))
			throw new ForbiddenException("No se le permite crear un autor");

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(INSERT_AUTOR,
					Statement.RETURN_GENERATED_KEYS);

			stmt.setString(1, autor.getName());

			System.out.println(autor.getName());
			System.out.println(stmt);
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();

			if (rs.next()) {

				int idautor = rs.getInt(1);
				// autor = getLibroFromDatabase(Integer.toString(idautor));
				System.out.println("autor creado");
				/*
				 * autor.setIdautor(rs.getInt("id"));
				 * autor.setName(rs.getString("titulo"));
				 */

			} else {
				throw new BadRequestException("No se le permite crear al autor");
			}

		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}

		return autor;
	}

	// ////admin elimina una ficha de autor

	private String DELETE_AUTOR = "DELETE FROM Autor where idautor = ?;";

	@DELETE
	@Path("/autor/{idautor}")
	public void DeleteAutor(@PathParam("idautor") String idautor) {
		// solo puede el admin
		if (!security.isUserInRole("admin"))
			throw new ForbiddenException("You are not allowed to delete a book");

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(DELETE_AUTOR);
			stmt.setInt(1, Integer.valueOf(idautor));
			System.out.println(stmt);

			int rows = stmt.executeUpdate();
			if (rows == 0) {
				throw new NotFoundException("No hay un autor con este nombre"
						+ idautor);// Updating inexistent sting
			} else {
				System.out.println("Autor eliminado");
			}
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}

		// no retorna nada el delete, es un void!
	}

	// //////admin actualiza ficha de autor

	private String UPDATE_AUTOR = "UPDATE autor set name=ifnull(?, name) where idautor = ?;";

	@PUT
	@Path("/autor/{idautor}")
	@Consumes(MediaType.LIBROS_API_LIBRO)
	@Produces(MediaType.LIBROS_API_LIBRO)
	public Autor UpdateAutor(@PathParam("idautor") int idautor, Autor autor) {

		if (!security.isUserInRole("admin"))
			throw new ForbiddenException("No se le permite actualizar autor");
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		PreparedStatement stmt = null;
		try {

			stmt = conn.prepareStatement(UPDATE_AUTOR);
			stmt.setString(1, autor.getName());
			stmt.setInt(2, idautor);

			int rows = stmt.executeUpdate();
			if (rows == 0) {
				throw new NotFoundException("No hay un autor con este nombre"
						+ autor);// Updating inexistent sting
			} else {
				
				System.out.println("Autor actualizado");
			}

		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}

		return autor;
	}

	/*
	 * private void validateUser(String stingid) { Sting sting =
	 * getStingFromDatabase(stingid); String username = sting.getUsername(); if
	 * (!security.getUserPrincipal().getName() .equals(username) throw new
	 * ForbiddenException( "You are not allowed to modify this sting."); }
	 */
	// //////crear una reseña, solo puede el registrado
	private String INSERT_RESENA = "insert into resenas (idlibros, username, fecha, texto) values (?,?,?,?);";

	@POST
	@Path("/{idlibros}/resenas")
	@Consumes(MediaType.LIBROS_API_RESENA)
	@Produces(MediaType.LIBROS_API_RESENA)
	public Resena CreateResena(@PathParam("idlibros") String idlibros,
			Resena resena) {

		if (!security.isUserInRole("registered"))
			throw new ForbiddenException(
					"No tienes permitido hacer una reseña de un libro");
		String registrado = security.getUserPrincipal().getName(); // obtengo
																	// nombre
																	// del
																	// registrado

		System.out.println("Estas registrado con nombre" + registrado);

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {

			stmt = conn.prepareStatement(INSERT_RESENA,
					Statement.RETURN_GENERATED_KEYS);

			stmt.setInt(1, Integer.valueOf(idlibros));
			stmt.setString(2, security.getUserPrincipal().getName()); // consigues
																		// el
																		// autor
																		// de la
																		// resena
			stmt.setDate(3, resena.getFecha());
			stmt.setString(4, resena.getTexto());

			System.out.println(stmt);
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();

			if (rs.next()) {

				int id = rs.getInt(1);
				// autor = getLibroFromDatabase(Integer.toString(idautor));
				System.out.println("resena creada");
				/*
				 * autor.setIdautor(rs.getInt("id"));
				 * autor.setName(rs.getString("titulo"));
				 */

			} else {
				throw new BadRequestException("No se le permite crear al autor");
			}

		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}

		return resena;
	}

	// /////////Borrar una reseña, solo puede el usuario registrado
	private String BORRAR_RESENA = "DELETE FROM resenas where idres = ? and idlibros = ?;";

	@DELETE
	@Path("/{idlibro}/resenas/{idres}")
	@Consumes(MediaType.LIBROS_API_RESENA)
	@Produces(MediaType.LIBROS_API_RESENA)
	public void deleteReview(@PathParam("idlibro") String idlibro,
			@PathParam("idres") String idres) {
		{
			// tienes que estar registrado
			if (!security.isUserInRole("registrado"))
				throw new ForbiddenException(
						"No se te permite borrar una reseña");

			Connection conn = null;
			try {
				conn = ds.getConnection();
			} catch (SQLException e) {
				throw new ServerErrorException(
						"Could not connect to the database",
						Response.Status.SERVICE_UNAVAILABLE);
			}
			System.out.println("conectados a la bd");
			System.out.println("idlibro" + idlibro + "idres" + idres);
			PreparedStatement stmt = null;
			try {
				stmt = conn.prepareStatement(BORRAR_RESENA);
				stmt.setInt(1, Integer.valueOf(idlibro));
				stmt.setInt(2, Integer.valueOf(idres));
				System.out.println(stmt);

				int rows = stmt.executeUpdate();
				if (rows == 0) {
					throw new NotFoundException("No hay una reseña con este id"
							+ idres + "ni con este idlibro" + idlibro);
				} else {
					System.out.println("Reseña eliminado");
				}
			} catch (SQLException e) {
				throw new ServerErrorException(e.getMessage(),
						Response.Status.INTERNAL_SERVER_ERROR);
			} finally {
				try {
					if (stmt != null)
						stmt.close();
					conn.close();
				} catch (SQLException e) {
				}
			}

			// no retorna nada el delete, es un void!
		}
	}

	// /////actualizamos una reseña de un usuario registrado
	private String UPDATE_RESENA = "UPDATE resenas set texto=ifnull(?, texto) where idlibro = ? and idres = ?;";

	@PUT
	@Path("/{idlibro}/resenas/{idres}")
	@Consumes(MediaType.LIBROS_API_RESENA)
	@Produces(MediaType.LIBROS_API_RESENA)
	public Resena UpdateResena(@PathParam("idlibro") String idlibro,
			@PathParam("idres") String idres, Resena resena) {

		// usuario registrado
		if (!security.isUserInRole("registered"))
			throw new ForbiddenException(

			"You are not allowed to create reviews for a book");
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(UPDATE_RESENA);

			stmt.setInt(1, Integer.valueOf(idlibro));
			stmt.setInt(2, Integer.valueOf(idres));
			stmt.setString(3, resena.getTexto());

			int rows = stmt.executeUpdate();
			if (rows == 0) {
				throw new NotFoundException("No hay una reseña con este id"
						+ idres + "ni con este idlibro" + idlibro);
			} else {
				System.out.println("Reseña actualizado");
			}

		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}

		return resena;

	}

	// private String GET_RESENA_BY_ID =
	// " SELECT * from resenas where idlibro = ?;";

	private Resena getResenaFromDatabase(String idlibro, String idres) {

		Resena resena = new Resena();

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_RESENA_BY_ID);
			stmt.setInt(1, Integer.valueOf(idlibro));
			stmt.setInt(1, Integer.valueOf(idres));
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				resena.setIdres(rs.getInt("idres"));
				resena.setIdlibro(rs.getInt("idlibro"));
				resena.setUsername(rs.getString("username"));
				resena.setFecha(rs.getDate("fecha"));
				resena.setTexto(rs.getString("texto"));

			} else {
				throw new NotFoundException("No hay una reseña con este id"
						+ idres + "ni con este idlibro" + idlibro);
			}

		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}

		return resena;
	}
}
