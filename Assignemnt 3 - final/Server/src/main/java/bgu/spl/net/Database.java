package bgu.spl.net;

import bgu.spl.net.impl.BGRSServer.Course;
import bgu.spl.net.impl.BGRSServer.Student;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Passive object representing the bgu.spl.net.Database where all courses and users are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add private fields and methods to this class as you see fit.
 */
public class Database {

	private static class Holder{
		private static Database instance = new Database();
		public static boolean initialized;
	}

	private Vector<Course> courses;
	private ConcurrentHashMap<String, Vector<Course>> studentsData;
	private ConcurrentHashMap<String,Student> students;
	private Vector<String> loggedIn;

	//to prevent user from creating new bgu.spl.net.Database
	private Database() {
		courses=new Vector<Course>();
		studentsData=new ConcurrentHashMap<String, Vector<Course>>();
		students=new ConcurrentHashMap<String,Student>();
		loggedIn=new Vector<String>();
	}

	/**
	 * Retrieves the single instance of this class.
	 */
	public static Database getInstance() {
		return Holder.instance;
	}
	
	/**
	 * loades the courses from the file path specified 
	 * into the bgu.spl.net.Database, returns true if successful.
	 */
	public boolean initialize(String coursesFilePath) {
		textReader reader=new textReader(coursesFilePath);
		courses=reader.readFile();
		if(courses!=null&!courses.isEmpty()) {
			Holder.initialized = true;
			return true;
		}
		return false;
	}

	public String getUserPassword(String userName){
		return students.get(userName).getPassword();
	}

	public void setLoggedIn(String userName){
		loggedIn.add(userName);
	}

	public boolean isLoggedIn(String userName){
		return loggedIn.contains(userName);
	}

	public boolean isRegistered(String userName){
		return students.containsKey(userName);
	}
	public boolean registerUser(String userName, String password, boolean isAdmin){
		if(isRegistered(userName))
			return false;
		Student st=new Student(userName,password,isAdmin);
		students.put(userName,st);
		studentsData.put(userName,new Vector<Course>());
		return true;
	}

	public boolean checkPassword(String userName, String password){
		return password.equals(getUserPassword(userName));
	}


	public boolean logUserIn(String userName, String password){

		if(isLoggedIn(userName))
			return false;
		if(isRegistered(userName)&&checkPassword(userName,password)){
			setLoggedIn(userName);
			return true;
		}
		return false;
	}

	public boolean logUserOut(String userName){
		return loggedIn.remove(userName);
	}

	public boolean registerToCourse(String userName, Short courseId){
		if(!isRegistered(userName)||!isLoggedIn(userName)||isAdmin(userName))
			return false;
		Course c=getCourse(courseId);
		if(c==null||!hasAllKdams(userName,c))
			return false;
		if(!c.register(userName))
			return false;
		studentsData.get(userName).add(c);
		return true;
	}

	public Course getCourse(Short courseId){
		for(Course c:courses) {
			if (c.getId() == courseId)
				return c;
		}
		return null;
	}

	public Vector<Short> getKdams(Short courseId){
		Course c=getCourse(courseId);
		if(c!=null)
			return c.getKdams();
		return new Vector<Short>();
	}

	public Vector<Course>getKdamCourses(Short courseId){
		Course c=getCourse(courseId);
		Vector<Course> output=new Vector<Course>();
		if(c!=null) {
			for (short k : c.getKdams()) {
				output.add(getCourse(k));
			}
		}
		return output;
	}

	public boolean isRegisteredToCourse(String userName, Short courseId){
		if(isValidStudent(userName)&&getCourse(courseId)!=null){
			Vector<Course> stuCourses=studentsData.get(userName);
			for(Course c:stuCourses) {
				if (c.getId() == courseId)
					return true;
			}
		}
		return false;
	}

	public boolean hasAllKdams(String userName, Course c){
		if(!isValidStudent(userName))
			return false;
		for(Short courseId:c.getKdams()){
			if(!isRegisteredToCourse(userName,courseId))
				return false;
		}
		return true;
	}
	public boolean isAdmin(String userName){
		if(userName==""||userName==null||!isRegistered(userName))
			return false;
		return students.get(userName).isAdmin();

	}

	public Vector<Short> getStudentCourses(String userName){
		Vector<Course> courses=studentsData.get(userName);
		Vector<Short> output=new Vector<>();
		for(Course c:courses){
			output.add(c.getId());
		}
		return output;
	}

	public boolean isValidStudent(String userName){
		return !isAdmin(userName) && isRegistered(userName);
	}

	public void unregisterFromCourse(String userName, Short courseID){
		int i = 0;
		while (courses.get(i).getId() != courseID) {
			i++;
		}
		studentsData.get(userName).remove(courses.get(i));
		courses.get(i).unregister();
	}
}
