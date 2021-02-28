//
// Created by spl211 on 02/01/2021.
//
#include <stdlib.h>
#include "../include/Writer.h"
#include <mutex>
#include "boost/lexical_cast.hpp"
#include "../include/connectionHandler.h"

using boost::asio::ip::tcp;
using std::string;
using std::mutex;
using std::cin;
using std::cout;
using std::endl;

Writer::Writer(ConnectionHandler *connectionHandler, std::mutex& mutex): connectionHandler(connectionHandler), mutex(mutex){}

void Writer::run() {
//    while (connectionHandler->isConnected() && !shouldStop) {
    while (!WriterShouldStop) {
        if(!stopTyping) {
            const short bufsize = 1024;
            char buf[bufsize];
            std::cin.getline(buf, bufsize);
            string line(buf);
            string opCode = line.substr(0, line.find(' '));
            string restLine = line.substr(line.find_first_of(' ') + 1);
            string toSend = "";
            if (opCode.compare("ADMINREG") == 0) {
                char bytes[2];
                short opcode = 1;
                shortToBytes(opcode, bytes);
                toSend.push_back(bytes[0]);
                toSend.push_back(bytes[1]);
                string userName = restLine.substr(0, restLine.find_first_of(' '));
                string password = restLine.substr(restLine.find_first_of(' ') + 1);
                toSend += userName + '\0' + password;
                connectionHandler->sendLine(toSend);
            } else if (opCode.compare("STUDENTREG") == 0) {
                char bytes[2];
                short opcode = 2;
                shortToBytes(opcode, bytes);
                toSend.push_back(bytes[0]);
                toSend.push_back(bytes[1]);
                string userName = restLine.substr(0, restLine.find_first_of(' '));
                string password = restLine.substr(restLine.find_first_of(' ') + 1);
                toSend += userName + '\0' + password;
                connectionHandler->sendLine(toSend);
            } else if (opCode.compare("LOGIN") == 0) {
                char bytes[2];
                short opcode = 3;
                shortToBytes(opcode, bytes);
                toSend.push_back(bytes[0]);
                toSend.push_back(bytes[1]);
                string userName = restLine.substr(0, restLine.find_first_of(' '));
                string password = restLine.substr(restLine.find_first_of(' ') + 1);
                toSend += userName + '\0' + password;
                connectionHandler->sendLine(toSend);
            } else if (line.compare("LOGOUT") == 0) {
                char bytes[2];
                short opcode = 4;
                shortToBytes(opcode, bytes);
                toSend.push_back(bytes[0]);
                toSend.push_back(bytes[1]);
                stopTyping=true;
                connectionHandler->sendLine(toSend);
            } else if (opCode.compare("COURSEREG") == 0) {
                char bytes[2];
                char bytesToSend[4];
                short opcode = 5;
                shortToBytes(opcode, bytes);
                bytesToSend[0]=bytes[0];
                bytesToSend[1]=bytes[1];
                short courseid=boost::lexical_cast<short>(restLine);
                shortToBytes(courseid,bytes);
                bytesToSend[2]=bytes[0];
                bytesToSend[3]=bytes[1];
                connectionHandler->sendBytes(bytesToSend,4);
            } else if (opCode.compare("KDAMCHECK") == 0) {
                char bytes[2];
                char bytesToSend[4];
                short opcode = 6;
                shortToBytes(opcode, bytes);
                bytesToSend[0]=bytes[0];
                bytesToSend[1]=bytes[1];
                short courseid=boost::lexical_cast<short>(restLine);
                shortToBytes(courseid,bytes);
                bytesToSend[2]=bytes[0];
                bytesToSend[3]=bytes[1];
                connectionHandler->sendBytes(bytesToSend,4);
            } else if (opCode.compare("COURSESTAT") == 0) {
                char bytes[2];
                char bytesToSend[4];
                short opcode = 7;
                shortToBytes(opcode, bytes);
                bytesToSend[0]=bytes[0];
                bytesToSend[1]=bytes[1];
                short courseid=boost::lexical_cast<short>(restLine);
                shortToBytes(courseid,bytes);
                bytesToSend[2]=bytes[0];
                bytesToSend[3]=bytes[1];
                connectionHandler->sendBytes(bytesToSend,4);
            } else if (opCode.compare("STUDENTSTAT") == 0) {
                char bytes[2];
                short opcode = 8;
                shortToBytes(opcode, bytes);
                toSend.push_back(bytes[0]);
                toSend.push_back(bytes[1]);
                toSend += restLine;
                connectionHandler->sendLine(toSend);
            } else if (opCode.compare("ISREGISTERED") == 0) {
                char bytes[2];
                char bytesToSend[4];
                short opcode = 9;
                shortToBytes(opcode, bytes);
                bytesToSend[0]=bytes[0];
                bytesToSend[1]=bytes[1];
                short courseid=boost::lexical_cast<short>(restLine);
                shortToBytes(courseid,bytes);
                bytesToSend[2]=bytes[0];
                bytesToSend[3]=bytes[1];
                connectionHandler->sendBytes(bytesToSend,4);
            } else if (opCode.compare("UNREGISTER") == 0) {
                char bytes[2];
                char bytesToSend[4];
                short opcode = 10;
                shortToBytes(opcode, bytes);
                bytesToSend[0]=bytes[0];
                bytesToSend[1]=bytes[1];
                short courseid=boost::lexical_cast<short>(restLine);
                shortToBytes(courseid,bytes);
                bytesToSend[2]=bytes[0];
                bytesToSend[3]=bytes[1];
                connectionHandler->sendBytes(bytesToSend,4);
            } else if (opCode.compare("MYCOURSES") == 0) {
                char bytes[2];
                short opcode = 11;
                shortToBytes(opcode, bytes);
                toSend.push_back(bytes[0]);
                toSend.push_back(bytes[1]);
                connectionHandler->sendBytes(bytes,2);
            }
        }
    }
}

void Writer::shortToBytes(short num, char* bytesArr){
    bytesArr[0] = ((num >> 8) & 0xFF);
    bytesArr[1] = (num & 0xFF);
}