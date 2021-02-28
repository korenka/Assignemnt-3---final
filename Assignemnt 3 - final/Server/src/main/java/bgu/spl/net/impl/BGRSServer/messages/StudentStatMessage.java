package bgu.spl.net.impl.BGRSServer.messages;

import bgu.spl.net.Database;

public class StudentStatMessage implements Message<String> {
    private short opCode=8;
    private String userName;
    private String studentName;

    public StudentStatMessage(String _userName,String _studentName){
        userName = _userName;
        studentName=_studentName;
    }

    @Override
    public String response() {
        Database db=Database.getInstance();
        if(!db.isAdmin(userName)||!db.isValidStudent(studentName))
            return new ErrorMessage(opCode).response();
        return new AckMessage(opCode, studentName, (short)0).response();
    }
}
