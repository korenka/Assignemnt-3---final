package bgu.spl.net;

import bgu.spl.net.impl.BGRSServer.Course;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class textReader {
    private String coursesFile;
    private short courseNum;
    private String courseName;
    private Vector<Short> KdamCourseList;
    private int numOfMaxStudents;
    private FileReader file1;
    private String line;
    private String[] tokens;
    private String[] tokens2;
    private Vector<Course> courses;

    public textReader(String _coursesFile){
        coursesFile = _coursesFile;
        try {
            file1 = new FileReader(coursesFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public Vector<Course> readFile() {
        BufferedReader buffer1 = new BufferedReader(file1);
        courses = new Vector<>();
        int j=0;
        try {
            while ((line = buffer1.readLine()) != null) {
                tokens = line.split("\\|",4);
                courseNum=Short.parseShort(tokens[0]);
                courseName = tokens[1];
                String token2 = tokens[2];
                token2 = token2.substring(1, token2.length()-1);
                KdamCourseList = new Vector<Short>();

                    if (token2 != "" & token2 != null) {
                        try {
                        tokens2 = token2.split(",", -2);
                        for (int i = 0; i < tokens2.length; i++) {
                                KdamCourseList.add(Short.parseShort(tokens2[i]));
                             }
                        }catch (NumberFormatException e) {continue;}
                    }

                else { KdamCourseList = null;}
                numOfMaxStudents = Integer.parseInt(tokens[3]);
                Course course = new Course(j++,courseNum, courseName, numOfMaxStudents ,KdamCourseList);
                courses.add(course);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return courses;
    }
}
