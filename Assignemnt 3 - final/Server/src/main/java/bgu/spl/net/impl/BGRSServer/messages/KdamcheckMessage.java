package bgu.spl.net.impl.BGRSServer.messages;

import bgu.spl.net.Database;

public class KdamcheckMessage implements Message<String> {
    private short opCode=6;
    String userName;
    short courseToCheck;

    public KdamcheckMessage(String _userName, short _courseToCheck){
        userName = _userName;
        courseToCheck = _courseToCheck;
    }

    @Override
    public String response() {
        Database db=Database.getInstance();
        if(!db.isValidStudent(userName)||!db.isLoggedIn(userName))
            return new ErrorMessage(opCode).response();
        return new AckMessage(opCode, userName, courseToCheck).response();
    }
}
