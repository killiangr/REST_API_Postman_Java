package backend.Videogame;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import backend.Rating;

@XmlRootElement(name = "Videogame")
public class Videogame {
	
	int videogame_id;
	
	int user_id;
	
	String title;
	
	String author;
	
	int year;
	
	List<String> comments;
	
	List<Rating> rates;
	
	public Videogame(int ID, String title, String author, int year, List<String> comments, int UID, List<Rating> rates) {
		this.videogame_id = ID;
		this.title = title;
		this.author = author;
		this.year = year;
		this.comments = comments;
		this.user_id = UID;
		this.rates = rates;
	}

	public Videogame() {
		//TODO Auto-generated constructor stub
	}

	// Videogame_id section
	public int getVideogame_id() {
		return videogame_id;
	}
	
	public void setVideogame_id(int id) {
		this.videogame_id = id;
	}
	
	
	// Title section
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	// Author section
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	
	// Year section
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	
	// Comment section
	public List<String> getComments() {
		return comments;
	}
	
	public void setComments(List<String> Comments) {
		this.comments = Comments;
	}
	
	public void addComment(String comment) {
		this.comments.add(comment);
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
	public List<Rating> getRates() {
		return rates;
	}

	public void setRates(List<Rating> rates) {
		this.rates = rates;
	}
	
	public void addRate(Rating rate) {
		this.rates.add(rate);
	}
}


