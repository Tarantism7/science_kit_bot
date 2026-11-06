// Declare the application modules.
//
// chemistry contains the chemical calculation logic,
// including the formula parser and molar mass calculator.
mod chemistry;

// models contains the data structures used for API requests,
// successful responses, and error responses.
mod models;

use axum::{
    // Extract JSON data from incoming HTTP requests.
    extract::Json,

    // HTTP status codes, such as 200 OK and 400 BAD REQUEST.
    http::StatusCode,

    // Router is used to configure API routes.
    // get and post define the HTTP methods supported by each route.
    routing::{get, post},

    // Main Axum application router.
    Router,
};

// Import the function responsible for calculating molar mass.
use chemistry::molar_mass::calculate_molar_mass;

// Import the data structures used to communicate with the API.
use models::{ErrorResponse, MolarMassRequest, MolarMassResponse};


/// Health check endpoint.
///
/// This endpoint allows clients to check whether the API server
/// is running correctly.
///
/// Returns a simple text response.
async fn health() -> &'static str {
    "Science Kit Chemistry Engine is running!"
}


/// Molar mass calculation endpoint.
///
/// Receives a chemical formula and atomic masses as JSON,
/// calculates the molar mass, and returns the result as JSON.
async fn molar_mass(
    Json(request): Json<MolarMassRequest>,
) -> Result<Json<MolarMassResponse>, (StatusCode, Json<ErrorResponse>)> {

    let formula = request.formula.trim();

    match calculate_molar_mass(formula, &request.atomic_masses) {
        Ok(result) => {
            Ok(Json(MolarMassResponse {
                formula: formula.to_string(),
                molar_mass: (result.total * 10000.0).round() / 10000.0,
                unit: "g/mol".to_string(),
                breakdown: result.breakdown,
            }))
        }
        Err(error) => {
            Err((
                StatusCode::BAD_REQUEST,
                Json(ErrorResponse { error }),
            ))
        }
    }
}


/// Main entry point of the application.
#[tokio::main]
async fn main() {
    // Create the Axum application and register its routes.
    let app = Router::new()
        .route("/health", get(health))
        .route("/api/chemistry/molar-mass", post(molar_mass));

    // Read the PORT environment variable (defaulting to 8082 if not set).
    let port = std::env::var("PORT")
        .unwrap_or_else(|_| "8082".to_string());
    
    let addr = format!("127.0.0.1:{}", port);

    // Create a TCP listener using the dynamic address and port.
    let listener = tokio::net::TcpListener::bind(&addr)
        .await
        .unwrap_or_else(|err| {
            panic!("Could not bind to port {}: {:?}", port, err);
        });

    println!("Science Kit Chemistry Engine running on port {}", port);

    axum::serve(listener, app)
        .await
        .expect("Server error");
}