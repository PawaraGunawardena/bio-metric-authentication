package databaseconnectivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteTest {
	private static Connection con;
	private static boolean hasData = false;
	
	
//	public static void main(String[] args){
//		try {
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			System.out.println("Can't romove 1");
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			System.out.println("Can't romove 2");
//			e.printStackTrace();
//		}
//	}
	public SQLiteTest(){
		
	}
	
	
	
	public ResultSet getUser(String username) throws SQLException, ClassNotFoundException {
	    if(con == null) {
	        // get connection
	        getConnection();
	    }
	    Statement state = con.createStatement();
	    ResultSet res = state.executeQuery("select average from user where username ='"+username+"' ;");
	    
	    
	    return res;
	    
	}
	public int getUserAverage(String username) throws SQLException, ClassNotFoundException {
	    if(con == null) {
	        // get connection
	        getConnection();
	    }
	    Statement state = con.createStatement();
	    ResultSet res = state.executeQuery("select average from user where username ='"+username+"' ;");
	    int average = res.getInt("average");
	    return average;
	}
	public int getUserVariance(String username) throws SQLException, ClassNotFoundException {
	    if(con == null) {
	        // get connection
	        getConnection();
	    }
	    Statement state = con.createStatement();
	    ResultSet res = state.executeQuery("select variance from user where username ='"+username+"' ;");
	    int average = res.getInt("variance");
	    return average;
	}
	public int getUserStandardDeviation(String username) throws SQLException, ClassNotFoundException {
	    if(con == null) {
	        // get connection
	        getConnection();
	    }
	    Statement state = con.createStatement();
	    ResultSet res = state.executeQuery("select standard_deviation from user where username ='"+username+"' ;");
	    int average = res.getInt("standard_deviation");
	    return average;
	}
	
	
	public void addUser(String username, long average, long variance, long standard_deviation) throws ClassNotFoundException, SQLException {
	     if(con == null) {
	         // get connection
	         getConnection();
	     }
	      PreparedStatement prep = con
	                .prepareStatement("insert into user values(?,?,?,?,?);");
	              prep.setString(2, username);
	              prep.setInt(3, (int)average);
	              prep.setInt(4, (int)variance);
	              prep.setInt(5, (int)standard_deviation);
	              prep.execute();
	      
	 }

	
	private void getConnection() throws ClassNotFoundException, SQLException {
	     // sqlite driver
	     Class.forName("org.sqlite.JDBC");
	     // database path, if it's new database, it will be created in the project folder
	     con = DriverManager.getConnection("jdbc:sqlite:Auth.db");
	     initialise();
	}
	
	
	private void initialise() throws SQLException {
//		hasData=true;
//		try {
//			dropTable();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		hasData = false;
	    if( !hasData ) {
	        
	        // check for database table
	        Statement state = con.createStatement();
	        ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='user'");
	        if( !res.next()) {
	            System.out.println("Building the User table with prepopulated values.");
	            // need to build the table
	             Statement state2 = con.createStatement();
	             state2.executeUpdate("create table user(id integer,"
	               + "username varchar(60)," + "average integer, variance integer, standard_deviation integer," + "primary key (id));");

	        }
	    }
	}
	
	public static void dropTable() throws ClassNotFoundException, SQLException{
		Class.forName("org.sqlite.JDBC");
	     // database path, if it's new database, it will be created in the project folder
	     con = DriverManager.getConnection("jdbc:sqlite:Auth.db");
	     if( hasData ) {
		        hasData = false;
		        // check for database table
//		        Statement state = con.createStatement();
		        //ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='user'");
		        //if( res.next()) {
		            System.out.println("Removing the User table with prepopulated values.");
		            // need to build the table
		            Statement state2 = con.createStatement();
		            
		            String query = "DROP TABLE IF EXISTS 'Auth.user'";
		            System.out.println("dropping output "+state2.execute(query));
		            state2.close();
		            //con.commit();
		            System.out.println("Removed the User table");
		            
		        //}else{
		        	//System.out.println("Can't romove 4");
		        //}
		    }else{
		    	System.out.println("Can't romove. It do not exist.");
		    }
	}
}
