package backend.DVD;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import backend.Rating;
import backend.User;
import backend.DVD.DVD;
import backend.DVD.DVDDB;
import backend.DVD.DVDResource;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

public class DVDResource {
	
	static int User_id;
	
	static User user;
	
	public DVDResource(User Uuser) {
		user = Uuser;
		User_id = user.getUser_id();
	}

	@POST
	//curl --header "Accept: application/json" --header "Content-Type: application/json" http://localhost:9993/api/user/1/DVDs POST --data ""{"""title""":"""Death on the Nile""","""author""":"""Agatha_Christie""","""year""":1937}""
	public DVD createDVD(DVD DVD) {
		System.out.println("DVD.createDVD()");
		List<String> comments = new ArrayList<>();
		List<Rating> rates = new ArrayList<>();

		if (DVDDB.getDVDbyTitle(DVD.getTitle()).size() >= 1 && DVDDB.getDVDbyAuthor(DVD.getAuthor()).size() >= 1 ) {
			throw new NotFoundException("DVD already exist.");
		}

		DVD newDVD = new DVD();
		newDVD.setTitle(DVD.getTitle());
		newDVD.setAuthor(DVD.getAuthor());
		newDVD.setYear(DVD.getYear());
		newDVD.setComments(comments);
		newDVD.setDVD_id(DVDDB.getDVDs().size()+1);
		newDVD.setUser_id(User_id);
		newDVD.setRates(rates);
		
		DVDDB.getDVDs().add(newDVD);

		return newDVD;
	}
	
	@POST
	@Path("/comment/{id}")
	public DVD createcomment(@PathParam("id") int ID, String comment) {
		DVD b = getDVDbyID(ID);
		b.addComment(comment);
		return b;
	}
	
	@POST
	@Path("/rate/{id}")
	public DVD createrating(@PathParam("id") int ID, int rate) {
		Rating r = new Rating(User_id, rate);
		DVDDB.getDVDbyID(ID).get().addRate(r);
		return DVDDB.getDVDbyID(ID).get();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<DVD> getDVDs() {
		System.out.println("getDVDs");

		return DVDDB.getDVDs();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public DVD getDVDbyID(@PathParam("id") int ID) {
		System.out.println("getDVD ");

		Optional<DVD> DVDById = DVDDB.getDVDbyID(ID);

		if (DVDById.isPresent()) {
			return DVDById.get();
		} else {
			throw new NotFoundException();
		}
	}
	
	@GET
	@Path("/author/{author}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<DVD> getDVDbyAuthor(@PathParam("author") String author) {
		System.out.println("getDVDbyAuthor");

		List<DVD> DVDById = DVDDB.getDVDbyAuthor(author);

		if (DVDById.size()>=1) {
			return DVDById;
		} else {
			throw new NotFoundException();
		}
	}
	
	@GET
	@Path("/title/{title}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<DVD> getDVDbyTitle(@PathParam("title") String title) {
		System.out.println("getDVDbyTitle");

		List<DVD> DVDById = DVDDB.getDVDbyTitle(title);

		if (DVDById.size()>=1) {
			return DVDById;
		} else {
			throw new NotFoundException();
		}
	}
	
	@GET
	@Path("/fromuser")
	@Produces(MediaType.APPLICATION_JSON)
	public List<DVD> getUserDVD() {
		System.out.println("getUserDVD ");

		List<DVD> DVDById = DVDDB.getDVDbyUserID(User_id);

		if (DVDById.size()>=1) {
			return DVDById;
		} else {
			throw new NotFoundException();
		}
	}
	
	@GET
	@Path("/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteUserDVD(@PathParam("id") int ID) {
		System.out.println("delete UserDVD ");

		Optional<DVD> DVD = DVDDB.getDVDbyID(ID);

		if (DVD.isPresent()) {
			DVD b = DVD.get();;
			if (b.getUser_id() == User_id){
				DVDDB.getDVDs().remove(b);
				DVDDB.getDVDsTaken().remove(b);
				DVDResource.user.getListDVDs().remove(b);
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
	public List<DVD> takeDVD() {
		System.out.println("takelist ");
		return user.getListDVDs();
	}
	
	@GET
	@Path("/take/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<DVD> takeList(@PathParam("id") int ID) {
		System.out.println("TakeDVD ");

		Optional<DVD> DVDById = DVDDB.getDVDbyID(ID);

		if (DVDById.isPresent()) {
			DVD DVD = DVDById.get();
			if (DVDDB.getDVDsTaken().size() >= 1) {
				if (DVDDB.getDVDsTaken().stream().filter(current -> current.getDVD_id()==ID).findAny().isPresent()) {
					throw new NotFoundException("DVD already taken");
				}
				else {
					DVDDB.getDVDsTaken().add(DVD);
					DVDResource.user.getListDVDs().add(DVD);
					return DVDResource.user.getListDVDs();
				}}
			else {
				DVDDB.getDVDsTaken().add(DVD);
				DVDResource.user.getListDVDs().add(DVD);
				return DVDResource.user.getListDVDs();
			}
		} else {
			throw new NotFoundException();
		}
	}
}
