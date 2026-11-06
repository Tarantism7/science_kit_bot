
use crate::chemistry::parser::parse_formula;
use std::collections::HashMap;

/// Creates a HashMap containing the atomic masses
/// required by the formula parser tests.
///
/// Each entry contains:
/// - Key: Chemical element symbol.
/// - Value: Atomic mass of that element.
///
/// This function is used by all tests so that we
/// do not need to recreate the HashMap repeatedly.
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


/// Tests whether the parser correctly interprets water (H2O).
///
/// Expected atom counts:
/// - Hydrogen: 2
/// - Oxygen: 1
///
/// This test checks that the parser correctly handles
/// individual elements and numerical multipliers.
#[test]
fn test_water() {

    // Parse the chemical formula H2O.
    //
    // unwrap() extracts the successful result.
    // If parsing fails, the test automatically fails.
    let result = parse_formula("H2O", &masses()).unwrap();

    // Verify that hydrogen has a count of 2.
    assert_eq!(result.get("H"), Some(&2));

    // Verify that oxygen has a count of 1.
    assert_eq!(result.get("O"), Some(&1));
}


/// Tests whether the parser correctly interprets carbon dioxide (CO2).
///
/// Expected atom counts:
/// - Carbon: 1
/// - Oxygen: 2
///
/// This test verifies that elements without an explicit
/// multiplier default to a count of 1.
#[test]
fn test_carbon_dioxide() {

    // Parse the chemical formula CO2.
    let result = parse_formula("CO2", &masses()).unwrap();

    // Carbon has no explicit multiplier, so its count is 1.
    assert_eq!(result.get("C"), Some(&1));

    // Oxygen has a multiplier of 2.
    assert_eq!(result.get("O"), Some(&2));
}


/// Tests whether the parser correctly handles parentheses.
///
/// Chemical formula:
/// Ca(OH)2
///
/// The multiplier 2 applies to every element inside
/// the parentheses.
///
/// Expected atom counts:
/// - Calcium: 1
/// - Oxygen: 2
/// - Hydrogen: 2
#[test]
fn test_calcium_hydroxide() {

    // Parse calcium hydroxide.
    let result = parse_formula("Ca(OH)2", &masses()).unwrap();

    // Calcium appears once outside the parentheses.
    assert_eq!(result.get("Ca"), Some(&1));

    // Oxygen appears once inside the group,
    // multiplied by 2.
    assert_eq!(result.get("O"), Some(&2));

    // Hydrogen also appears once inside the group,
    // multiplied by 2.
    assert_eq!(result.get("H"), Some(&2));
}


/// Tests whether the parser correctly handles
/// a more complex formula containing parentheses and multipliers.
///
/// Chemical formula:
/// Al2(SO4)3
///
/// Expected atom counts:
/// - Aluminium: 2
/// - Sulfur: 3
/// - Oxygen: 12
///
/// This test verifies that the group multiplier
/// is applied to every element inside the parentheses.
#[test]
fn test_aluminium_sulfate() {

    // Parse aluminium sulfate.
    let result = parse_formula("Al2(SO4)3", &masses()).unwrap();

    // Aluminium has an explicit multiplier of 2.
    assert_eq!(result.get("Al"), Some(&2));

    // Sulfur has a count of 1 inside the group,
    // multiplied by 3.
    assert_eq!(result.get("S"), Some(&3));

    // Oxygen has a count of 4 inside the group,
    // multiplied by 3.
    assert_eq!(result.get("O"), Some(&12));
}


/// Tests whether the parser correctly handles
/// multiple elements and parentheses in a compound.
///
/// Chemical formula:
/// (NH4)2SO4
///
/// Expected atom counts:
/// - Nitrogen: 2
/// - Hydrogen: 8
/// - Sulfur: 1
/// - Oxygen: 4
///
/// This test checks whether the parser can combine
/// elements from a multiplied group with elements
/// appearing outside that group.
#[test]
fn test_ammonium_sulfate() {

    // Parse ammonium sulfate.
    let result = parse_formula("(NH4)2SO4", &masses()).unwrap();

    // Nitrogen: 1 × 2 = 2.
    assert_eq!(result.get("N"), Some(&2));

    // Hydrogen: 4 × 2 = 8.
    assert_eq!(result.get("H"), Some(&8));

    // Sulfur appears once outside the parentheses.
    assert_eq!(result.get("S"), Some(&1));

    // Oxygen appears four times outside the parentheses.
    assert_eq!(result.get("O"), Some(&4));
}


/// Tests whether the parser correctly handles nested parentheses.
///
/// Chemical formula:
/// Al2((OH)2)3
///
/// Expected atom counts:
/// - Aluminium: 2
/// - Oxygen: 6
/// - Hydrogen: 6
///
/// This test is particularly important because it verifies
/// that parse_group() can call itself recursively.
///
/// The inner multiplier is 2 and the outer multiplier is 3.
/// Therefore, the total multiplier for O and H is 2 × 3 = 6.
#[test]
fn test_nested_parentheses() {

    // Parse a formula containing nested groups.
    let result = parse_formula("Al2((OH)2)3", &masses()).unwrap();

    // Aluminium appears twice.
    assert_eq!(result.get("Al"), Some(&2));

    // Oxygen: 1 × 2 × 3 = 6.
    assert_eq!(result.get("O"), Some(&6));

    // Hydrogen: 1 × 2 × 3 = 6.
    assert_eq!(result.get("H"), Some(&6));
}


/// Tests whether the parser correctly rejects invalid formulas.
///
/// The test includes several different types of invalid input:
///
/// - ""       : Empty formula.
/// - "H0"     : Zero atom multiplier.
/// - "Xx2"    : Unknown element.
/// - "Ca(OH2" : Missing closing parenthesis.
/// - "H2O)"   : Unexpected closing parenthesis.
/// - "()"     : Empty parentheses.
///
/// Every formula in this list should produce an error.
#[test]
fn test_invalid_formulas() {

    // Iterate over all invalid chemical formulas.
    for formula in ["", "H0", "Xx2", "Ca(OH2", "H2O)", "()"] {

        // Verify that parsing fails for each invalid formula.
        //
        // is_err() returns true when the parser returns Err.
        //
        // The custom message helps identify which formula
        // caused the test to fail.
        assert!(
            parse_formula(formula, &masses()).is_err(),
            "Expected {} to be invalid",
            formula
        );
    }
}
