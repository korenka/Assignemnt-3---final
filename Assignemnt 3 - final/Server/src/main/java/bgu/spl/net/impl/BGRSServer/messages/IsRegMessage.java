package bgu.spl.net.impl.BGRSServer.messages;

import bgu.spl.net.Database;

public class IsRegMessage implements Message<String> {
    private short opCode=9;
    private String userName;
    private Short courseId;
    public IsRegMessage(String _userName, Short _courseId){
        userName=_userName;
        courseId=_courseId;
    }
    @Override
    public String response() {
        Database db=Database.getInstance();
        if(userName==null||!db.isValidStudent(userName)||db.getCourse(courseId)==null||!db.isLoggedIn(userName))
            return new ErrorMessage(opCode).response();

//        AckMessage output;
//        if(db.isRegisteredToCourse(userName,courseId))
//            return new AckMessage((short)91,userName,courseId).response();
        return new AckMessage((short)9,userName,courseId).response();
    }
}
