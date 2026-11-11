
use crate::chemistry::parser::parse_formula;
use std::collections::HashMap;

fn masses() -> HashMap<String, f64> {
    HashMap::from([
        ("H".to_string(), 1.008),       // Hydrogen
        ("C".to_string(), 12.011),      // Carbon
        ("N".to_string(), 14.007),      // Nitrogen
        ("O".to_string(), 15.999),      // Oxygen
        ("S".to_string(), 32.06),       // Sulfur
        ("Al".to_string(), 26.981538),  // Aluminium
        ("Ca".to_string(), 40.078),     // Calcium
        ("Mg".to_string(), 24.305),     // Magnesium
        ("Na".to_string(), 22.989770), // Sodium
        ("Cl".to_string(), 35.45),      // Chlorine
    ])
}

#[test]
fn test_water() {

    let result = parse_formula("H2O", &masses()).unwrap();

    assert_eq!(result.get("H"), Some(&2));
    assert_eq!(result.get("O"), Some(&1));
}

#[test]
fn test_carbon_dioxide() {

    let result = parse_formula("CO2", &masses()).unwrap();

    assert_eq!(result.get("C"), Some(&1));
    assert_eq!(result.get("O"), Some(&2));
}

#[test]
fn test_calcium_hydroxide() {

    let result = parse_formula("Ca(OH)2", &masses()).unwrap();

    assert_eq!(result.get("Ca"), Some(&1));
    assert_eq!(result.get("O"), Some(&2));
    assert_eq!(result.get("H"), Some(&2));
}

#[test]
fn test_aluminium_sulfate() {

    let result = parse_formula("Al2(SO4)3", &masses()).unwrap();

    assert_eq!(result.get("Al"), Some(&2));
    assert_eq!(result.get("S"), Some(&3));
    assert_eq!(result.get("O"), Some(&12));
}

#[test]
fn test_ammonium_sulfate() {

    let result = parse_formula("(NH4)2SO4", &masses()).unwrap();

    assert_eq!(result.get("N"), Some(&2));
    assert_eq!(result.get("H"), Some(&8));
    assert_eq!(result.get("S"), Some(&1));
    assert_eq!(result.get("O"), Some(&4));
}

#[test]
fn test_nested_parentheses() {

    let result = parse_formula("Al2((OH)2)3", &masses()).unwrap();

    assert_eq!(result.get("Al"), Some(&2));
    assert_eq!(result.get("O"), Some(&6));
    assert_eq!(result.get("H"), Some(&6));
}

#[test]
fn test_invalid_formulas() {

    for formula in ["", "H0", "Xx2", "Ca(OH2", "H2O)", "()"] {
        assert!(
            parse_formula(formula, &masses()).is_err(),
            "Expected {} to be invalid",
            formula
        );
    }
}
