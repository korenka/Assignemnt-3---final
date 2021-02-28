//
// Created by spl211 on 02/01/2021.
//

#ifndef ASS3_READER_H
#define ASS3_READER_H

#include "../include/connectionHandler.h"
#include <mutex>
class Reader{

private:
    ConnectionHandler* connectionHandler;
    std::mutex& mutex;

public:
    Reader(ConnectionHandler* connectionHandler, std::mutex& mutex);

    void run();
};

#endif //ASS3_READER_H
