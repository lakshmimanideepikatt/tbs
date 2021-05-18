import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
   public class MovieTicketBooking
    {
    public static void main(String[] args) throws ClassNotFoundException
     	{
    		try {
				Insertor in=new Insertor();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
    		Class.forName("org.sqlite.JDBC");
    		Connection connection = null;
    		try {
    			connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
    			Statement statement = connection.createStatement();
    			while(true) {
    			Scanner sc=new Scanner(System.in);
    			System.out.println("Select a theater from the following using id");
    			ResultSet theaterresult=statement.executeQuery("SELECT * FROM Theaters");
    			while(theaterresult.next()) {
    				System.out.println(theaterresult.getString("Theatername")+" "+theaterresult.getString("Theaterid"));
    			}
    			String selectedtheater=sc.nextLine();
    			System.out.println("Select screen from that theater A or B");
    			String selectedscreen=sc.nextLine();
    			System.out.println("Select slot from given slots ");
    			ResultSet slotresult=statement.executeQuery("SELECT * FROM Slots where Theaterid='" + selectedtheater + "'");
    			//ResultSet slotresult=statement.executeQuery("SELECT * FROM Slots");

    			String date="2018-05-17";
    			boolean Fal=false;
    			while(slotresult.next()) {
    				String slot=slotresult.getString("Slot");
    			//	String slot="2:00 PM";
//    				ResultSet availabilityseats=statement.executeQuery("Select SeatNo from Seats where theaterid='" + selectedtheater + "' and date='" + date + "' and Slot='"+slot+ "' and screen='" +selectedscreen+"' and Availability IS'" +Fal+ "'");
//    				int count=availabilityseats.getFetchSize();
//    				String avai="";
//    				if(count>10&&count<=20) {
//    					avai="Available";
//    				}
//    				else if(count>0&&count<=10) {
//    					avai="Limited seats available";
//    				}
//    				else {
//    					avai="No seats available";
//    				}
    				System.out.println(slot);
    			}
    			String selectedslot="10:30 AM";
				ResultSet availability=statement.executeQuery("Select SeatNo from Seats where theaterid='" + selectedtheater + "' and date='" + date + "' and Slot='"+selectedslot+ "' and screen='" +selectedscreen+"' and Availability IS'" +Fal+ "'");
				System.out.println("Select seats from below");
				HashSet<Integer>hs=new HashSet<Integer>();
				while(availability.next()) {
					System.out.print(availability.getInt("SeatNo")+" ");
					hs.add(availability.getInt("SeatNo"));
				}
				System.out.println("Enter -1 to stop booking a seat");
				ArrayList<Integer>al=new ArrayList<Integer>();
				while(true) {
					int n=sc.nextInt();
					if(n==-1)
						break;
					if(!hs.contains(n)||n>20||n<1) {
						System.out.println("Enter a valid number");
						continue;
					}
					boolean tr=true;
			        statement.executeUpdate("UPDATE Seats SET Availability='"+tr+"' WHERE theaterid='" + selectedtheater + "' and date='" + date + "' and Slot='"+selectedslot+ "' and screen='" +selectedscreen+"'");
			        hs.remove(new Integer(n));
			        al.add(n);
				}
				if(al.size()>0){
		            System.out.print("Congrats for booking seats :");
		            for(int i:al){
		                System.out.print(i+",");
		            }
		            System.out.println("!");
		        }
		        sc.nextLine();
		        System.out.println("Enter y or n, to continue booking select y otherwise n");
		        char se=sc.nextLine().charAt(0);
		        if(se=='n')
		         break;
			   }
    		}
    		catch(SQLException e){  System.err.println(e.getMessage()); }       
    		finally {         
    		      try {
    		            if(connection != null)
    		               connection.close();
    		       }
    		      catch(SQLException e) {        
    		         System.err.println(e); 
    		       }
    		}
     	}


	}


