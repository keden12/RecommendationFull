package Recommendation;

import java.io.File;



import java.util.Collection;
import java.util.Scanner;

import utils.Serializer;
import utils.XMLSerializer;
import values.Movie;
import values.User;
import asg.cliche.*;

public class Client {
    //creating new file 
    static File  datastore = new File("datastore.xml");
    static Serializer serializer = new XMLSerializer(datastore);
    static RecommendationAPI recommendation = new RecommendationAPI(serializer);
	
	//currently logged in user
	static User CurrentlyLogged; 
	//currently logged in admin
	static User AdminLogged; 
	static boolean check = false; 
	static boolean errorfix = false;
	
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {

	    
            //executes this code before anything else
	    //loads the data or else creates the file with data
	    if (datastore.isFile())
	    {
	      recommendation.load();
	      
	    }
	    else
	    {
	    recommendation.InitialLoad();
	    }
	    recommendation.addUser("Jamie","Sagan",18,"M","Carpenter","123213213","jamie12","hello123");
	    recommendation.addUser("Kamil","Bigos",18,"M","Student","12345","keden12","admin");
	     
	    AdminLogged = recommendation.getUserByUsername("keden12");
	    AdminLogged.setAdmin();
	    
	    RecommendationAPI.store(); 
	    
	    
	    //authenticates
	    auth();
	    if(errorfix == true)
	    {
	    loggedIn();
	    }
	    else{
	    	System.out.println("Exited Successfully");
	    }

	   
	}
	
	   @Command(description="Get all users details")
	    public static void getUsers()
	    {

		   Collection<User> users = recommendation.getUsers();
          System.out.println(users);
	    }
	   
	   
       //login menu
	@SuppressWarnings({ "resource", "unused" })
	public static void loginMenu() throws Exception
		{
		    check = false;
		    Scanner in = new Scanner(System.in);
		    Scanner str = new Scanner(System.in);
			String username;
			String password;
			System.out.println("Authentication process");
			System.out.println("Enter your username: ");
			username = in.nextLine();
			System.out.println("Enter your password: ");
			password = in.nextLine();
			while(!recommendation.authenticate(username, password))
			{
				System.out.println("------Log in failed------");
				System.out.println("Make sure that you input correct details");
				System.out.println("Enter your username: ");
				username = in.nextLine();
				System.out.println("Enter your password: ");
				password = in.nextLine();
			}

				System.out.println("Logged in successfully!");
				check = true;
			

				CurrentlyLogged = recommendation.getUserByUsername(username);
			}
			
	
	   
	   //authentication process
	   @SuppressWarnings("resource")
	public static void auth() throws Exception
	   {

		   Scanner in = new Scanner(System.in);
		   System.out.println("----------Welcome to RecommendMe-------");
		   System.out.println("Press ENTER to see a list of options");
		   in.nextLine();
		   System.out.println("1. Log In");
		   System.out.println("2. Sign Up");
		   System.out.println("3. Exit");
		   System.out.println("Enter in your choice:");
		   int ans = in.nextInt();
		   while(ans > 3 || ans < 1)
		   {
			   System.out.println("Incorrect input, please enter another number [1-3]:");
			   ans = in.nextInt();
		   }
		  if(ans == 1)
		  {
			  loginMenu();
			  errorfix = true;
		  }
		  else if(ans == 2)
		  {
			  
			  addUser();
			  errorfix = true;
			  
		  }
		   
	   }
	   //Sign Up process
	   @SuppressWarnings({ "static-access", "resource" })
	public static void addUser() throws Exception
		{
		   Scanner in = new Scanner(System.in);
		   Scanner ages = new Scanner(System.in);

			System.out.println("Please enter in your details in order to Sign Up\n");

			System.out.println("Enter in Your first name: ");
			String firstName = in.nextLine();

			System.out.println("Enter your last name: ");
			String lastName = in.nextLine();

			//Loop if non-numerical or within range
			int age = 0;
				
					System.out.println("Enter in Your age:");
					age = ages.nextInt();
					while(age > 99 || age < 1)
					{
						System.out.println("Enter in a proper age between 1-99:");
						age = ages.nextInt();
					}
				
			

			System.out.println("What's Your gender? (M/F/N)");
			String gender = in.nextLine();
			while(!gender.equals("M")&&!gender.equals("F")&&!gender.equals("N"))
			{
				System.out.println("Invalid gender, Please enter M/F/N:");
				gender = in.nextLine();
			}



			System.out.println("Enter Your occupation: ");
			String occupation = in.nextLine();
			
			System.out.println("Enter Your ZIP Code: ");
			String zip = in.nextLine();

			//Loops to check that the username is unique
			boolean unique = false;
			String username = null;
			while (!unique) 
			{
				System.out.println("Enter a username: ");
				username = in.nextLine();
				Collection<User> checkUsername = recommendation.getUsers();
				if (!checkUsername.contains(username)) //If username is unique, break from loop
				{
					unique = true;
				}
				else
				{
					System.out.println("Username chosen is not unique");
				}
			}

			System.out.println("Enter a password: ");
			String password = in.nextLine();

			User addedUser = recommendation.addUser(firstName, lastName, age, gender, occupation,zip, username, password);
			recommendation.store();
			CurrentlyLogged = addedUser;
			System.out.println("Your details have been saved!");
	}
	   
	   //When logged in
	   @SuppressWarnings({ "resource" })
	public static void loggedIn() throws Exception
	   {
		   
		   if(CurrentlyLogged.role.equals("admin"))
		   {
			  int choice = 0;
			  do{
			   Scanner in = new Scanner(System.in);
			   System.out.println("Welcome Admin "+CurrentlyLogged.username+",Below is your Menu!");
			   System.out.println("--------ADMIN MENU------------");
			   System.out.println("1. Remove a User ");
			   System.out.println("2. Promote to Admin ");
			   System.out.println("3. Revoke Admin ");
			   System.out.println("4. Remove a Movie ");
			   System.out.println("5. Add a new movie");
			   System.out.println("6. Rate a movie");
			   System.out.println("7. Search movies");
			   System.out.println("8. Get a Personal Recommendation");
			   System.out.println("9. Search users");
			   System.out.println("10. Exit");
			   System.out.println("Enter your choice >>");
			   choice = in.nextInt();
			   while(choice > 10 || choice < 1)
			   {
				   System.out.println("Incorrect input! Enter a choice between 1-9:");
				   choice = in.nextInt();
			   }
			   switch(choice)
			   {
			   case 1:
				   RemoveUser();
				   break;
			    
			   case 2:
				   Promote();
				   break;
			   case 3:
				   Revoke();
				   break;
			   case 4:
				   removeMovie();
				   break;
				   
			   case 5:
				   addMovie();
				   break;
			   case 6:
				   addARating();
				   break;
			   case 7:
				   searchMovies();
				   break;
				   
			   case 8:
				   personalRec();
				   break;
			   case 9:
				   getUsername();
				   break;
			   case 10:
				   System.out.println("You exited the program!");
			   }
			  }while(choice != 10);
				   
		   }
		   else
		   {
			   Scanner in = new Scanner(System.in);
			   int choice = 0;
			  do{
			   System.out.println("Welcome User "+CurrentlyLogged.username+",Below is your Menu!");
			   System.out.println("--------USER MENU------------");
			   System.out.println("1.Rate a movie");
			   System.out.println("2.Add a movie");
			   System.out.println("3.Search movies");
			   System.out.println("4.Search for Users");
			   System.out.println("5.Get personal recommendations");
			   System.out.println("6.Delete account");
			   System.out.println("7.Exit");
			   System.out.println("Enter your choice >>");
			   choice = in.nextInt();
			   while(choice > 7 || choice < 1)
			   {
				   System.out.println("Incorrect choice! Enter your choice between 1-9:");
				   choice = in.nextInt();
			   }
			   switch(choice)
			   {
			   
			   case 1:
				   addARating();
				   break;
			   case 2:
				   addMovie();
				   break;
				   
			   case 3:
				   searchMovies();
				   break;
			   case 4:
				   getUsername();
				   break;
			   case 5:
				   personalRec();
				   break;
			   case 6:
				   deleteAccount();
			   case 7:
				   System.out.println("You have exited the program!");
			   }
			  }while(choice != 7 && choice != 6);
			   
			   
			   
			   
			   
			   }
			    
			   
			   
		   }
		   
		   
	   //remove a user
	   @SuppressWarnings("resource")
	public static void RemoveUser()
	   {
		   Scanner in = new Scanner(System.in);
		   System.out.println("Enter The Username of the User to delete:");
		   String username = in.nextLine();
		   User delete = recommendation.getUserByUsername(username);
		   recommendation.removeUser(delete.id);
  
	   }
	   
	   
        //delete account
	@SuppressWarnings("static-access")
	public static void deleteAccount() throws Exception
	   {

	
		   recommendation.removeUser(CurrentlyLogged.id);
		   System.out.println("User "+CurrentlyLogged.username+" successfully removed!");
		   System.out.println("Exiting the program...");
		   recommendation.store();

	   }
	   
        //promote as admin 
	   @SuppressWarnings({ "resource", "static-access" })
	public static void Promote() throws Exception
	   {
		   Scanner in = new Scanner(System.in);
		   System.out.println("Enter the Username of the User to set as Admin:");
		   String username = in.nextLine();
		   User promote = recommendation.getUserByUsername(username);
		   promote.setAdmin();
		   recommendation.store();
 
	   }
	   
        //personal recommendations
		public static void personalRec()
		{
			if (CurrentlyLogged.ratingsUser.size() > 0)
			{
				System.out.println(recommendation.getUserRecommendations(CurrentlyLogged.id));
			}
			else
			{
				System.out.println("No recommendations for you at the moment. We suggest you to rate more movies");
			}

	}
	   
        //get user by username
		@SuppressWarnings("resource")
		public static void getUsername()
		{
			Scanner in = new Scanner(System.in);
			System.out.println("Enter the Username: ");
			String username = in.nextLine();
			System.out.println(recommendation.getUserByUsername(username));
		}
	   
	
        //revoke admin
	   @SuppressWarnings({ "resource", "static-access" })
	public static void Revoke() throws Exception
	   {
		   Scanner in = new Scanner(System.in);
		   System.out.println("Enter the Username of the Admin to Revoke his Powers:");
		   String username = in.nextLine();
		   User revoke = recommendation.getUserByUsername(username);
		   revoke.role = "user";
		   System.out.println("Admin "+revoke.username+" has been revoked!");
		   recommendation.store();
	   }
	   
        //delete movie
		@SuppressWarnings({ "resource", "static-access"})
		public static void removeMovie() throws Exception
		   {
			   Scanner in = new Scanner(System.in);
			   System.out.println("Enter the Movie ID of the Movie to delete:");
			   long id = in.nextLong();
			   Movie remove = recommendation.getMovie(id);
			   recommendation.RemoveMovie(id);
			   System.out.println(remove.title+" has beed successfully removed!");
			   recommendation.store();
		   }
		
        //add movie process
		@SuppressWarnings({ "static-access", "unused", "resource" })
		public static void addMovie() throws Exception
		{
			Scanner yr = new Scanner(System.in);
			Scanner in = new Scanner(System.in);
			System.out.println("Enter in the following details to add a movie:");

			boolean unique = false;
			String title = "";
			String date = "";

				System.out.println("Enter movie title: ");
				title = in.nextLine();

				System.out.println("Enter the date the movie was released(eg. 12/05/2015):");
				 date = in.nextLine();


			System.out.println("Enter IMDb url of the movie:");
			String url = in.nextLine();
			if (url.isEmpty())
			{
				url = "No URL";
			}

			int rating = 0;
		    boolean test2 = false;
			while (!test2) 
			{
				try 
				{
					System.out.println("Enter rating for this movie(-5 being the lowest and 5 being the highest):");
					rating = yr.nextInt();
					if (rating >=-5 && rating <= 5)
					{
						test2 = true;
					}
					else
					{
						System.out.println("Integer values between -5 and 5 only!");
					}
				} 
				catch (Exception e) 
				{
					
					System.out.println("Integer values only!");
				}
			}
			
			System.out.println("Enter the timestamp for this movie:");
			int timestamp = yr.nextInt();
			Movie movie = recommendation.addMovie(title, date, url);
			recommendation.addRating(CurrentlyLogged.id, movie.movieid, rating,timestamp);

			System.out.println("Movie added successfully!");
			recommendation.store();
		}

		   
	   
	  //add rating process
	   @SuppressWarnings({ "static-access", "resource" })
	public static void addARating() throws Exception
		{
		   
		    Scanner in = new Scanner(System.in);
			long mvId = 0L; //The movie id
			int rating = 0;

			//Loop to ensure the id is a number and the movie id exists in the system
			boolean number = false;
			while (!number) 
			{
				try 
				{
					System.out.println("Enter movie Id of movie you want to add rating: ");
					mvId = in.nextInt();
					//				StdIn.readString();

					if(recommendation.movies.containsKey(mvId))
					{
						System.out.println(recommendation.getMovie(mvId));
						number = true;
					}
					else 
					{
						System.out.println("Movie id does not exist");
					}
				}
				catch (Exception e) 
				{
					System.out.println("Numerical values only");					
				}
			}

			//Loop to ensure the rating is within the range and is numerical
			boolean test = false;
			while (!test) 
			{
				try 
				{
					System.out.println("Rate this movie(-5 being the worst and 5 being the best):");
					rating = in.nextInt();
					if(rating >= -5 && rating <= 5)
					{
						test = true;
					}
					else 
					{
						System.out.println("Enter an int between -5 and 5!");
					}
				}
				catch (Exception e) 
				{
					System.out.println("Integer values only");					
				}
			}
			System.out.println("Enter in a Timestamp:");
			int timestamp = in.nextInt();
			System.out.println("Successfully rated!");
			recommendation.addRating(CurrentlyLogged.id, mvId, rating,timestamp);
			recommendation.store();
		}

	   
	   
	   
	   
	   
	   //search for movies
		@SuppressWarnings("resource")
		public static void searchMovies()
		{
			Scanner in = new Scanner(System.in);
			System.out.println("Enter your search: ");
			String prefix = in.nextLine();
			System.out.println(recommendation.searchMovies(prefix));
	     }
	

	
}


