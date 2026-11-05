
--liquibase formatted sql

--changeset science-kit:chem03-add-formulas

INSERT INTO chemistry_formulas
(category, name, formula, variables, description, units, keywords)
VALUES

-- STOICHIOMETRY

(
    'Stoichiometry',
    'Number of Particles',
    'N = n × Nₐ',
    'N = number of particles, n = amount of substance, Nₐ = Avogadro constant',
    'Calculates the number of particles from the amount of substance.',
    'N: particles, n: mol, Nₐ: mol⁻¹',
    'particles,avogadro,atoms,molecules,moles'
),
(
    'Stoichiometry',
    'Molar Mass',
    'M = m / n',
    'M = molar mass, m = mass, n = amount of substance',
    'Calculates molar mass from mass and amount of substance.',
    'M: g/mol, m: g, n: mol',
    'molar mass,mass,moles,stoichiometry'
),
(
    'Stoichiometry',
    'Limiting Reactant',
    'n(product) = n(reactant) × coefficient(product) / coefficient(reactant)',
    'n = amount of substance, coefficients = stoichiometric coefficients',
    'Determines the theoretical amount of product based on the limiting reactant.',
    'n: mol',
    'limiting reactant,excess reactant,stoichiometry,yield'
),
(
    'Stoichiometry',
    'Percentage Yield',
    'Yield % = (actual yield / theoretical yield) × 100',
    'Actual yield = experimentally obtained product, theoretical yield = maximum predicted product',
    'Calculates the efficiency of a chemical reaction.',
    '%',
    'yield,percentage yield,reaction efficiency,stoichiometry'
),
(
    'Stoichiometry',
    'Empirical Formula',
    'Element ratio = moles of element / smallest mole value',
    'Moles of element = mass of element / atomic molar mass',
    'Determines the simplest whole-number ratio of atoms in a compound.',
    'mol',
    'empirical formula,element ratio,composition,stoichiometry'
),

-- SOLUTIONS

(
    'Solutions',
    'Molality',
    'b = n(solute) / m(solvent)',
    'b = molality, n = amount of solute, m = mass of solvent',
    'Calculates the amount of solute per kilogram of solvent.',
    'b: mol/kg, n: mol, m: kg',
    'molality,solution,concentration,solvent,solute'
),
(
    'Solutions',
    'Mass Concentration',
    'β = m / V',
    'β = mass concentration, m = mass of solute, V = solution volume',
    'Calculates the mass of solute per unit volume of solution.',
    'β: g/L, m: g, V: L',
    'mass concentration,solution,concentration'
),
(
    'Solutions',
    'Mole Fraction',
    'Xᵢ = nᵢ / n(total)',
    'Xᵢ = mole fraction, nᵢ = moles of component, n(total) = total moles',
    'Calculates the proportion of a component in a mixture.',
    'Dimensionless',
    'mole fraction,mixture,solutions,composition'
),
(
    'Solutions',
    'Parts Per Million',
    'ppm = (mass of component / total mass) × 10⁶',
    'Mass of component = mass of substance being measured, total mass = mass of mixture',
    'Expresses very small concentrations of a substance.',
    'ppm',
    'ppm,concentration,trace,solutions'
),

-- ACIDS AND BASES

(
    'Acids and Bases',
    'Hydrogen Ion Concentration',
    '[H⁺] = 10^(-pH)',
    '[H⁺] = hydrogen ion concentration, pH = acidity',
    'Calculates hydrogen ion concentration from pH.',
    '[H⁺]: mol/L',
    'hydrogen ion,ph,acidity,acid'
),
(
    'Acids and Bases',
    'Hydroxide Ion Concentration',
    '[OH⁻] = 10^(-pOH)',
    '[OH⁻] = hydroxide ion concentration, pOH = basicity',
    'Calculates hydroxide ion concentration from pOH.',
    '[OH⁻]: mol/L',
    'hydroxide ion,poh,bases,basicity'
),
(
    'Acids and Bases',
    'Water Ion Product',
    'K_w = [H⁺][OH⁻]',
    'K_w = ionic product of water, [H⁺] = hydrogen ion concentration, [OH⁻] = hydroxide ion concentration',
    'Describes the equilibrium between hydrogen and hydroxide ions in water.',
    'K_w: dimensionless under standard-state convention',
    'water,ionic product,equilibrium,kw,acids,bases'
),
(
    'Acids and Bases',
    'Acid Dissociation Constant',
    'K_a = [H⁺][A⁻] / [HA]',
    'K_a = acid dissociation constant, HA = acid, A⁻ = conjugate base',
    'Measures the strength of an acid in solution.',
    'K_a: dimensionless under standard-state convention',
    'ka,acid strength,dissociation,equilibrium'
),
(
    'Acids and Bases',
    'Henderson-Hasselbalch Equation',
    'pH = pK_a + log₁₀([A⁻] / [HA])',
    'pH = acidity, pK_a = negative logarithm of K_a, [A⁻] = conjugate base concentration, [HA] = acid concentration',
    'Calculates the pH of a buffer solution.',
    'Dimensionless',
    'buffer,henderson hasselbalch,ph,pka,acid'
),

-- GAS LAWS

(
    'Gas Laws',
    'Boyles Law',
    'P₁V₁ = P₂V₂',
    'P₁ = initial pressure, V₁ = initial volume, P₂ = final pressure, V₂ = final volume',
    'Describes the inverse relationship between pressure and volume at constant temperature.',
    'P: Pa, V: m³',
    'boyle,pressure,volume,gas laws'
),
(
    'Gas Laws',
    'Charles Law',
    'V₁ / T₁ = V₂ / T₂',
    'V₁ = initial volume, T₁ = initial temperature, V₂ = final volume, T₂ = final temperature',
    'Describes the relationship between gas volume and absolute temperature at constant pressure.',
    'V: m³, T: K',
    'charles,temperature,volume,gas laws'
),
(
    'Gas Laws',
    'Gay-Lussac Law',
    'P₁ / T₁ = P₂ / T₂',
    'P₁ = initial pressure, T₁ = initial temperature, P₂ = final pressure, T₂ = final temperature',
    'Describes the relationship between gas pressure and absolute temperature at constant volume.',
    'P: Pa, T: K',
    'gay lussac,pressure,temperature,gas laws'
),
(
    'Gas Laws',
    'Combined Gas Law',
    'P₁V₁ / T₁ = P₂V₂ / T₂',
    'P = pressure, V = volume, T = absolute temperature',
    'Combines pressure, volume and temperature relationships for a fixed amount of ideal gas.',
    'P: Pa, V: m³, T: K',
    'combined gas law,pressure,volume,temperature'
),
(
    'Gas Laws',
    'Daltons Law of Partial Pressures',
    'P(total) = P₁ + P₂ + ... + Pₙ',
    'P(total) = total pressure, Pᵢ = partial pressure of each gas',
    'Calculates the total pressure of a mixture of non-reacting gases.',
    'P: Pa',
    'dalton,partial pressure,gas mixture,pressure'
),

-- DENSITY

(
    'Density',
    'Relative Density',
    'd = ρ(substance) / ρ(reference)',
    'd = relative density, ρ = density',
    'Compares the density of a substance with a reference substance.',
    'Dimensionless',
    'relative density,specific gravity,density'
),

-- CHEMICAL COMPOSITION

(
    'Chemical Composition',
    'Atomic Mass Percentage',
    'Element % = (mass of element / molar mass of compound) × 100',
    'Mass of element = total atomic mass contribution of that element in one mole of compound',
    'Calculates the mass percentage of an element in a chemical compound.',
    '%',
    'element percentage,composition,molar mass'
),

-- THERMOCHEMISTRY

(
    'Thermochemistry',
    'Enthalpy Change',
    'ΔH = H(products) - H(reactants)',
    'ΔH = enthalpy change, H = enthalpy',
    'Calculates the enthalpy change of a chemical reaction.',
    'ΔH: kJ/mol',
    'enthalpy,reaction heat,thermochemistry'
),
(
    'Thermochemistry',
    'Calorimetry',
    'q(system) = -q(surroundings)',
    'q = heat transferred between the system and surroundings',
    'Describes conservation of heat energy in an isolated calorimetric setup.',
    'q: J',
    'calorimetry,heat,energy,thermochemistry'
),
(
    'Thermochemistry',
    'Gibbs Free Energy',
    'ΔG = ΔH - TΔS',
    'ΔG = Gibbs free energy change, ΔH = enthalpy change, T = absolute temperature, ΔS = entropy change',
    'Predicts thermodynamic spontaneity at constant temperature and pressure.',
    'ΔG: J/mol, ΔH: J/mol, T: K, ΔS: J/(mol·K)',
    'gibbs,free energy,entropy,enthalpy,spontaneity'
),
(
    'Thermochemistry',
    'Entropy Change',
    'ΔS = q(rev) / T',
    'ΔS = entropy change, q(rev) = reversible heat transfer, T = absolute temperature',
    'Relates entropy change to reversible heat transfer.',
    'ΔS: J/K, q: J, T: K',
    'entropy,heat,thermodynamics'
),

-- ELECTROCHEMISTRY

(
    'Electrochemistry',
    'Cell Potential',
    'E(cell) = E(cathode) - E(anode)',
    'E(cell) = cell potential, E(cathode) = cathode reduction potential, E(anode) = anode reduction potential',
    'Calculates the standard cell potential from standard reduction potentials.',
    'E: V',
    'electrochemistry,cell potential,voltage,electrode'
),
(
    'Electrochemistry',
    'Faradays Law',
    'm = (M × I × t) / (z × F)',
    'm = deposited mass, M = molar mass, I = current, t = time, z = electrons transferred per ion, F = Faraday constant',
    'Calculates the mass of a substance deposited during electrolysis.',
    'm: g, M: g/mol, I: A, t: s, F: C/mol',
    'faraday,electrolysis,current,electric charge'
),

-- CHEMICAL KINETICS

(
    'Chemical Kinetics',
    'Average Reaction Rate',
    'Rate = -Δ[reactant] / Δt',
    'Rate = reaction rate, Δ[reactant] = concentration change, Δt = time interval',
    'Calculates the average rate at which a reactant is consumed.',
    'mol/(L·s)',
    'reaction rate,kinetics,concentration,time'
),
(
    'Chemical Kinetics',
    'First Order Half-Life',
    't₁/₂ = ln(2) / k',
    't₁/₂ = half-life, k = first-order rate constant',
    'Calculates the half-life of a first-order reaction.',
    't: s, k: s⁻¹',
    'half life,first order,kinetics,rate constant'
),
(
    'Chemical Kinetics',
    'Arrhenius Equation',
    'k = A × e^(-Eₐ / RT)',
    'k = rate constant, A = pre-exponential factor, Eₐ = activation energy, R = gas constant, T = absolute temperature',
    'Describes the temperature dependence of a reaction rate constant.',
    'Eₐ: J/mol, R: J/(mol·K), T: K',
    'arrhenius,activation energy,temperature,kinetics'
);
