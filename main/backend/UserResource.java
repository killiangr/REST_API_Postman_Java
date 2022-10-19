package backend;

import java.util.ArrayList;

import java.util.List;

import backend.Book.Book;
import backend.Book.BookResource;
import backend.DVD.DVD;
import backend.DVD.DVDResource;
import backend.Videogame.Videogame;
import backend.Videogame.VideogameResource;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/user")
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class UserResource {
	
	public static List<Book> Books = new ArrayList<>();
	public static List<DVD> DVDs = new ArrayList<>();
	public static List<Videogame> Videogames = new ArrayList<>();
	
	@Path("/{id}/books")
	public BookResource getBookResource(@PathParam("id") int ID) {
		System.out.println("BookResource.getBookResource()");
		if(UserDB.getUsersbyID(ID).isPresent()) {
			User user = UserDB.getUsersbyID(ID).get();
			return new BookResource(user);}
		else {
			User user = new User(ID, Books, DVDs, Videogames);
			return new BookResource(user);
		}
		
	}
	
	@Path("/{id}/DVDs")
	public DVDResource getDVDResource(@PathParam("id") int ID) {
		System.out.println("BookResource.getDVDResource()");
		if(UserDB.getUsersbyID(ID).isPresent()) {
			User user = UserDB.getUsersbyID(ID).get();
			return new DVDResource(user);}
		else {
			User user = new User(ID, Books, DVDs, Videogames);
			return new DVDResource(user);
		}
		
	}
	
	@Path("/{id}/Videogames")
	public VideogameResource getVideogameResource(@PathParam("id") int ID) {
		System.out.println("BookResource.getVideogameResource()");
		if(UserDB.getUsersbyID(ID).isPresent()) {
			User user = UserDB.getUsersbyID(ID).get();
			return new VideogameResource(user);}
		else {
			User user = new User(ID, Books, DVDs, Videogames);
			return new VideogameResource(user);
		}

		
	}
	
}
