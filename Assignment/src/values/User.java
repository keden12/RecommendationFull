package values;




import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Objects;

import static com.google.common.base.MoreObjects.toStringHelper;


public class User {
      
	
	public static Long counter = 0l;
	
	
	  public String fname;
	  public String lname;
	  public int age;
	  public String gender;
	  public String occupation;
	  public String zip;
	  public Long id;
	  public String username;
	  public String password;
	  public String role;
	
	  public Map<Long, Rating> ratingsUser = new HashMap<>();
	
	  
	
	  public User(String fname, String lname, int age, String gender,String occupation ,String zip, String username, String password)
	  {
	    this.fname= fname;
	    this.lname = lname;
	    this.age = age;
	    this.gender = gender;
	    this.occupation = occupation;
	    this.id        = counter++;
	    this.zip = zip;
	    this.username = username;
	    this.password = password;
	    this.role = "user";
	  }

	  
	  
	  public void setAdmin()
	  {
		  this.role = "admin";
	  }
	  
	  
	  
	  

	  public int hashCode()  
	  {  
	     return Objects.hashCode(this.fname, this.lname, this.age, this.gender,this.occupation,this.zip,this.username,this.password);  
	  }
	  
	
	  public String toString()
	  {
	    return toStringHelper(this).addValue(id)
	    		                   .addValue(fname)
	                               .addValue(lname)
	                               .addValue(age)
	                               .addValue(gender)
	                               .addValue(occupation)
	                               .addValue(zip)
	                               .toString();
	  }
	  
	  
	  
	  
	  
	  
	  
	  
	  @Override
	  public boolean equals(final Object obj)
	  {
	    if (obj instanceof User)
	    {
	      final User other = (User) obj;
	      return Objects.equal(fname, other.fname) 
	          && Objects.equal(lname,  other.lname)
	          && Objects.equal(age,     other.age)
	          && Objects.equal(gender,  other.gender)
	          && Objects.equal(occupation,  other.occupation)
	          && Objects.equal(id,  other.id)
	          && Objects.equal(zip,  other.zip)
	           && Objects.equal(ratingsUser, other.ratingsUser)
	          && Objects.equal(username,  other.username)
	          && Objects.equal(password,  other.password);
	    
	    }
	    else
	    {
	      return false;
	    }
	  }
	
	
	
	
	
	
	
}
