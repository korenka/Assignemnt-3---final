package bgu.spl.net.impl.BGRSServer.messages;
import bgu.spl.net.Database;

public class UnregMessage  implements Message<String> {
    private short opCode=10;
    String userName;
    short courseToUnregister;

    public UnregMessage(String _userName, short _courseToUnregister){
        userName = _userName;
        courseToUnregister = _courseToUnregister;
    }

    @Override
    public String response() {
        Database db = Database.getInstance();
        if (!db.isRegisteredToCourse(userName, courseToUnregister)||!db.isValidStudent(userName)) {
            return new ErrorMessage(opCode).response();
        }
        db.unregisterFromCourse(userName, courseToUnregister);
        return new AckMessage(opCode, userName, courseToUnregister).response();
    }
}
