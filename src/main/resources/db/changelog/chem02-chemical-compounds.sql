CREATE TABLE chemical_compounds (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    formula VARCHAR(100) NOT NULL,
    molar_mass NUMERIC(12,6),
    compound_type VARCHAR(100),
    description TEXT,
    appearance TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT unique_compound_formula UNIQUE (formula)
);

CREATE INDEX idx_chemical_compounds_name
    ON chemical_compounds(name);

CREATE INDEX idx_chemical_compounds_formula
    ON chemical_compounds(formula);

INSERT INTO chemical_compounds
(name, formula, molar_mass, compound_type, description, appearance)
VALUES

(
    'Water',
    'H2O',
    18.015,
    'Oxide',
    'A compound consisting of hydrogen and oxygen.',
    'Colorless liquid'
),

(
    'Sodium chloride',
    'NaCl',
    58.440,
    'Ionic compound',
    'Commonly known as table salt.',
    'White crystalline solid'
),

(
    'Carbon dioxide',
    'CO2',
    44.009,
    'Molecular compound',
    'A compound containing carbon and oxygen.',
    'Colorless gas'
),

(
    'Sulfuric acid',
    'H2SO4',
    98.079,
    'Acid',
    'A strong diprotic acid.',
    'Colorless liquid'
),

(
    'Glucose',
    'C6H12O6',
    180.156,
    'Carbohydrate',
    'A simple monosaccharide sugar.',
    'White crystalline solid'
);