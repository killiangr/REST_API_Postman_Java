package backend.Book;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import backend.Rating;


public class BookDB {
	
	private static List<Book> books = new ArrayList<>();
	private static List<Book> taken = new ArrayList<>();
	private static List<String> comments = new ArrayList<>();
	private static List<Rating> rates = new ArrayList<>();
	
	static {
		books.add(new Book(1, "Harry Potter", "JK_Rowling", 1997, comments, rates,1));
		//books.add(new Book(2, "And then ther were none", "Agatha_Christie", 1939, comments, rates, 2));
	}
	
	public static List<Book> getBooks(){
		return books;
	}
	
	public static List<Book> getBooksTaken(){
		return taken;
	}
	
	public static Optional<Book> getBookbyID(int ID){
		return BookDB.getBooks().stream().filter(current -> current.getBook_id()==ID).findAny();
	}
	
	public static List<Book> getBookbyTitle(String title){
		return BookDB.getBooks().stream().filter(current -> current.getTitle().equals(title)).collect(Collectors.toList());
	}
	
	public static List<Book> getBookbyAuthor(String author){
		return BookDB.getBooks().stream().filter(current -> current.getAuthor().equals(author)).collect(Collectors.toList());
	}
	
	//EMLJFLKSDHGLQDFJDS
	public static Optional<Book> getBookbyYear(int year){
		return BookDB.getBooks().stream().filter(current -> current.getBook_id()==year).findFirst();
	}
	
	public static List<Book> getBookbyUserID(int ID){
		return BookDB.getBooks().stream().filter(current -> current.getUser_id()==ID).collect(Collectors.toList());
	}

}
