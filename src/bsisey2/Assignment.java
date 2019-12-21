package bsisey2;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import hibernate .*;

public class Assignment {

	public static boolean isExistUsername (String username) {
		boolean userExist = false;
		try {
			Session currentSession = HibernateUtil.currentSession();
			String query = "select users from Users users where username = '" + username +"'" ;
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
		Users newUser = new Users();
		
		try {
			Timestamp birthDate = getBirthDate(day_of_birth, month_of_birth, year_of_birth);
			Timestamp currentTime = getCurrentTimestamp();
			Session currentSession = HibernateUtil.currentSession();
			newUser.setFirstName(first_name);
			newUser.setLastName(last_name);
			newUser.setUsername(username);
			newUser.setPassword(password);
			newUser.setDateOfBirth(birthDate);
			newUser.setRegistrationDate(currentTime);
			
			commitTransaction(currentSession, newUser);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			HibernateUtil.closeSession();
		}
		
		return "" + newUser.getUserid();
	}

	public static List<Mediaitems> getTopNItems (int top_n){
		
		List<Mediaitems> finalList = null ;
		try {
			Session currentSession = HibernateUtil.currentSession();
			String query = "select mediaitems from Mediaitems mediaitems " +
					"where rownum <= " + top_n + 
					" order by mid DESC";
			finalList = currentSession.createQuery(query).list();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			HibernateUtil.closeSession();
		}
		
		return finalList ;
	}

	public static String validateUser (String username, String password) {
		final String negativeAnswer = "Not Found";
		
		if(!isExistUsername(username)) {
			return negativeAnswer;
		}
		Users foundUser = null; 
		try {
			Session currentSession = HibernateUtil.currentSession();
			String query = "select users from Users users where username = '" + username +"'"
							+" AND password = '" + password + "'";
			List<Users> usersList = currentSession.createQuery(query).list();
			if(usersList.size() == 0) {
				return "" + negativeAnswer;
			}
			foundUser = usersList.get(0);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			HibernateUtil.closeSession();
		}
		
		return "" + foundUser.getUserid();
	}
	
	public static String validateAdministrator (String username, String password) {
		final String negativeAnswer = "Not Found";
		
		if(!isExistUsername(username)) {
			return negativeAnswer;
		}
		Administrators foundAdmin = null ; 
		try {
			Session currentSession = HibernateUtil.currentSession();
			String query = "select admins from Administrators admins where username = '" + username +"'"
							+" AND password = '" + password + "'" ;
			List<Administrators> adminList = currentSession.createQuery(query).list();
			if(adminList.size() == 0) {
				return "" + negativeAnswer ;
			}
			foundAdmin = adminList.get(0);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			HibernateUtil.closeSession();
		}
		
		return "" + foundAdmin.getAdminid();
	}
	
	public static void insertToHistory (String userid, String mid) {
		String sucessfullEndMsg = "The insertion to history table was successful <server time>";
		Users user = getUser(userid);
		Mediaitems item = getMediaItem(mid); 
		
		if(user == null || item == null ) {
			return;
		}
		
		try {
			Session currentSession = HibernateUtil.currentSession();
			int uid = Integer.parseInt(userid);
			int mid_int = Integer.parseInt(mid);
			Timestamp timeStamp = getCurrentTimestamp();
			HistoryId historyId = new HistoryId(new Integer(uid), new Integer(mid_int), timeStamp);
			History history = new History();
			history.setId(historyId);			
			commitTransaction(currentSession, history);
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			HibernateUtil.closeSession();
		}
		
		System.out.println(sucessfullEndMsg);
	}
	
	public static List<?> getHistory (String userid){
		List<?> historyItems = null ;
		
		try {
			Session currentSession = HibernateUtil.currentSession();
			String query = "select history.id.mid , " +
					"history.id.viewtime from History history " +
					"where history.id.userid = '" + userid +"'" + " order by viewtime ASC";
			historyItems = currentSession.createQuery(query).list();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			HibernateUtil.closeSession();
		}
		return historyItems ;
	}
	
	public static void insertToLog (String userid) {
		String sucessMsg = "The insertion to log table was successful <server time>" ;
		
		try {
			Session currentSession = HibernateUtil.currentSession();
			Loginlog logItem = new Loginlog();
			LoginlogId logInId = new LoginlogId(new Integer(userid), getCurrentTimestamp());
			logItem.setId(logInId);
			commitTransaction(currentSession, logItem);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			HibernateUtil.closeSession();
		}
		System.out.println(sucessMsg);
	}
	
	public static int getNumberOfRegistredUsers(int n) {
		List<Long> registeredUsersList = null;
		long registeredUsers = 0;
		try {
			Session currentSession = HibernateUtil.currentSession();
			String query = "select count(users.userid) from Users users where users.registrationDate > sysdate - '" + n +"'" ;
			registeredUsersList = currentSession.createQuery(query).list();
			registeredUsers = registeredUsersList.get(0);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			HibernateUtil.closeSession();
		}
		
		return (int)registeredUsers;
	}
	
	public static List<Users> getUsers (){
		List<Users> usersList = null;
		try {
			Session currentSession = HibernateUtil.currentSession();
			String query = "select users from Users users" ;
			usersList = currentSession.createQuery(query).list();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			HibernateUtil.closeSession();
		}
		return usersList;
	}

	public static Users getUser (String userid) {
		List<Users> usersList = null;
		Users user = null;
		try {
			Session currentSession = HibernateUtil.currentSession();
			String query = "select users from Users users where users.userid = " + userid;
			usersList = currentSession.createQuery(query).list();
			user = usersList.get(0);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			HibernateUtil.closeSession();
		}
		
		return user;
	}

	
	
	private static Mediaitems getMediaItem(String mid) {
		List<Mediaitems> itemList = null;
		Mediaitems item = null;
		
		try {
			Session currentSession = HibernateUtil.currentSession();
			String query = "select items from Mediaitems items where items.mid = '" + mid + "'";
			itemList = currentSession.createQuery(query).list();
			item = itemList.get(0);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			HibernateUtil.closeSession();
		}
		return item;
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
	
	private static void commitTransaction(Session currentSession, Object item) throws Exception {
		Transaction currentTransaction = currentSession.beginTransaction();
		currentSession.saveOrUpdate(item);
		currentTransaction.commit();
	}
}