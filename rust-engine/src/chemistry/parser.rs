
use std::collections::HashMap;

/// FormulaParser is responsible for reading and interpreting
/// a chemical formula, such as H2O, Ca(OH)2, or Al2(SO4)3.
///
/// The lifetime `'a` ensures that the parser borrows the formula
/// and atomic mass data without taking ownership of them.
pub struct FormulaParser<'a> {
    // Original chemical formula provided by the user.
    formula: &'a str,

    // Byte representation of the formula.
    // Using bytes makes it easier to inspect individual ASCII characters.
    bytes: &'a [u8],

    // Tracks the current position while parsing the formula.
    // It is incremented as characters are processed.
    position: usize,

    // Reference to the HashMap containing valid element symbols
    // and their corresponding atomic masses.
    atomic_masses: &'a HashMap<String, f64>,
}

impl<'a> FormulaParser<'a> {

    /// Creates a new FormulaParser instance.
    ///
    /// # Parameters
    /// - `formula`: Chemical formula to parse.
    /// - `atomic_masses`: Available element symbols and atomic masses.
    ///
    /// The parser starts at position 0.
    pub fn new(formula: &'a str, atomic_masses: &'a HashMap<String, f64>) -> Self {
        Self {
            formula,
            bytes: formula.as_bytes(),
            position: 0,
            atomic_masses,
        }
    }

    /// Parses the complete chemical formula.
    ///
    /// Returns a HashMap containing each element and its total atom count.
    ///
    /// Example:
    /// "H2O" produces:
    /// H -> 2
    /// O -> 1
    pub fn parse(&mut self) -> Result<HashMap<String, u32>, String> {

        // Check whether the formula is empty.
        // An empty formula cannot represent a chemical compound.
        if self.formula.is_empty() {
            return Err("Formula cannot be empty".to_string());
        }

        // Only ASCII characters are supported.
        // This allows safe byte-by-byte parsing and string slicing.
        if !self.formula.is_ascii() {
            return Err("Formula must contain ASCII characters only".to_string());
        }

        // Start parsing the formula as a group.
        // false means that this is the outermost group,
        // so a closing parenthesis is not expected.
        let result = self.parse_group(false)?;

        // Ensure that at least one element was successfully parsed.
        if result.is_empty() {
            return Err("Formula cannot be empty".to_string());
        }

        // Return the parsed elements and their atom counts.
        Ok(result)
    }

    /// Parses a group of elements.
    ///
    /// A group can contain:
    /// - Individual elements, such as H or O.
    /// - Elements with multipliers, such as H2 or O4.
    /// - Nested groups, such as (OH)2.
    ///
    /// `expect_closing` determines whether the current group
    /// must end with a closing parenthesis.
    fn parse_group(
        &mut self,
        expect_closing: bool,
    ) -> Result<HashMap<String, u32>, String> {

        // Stores the elements and their accumulated atom counts
        // within the current group.
        let mut elements = HashMap::new();

        // Continue parsing until the end of the formula is reached.
        while self.position < self.bytes.len() {

            // Read the character at the current position.
            let current = self.bytes[self.position];

            match current {

                // Handle a closing parenthesis ')'.
                b')' => {

                    // A closing parenthesis is only valid inside
                    // a group that was opened with '('.
                    if !expect_closing {
                        return Err(format!(
                            "Unexpected closing parenthesis at position {}",
                            self.position + 1
                        ));
                    }

                    // Move past the closing parenthesis.
                    self.position += 1;

                    // Return the elements found inside this group.
                    return Ok(elements);
                }

                // Handle an opening parenthesis '('.
                b'(' => {

                    // Move past '(' before parsing the nested group.
                    self.position += 1;

                    // Recursively parse the contents of the parentheses.
                    //
                    // For example:
                    // (OH)2
                    // The nested parser processes OH.
                    let nested = self.parse_group(true)?;

                    // Reject empty groups such as ().
                    if nested.is_empty() {
                        return Err("Empty parentheses are not allowed".to_string());
                    }

                    // Read the multiplier immediately after ')'.
                    //
                    // Example:
                    // (OH)2 -> multiplier = 2
                    // (SO4)3 -> multiplier = 3
                    let multiplier = self.parse_multiplier()?;

                    // Apply the multiplier to every element inside
                    // the nested group.
                    for (symbol, count) in nested {

                        // Multiply the element count by the group multiplier.
                        //
                        // checked_mul prevents integer overflow.
                        let multiplied = count
                            .checked_mul(multiplier)
                            .ok_or_else(|| {
                                "Atom count exceeds supported limit".to_string()
                            })?;

                        // Get the existing count for this element.
                        // If the element does not exist, initialize it to 0.
                        let entry = elements.entry(symbol).or_insert(0u32);

                        // Add the multiplied count to the current total.
                        //
                        // checked_add prevents integer overflow when
                        // the same element appears multiple times.
                        *entry = entry
                            .checked_add(multiplied)
                            .ok_or_else(|| {
                                "Atom count exceeds supported limit".to_string()
                            })?;
                    }
                }

                // Handle an element symbol beginning with an uppercase letter.
                //
                // Examples:
                // H, O, C, Na, Mg, Fe
                byte if byte.is_ascii_uppercase() => {

                    // Remember the starting position of the element symbol.
                    let start = self.position;

                    // Move past the uppercase letter.
                    self.position += 1;

                    // Continue reading lowercase letters.
                    // This supports element symbols containing multiple letters.
                    //
                    // Examples:
                    // Na, Mg, Fe, Cl
                    while self.position < self.bytes.len()
                        && self.bytes[self.position].is_ascii_lowercase()
                    {
                        self.position += 1;
                    }

                    // Extract the complete element symbol from the formula.
                    //
                    // Example:
                    // If the formula contains Na, symbol will be "Na".
                    let symbol = &self.formula[start..self.position];

                    // Check whether the element exists in the atomic masses HashMap.
                    // If it does not exist, return an unknown element error.
                    if !self.atomic_masses.contains_key(symbol) {
                        return Err(format!("Unknown element: {}", symbol));
                    }

                    // Read the multiplier following the element symbol.
                    //
                    // Examples:
                    // H2 -> 2
                    // O4 -> 4
                    // Na -> 1 (default when no number is present)
                    let count = self.parse_multiplier()?;

                    // Get the existing atom count for this element.
                    // Initialize it to zero if this is its first occurrence.
                    let entry = elements
                        .entry(symbol.to_string())
                        .or_insert(0u32);

                    // Add the new atom count to the existing count.
                    //
                    // checked_add prevents integer overflow.
                    *entry = entry
                        .checked_add(count)
                        .ok_or_else(|| {
                            "Atom count exceeds supported limit".to_string()
                        })?;
                }

                // Handle any character that does not match the supported syntax.
                //
                // Examples of invalid characters:
                // @, #, +, -, [, ], lowercase letters at the start
                _ => {
                    return Err(format!(
                        "Invalid character '{}' at position {}",
                        current as char,
                        self.position + 1
                    ));
                }
            }
        }

        // If this group was opened with '(' but the formula ended
        // before ')' was found, return a missing parenthesis error.
        if expect_closing {
            return Err("Missing closing parenthesis ')'".to_string());
        }

        // Return all elements found in the current group.
        Ok(elements)
    }

    /// Reads a numerical multiplier from the current position.
    ///
    /// Examples:
    /// - H2 -> returns 2
    /// - O4 -> returns 4
    /// - Na -> returns 1
    ///
    /// The multiplier must be a positive u32 value.
    fn parse_multiplier(&mut self) -> Result<u32, String> {

        // Save the starting position of the multiplier.
        let start = self.position;

        // Continue reading while the current character is a digit.
        while self.position < self.bytes.len()
            && self.bytes[self.position].is_ascii_digit()
        {
            self.position += 1;
        }

        // If no digits were found, the default multiplier is 1.
        //
        // Example:
        // H -> 1 atom of hydrogen.
        // O -> 1 atom of oxygen.
        if start == self.position {
            return Ok(1);
        }

        // Extract the digits and convert them into a u32.
        //
        // If the number is too large or cannot be parsed,
        // return an error.
        let multiplier: u32 = self.formula[start..self.position]
            .parse()
            .map_err(|_| {
                "Invalid or excessively large multiplier".to_string()
            })?;

        // A multiplier of zero is not allowed because an element
        // or group must contain at least one atom.
        if multiplier == 0 {
            return Err("Atom count must be positive".to_string());
        }

        // Return the successfully parsed multiplier.
        Ok(multiplier)
    }
}

/// Public function used by other parts of the application
/// to parse a chemical formula.
///
/// This function creates a FormulaParser and calls its parse method.
///
/// # Parameters
/// - `formula`: Chemical formula supplied by the user.
/// - `atomic_masses`: HashMap containing valid element symbols.
///
/// # Returns
/// A HashMap containing element symbols and their total atom counts,
/// or an error if the formula is invalid.
pub fn parse_formula(
    formula: &str,
    atomic_masses: &HashMap<String, f64>,
) -> Result<HashMap<String, u32>, String> {

    // Create a new parser instance for the supplied formula.
    let mut parser = FormulaParser::new(formula, atomic_masses);

    // Parse the formula and return the result.
    parser.parse()
}
