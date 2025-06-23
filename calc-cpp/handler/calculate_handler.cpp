#include "calculate_handler.h"

void setup_calculate_routes(crow::SimpleApp& app) {
	// TODO: Implement actual calculation logic later..

    CROW_ROUTE(app, "/calculate/edot").methods("POST"_method)([](const crow::request& req) {
        auto body = crow::json::load(req.body);
        if (!body) return crow::response(400, "Invalid JSON!");

        double p1 = body["P1"].d();
        double edot = 1.0e45 * p1;

        crow::json::wvalue result;
        result["edot"] = edot;
        return crow::response(result);
    });
}
