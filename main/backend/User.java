package backend;

import java.util.List;

import backend.Book.Book;
import backend.DVD.DVD;
import backend.Videogame.Videogame;

public class User {

	int User_id;
	
	List<Book> ListBooks;
	
	List<DVD> ListDVDs;
	
	List<Videogame> ListVideogames;
	
	public User(int User_id, List<Book> lBook, List<DVD> lDVD, List<Videogame> lVideo) {
		this.ListBooks = lBook;
		this.User_id = User_id;
		this.ListDVDs = lDVD;
		this.ListVideogames = lVideo;
	}

	public User(List<Book> lBook, List<DVD> lDVD, List<Videogame> lVideo) {
		this.ListBooks = lBook;
		this.ListVideogames = lVideo;
		this.ListDVDs = lDVD;
	}

	public int getUser_id() {
		return User_id;
	}

	public void setUser_id(int user_id) {
		this.User_id = user_id;
	}

	public List<Book> getListBooks() {
		return ListBooks;
	}

	public void setListBooks(List<Book> listBooks) {
		this.ListBooks = listBooks;
	}

	public List<DVD> getListDVDs() {
		return ListDVDs;
	}

	public void setListDVDs(List<DVD> listDVDs) {
		this.ListDVDs = listDVDs;
	}

	public List<Videogame> getListVideogames() {
		return ListVideogames;
	}

	public void setListVideogames(List<Videogame> listVideogames) {
		this.ListVideogames = listVideogames;
	}
	

	
}
