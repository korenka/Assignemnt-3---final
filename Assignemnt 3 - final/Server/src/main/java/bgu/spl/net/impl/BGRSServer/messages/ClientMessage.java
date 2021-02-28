package bgu.spl.net.impl.BGRSServer.messages;

public class ClientMessage {
    private short opCode;
    private String data;
    public ClientMessage(short _opCode, String _data){
        opCode=_opCode;
        data=_data;
    }

    public ClientMessage(){data="";}

    public void setData(String data) {
        this.data = data;
//        this.data.
    }

    public void setOpCode(short opCode) {
        this.opCode = opCode;
    }

    public short getOpCode() {
        return opCode;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return opCode +" "+ data;
    }
}
