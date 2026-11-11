mod chemistry;
mod models;

use axum::{
    extract::Json,
    http::StatusCode,
    routing::{get, post},
    Router,
};

use chemistry::molar_mass::calculate_molar_mass;

use models::{ErrorResponse, MolarMassRequest, MolarMassResponse};

async fn health() -> &'static str {
    "Science Kit Chemistry Engine is running!"
}

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
    let app = Router::new()
        .route("/health", get(health))
        .route("/api/chemistry/molar-mass", post(molar_mass));

    let port = std::env::var("PORT")
        .unwrap_or_else(|_| "8082".to_string());
    
    let addr = format!("127.0.0.1:{}", port);

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