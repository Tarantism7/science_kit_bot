
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
///
/// # Request
/// Expects a JSON object containing:
/// - formula: Chemical formula, such as "H2O".
/// - atomic_masses: Element symbols and their atomic masses.
///
/// # Successful response
/// Returns:
/// - The chemical formula.
/// - The calculated molar mass.
/// - The measurement unit.
/// - A breakdown of each element's contribution.
///
/// # Error response
/// Returns HTTP 400 BAD REQUEST if the calculation fails.
async fn molar_mass(
    // Extract the JSON request body and deserialize it
    // into the MolarMassRequest struct.
    Json(request): Json<MolarMassRequest>,

// The function can return two possible outcomes:
//
// 1. Ok(Json<MolarMassResponse>):
//    A successful calculation with an HTTP 200 response.
//
// 2. Err((StatusCode, Json<ErrorResponse>)):
//    An error containing an HTTP status code and JSON error message.
) -> Result<Json<MolarMassResponse>, (StatusCode, Json<ErrorResponse>)> {

    // Remove leading and trailing whitespace from the formula.
    //
    // Example:
    // "  H2O  " becomes "H2O".
    let formula = request.formula.trim();

    // Call the molar mass calculation function.
    //
    // Pass:
    // 1. The cleaned chemical formula.
    // 2. The HashMap containing atomic masses.
    //
    // The function returns either a successful result or an error.
    match calculate_molar_mass(formula, &request.atomic_masses) {

        // Handle a successful molar mass calculation.
        Ok(result) => {

            // Create the JSON response containing the calculation results.
            Ok(Json(MolarMassResponse {

                // Return the cleaned chemical formula.
                formula: formula.to_string(),

                // Round the molar mass to four decimal places.
                //
                // Multiplying by 10000 moves four decimal places
                // to the left of the decimal point.
                //
                // round() rounds to the nearest integer.
                //
                // Dividing by 10000 restores the decimal places.
                //
                // Example:
                // 18.01528 becomes 18.0153.
                molar_mass: (result.total * 10000.0).round() / 10000.0,

                // Specify the molar mass measurement unit.
                unit: "g/mol".to_string(),

                // Include the individual element contributions.
                // This provides a detailed breakdown of the calculation.
                breakdown: result.breakdown,
            }))
        }

        // Handle errors returned by the molar mass calculator.
        Err(error) => {

            // Return HTTP 400 BAD REQUEST together with
            // a JSON object containing the error message.
            //
            // This indicates that the submitted calculation request
            // could not be processed successfully.
            Err((
                StatusCode::BAD_REQUEST,
                Json(ErrorResponse { error }),
            ))
        }
    }
}


/// Main entry point of the application.
///
/// The Tokio runtime allows the application to execute
/// asynchronous functions.
///
/// This function:
/// 1. Creates the Axum application router.
/// 2. Registers the API endpoints.
/// 3. Creates a TCP listener.
/// 4. Starts the HTTP server.
#[tokio::main]
async fn main() {

    // Create the Axum application and register its routes.
    let app = Router::new()

        // GET /health
        //
        // Used to check whether the server is running.
        .route("/health", get(health))

        // POST /api/chemistry/molar-mass
        //
        // Accepts a chemical formula and calculates its molar mass.
        .route("/api/chemistry/molar-mass", post(molar_mass));

    // Create a TCP listener on localhost (127.0.0.1),
    // using port 8081.
    //
    // The server will accept incoming HTTP connections
    // through this address.
    let listener = tokio::net::TcpListener::bind("127.0.0.1:8081")
        .await
        .expect("Could not bind to port 8081");

    // Print a startup message to the terminal.
    // This confirms that the application is ready to serve requests.
    println!("Science Kit Chemistry Engine running on port 8081");

    // Start the Axum HTTP server.
    //
    // The server listens for incoming connections using
    // the TCP listener and processes requests through
    // the configured application router.
    //
    // If the server encounters an unexpected error,
    // the application will terminate with the message "Server error".
    axum::serve(listener, app)
        .await
        .expect("Server error");
}
