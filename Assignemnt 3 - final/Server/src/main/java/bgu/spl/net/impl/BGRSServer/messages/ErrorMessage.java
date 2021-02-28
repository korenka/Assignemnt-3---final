package bgu.spl.net.impl.BGRSServer.messages;

import bgu.spl.net.impl.BGRSServer.MessageEncoderDecoderImpl;

public class ErrorMessage implements Message<String> {
    private short OpCode=13;
    private short mOpCode;
    public ErrorMessage(short mOpCode){
//    public ErrorMessage(short opCode,short mOpCode){
//        this.OpCode=opCode;
        this.mOpCode=mOpCode;
    }

    @Override
    public String response() {
//        return MessageEncoderDecoderImpl.shortToBytes(OpCode)+""+MessageEncoderDecoderImpl.shortToBytes(mOpCode);
        return "ERR "+mOpCode;
    }
}
