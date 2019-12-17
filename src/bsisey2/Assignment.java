package bsisey2;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.xerces.impl.dv.xs.DayDV;
import org.hibernate.Session;
import org.hibernate.Transaction;

import hibernate .*;

public class Assignment {

	public static boolean isExistUsername (String username) {
		boolean userExist = false;
		try {
			Session currentSession = HibernateUtil.currentSession();
			String query = "select users from Users users where username = " + username ;
			List<Users> usersList = currentSession.createQuery(query).list();
			userExist = usersList.size() > 0 ;
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			HibernateUtil.closeSession();
		}
		return userExist ;
	}

	public static String insertUser(String username, String password, String first_name, String
			last_name, String day_of_birth, String month_of_birth, String year_of_birth) {
		
		if(isExistUsername(username)) {
			return null;
		}
		
		try {
			Timestamp birthDate = getBirthDate(day_of_birth, month_of_birth, year_of_birth);
			Timestamp currentTime = getCurrentTimestamp();
			Session currentSession = HibernateUtil.currentSession();
			Users newUser = new Users();
			newUser.setFirstName(first_name);
			newUser.setLastName(last_name);
			newUser.setUsername(username);
			newUser.setPassword(password);
			newUser.setDateOfBirth(birthDate);
			newUser.setRegistrationDate(currentTime);
			Transaction currentTransaction = currentSession.beginTransaction();
			currentSession.saveOrUpdate(newUser);
			currentTransaction.commit();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			HibernateUtil.closeSession();
		}
		
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
	
	public static List getHistory (String userid){
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

	private static Timestamp getBirthDate(String day, String month, String year) {
		Timestamp timestamp ;
		String pattern = "yyyy-MM-dd";
		String date = day + "-" + month + "-" + year ;
		try {
			DateFormat simpleFormat = new SimpleDateFormat(pattern);
			timestamp = new Timestamp(simpleFormat.parse(date).getTime());
			return timestamp;
		}catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	private static Timestamp getCurrentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}
}