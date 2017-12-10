package models;

import static models.Fixtures.users;
import static models.Fixtures.movies;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import values.Movie;

public class MovieTest {

	Movie legend = new Movie ("I am Legend", "12/06/2009","http://imbd.com/legend");

	@Test
	public void testCreate()
	{
			    assertEquals ("I am Legend",               legend.title);
			    assertEquals ("12/06/2009",             legend.releasedate);
			    assertEquals ("http://imbd.com/legend",   legend.url);   
	}

			  @Test
			  public void testIds()
			  {
			    Set<Long> ids = new HashSet<>();
			    for (Movie movie : movies)
			    {
			      ids.add(movie.movieid);
			    }
			    assertEquals (users.length, ids.size());
			  }
			  
			  
			  @Test
			  public void testToString()
			  {
			    assertEquals ("Movie{" + legend.movieid +", I am Legend, 12/06/2009, http://imbd.com/legend}", legend.toString());
			  }
			  
			  @Test
			  public void testEquals()
			  {
			    Movie movie2   = new Movie ("8 Mile", "4/9/2006","http://imbd.com/8mile");

			    assertEquals(legend, legend);
			    assertNotEquals(legend, movie2);
			  } 

	
	
	
	
	
}
