package bsisey2;

import java.util.List;

import javax.sound.midi.Soundbank;

import hibernate.Mediaitems;
import hibernate.Users;

public class Main {
	
	static Assignment assignment = new Assignment();
	
	public static void main(String[] args) {
		Assignment assignment = new Assignment();
		inserUser();
		System.out.println(assignment.isExistUsername("adam"));
		
		getTopN(0);
		
		validateUser("adam", "abcd");
		
		validateAdmin("adam", "abcd");
		
		insertToHistory();
		
		getHistory("1");
		
		insertToLog("1");
		
		getNumberOfRegistredUsers(4);
	
		getUsers();
		
		getUser("1");
		
		getUser("2");
	}
	
	public static void inserUser() {
		assignment.insertUser("adam", "abcd", "a", "a", "1", "1", "1990");
		assignment.insertUser("bony", "bbbbb", "a", "a", "2", "1", "1990");
	}
	
	public static void getTopN(int n) {
		List<Mediaitems> ansList = assignment.getTopNItems(n);
		System.out.println("tope " + n + " items : ");
		for (Mediaitems mediaitems : ansList) {
			System.out.println(mediaitems.getTitle());
		}
	}
	
	public static void validateUser(String username, String password) {
		String ans = assignment.validateUser(username, password);
		System.out.println(ans);
	}
	
	public static void validateAdmin(String username, String password) {
		String ans = assignment.validateAdministrator(username, password);
		System.out.println(ans);
	}
	
	public static void insertToHistory() {
		assignment.insertToHistory("1", "1");
	}
	
	
	public static void getHistory(String userid) {
		List<?> ansList = assignment.getHistory(userid);
		
		System.out.println("history of user " + userid);
		for (Object object : ansList) {
			System.out.println(object);
		}
		
	}
	
	public static void insertToLog(String userid) {
		assignment.insertToLog(userid);
	}
	
	public static void getNumberOfRegistredUsers(int n) {
		int ans = assignment.getNumberOfRegistredUsers(n);
		System.out.println("number of reg users " + ans);
	}

	public static void getUsers() {
		List<Users> users = assignment.getUsers();
		System.out.println("all users from DB: ");
		for (Users users2 : users) {
			System.out.println(users2.getUsername());
		}
	}
	
	public static void getUser(String userid) {
		Users user = assignment.getUser(userid);
		System.out.println(user.getUsername());
	}
	
	
}
