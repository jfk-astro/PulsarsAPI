#ifndef SERVER_H
#define SERVER_H

#include "../include/crow_all.h"
#include "../handler/calculate_handler.h"

class CrowServer {
public:
	void setup_routes();
	void run(int port = 8081);

private:
	crow::SimpleApp app_;
};

#endif // SERVER_H