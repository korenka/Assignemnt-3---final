CFLAGS:=-c -Wall -Weffc++ -g -std=c++11 -Iinclude 
LDFLAGS:=-lboost_system -pthread -lboost_thread


all: bin/BGRSclient

@echo 'Building target: BGRSclient'
@echo 'Invoking: C++ Linker'
bin/BGRSclient: bin/BGRSclient.o bin/connectionHandler.o bin/Writer.o bin/Reader.o
	 g++ -Wall -Weffc++ -o bin/BGRSclient bin/connectionHandler.o bin/BGRSclient.o bin/Writer.o bin/Reader.o $(LDFLAGS)
@echo 'Finished building target: BGRSclient'

bin/connectionHandler.o: src/connectionHandler.cpp
	g++ $(CFLAGS) -o bin/connectionHandler.o src/connectionHandler.cpp $(LDFLAGS)

bin/BGRSclient.o: src/BGRSclient.cpp
	g++ $(CFLAGS) -o bin/BGRSclient.o src/BGRSclient.cpp

bin/Reader.o: src/Reader.cpp
	g++ $(CFLAGS) -o bin/Reader.o src/Reader.cpp $(LDFLAGS)

bin/Writer.o: src/Writer.cpp
	g++ $(CFLAGS) -o bin/Writer.o src/Writer.cpp $(LDFLAGS)

.PHONY: clean
clean:
	@echo 'Cleaning'
	rm -f bin/*
