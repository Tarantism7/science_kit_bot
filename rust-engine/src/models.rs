
use serde::{Deserialize, Serialize};
use std::collections::HashMap;

/// Represents the JSON request received by the molar mass API.
///
/// This struct defines the data that a client must send
/// when requesting a molar mass calculation.
///
/// `Deserialize` allows Serde to convert incoming JSON data
/// into a Rust struct.
#[derive(Debug, Deserialize)]
pub struct MolarMassRequest {

    // Chemical formula supplied by the client.
    //
    // Example: "H2O", "NaCl", "Ca(OH)2"
    pub formula: String,

    // Stores the atomic masses of the available elements.
    //
    // The element symbol is the key (String),
    // and its atomic mass is the value (f64).
    //
    // Example:
    // "H" -> 1.008
    // "O" -> 15.999
    pub atomic_masses: HashMap<String, f64>,
}


/// Represents the contribution of an individual element
/// to the total molar mass of a chemical compound.
///
/// This struct is used to provide a detailed breakdown
/// of the molar mass calculation.
///
/// `Serialize` allows Serde to convert this Rust struct
/// into JSON so it can be returned to the client.
#[derive(Debug, Serialize)]
pub struct ElementBreakdown {

    // Chemical symbol of the element.
    //
    // Example: "H", "O", "Na"
    pub symbol: String,

    // Number of atoms of this element in the compound.
    //
    // Example:
    // In H2O, hydrogen has a count of 2.
    pub count: u32,

    // Atomic mass of the element.
    //
    // Example:
    // Hydrogen has an atomic mass of approximately 1.008.
    pub atomic_mass: f64,

    // Total mass contribution of this element.
    //
    // Calculated using:
    // Atomic mass × Number of atoms
    //
    // Example:
    // Hydrogen: 1.008 × 2 = 2.016
    pub contribution: f64,
}


/// Represents the successful response returned by the molar mass API.
///
/// Contains the original formula, calculated molar mass,
/// measurement unit, and individual element contributions.
///
/// `Serialize` converts this struct into a JSON response.
#[derive(Debug, Serialize)]
pub struct MolarMassResponse {

    // Chemical formula submitted by the client.
    pub formula: String,

    // Final calculated molar mass of the compound.
    //
    // Example:
    // Water (H2O) = 18.015 g/mol
    pub molar_mass: f64,

    // Unit used to express the molar mass.
    //
    // In this application, the unit is "g/mol"
    // (grams per mole).
    pub unit: String,

    // List containing the contribution of each element.
    //
    // Each entry uses the ElementBreakdown struct.
    pub breakdown: Vec<ElementBreakdown>,
}


/// Represents an error response returned by the API.
///
/// Used when a calculation request cannot be processed,
/// for example, when the chemical formula contains
/// an unknown element or invalid syntax.
///
/// `Serialize` allows the error message to be returned as JSON.
#[derive(Debug, Serialize)]
pub struct ErrorResponse {

    // Human-readable description of the error.
    //
    // Example:
    // "Unknown element: Xx"
    pub error: String,
}
