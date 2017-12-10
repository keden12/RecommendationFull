package values;

import static com.google.common.base.MoreObjects.toStringHelper;


import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Objects;

public class Rating {

	
	public Long userid;
	public Long itemid;
	public int rating;
	public int timestamp;
	
	 public Map<Long, User> usersRatings = new HashMap<>();
	
	public Rating(Long userid,Long itemid,int rating,int timestamp)
	{
		this.userid = userid;
		this.itemid = itemid;
		this.rating = rating;
		this.timestamp = timestamp;
		
		
	}
	
	
	public int getRating() {
		return rating;
   }
	
	public int hashCode()  
	 {  
	    return Objects.hashCode(this.userid, this.itemid, this.rating, this.timestamp);  
	 }
	
	  public String toString()
	  {
	    return toStringHelper(this).addValue(userid)
	                               .addValue(itemid)
	                               .addValue(rating)
	                               .addValue(timestamp)
	                               .toString();
	  }
	  
	  @Override
	  public boolean equals(final Object obj)
	  {
	    if (obj instanceof Rating)
	    {
	      final Rating other = (Rating) obj;
	      return Objects.equal(userid, other.userid) 
	          && Objects.equal(itemid,  other.itemid)
	          && Objects.equal(rating,     other.rating)
	          && Objects.equal(timestamp,  other.timestamp);

	    }
	    else
	    {
	      return false;
	    }
	  }
	
	
	
	
	
}
