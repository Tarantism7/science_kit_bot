
use std::collections::HashMap;

use crate::models::ElementBreakdown;

use super::parser::parse_formula;

/// Stores the result of a molar mass calculation.
///
/// `total` contains the total molar mass of the compound.
/// `breakdown` contains the contribution of each individual element.
pub struct MolarMassResult {
    pub total: f64,
    pub breakdown: Vec<ElementBreakdown>,
}

/// Calculates the molar mass of a chemical compound.
///
/// # Parameters
/// - `formula`: The chemical formula as a string (e.g. "H2O").
/// - `atomic_masses`: A HashMap containing element symbols and their atomic masses.
///
/// # Returns
/// - `Ok(MolarMassResult)` if the calculation is successful.
/// - `Err(String)` if the formula is invalid or a calculation error occurs.
pub fn calculate_molar_mass(
    formula: &str,
    atomic_masses: &HashMap<String, f64>,
) -> Result<MolarMassResult, String> {

    // Parse the chemical formula and determine how many atoms
    // of each element are present.
    //
    // For example, "H2O" produces:
    // H -> 2
    // O -> 1
    let elements = parse_formula(formula, atomic_masses)?;

    // Convert the HashMap into a Vector so that the elements
    // can be sorted alphabetically.
    //
    // Sorting ensures that the breakdown is always returned
    // in the same order, making the result deterministic.
    let mut elements: Vec<_> = elements.into_iter().collect();

    // Sort elements by their chemical symbols.
    elements.sort_by(|a, b| a.0.cmp(&b.0));

    // Initialize the total molar mass.
    let mut total = 0.0;

    // Store the individual mass contributions of each element.
    let mut breakdown = Vec::new();

    // Iterate over every element and its atom count.
    for (symbol, count) in elements {

        // Retrieve the atomic mass of the current element.
        // Return an error if the element is missing from the HashMap.
        let atomic_mass = atomic_masses
            .get(&symbol)
            .ok_or_else(|| format!("Missing atomic mass for {}", symbol))?;

        // Validate the atomic mass.
        // Atomic masses must be finite and greater than zero.
        //
        // This prevents invalid values such as:
        // - NaN (Not a Number)
        // - Infinity
        // - Zero or negative values
        if !atomic_mass.is_finite() || *atomic_mass <= 0.0 {
            return Err(format!("Invalid atomic mass for element {}", symbol));
        }

        // Calculate the mass contribution of this element.
        //
        // Formula:
        // Contribution = Atomic Mass × Number of Atoms
        //
        // Example:
        // Hydrogen: 1.008 × 2 = 2.016
        let contribution = atomic_mass * count as f64;

        // Check whether the multiplication produced a valid number.
        // This can happen if the result overflows the f64 range.
        if !contribution.is_finite() {
            return Err("Invalid calculation result".to_string());
        }

        // Add this element's contribution to the total molar mass.
        total += contribution;

        // Store the element's calculation details.
        // This allows the caller to see how the final result was obtained.
        breakdown.push(ElementBreakdown {
            symbol,
            count,
            atomic_mass: *atomic_mass,
            contribution,
        });
    }

    // Perform a final validation of the total molar mass.
    // Ensures that adding all contributions did not produce
    // an invalid floating-point value.
    if !total.is_finite() {
        return Err("Calculation produced an invalid result".to_string());
    }

    // Return the final result containing:
    // 1. The total molar mass.
    // 2. A breakdown of each element's contribution.
    Ok(MolarMassResult { total, breakdown })
}
