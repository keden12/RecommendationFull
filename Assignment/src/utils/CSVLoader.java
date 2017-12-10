package utils;


import java.io.File;

import java.util.ArrayList;
import java.util.List;

import values.Movie;
import values.Rating;
import values.User;
import edu.princeton.cs.introcs.In;

public class CSVLoader {

	public List<User> loadUsers(String filename) throws Exception {
		File usersFile = new File(filename);
		In inUsers = new In(usersFile);
		String delims = "[|]";
		List<User> users = new ArrayList<User>();
		while (!inUsers.isEmpty()) {
			String userDetails = inUsers.readLine();
			String[] userTokens = userDetails.split(delims);
			if (userTokens.length == 7) {
				String firstName = userTokens[1];
				String lastName = userTokens[2];
				int age = Integer.valueOf(userTokens[3]);
				String gender = userTokens[4];
				String occupation = userTokens[5];
				String zip = userTokens[6];
				String username = "";
                String password = "";
				users.add(new User(firstName, lastName, age, gender, occupation,zip,username,password));
			} else {
				throw new Exception("Invalid User length: " + userTokens.length);
			}
		}
		return users;
	}


	public List<Movie> loadMovies(String filename) throws Exception {
			File movieFile = new File(filename);
			In inMovies= new In(movieFile);
			String delims = "[|]";
			List<Movie> movies = new ArrayList<Movie>();
			while (!inMovies.isEmpty()) {
				String movieDetails = inMovies.readLine();
				String[] movieTokens = movieDetails.split(delims);
				if (movieTokens.length == 23) {
					String title = movieTokens[1];
					String date = movieTokens[2];
					String url = movieTokens[3];
					movies.add(new Movie(title, date, url));
				} 
				else 
				{
				throw new Exception("Invalid Movie length: " + movieTokens.length);
				}
			}
			return movies;
		}

	public List<Rating> loadRatings(String filename) throws Exception {
		File ratingFile = new File(filename);
		In inRating = new In(ratingFile);
		String delims = "[|]";
		List<Rating> ratings = new ArrayList<Rating>();
		while (!inRating.isEmpty()) {
			String ratingDetails = inRating.readLine();
			String[] ratingTokens = ratingDetails.split(delims);
			if (ratingTokens.length == 4) {
				Long userid = Long.valueOf(ratingTokens[0]);
				Long itemid = Long.valueOf(ratingTokens[1]);
				int rating = Integer.valueOf(ratingTokens[2]);
				int timestamp = Integer.valueOf(ratingTokens[3]);
				ratings.add(new Rating(userid, itemid, rating,timestamp));
			} 
			else 
			{
			throw new Exception("Invalid Ratings length: " + ratingTokens.length);
			}
		}
		return ratings;
	}

}

	
	
	
	
	
	

