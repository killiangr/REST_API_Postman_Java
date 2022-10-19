package backend.Videogame;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import backend.Rating;
import backend.User;
import backend.Videogame.Videogame;
import backend.Videogame.VideogameDB;
import backend.Videogame.VideogameResource;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

public class VideogameResource {
	
	static int User_id;
	
	static User user;
	
	public VideogameResource(User Uuser) {
		user = Uuser;
		User_id = user.getUser_id();
	}

	@POST
	//curl --header "Accept: application/json" --header "Content-Type: application/json" http://localhost:9993/api/user/1/Videogames POST --data ""{"""title""":"""Death on the Nile""","""author""":"""Agatha_Christie""","""year""":1937}""
	public Videogame createVideogame(Videogame Videogame) {
		System.out.println("Videogame.createVideogame()");
		List<String> comments = new ArrayList<>();
		List<Rating> rates = new ArrayList<>();

		if (VideogameDB.getVideogamebyTitle(Videogame.getTitle()).size() >= 1 && VideogameDB.getVideogamebyAuthor(Videogame.getAuthor()).size() >= 1 ) {
			throw new NotFoundException("Videogame already exist.");
		}

		Videogame newVideogame = new Videogame();
		newVideogame.setTitle(Videogame.getTitle());
		newVideogame.setAuthor(Videogame.getAuthor());
		newVideogame.setYear(Videogame.getYear());
		newVideogame.setComments(comments);
		newVideogame.setVideogame_id(VideogameDB.getVideogames().size()+1);
		newVideogame.setUser_id(User_id);
		newVideogame.setRates(rates);
		
		VideogameDB.getVideogames().add(newVideogame);

		return newVideogame;
	}
	
	@POST
	@Path("/comment/{id}")
	public void createcomment(@PathParam("id") int ID, String comment) {
		Videogame b = getVideogamebyID(ID);
		b.addComment(comment);
	}
	
	@POST
	@Path("/rate/{id}")
	public Videogame createrating(@PathParam("id") int ID, int rate) {
		Rating r = new Rating(User_id, rate);
		VideogameDB.getVideogamebyID(ID).get().addRate(r);
		return VideogameDB.getVideogamebyID(ID).get();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Videogame> getVideogames() {
		System.out.println("getVideogames");

		return VideogameDB.getVideogames();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Videogame getVideogamebyID(@PathParam("id") int ID) {
		System.out.println("getVideogame ");

		Optional<Videogame> VideogameById = VideogameDB.getVideogamebyID(ID);

		if (VideogameById.isPresent()) {
			return VideogameById.get();
		} else {
			throw new NotFoundException();
		}
	}
	
	@GET
	@Path("/author/{author}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Videogame> getVideogamebyAuthor(@PathParam("author") String author) {
		System.out.println("getVideogamebyAuthor");

		List<Videogame> VideogameById = VideogameDB.getVideogamebyAuthor(author);

		if (VideogameById.size()>=1) {
			return VideogameById;
		} else {
			throw new NotFoundException();
		}
	}
	
	@GET
	@Path("/title/{title}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Videogame> getVideogamebyTitle(@PathParam("title") String title) {
		System.out.println("getVideogamebyTitle");

		List<Videogame> VideogameById = VideogameDB.getVideogamebyTitle(title);

		if (VideogameById.size()>=1) {
			return VideogameById;
		} else {
			throw new NotFoundException();
		}
	}
	
	@GET
	@Path("/fromuser")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Videogame> getUserVideogame() {
		System.out.println("getUserVideogame ");

		List<Videogame> VideogameById = VideogameDB.getVideogamebyUserID(User_id);

		if (VideogameById.size()>=1) {
			return VideogameById;
		} else {
			throw new NotFoundException();
		}
	}
	
	@GET
	@Path("/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteUserVideogame(@PathParam("id") int ID) {
		System.out.println("delete UserVideogame ");

		Optional<Videogame> Videogame = VideogameDB.getVideogamebyID(ID);

		if (Videogame.isPresent()) {
			Videogame b = Videogame.get();;
			if (b.getUser_id() == User_id){
				VideogameDB.getVideogames().remove(b);
				VideogameDB.getVideogamesTaken().remove(b);
				VideogameResource.user.getListVideogames().remove(b);
			}
			else {
				throw new NotFoundException();
			}
		} else {
			throw new NotFoundException();
		}
	}
	
	@GET
	@Path("/takelist")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Videogame> takeVideogame() {
		System.out.println("takelist ");
		return user.getListVideogames();
	}
	
	@GET
	@Path("/take/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Videogame> takeList(@PathParam("id") int ID) {
		System.out.println("TakeVideogame ");

		Optional<Videogame> VideogameById = VideogameDB.getVideogamebyID(ID);

		if (VideogameById.isPresent()) {
			Videogame Videogame = VideogameById.get();
			if (VideogameDB.getVideogamesTaken().size() >= 1) {
				if (VideogameDB.getVideogamesTaken().stream().filter(current -> current.getVideogame_id()==ID).findAny().isPresent()) {
					throw new NotFoundException("Videogame already taken");
				}
				else {
					VideogameDB.getVideogamesTaken().add(Videogame);
					VideogameResource.user.getListVideogames().add(Videogame);
					return VideogameResource.user.getListVideogames();
				}}
			else {
				VideogameDB.getVideogamesTaken().add(Videogame);
				VideogameResource.user.getListVideogames().add(Videogame);
				return VideogameResource.user.getListVideogames();
			}
		} else {
			throw new NotFoundException();
		}
	}

}
