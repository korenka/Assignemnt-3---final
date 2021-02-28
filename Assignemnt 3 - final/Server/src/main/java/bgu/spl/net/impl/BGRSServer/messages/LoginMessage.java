package bgu.spl.net.impl.BGRSServer.messages;
import bgu.spl.net.Database;

public class LoginMessage implements Message<String> {
    private short opCode=3;
    private String uName;
    private String pass;

    public LoginMessage(String userName, String password){
        uName=userName;
        pass=password;
    }

    @Override
    public String response() {
        Database db=Database.getInstance();
        if(db.logUserIn(uName,pass))
            return new AckMessage(opCode,uName,(short)0).response();
        return new ErrorMessage(opCode).response();
    }
}
