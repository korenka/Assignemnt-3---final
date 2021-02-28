package bgu.spl.net.impl.BGRSServer.messages;
import bgu.spl.net.Database;

public class AdminRegMessage implements Message<String> {
    private short opCode=1;
    private String uName;
    private String pass;
    public AdminRegMessage(String userName, String password){
        uName=userName;
        pass=password;
    }
    @Override
    public String response() {
        Database db=Database.getInstance();
        if(!db.registerUser(uName,pass,true))
            return new ErrorMessage(opCode).response();
        return new AckMessage(opCode,uName, (short) 0).response();
    }
}
