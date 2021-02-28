package bgu.spl.net.impl.BGRSServer.messages;

import bgu.spl.net.Database;

public class CourseRegMessage implements Message<String> {
    private short opCode=5;
    private String userName;
    private short courseId;
    public CourseRegMessage(String _userName, short _courseId){
        userName=_userName;
        courseId=_courseId;
    }
    @Override
    public String response() {
        Database db=Database.getInstance();
        if(db.registerToCourse(userName,courseId))
            return new AckMessage(opCode,userName,courseId).response();
        return new ErrorMessage(opCode).response();
    }
}
