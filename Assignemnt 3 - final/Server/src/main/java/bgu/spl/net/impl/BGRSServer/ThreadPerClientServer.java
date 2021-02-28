package bgu.spl.net.impl.BGRSServer;
import bgu.spl.net.srv.BaseServer;
import bgu.spl.net.srv.BlockingConnectionHandler;
import bgu.spl.net.api.*;

import java.util.function.Supplier;

public class ThreadPerClientServer extends BaseServer {
    public ThreadPerClientServer(
            int port,
            Supplier<MessagingProtocol> protocolFactory,
            Supplier<MessageEncoderDecoder> encoderDecoderFactory) {

        super(port, protocolFactory, encoderDecoderFactory);
    }

    @Override
    protected void execute(BlockingConnectionHandler handler) {
        new Thread(handler).start();
    }
}
