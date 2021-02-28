//
// Created by spl211 on 02/01/2021.
//

#include "../include/Reader.h"
#include "../include/connectionHandler.h"
using std::string;

Reader::Reader(ConnectionHandler *connectionHandler, std::mutex& mutex):connectionHandler(connectionHandler), mutex(mutex) {}

void Reader::run() {
    while (!ReaderShouldStop){
        string answer;
        if(connectionHandler->getLine(answer)) {
            if (!answer.compare("") == 0) {
                std::cout << answer << std::endl;
            }
            if(answer.compare("ACK 4")==0) {
                ReaderShouldStop=true;
                WriterShouldStop=true;
            }
            else
                stopTyping=false;
        }
    }
}
