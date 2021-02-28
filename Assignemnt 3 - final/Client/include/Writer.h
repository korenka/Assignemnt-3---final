//
// Created by spl211 on 02/01/2021.
//

#ifndef BOOST_ECHO_CLIENT_WRITER_H
#define BOOST_ECHO_CLIENT_WRITER_H

#include "../include/connectionHandler.h"
#include <mutex>

class Writer {

private:
    ConnectionHandler* connectionHandler;
    std::mutex& mutex;

public:
    Writer(ConnectionHandler* connectionHandler, std::mutex& mutex);
    void run();
    void shortToBytes(short num, char* bytesArr);
    };

#endif //BOOST_ECHO_CLIENT_WRITER_H
