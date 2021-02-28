package bgu.spl.net.impl.BGRSServer.messages;
import bgu.spl.net.Database;

public class LogoutMessage implements Message<String> {

    private short opCode=4;
    private String userName;

    public LogoutMessage(String _userName){
        userName=_userName;
    }
    @Override
    public String response() {
        Database database = Database.getInstance();
        if (!database.isLoggedIn(userName)){
            return new ErrorMessage(opCode).response();
        }
        database.logUserOut(userName);
        return new AckMessage(opCode, userName, (short)0).response();
    }
}
