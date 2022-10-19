package backend;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Rating")
public class Rating {
	
	int User_id;
	
	int rate;
	
	public Rating(int user_id, int rate) {
		this.User_id = user_id;
		this.rate = rate;
	}

	public int getUser_id() {
		return User_id;
	}

	public void setUser_id(int user_id) {
		User_id = user_id;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}
	
}
