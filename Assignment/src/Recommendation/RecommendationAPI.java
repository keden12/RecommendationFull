package Recommendation;


import java.util.ArrayList;


import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;







import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.base.Optional;

import utils.CSVLoader;
import utils.Serializer;
import values.Movie;
import values.Rating;
import values.User;








public class RecommendationAPI {
	
  private static Serializer serializer;
  private static Map<Long, User> users = new HashMap<>();
  //private static Map<Long, Admin> admins = new HashMap<>();
  static Map<Long, Movie> movies = new HashMap<>();
  private static Map<Long, Rating> ratings = new HashMap<>();
  private static Map<String, Movie> movietitle = new HashMap<>();
  private static Map<String, Movie> moviedate = new HashMap<>();
  private static Map<String, User> Login = new HashMap<>();
 // private static Map<String, Admin> AdminLogin = new HashMap<>();
  
  public RecommendationAPI()
  {
	  
  }
  
  @SuppressWarnings("static-access")
public RecommendationAPI(Serializer serializer)
  {
	 this.serializer = serializer; 
  }
  
  
  
//  public static Admin addAdmin(String fname, String lname, int age,String gender,String username,String password)
 // {
//	  Admin admin = new Admin(fname,lname,age,gender,username,password);
//	  AdminLogin.put(admin.username,admin);
//	  return admin;
 // }
  
  
  
  
  
  
  public static Movie addMovie(String title,String releasedate,String url)
  {
	  
	 Movie movie = new Movie(title,releasedate,url);
	 movies.put(movie.movieid, movie);
	 movietitle.put(title,movie);
	 moviedate.put(releasedate,movie);
	 return movie;
	
  }
  
  public static Rating addRating(Long userid,Long itemid,int rating,int timestamp)
  {
	    Rating ratingsure = null;
	    Optional<User> user = Optional.fromNullable(users.get(userid));
	    if (user.isPresent())
	    {
	     ratingsure = new Rating (userid,itemid,rating,timestamp);
	      user.get().ratingsUser.put(ratingsure.userid, ratingsure);
	      ratings.put(ratingsure.userid, ratingsure);
	    }
	    return ratingsure;
	  }
  
  


public static User addUser(String firstName, String lastName, int age, String gender,String occupation,String zip,String username,String password) 
  {
    User user = new User (firstName, lastName, age, gender,occupation,zip,username,password);
    users.put(user.id, user);
    Login.put(user.username, user);
    
    return user;
  }
  
  public static User getUser(Long userid)
  {
	  return users.get(userid);
  }
  

public void removeUser(Long id) 
  {
	  User user = getUser(id);
	  users.remove(user.id);  
	  
  }
public User getUserByUsername(String username)
	{
		return Login.get(username);
}

//public Admin getAdminByUsername(String username)
//{
//	return AdminLogin.get(username);
//}

  public static Movie getMovie(Long movieid) 
  {
    return movies.get(movieid);
  }
  
  public static Rating getUserRatings(Long userid) 
  {
    return ratings.get(userid);
  }

  public static Movie getMoviesByTitle(String title)
  {
	  
	  return movietitle.get(title);
  }
  public Collection<User> getUsers()
  {
    return users.values();
  }
  public Collection<Movie> getMovies()
  {
    return movies.values();
  }
  
  public Collection<Rating> getRatings()
  {
    return ratings.values();
  }
  
  
  public void RemoveMovie(Long id)
  {
	  Movie movie = getMovie(id);
	  movies.remove(movie.movieid);  
  }
  
	public boolean authenticate(String username, String password)
	{

		if (Login.containsKey(username))
		{
			User user = Login.get(username);
			if (user.password.matches(password))
			{
				return true;
			}
		}
		return false;
}
	
//	public boolean AdminAuthenticate(String username, String password)
//	{

//		if (AdminLogin.containsKey(username))
//		{
//			Admin admin = AdminLogin.get(username);
//			if (admin.password.matches(password))
//			{
//				return true;
//			}
//		}
//		return false;
// }
	
//	public static Collection<Admin> getAdmins()
//	{
//		return AdminLogin.values();
		
//	}
	public List<Movie> getUserRecommendations(long userId)
	{

		User currentUser = getUser(userId);
		if (currentUser.ratingsUser.size() > 0) 
		{
			Set<Long> targetUsers = new HashSet<>();
			
			List<Rating> currentUserRatings = new ArrayList<>(currentUser.ratingsUser.values());

			for (Rating currentUserRating: currentUserRatings)
			{
				Movie movie = getMovie(currentUserRating.itemid);
				if (movie.ratings.size() > 1)
				{
					targetUsers.addAll(movie.ratings.keySet());
				}
			}
			
			Long mostSimilar = 0L;
			int totalSimilarity = 0;
			targetUsers.remove(currentUser.id);
//			System.out.println(targetUsers);
			if (targetUsers != null && targetUsers.size() > 0) 
			{
				for (Long targetId: targetUsers)
				{
					User targetUser = getUser(targetId);
					int currentSimilarity = 0;
					int targetRating = 0;
					int currentRating = 0;
					
					for (Rating targetUserRating: targetUser.ratingsUser.values())
					{
						Movie movie = getMovie(targetUserRating.itemid);			
						
						if (currentUser.ratingsUser.containsKey(targetUserRating.itemid))
						{
							targetRating = targetUserRating.rating;
							currentRating = currentUser.ratingsUser.get(movie.movieid).rating;
							currentSimilarity += (currentRating * targetRating);
						}
					}
					
					if (currentSimilarity > totalSimilarity)
					{
						totalSimilarity = currentSimilarity;
						mostSimilar = targetId;
					}						
				}
			}
			
			User mostSimilarUser = getUser(mostSimilar);
			System.out.println(mostSimilarUser);
			List<Movie> recommendedMoviesList = new ArrayList<>();
			if (mostSimilarUser != null) 
			{
				for (Rating rating: mostSimilarUser.ratingsUser.values())
				{
					if (rating.rating >=3 && !currentUser.ratingsUser.containsKey(rating.itemid))
					{
						recommendedMoviesList.add(getMovie(rating.itemid));
					}
				}
			}
			return recommendedMoviesList;
		}
		else
		{
			return null;
		}
}
	public List<Movie> searchMovies(String prefix)
	{
		List<Movie> searchMovies = new ArrayList<>(movies.values());
		return filter(searchMovies, prefix);
    }
	
	private static List<Movie> filter(final Collection<Movie> source, final String prefix) 
	{

		return source.stream().filter(item -> item.title.toLowerCase().startsWith(prefix.toLowerCase())).collect(Collectors.toList());
    }
	
  
  public static Movie getMoviesByDate(String date)
  {
	  
	  return moviedate.get(date);
 
  }
 
  
  

@SuppressWarnings("unchecked")
public void load() throws Exception
{
	serializer.read();
	users = (Map<Long, User>) serializer.pop();
	movies  = (Map<Long, Movie>) serializer.pop();
	ratings = (Map<Long, Rating>) serializer.pop();
//	AdminLogin = (Map<String, Admin>) serializer.pop();
    User.counter = (Long)serializer.pop();
    Movie.counter = (Long)serializer.pop();
}



static void store() throws Exception
{
//	serializer.push(AdminLogin);
	serializer.push(User.counter);
	serializer.push(Movie.counter);
	serializer.push(users);
	serializer.push(movies);
	serializer.push(ratings);
	serializer.write();

}

public void InitialLoad() throws Exception
{
CSVLoader loader = new CSVLoader();
List <User> usersdata = loader.loadUsers("data_movieLens/users.dat");
for(User user: usersdata)
{
users.put(user.id,user);
}
//List <Admin> adminsdata = loader.loadAdmins("data_movieLens/admin.dat");
//for(Admin admin: adminsdata)
//{
//AdminLogin.put(admin.username,admin);
//}
List <Movie> moviedata = loader.loadMovies("data_movieLens/items.dat");
for(Movie movie:  moviedata )
{
movies.put(movie.movieid, movie);
}
List <Rating> ratingdata = loader.loadRatings("data_movieLens/ratings.dat");
for(Rating rating:  ratingdata )
{
ratings.put(rating.userid, rating);
}
 }





}
