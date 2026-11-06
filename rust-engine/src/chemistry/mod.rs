
/// Makes the molar_mass module publicly accessible.
///
/// This module contains the logic for calculating
/// the molar mass of chemical compounds.
pub mod molar_mass;

/// Makes the parser module publicly accessible.
///
/// This module contains the logic for reading and interpreting
/// chemical formulas, including elements, multipliers,
/// and nested parentheses.
pub mod parser;

/// Includes the tests module only when running tests.
///
/// The cfg(test) attribute ensures that this module
/// is compiled during testing but excluded from normal
/// production builds.
#[cfg(test)]
mod tests;
