//
// Created by spl211 on 04/01/2021.
//
#include <stdlib.h>
#include <mutex>
#include <thread>
#include "../include/connectionHandler.h"
#include "../include/Writer.h"
#include "../include/Reader.h"
using namespace std;

void decode(Reader reader){
    reader.run();
}

int main (int argc, char *argv[]) {
    if (argc < 3) {
        return -1;
    }
    std::string host = argv[1];
    short port = atoi(argv[2]);
    ConnectionHandler connectionHandler(host, port);
    if (!connectionHandler.connect()) {
        return 1;
    }
    std::mutex mutex;
    Writer writer(&connectionHandler, mutex);
    Reader reader(&connectionHandler, mutex);
    thread writerThread(&Writer::run, &writer);
    decode(reader);
    writerThread.join();
    return 0;
}