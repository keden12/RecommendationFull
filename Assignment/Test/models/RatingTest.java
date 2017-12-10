package models;


import static org.junit.Assert.*;


import org.junit.Test;

import values.Rating;

public class RatingTest {
	
	
	
	Rating legend = new Rating(Long.valueOf(1),Long.valueOf(2),3,88725);
	
	
	@Test
	public void testCreate()
	{
			    assertEquals (Long.valueOf(1),               legend.userid);
			    assertEquals (Long.valueOf(2),             legend.itemid);
			    assertEquals (3,                          legend.rating);  
			    assertEquals(88725,                       legend.timestamp);
	}
			  
			  
			  @Test
			  public void testToString()
			  {
			    assertEquals ("Rating{" + legend.userid +", "+legend.itemid+", "+legend.rating+", "+legend.timestamp+"}", legend.toString());
			  }
			  
			  @Test
			  public void testEquals()
			  {
			   Rating rating2  = new Rating (Long.valueOf(3),Long.valueOf(5),2,88725);

			    assertEquals(legend, legend);
			    assertNotEquals(legend, rating2);
			  } 
			 
		

}
