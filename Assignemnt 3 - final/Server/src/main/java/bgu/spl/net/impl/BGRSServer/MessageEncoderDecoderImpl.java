package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.BGRSServer.messages.ClientMessage;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


    public class MessageEncoderDecoderImpl implements MessageEncoderDecoder<ClientMessage> {

        private byte[] bytes;
        private int len;
        private int zeroCounter;
        private int byteCounter;
        private byte[] opCodeBytes;
        private short msgOpCode;
        private boolean coughtOpCode, coughtUserName;

        public MessageEncoderDecoderImpl(){
            bytes = new byte[1 << 10]; //start with 1k
            len = 0;
            zeroCounter=0;
            byteCounter=0;
            opCodeBytes =new byte[2];
            coughtOpCode=false;
            coughtUserName=false;
            msgOpCode=0;
        }

        @Override
        public ClientMessage decodeNextByte(byte nextByte) {
            if (nextByte == '\0'&&byteCounter!=0) {
                zeroCounter++;
            }
            pushByte(nextByte);
            if(byteCounter==2&!coughtOpCode){
                msgOpCode = popShort();
                coughtOpCode=msgOpCode!=0;
                if(msgOpCode==8|msgOpCode==1|msgOpCode==1|msgOpCode==3) return null;
            }

            if(coughtOpCode) {
                switch (msgOpCode) {
                    case 1:
                    case 2:
                    case 3: {
                        if (zeroCounter == 1 && !coughtUserName) {
                            pushByte(" ".getBytes()[0]);
                            coughtUserName = true;
                            break;
                        } else if (zeroCounter == 2 & nextByte == '\0') {
                            return new ClientMessage(msgOpCode, popString());
                        }
                        break;
                    }
                    case 4:
                    case 11: {
                        reset();
                        return new ClientMessage(msgOpCode, "");
                    }
                    case 5: {
                    }
                    case 6: {
                    }
                    case 9: {
                    }
                    case 10: {
                    }
                    case 7: {
                        if (byteCounter == 4) {
                            reset();
                            return new ClientMessage(msgOpCode, popShort() + "");
                        }
                        break;
                    }
                    case 8: {
                        if (nextByte == '\0') {
                            return new ClientMessage(msgOpCode, popString());
                        }
                        break;
                    }
                }
            }
            return null; //not a line yet
        }


        @Override
        public byte[] encode(ClientMessage message) {
            return (message.getData() +'\0').getBytes();
        }


        public static byte[] shortToBytes(short num)
        {
            byte[] bytesArr = new byte[2];
            bytesArr[0] = (byte)((num >> 8) & 0xFF);
            bytesArr[1] = (byte)(num & 0xFF);
            return bytesArr;
        }

        private void pushByte(byte nextByte) {
            if(byteCounter<2){
                opCodeBytes[byteCounter]=nextByte;
            }
            else if(msgOpCode==5|msgOpCode==6|msgOpCode==9|msgOpCode==10|msgOpCode==7) {
                if (byteCounter == 2) {
                    opCodeBytes[0] = nextByte;
                } else if (byteCounter == 3)
                    opCodeBytes[1] = nextByte;
            }
            else {
                if (len >= bytes.length) {
                    bytes = Arrays.copyOf(bytes, len * 2);
                }
                bytes[len++] = nextByte;
            }

            byteCounter++;
        }

        private String popString() {
            String result = new String(bytes, 0, len, StandardCharsets.UTF_8);
            reset();
            return result;
        }

        private short popShort(){
            short result= ByteBuffer.wrap(opCodeBytes).getShort();
            return result;
        }
        private void reset(){
            len = 0;
            zeroCounter=0;
            byteCounter=0;
            coughtOpCode=false;
            coughtUserName=false;
            bytes = new byte[1 << 10];
        }
    }


