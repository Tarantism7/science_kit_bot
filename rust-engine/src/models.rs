use serde::{Deserialize, Serialize};
use std::collections::HashMap;

#[derive(Debug, Deserialize)]
pub struct MolarMassRequest {
    pub formula: String,
    pub atomic_masses: HashMap<String, f64>,
}

#[derive(Debug, Serialize)]
pub struct ElementBreakdown {
    pub symbol: String,
    pub count: u32,
    pub atomic_mass: f64,
    pub contribution: f64,
}

#[derive(Debug, Serialize)]
pub struct MolarMassResponse {
    pub formula: String,
    pub molar_mass: f64,
    pub unit: String,
    pub breakdown: Vec<ElementBreakdown>,
}

#[derive(Debug, Serialize)]
pub struct ErrorResponse {
    pub error: String,
}

#[derive(Debug, Deserialize)]
pub struct MoleCalculatorRequest {
    pub amount: f64,
    pub calculation: String,
    pub formula: Option<String>,

    #[serde(default)]
    pub atomic_masses: HashMap<String, f64>,
}

#[derive(Debug, Serialize)]
pub struct MoleCalculatorResponse {
    pub result: f64,
    pub unit: String,
    pub molar_mass: Option<f64>,
}
