use std::collections::HashMap;

use crate::models::ElementBreakdown;

use super::parser::parse_formula;

pub struct MolarMassResult {
    pub total: f64,
    pub breakdown: Vec<ElementBreakdown>,
}

pub fn calculate_molar_mass(
    formula: &str,
    atomic_masses: &HashMap<String, f64>,
    ) -> Result<MolarMassResult, String> {

    let elements = parse_formula(formula, atomic_masses)?;

    let mut elements: Vec<_> = elements.into_iter().collect();

    elements.sort_by(|a, b| a.0.cmp(&b.0));

    let mut total = 0.0;

    let mut breakdown = Vec::new();

    for (symbol, count) in elements {

        let atomic_mass = atomic_masses
            .get(&symbol)
            .ok_or_else(|| format!("Missing atomic mass for {}", symbol))?;

        if !atomic_mass.is_finite() || *atomic_mass <= 0.0 {
            return Err(format!("Invalid atomic mass for element {}", symbol));
        }

        let contribution = atomic_mass * count as f64;

        if !contribution.is_finite() {
            return Err("Invalid calculation result".to_string());
        }

        total += contribution;

        breakdown.push(ElementBreakdown {
            symbol,
            count,
            atomic_mass: *atomic_mass,
            contribution,
        });
    }

    if !total.is_finite() {
        return Err("Calculation produced an invalid result".to_string());
    }
    Ok(MolarMassResult { total, breakdown })
}
