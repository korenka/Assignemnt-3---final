package bgu.spl.net.impl.BGRSServer.messages;

import java.util.Comparator;
import java.util.Vector;
import bgu.spl.net.Database;
import bgu.spl.net.impl.BGRSServer.Course;
import bgu.spl.net.impl.BGRSServer.MessageEncoderDecoderImpl;

public class AckMessage implements Message<String> {
    private short opCode=12;
    private short mOpCode;
    private String userName;
    private short courseId;

    public AckMessage(short _mopCode,String _userName, short _courseId){
        mOpCode=_mopCode;
        userName=_userName;
        courseId=_courseId;
    }
    @Override
    public String response() {
        String output="ACK "+mOpCode;
        Database db=Database.getInstance();
        switch(mOpCode){
            case 6:{
                output+="\n[";
                Vector<Course> kdams=db.getKdamCourses(courseId);
                kdams.sort(new Comparator<Course>() {
                    @Override
                    public int compare(Course o1, Course o2) {
                        return o1.getSerialNum()-o2.getSerialNum();
                    }
                });
                if(!kdams.isEmpty()) {
                    for (int i = 0; i < kdams.size(); i++) {
                        output += kdams.get(i) + ",";
                    }
                    output = output.substring(0, output.length() - 1);
                }
                output+="]";
                break;
            }
            case 7:{
                Course c=db.getCourse(courseId);
                output+="\nCourse: ("+c.getId()+") "+c.getName()+"\n";
                int ava= c.getMaxReg()-c.getCurrentReg();
                output+="Seats Available:"+ava+"/"+c.getMaxReg()+"\n";
                output+="Students Registered: [";
                if(!c.getRegisteredNames().isEmpty()) {
                    for (int i = 0; i < c.getRegisteredNames().size(); i++) {
                        String name=c.getRegisteredNames().get(i);
                        output += name.substring(0,name.length()) + ", ";
                    }
                    output = output.substring(0, output.length() - 2);
                }
                output+="]";
                break;
            }
            case 8:{
                output+="\nStudent:"+userName;
                Vector<Short> studentCourses = db.getStudentCourses(userName);
                String courses = "";
                courses += "[";
                if(!studentCourses.isEmpty()) {
                    for (int i : studentCourses) {
                        courses += i + ",";
                    }
                    courses.substring(0, courses.length() - 2);
                }
                courses += "]";
                output += '\n'+courses;
                break;
            }
            case 9:{
                break;
            }

            case 11:{
                output+="\n[";
                if(!db.getStudentCourses(userName).isEmpty()) {
                    for (int i : db.getStudentCourses(userName))
                        output += i + ",";
                    output = output.substring(0, output.length() - 1);
                }
                output+="]";
                break;
            }
        }
        return output+'\0';
    }
}
