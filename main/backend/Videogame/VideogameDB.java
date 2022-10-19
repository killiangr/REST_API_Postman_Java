package backend.Videogame;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlRootElement;

import backend.Rating;
import backend.Book.Book;
import backend.Videogame.VideogameDB;

@XmlRootElement(name = "Videogame")
public class VideogameDB {
	private static List<Videogame> Videogames = new ArrayList<>();
	private static List<Videogame> taken = new ArrayList<>();
	private static List<String> comments = new ArrayList<>();
	private static List<Rating> rates = new ArrayList<>();
	
	static {
		Videogames.add(new Videogame(1, "Harry Potter", "JK_Rowling", 1997, comments,1 , rates));
		//Videogames.add(new Videogame(2, "And then ther were none", "Agatha_Christie", 1939, comments,2 , rates));
	}
	
	public static List<Videogame> getVideogames(){
		return Videogames;
	}
	
	public static List<Videogame> getVideogamesTaken(){
		return taken;
	}
	
	public static Optional<Videogame> getVideogamebyID(int ID){
		return VideogameDB.getVideogames().stream().filter(current -> current.getVideogame_id()==ID).findFirst();
	}
	
	public static List<Videogame> getVideogamebyTitle(String title){
		return VideogameDB.getVideogames().stream().filter(current -> current.getTitle().equals(title)).collect(Collectors.toList());
	}
	
	public static List<Videogame> getVideogamebyAuthor(String author){
		return VideogameDB.getVideogames().stream().filter(current -> current.getAuthor().equals(author)).collect(Collectors.toList());
	}
	
	public static Optional<Videogame> getVideogamebyYear(int year){
		return VideogameDB.getVideogames().stream().filter(current -> current.getVideogame_id()==year).findFirst();
	}
	
	public static List<Videogame> getVideogamebyUserID(int ID){
		return VideogameDB.getVideogames().stream().filter(current -> current.getUser_id()==ID).collect(Collectors.toList());
	}
}
