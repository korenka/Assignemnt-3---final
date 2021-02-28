package bgu.spl.net.impl.BGRSServer.messages;

import bgu.spl.net.Database;

public class MyCoursesMessage  implements Message<String> {
    private short opCode=11;
    private String username;
    public MyCoursesMessage(String _userName){
        username=_userName;
    }
    @Override
    public String response() {
        Database db=Database.getInstance();
        if(db.isValidStudent(username)&&db.isLoggedIn(username))
            return new AckMessage(opCode,username,(short)0).response();
        return new ErrorMessage(opCode).response();
    }
}
