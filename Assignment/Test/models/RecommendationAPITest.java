package models;


import static models.Fixtures.users;
import static models.Fixtures.movies;
import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import values.Movie;
import values.Rating;
import values.User;
import Recommendation.RecommendationAPI;

public class RecommendationAPITest
{
  private RecommendationAPI recommendation;

@SuppressWarnings("static-access")
@Before
  public void setup()
  {
    recommendation = new RecommendationAPI();
    for (User user : users)
    {
      
      recommendation.addUser(user.fname, user.lname, user.age, user.gender,user.occupation,user.zip,user.username,user.password);
    }
    for (Movie movie : movies)
    {
      
      recommendation.addMovie(movie.title, movie.releasedate,movie.url);
    }
  }

  @After
  public void tearDown()
  {
    recommendation = null;
  }

@SuppressWarnings({ "static-access" })
@Test
public void testUser()
  {
	int testadd = recommendation.getUsers().size();
	recommendation.addUser("Bartek", "Mrowicki",19 ," M", "Cathering Assistant" ,"20003","bartus123","bartek");
	assertEquals(testadd + 1,recommendation.getUsers().size());
    recommendation.removeUser(Long.valueOf(3));
    assertEquals(testadd,recommendation.getUsers().size());
  }


@SuppressWarnings("static-access")
@Test
public void testMovie()
{
	
	int testadd = recommendation.getMovies().size();
    Movie legend = new Movie("I am Legend", "12/10/2009","http://www.imbd.com");
	recommendation.addMovie("I am Legend", "12/10/2009","http://www.imbd.com");
	assertEquals(testadd + 1,recommendation.getMovies().size());
	 assertEquals (legend, recommendation.getMoviesByTitle("I am Legend"));
	 
	
}

@SuppressWarnings("static-access")
@Test
public void testRating()
{
	
	int testadd = recommendation.getRatings().size();
	recommendation.addRating(Long.valueOf(4), Long.valueOf(1), 3, 1234);
	assertEquals(testadd +1,recommendation.getRatings().size());

}

@SuppressWarnings("static-access")
@Test
public void getMovie()
{
	Movie test = recommendation.addMovie("Tarzan", "12/12/2009", "http://www.imbd.com");
	assertEquals(test,recommendation.getMoviesByTitle("Tarzan"));
	
	Movie test2 = recommendation.getMoviesByTitle("Tarzan");
	assertEquals(test,recommendation.getMovie(test2.movieid));
}

@SuppressWarnings("static-access")
@Test
public void getUserRatings() 
{
	Rating rating = recommendation.addRating(Long.valueOf(1), Long.valueOf(1), 3, 11234);
	assertEquals(rating,recommendation.getUserRatings(Long.valueOf(1)));
    
}

@SuppressWarnings("static-access")
@Test
public void getUserByUsername()
{
	
	User user = recommendation.addUser("Bartek", "Mrowicki",19 ," M", "Cathering Assistant" ,"20003","bartus123","bartek");
	assertEquals(user,recommendation.getUserByUsername("bartus123"));
	
}

@SuppressWarnings("static-access")
@Test
public void CheckIfUser()
{
	
	User user = recommendation.addUser("Bartek", "Mrowicki",19 ," M", "Cathering Assistant" ,"20003","bartus123","bartek");
	assertEquals(user.role,"user");
	
}

@SuppressWarnings({ "static-access" })
@Test
public void SearchMovies()
{
	
	Movie tarzan = recommendation.addMovie("Lego Tarzan", "12/12/2014", "www.imbd.com/");
	Collection<Movie> search = recommendation.searchMovies("Lego"); 
	assertEquals(search.contains(tarzan),true);

}

@SuppressWarnings("static-access")
@Test
public void getUserRecommendations()
{  
	Movie spiderman = recommendation.addMovie("Lego Spiderman", "12/08/2012", "www.imbd.com/");
	Movie batman = recommendation.addMovie("Lego Batman", "12/08/2012", "www.imbd.com/");
	Movie tarzan = recommendation.addMovie("Lego Tarzan", "12/12/2014", "www.imbd.com/");
	User user = recommendation.addUser("Bartek", "Mrowicki",19 ," M", "Cathering Assistant" ,"20003","bartus123","bartek");
	User user2 = recommendation.addUser("Kamil", "Bigos",18 ," M", "Programmer" ,"20003","keden12","admin");
	recommendation.addRating(user.id,batman.movieid,5,12345);
	recommendation.addRating(user.id,batman.movieid,4,12222);
	recommendation.addRating(user2.id,tarzan.movieid,5,12345);
	recommendation.addRating(user2.id,batman.movieid,4,12222);
	recommendation.addRating(user2.id,spiderman.movieid,5,12233);
    Collection<Movie> recommend = recommendation.getUserRecommendations(user.id);
    assertEquals(recommend.contains(spiderman),false);	
}



}
  
