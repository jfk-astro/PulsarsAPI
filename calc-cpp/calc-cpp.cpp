#include "calc-cpp.h"

int main() {
	CrowServer server;

	server.setup_routes();
	server.run();
}
