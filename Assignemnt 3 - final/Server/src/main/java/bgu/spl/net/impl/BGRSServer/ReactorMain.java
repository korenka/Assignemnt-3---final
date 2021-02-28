package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.Database;
import bgu.spl.net.srv.Reactor;

public class ReactorMain {
    public static void main(String[] args){
        Database.getInstance().initialize("./Courses.txt");
        new Reactor(Integer.parseInt(args[1]),
                Integer.parseInt(args[0]),
                ()->new MessagingProtocolImpl(),
                ()->new MessageEncoderDecoderImpl()).serve();
    }
}
