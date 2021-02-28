package bgu.spl.net.impl.BGRSServer.messages;

import bgu.spl.net.Database;

public class CourseStatMessage implements Message<String> {
    private short opCode=7;
    private short courseId;
    private String username;
    public CourseStatMessage(String _username, short _courseId){
        courseId=_courseId;
        username=_username;
    }
    @Override
    public String response() {
        Database db=Database.getInstance();
        if(!db.isAdmin(username)||db.getCourse(courseId)==null)
            return new ErrorMessage(opCode).response();
        return new AckMessage(opCode,username,courseId).response();
    }
}
