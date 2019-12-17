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
		
		return newUser.getUsetid();
	}

	public static List<Mediaitems> getTopNItems (int top_n){
		
		List<Mediaitems> finalList ;
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
		Users foundUser; 
		try {
			Session currentSession = HibernateUtil.currentSession();
			String query = "select users from Users users where username = " + username
							+" AND password = " + password ;
			List<Users> usersList = currentSession.createQuery(query).list();
			foundUser = usersList.get(0);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			HibernateUtil.closeSession();
		}
		
		return foundUser.getUserid();
	}
	
	public static String validateAdministrator (String username, String password) {
		final String negativeAnswer = "Not Found";
		
		if(!isExistUsername(username)) {
			return negativeAnswer;
		}
		Administrators foundAdmin; 
		try {
			Session currentSession = HibernateUtil.currentSession();
			String query = "select admins from Administrators admins where username = " + username
							+" AND password = " + password ;
			List<Administrators> adminList = currentSession.createQuery(query).list();
			foundAdmin = adminList.get(0);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			HibernateUtil.closeSession();
		}
		
		return foundAdmin.getUserid();
	}
	
	public static void insertToHistory (String userid, String mid) {
		String sucessfullEndMsg = "The insertion to history table was successful <server time>";
		Users user = getUser(userid);
		Mediaitems item = getMediaItem(mid); 
		
		if(user == null || item == null ) {
			return;
		}
		
		try {
			Session currentSession = HibernateUtils.currentSession();
			int uid = Integer.parseInt(userid);
			int mid = Integer.parseInt(mid);
			Timestamp timeStamp = getCurrentTimestamp();
			History history = new History(uid, mid, timeStamp);
			
			commitTransaction(currentSession, history);
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			HibernateUtils.closeSession();
		}
		
		System.out.println(sucessfullEndMsg);
	}
	
	public static List<?> getHistory (String userid){
		List<?> historyItems ;
		
		try {
			Session currentSession = HibernateUtils.currentSession();
			String query = "select history.mediaitems.title , " +
					"history.id.viewtime from History history " +
					"where history.id.userid = " + userid + "orderby viewtime ASC";
			historyItems = currentSession.createQuery(query).list();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			HibernateUtils.closeSession();
		}
		return historyItems ;
	}
	
	public static void insertToLog (String userid) {
		String sucessMsg = "The insertion to log table was successful <server time>" ;
		
		try {
			Session currentSession = HibernateUtils.currentSession();
			Loginlog logItem = new Loginlog();
			logItem.setUserid(userid);
			logItem.setLogintime(getCurrentTimestamp());
			commitTransaction(currentSession, logItem);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			HibernateUtils.closeSession();
		}
		System.out.println(sucessMsg);
	}
	
	public static int getNumberOfRegistredUsers(int n) {
		List<Integer> registeredUsersList = null;
		int registeredUsers = 0;
		try {
			Session currentSession = HibernateUtils.currentSession();
			String query = "select count(users.userid) from Users users where users.registrationDate > sysdate - " + n ;
			registeredUsersList = currentSession.createQuery(query).list();
			registeredUsers = registeredUsersList.get(0);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			HibernateUtils.closeSession();
		}
		
		return registeredUsers;
	}
	
	public static List<Users> getUsers (){
		List<Users> usersList = null;
		try {
			Session currentSession = HibernateUtils.currentSession();
			String query = "select users from Users users" ;
			usersList = currentSession.createQuery(query).list();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			HibernateUtils.closeSession();
		}
		return usersList;
	}

	public static Users getUser (String userid) {
		List<Users> usersList = null;
		Users user = null;
		try {
			Session currentSession = HibernateUtils.currentSession();
			String query = "select users from Users users where users.userid = " + userid;
			usersList = currentSession.createQuery(query).list();
			user = usersList.get(0);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			HibernateUtils.closeSession();
		}
		
		return user;
	}

	
	
	private static Mediaitem getMediaItem(String mid) {
		List<Mediaitem> itemList = null;
		Mediaitem item = null;
		
		try {
			Session currentSession = HibernateUtils.currentSession();
			String query = "select items from MediaItems items where items.mid = " + mid;
			itemList = currentSession.createQuery(query).list();
			item = itemList.get(0);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			HibernateUtils.closeSession();
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