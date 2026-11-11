CREATE TABLE chemistry_formulas (
    id BIGSERIAL PRIMARY KEY,
    category VARCHAR(100) NOT NULL,
    name VARCHAR(200) NOT NULL,
    formula TEXT NOT NULL,
    variables TEXT NOT NULL,
    description TEXT,
    units TEXT,
    keywords TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_chemistry_formulas_category
    ON chemistry_formulas(category);

CREATE INDEX idx_chemistry_formulas_name
    ON chemistry_formulas(name);

CREATE INDEX idx_chemistry_formulas_keywords
    ON chemistry_formulas USING GIN (
        to_tsvector('simple', COALESCE(keywords, ''))
    );

INSERT INTO chemistry_formulas
(category, name, formula, variables, description, units, keywords)
VALUES

(
    'Stoichiometry',
    'Mole Calculation',
    'n = m / M',
    'n = amount of substance, m = mass, M = molar mass',
    'Calculates the amount of substance from mass and molar mass.',
    'n: mol, m: g, M: g/mol',
    'mole,mass,molar,molar mass,stoichiometry'
),

(
    'Solutions',
    'Molarity',
    'c = n / V',
    'c = concentration, n = amount of substance, V = solution volume',
    'Calculates the molar concentration of a solution.',
    'c: mol/L, n: mol, V: L',
    'molarity,concentration,solution,moles'
),

(
    'Solutions',
    'Dilution Equation',
    'C₁V₁ = C₂V₂',
    'C₁ = initial concentration, V₁ = initial volume, C₂ = final concentration, V₂ = final volume',
    'Calculates concentration or volume after dilution.',
    'C: mol/L, V: L',
    'dilution,concentration,solution'
),

(
    'Acids and Bases',
    'pH',
    'pH = -log₁₀[H⁺]',
    '[H⁺] = hydrogen ion concentration',
    'Calculates pH from hydrogen ion concentration.',
    '[H⁺]: mol/L',
    'ph,acids,bases,hydrogen,acidity'
),

(
    'Acids and Bases',
    'pOH',
    'pOH = -log₁₀[OH⁻]',
    '[OH⁻] = hydroxide ion concentration',
    'Calculates pOH from hydroxide ion concentration.',
    '[OH⁻]: mol/L',
    'poh,hydroxide,bases'
),

(
    'Acids and Bases',
    'pH and pOH Relationship',
    'pH + pOH = 14',
    'pH = acidity, pOH = basicity',
    'Relationship at approximately 25 °C.',
    'Dimensionless',
    'ph,poh,acid,base,water'
),

(
    'Gas Laws',
    'Ideal Gas Law',
    'PV = nRT',
    'P = pressure, V = volume, n = amount, R = gas constant, T = temperature',
    'Describes the relationship between pressure, volume and temperature of an ideal gas.',
    'P: Pa, V: m³, n: mol, T: K',
    'gas,ideal,pressure,volume,temperature'
),

(
    'Density',
    'Density',
    'ρ = m / V',
    'ρ = density, m = mass, V = volume',
    'Calculates density from mass and volume.',
    'ρ: g/cm³, m: g, V: cm³',
    'density,mass,volume'
),

(
    'Chemical Composition',
    'Mass Percentage',
    'Mass % = (mass of component / total mass) × 100',
    'Mass of component and total mass',
    'Calculates the percentage of a component in a mixture.',
    '%',
    'mass percentage,composition,mixture'
),

(
    'Thermochemistry',
    'Heat',
    'q = mcΔT',
    'q = heat, m = mass, c = specific heat capacity, ΔT = temperature change',
    'Calculates heat absorbed or released during a temperature change.',
    'q: J, m: g, c: J/(g·K), ΔT: K',
    'heat,thermochemistry,specific heat,temperature'
);