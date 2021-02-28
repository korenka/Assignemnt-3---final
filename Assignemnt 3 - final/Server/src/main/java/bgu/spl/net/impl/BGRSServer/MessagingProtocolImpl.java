package bgu.spl.net.impl.BGRSServer;


import bgu.spl.net.Database;
import bgu.spl.net.impl.BGRSServer.messages.*;
import bgu.spl.net.api.MessagingProtocol;


public class MessagingProtocolImpl implements MessagingProtocol<ClientMessage> {
    private String loggedInUser = "";
    private boolean shouldTerminate;

    @Override
    public ClientMessage process(ClientMessage msg) {
        ClientMessage output = new ClientMessage();
        String respone="";
        Database db=Database.getInstance();
        switch (msg.getOpCode()) {
            case 1: {
                if(loggedInUser!="")
                    output.setData(new ErrorMessage(msg.getOpCode()).response());
                else output.setData(new AdminRegMessage(msg.getData().split(" ")[0],
                        msg.getData().split(" ")[1]).response());
                break;
            }
            case 2: {
                if(loggedInUser!="")
                    output.setData(new ErrorMessage(msg.getOpCode()).response());
                else output.setData(new StudentRegMessage(msg.getData().split(" ")[0],
                        msg.getData().split(" ")[1]).response());
                break;
            }
            case 3: {
                    loggedInUser = msg.getData().split(" ")[0];
                    output.setData(new LoginMessage(msg.getData().split(" ")[0],
                            msg.getData().split(" ")[1]).response());

                break;
            }
            case 4: {
                if (loggedInUser.isEmpty())
                    output.setData(new ErrorMessage(msg.getOpCode()).response());
                output.setData(new LogoutMessage(loggedInUser).response());
                shouldTerminate=true;
                break;
            }
            case 5: {
                if (loggedInUser.isEmpty())
                    output.setData(new ErrorMessage(msg.getOpCode()).response());
                output.setData(new CourseRegMessage(loggedInUser, Short.parseShort(msg.getData())).response());
                break;
            }
            case 6: {
                if (loggedInUser.isEmpty())
                    output.setData(new ErrorMessage(msg.getOpCode()).response());
                output.setData(new KdamcheckMessage(loggedInUser, Short.parseShort(msg.getData())).response());
                break;
            }
            case 7: {
                if (loggedInUser.isEmpty()||!db.isAdmin(loggedInUser))
                    output.setData(new ErrorMessage(msg.getOpCode()).response());
                output.setData(new CourseStatMessage(loggedInUser, Short.parseShort(msg.getData())).response());
                break;
            }
            case 8: {
                if (loggedInUser.isEmpty())
                    output.setData(new ErrorMessage(msg.getOpCode()).response());
                output.setData(new StudentStatMessage(loggedInUser, msg.getData()).response());
                break;
            }
            case 9: {
                if (loggedInUser.isEmpty())
                    output.setData(new ErrorMessage(msg.getOpCode()).response());
                if(db.isRegisteredToCourse(loggedInUser,Short.parseShort(msg.getData())))
                    output.setData(new IsRegMessage(loggedInUser, Short.parseShort(msg.getData())).response()+"REGISTERED");
                else
                    output.setData(new IsRegMessage(loggedInUser, Short.parseShort(msg.getData())).response()+"NOT REGISTERED");
                break;
            }
            case 10: {
                if (loggedInUser.isEmpty())
                    output.setData(new ErrorMessage(msg.getOpCode()).response());
                output.setData(new UnregMessage(loggedInUser, Short.parseShort(msg.getData())).response());
                break;
            }
            case 11: {
                if (loggedInUser.isEmpty())
                    output.setData(new ErrorMessage(msg.getOpCode()).response());
                output.setData(new MyCoursesMessage(loggedInUser).response());
                break;
            }
        }
        if(output.getData().substring(0,3)=="ERR")
            output.setOpCode((short)13);
        else output.setOpCode((short)12);
        output.setData(output.getData()+'\0');
        return output;
    }

    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }
}
