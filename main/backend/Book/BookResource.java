package backend.Book;

import java.util.ArrayList;


import java.util.List;
import java.util.Optional;

import backend.Rating;
import backend.User;
import backend.UserDB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

public class BookResource {
	
	int User_id;
	
	User user;
	
	public BookResource(User Uuser) {
		user = Uuser;
		User_id = user.getUser_id();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	//curl --header "Accept: application/json" --header "Content-Type: application/json" http://localhost:9993/api/user/1/books POST --data ""{"""title""":"""Death on the Nile""","""author""":"""Agatha_Christie""","""year""":1937}""
	public Book createBook(Book Book) {
		System.out.println("Book.createBook()");
		List<String> comments = new ArrayList<>();
		List<Rating> rates = new ArrayList<>();

		if (BookDB.getBookbyTitle(Book.getTitle()).size() >= 1 && BookDB.getBookbyAuthor(Book.getAuthor()).size() >= 1 ) {
			throw new NotFoundException("Book already exist.");
		}

		Book newBook = new Book();
		newBook.setTitle(Book.getTitle());
		newBook.setAuthor(Book.getAuthor());
		newBook.setYear(Book.getYear());
		newBook.setComments(comments);
		newBook.setRates(rates);
		newBook.setBook_id(BookDB.getBooks().size()+1);
		newBook.setUser_id(User_id);
		
		BookDB.getBooks().add(newBook);

		return newBook;
	}
	
	@POST
	@Path("/comment/{id}")
	public Book createcomment(@PathParam("id") int ID, String comment) {
		Book b = getBookbyID(ID);
		b.addComment(comment);
		return b;
	}
	
	@POST
	@Path("/rate/{id}")
	public Book createrating(@PathParam("id") int ID, int rate) {
		Rating r = new Rating(User_id, rate);
		BookDB.getBookbyID(ID).get().addRate(r);
		return BookDB.getBookbyID(ID).get();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book> getBooks() {
		System.out.println("getBooks");

		return BookDB.getBooks();
	}
	
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Book getBookbyID(@PathParam("id") int ID) {
		System.out.println("getBook ");

		Optional<Book> BookById = BookDB.getBookbyID(ID);

		if (BookById.isPresent()) {
			return BookById.get();
		} else {
			throw new NotFoundException();
		}
	}
	
	@GET
	@Path("/author/{author}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book> getBookbyAuthor(@PathParam("author") String author) {
		System.out.println("getBookbyAuthor");

		List<Book> BookById = BookDB.getBookbyAuthor(author);

		if (BookById.size()>=1) {
			return BookById;
		} else {
			throw new NotFoundException();
		}
	}
	
	@GET
	@Path("/title/{title}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book> getBookbyTitle(@PathParam("title") String title) {
		System.out.println("getBookbyTitle");

		List<Book> BookById = BookDB.getBookbyTitle(title);

		if (BookById.size()>=1) {
			return BookById;
		} else {
			throw new NotFoundException();
		}
	}
	
	@GET
	@Path("/fromuser")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book> getUserBook() {
		System.out.println("getUserBook ");

		List<Book> BookById = BookDB.getBookbyUserID(User_id);

		if (BookById.size()>=1) {
			return BookById;
		} else {
			throw new NotFoundException();
		}
	}
	
	@GET
	@Path("/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteUserBook(@PathParam("id") int ID) {
		System.out.println("delete UserBook ");

		Optional<Book> book = BookDB.getBookbyID(ID);

		if (book.isPresent()) {
			Book b = book.get();;
			if (b.getUser_id() == User_id){
				BookDB.getBooks().remove(b);
				BookDB.getBooksTaken().remove(b);
				//BookResource.user.getListBooks().remove(b);
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
	public List<Book> takeBook() {
		System.out.println("takelist ");
		return user.getListBooks();
	}
	
	@GET
	@Path("/take/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book> takeList(@PathParam("id") int ID) {
		System.out.println("Takebook ");

		Optional<Book> BookById = BookDB.getBookbyID(ID);

		if (BookById.isPresent()) {
			Book book = BookById.get();
			if (BookDB.getBooksTaken().size() >= 1) {
				if (BookDB.getBooksTaken().stream().filter(current -> current.getBook_id()==ID).findAny().isPresent()) {
					throw new NotFoundException("Book already taken");
				}
				else {
					BookDB.getBooksTaken().add(book);
					user.getListBooks().add(book);
					return user.getListBooks();
				}}
			else {
				BookDB.getBooksTaken().add(book);
				user.getListBooks().add(book);
				return user.getListBooks();
			}
		} else {
			throw new NotFoundException();
		}
	}

}
