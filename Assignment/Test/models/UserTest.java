package models;

import static models.Fixtures.users;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import values.User;



public class UserTest {
	
    
		User aaron = new User ("Aaron", "Cosulich",22 ,"M", "Cleaner" ,"278853","aaron123","aaronco");
        
@Test
public void testCreate()
{
	
		    assertEquals ("Aaron",               aaron.fname);
		    assertEquals ("Cosulich",             aaron.lname);
		    assertEquals (22,                        aaron.age);   
		    assertEquals ("M",              aaron.gender);   
		    assertEquals ("Cleaner",              aaron.occupation); 
		    assertEquals ("278853",              aaron.zip); 
		    assertEquals ("aaron123",              aaron.username); 
		    assertEquals ("aaronco",              aaron.password); 
}

		  @Test
		  public void testIds()
		  {
		    Set<Long> ids = new HashSet<>();
		    for (User user : users)
		    {
		      ids.add(user.id);
		    }
		    assertEquals (users.length, ids.size());
		  }
		  
		  
		  @Test
		  public void testToString()
		  {
		    assertEquals ("User{" + aaron.id + ", Aaron, Cosulich, "+22+", M, Cleaner, "+278853+"}", aaron.toString());
		  }
		  
		  @Test
		  public void testEquals()
		  {
		    User bart   = new User ("Bartek", "Cosulich", 22 ,"M", "Cathering Assistant" ,"276666","bartusiek123","bartus");

		    assertEquals(aaron, aaron);
		    assertNotEquals(aaron, bart);
		  } 
		  
		  @Test
		  public void testSetAdmin()
		  {
			  assertEquals ("user",aaron.role); 
			  aaron.setAdmin();
			  assertEquals("admin",aaron.role);  
		  }



}