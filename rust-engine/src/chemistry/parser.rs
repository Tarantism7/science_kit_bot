use std::collections::HashMap;

pub struct FormulaParser<'a> {

    formula: &'a str,

    bytes: &'a [u8],

    position: usize,

    atomic_masses: &'a HashMap<String, f64>,
}

impl<'a> FormulaParser<'a> {

    pub fn new(formula: &'a str, atomic_masses: &'a HashMap<String, f64>) -> Self {
        Self {
            formula,
            bytes: formula.as_bytes(),
            position: 0,
            atomic_masses,
        }
    }

    pub fn parse(&mut self) -> Result<HashMap<String, u32>, String> {

        if self.formula.is_empty() {
            return Err("Formula cannot be empty".to_string());
        }

        if !self.formula.is_ascii() {
            return Err("Formula must contain ASCII characters only".to_string());
        }

        let result = self.parse_group(false)?;

        if result.is_empty() {
            return Err("Formula cannot be empty".to_string());
        }

        Ok(result)
    }

    fn parse_group(
        &mut self,
        expect_closing: bool,
    ) -> Result<HashMap<String, u32>, String> {

        let mut elements = HashMap::new();

        while self.position < self.bytes.len() {

            let current = self.bytes[self.position];

            match current {

                b')' => {

                    if !expect_closing {
                        return Err(format!(
                            "Unexpected closing parenthesis at position {}",
                            self.position + 1
                        ));
                    }

                    self.position += 1;

                    return Ok(elements);
                }

                b'(' => {

                    self.position += 1;

                    let nested = self.parse_group(true)?;

                    if nested.is_empty() {
                        return Err("Empty parentheses are not allowed".to_string());
                    }

                    let multiplier = self.parse_multiplier()?;

                    for (symbol, count) in nested {

                        let multiplied = count
                            .checked_mul(multiplier)
                            .ok_or_else(|| {
                                "Atom count exceeds supported limit".to_string()
                            })?;

                        let entry = elements.entry(symbol).or_insert(0u32);

                        *entry = entry
                            .checked_add(multiplied)
                            .ok_or_else(|| {
                                "Atom count exceeds supported limit".to_string()
                            })?;
                    }
                }

                byte if byte.is_ascii_uppercase() => {

                    let start = self.position;
                    self.position += 1;

                    while self.position < self.bytes.len()
                        && self.bytes[self.position].is_ascii_lowercase()
                    {
                        self.position += 1;
                    }

                    let symbol = &self.formula[start..self.position];

                    if !self.atomic_masses.contains_key(symbol) {
                        return Err(format!("Unknown element: {}", symbol));
                    }

                    let count = self.parse_multiplier()?;
                                        
                    let entry = elements
                        .entry(symbol.to_string())
                        .or_insert(0u32);

                    *entry = entry
                        .checked_add(count)
                        .ok_or_else(|| {
                            "Atom count exceeds supported limit".to_string()
                        })?;
                }
                _ => {
                    return Err(format!(
                        "Invalid character '{}' at position {}",
                        current as char,
                        self.position + 1
                    ));
                }
            }
        }
        if expect_closing {
            return Err("Missing closing parenthesis ')'".to_string());
        }

        Ok(elements)
    }

    fn parse_multiplier(&mut self) -> Result<u32, String> {

        let start = self.position;

        while self.position < self.bytes.len()
            && self.bytes[self.position].is_ascii_digit()
        {
            self.position += 1;
        }

        if start == self.position {
            return Ok(1);
        }

        let multiplier: u32 = self.formula[start..self.position]
            .parse()
            .map_err(|_| {
                "Invalid or excessively large multiplier".to_string()
            })?;

        if multiplier == 0 {
            return Err("Atom count must be positive".to_string());
        }

        Ok(multiplier)
    }
}

pub fn parse_formula(
    formula: &str,
    atomic_masses: &HashMap<String, f64>,
) -> Result<HashMap<String, u32>, String> {

    let mut parser = FormulaParser::new(formula, atomic_masses);

    parser.parse()
}
