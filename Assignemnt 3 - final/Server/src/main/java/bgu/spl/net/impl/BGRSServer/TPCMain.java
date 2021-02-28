package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.Database;

public class TPCMain {

    public static void main(String[] args) {

        Database.getInstance().initialize("./Courses.txt");
        new ThreadPerClientServer(Integer.parseInt(args[0]),
                ()->new MessagingProtocolImpl(),
                ()->new MessageEncoderDecoderImpl()).serve();

    }
}
