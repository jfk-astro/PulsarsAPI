#include "server.h"

void CrowServer::run(int port) {
	this->app_.port(port).multithreaded().run();
}

void CrowServer::setup_routes() {
	setup_calculate_routes(this->app_);
}
