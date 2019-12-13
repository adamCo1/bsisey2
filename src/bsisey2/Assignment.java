package bsisey2;

import antlr.collections.List;

public class Assignment {

	public static boolean isExistUsername (String username) {
		return true ;
	}

	public static String insertUser(String username, String password, String first_name, String
			last_name, String day_of_birth, String month_of_birth, String year_of_birth) {
		return null;
	}

	public static List<Mediaitems> getTopNItems (int top_n){
		return null ;
	}

	public static String validateUser (String username, String password) {
		return null;
	}
	
	public static String validateAdministrator (String username, String password) {
		return null ;
	}
	
	public static void insertToHistory (String userid, String mid) {
		return ;
	}
	
	public static List<String, Date> getHistory (String userid){
		return null ;
	}
	
	public static void insertToLog (String userid) {
		return ;
	}
	
	public static int getNumberOfRegistredUsers(int n) {
		return 0;
	}
	
	public static List<Users> getUsers (){
		return null ;
	}

	public static Users getUser (String userid) {
		return null ;
	}

	
}