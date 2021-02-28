package bgu.spl.net.impl.BGRSServer.messages;
import bgu.spl.net.Database;

public class StudentRegMessage implements Message<String> {
    private short opCode=2;
    private String uName;
    private String pass;

    public StudentRegMessage(String userName, String password){
        uName = userName;
        pass = password;
    }
    @Override
    public String response(){
        Database db = Database.getInstance();
        if(!db.registerUser(uName,pass,false))
            return new ErrorMessage(opCode).response();
        return new AckMessage(opCode, uName, (short)0).response();
    }
}
