package backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import backend.Book.Book;
import backend.DVD.DVD;
import backend.Videogame.Videogame;

public class UserDB {
	public static List<Book> Books = new ArrayList<>();
	public static List<DVD> DVDs = new ArrayList<>();
	public static List<Videogame> Videogames = new ArrayList<>();
	public static List<User> Users = new ArrayList<>();
	
	static {
		Users.add(new User(1,Books,DVDs,Videogames));
		//Users.add(new User(2,Books,DVDs,Videogames));
		
	}
	
	static List<User> getUsers(){
		return Users;
	}
	
	public static Optional<User> getUsersbyID(int ID){
		return UserDB.getUsers().stream().filter(current -> current.getUser_id()==ID).findFirst();
	}
	
}
