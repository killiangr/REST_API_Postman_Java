package backend.DVD;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import backend.Rating;
import backend.Book.Book;
import backend.DVD.DVDDB;

public class DVDDB {
	
	private static List<DVD> DVDs = new ArrayList<>();
	private static List<String> comments = new ArrayList<>();
	private static List<Rating> rates = new ArrayList<>();
	private static List<DVD> taken = new ArrayList<>();
	
	static {
		DVDs.add(new DVD(1, "Harry Potter a Poudlard", "JK_Rowling", 2001, comments, 1,rates));
		//DVDs.add(new DVD(2, "Back to the Future", "Robert Zemeckis", 1985, comments,2));
	}
	
	public static List<DVD> getDVDs(){
		return DVDs;
	}
	
	public static List<DVD> getDVDsTaken(){
		return taken;
	}
	
	public static Optional<DVD> getDVDbyID(int ID){
		return DVDDB.getDVDs().stream().filter(current -> current.getDVD_id()==ID).findFirst();
	}
	
	public static List<DVD> getDVDbyTitle(String title){
		return DVDDB.getDVDs().stream().filter(current -> current.getTitle().equals(title)).collect(Collectors.toList());
	}
	
	public static List<DVD> getDVDbyAuthor(String author){
		return DVDDB.getDVDs().stream().filter(current -> current.getAuthor().equals(author)).collect(Collectors.toList());
	}
	
	public static Optional<DVD> getDVDbyYear(int year){
		return DVDDB.getDVDs().stream().filter(current -> current.getDVD_id()==year).findFirst();
	}
	
	public static List<DVD> getDVDbyUserID(int ID){
		return DVDDB.getDVDs().stream().filter(current -> current.getUser_id()==ID).collect(Collectors.toList());
	}
}
