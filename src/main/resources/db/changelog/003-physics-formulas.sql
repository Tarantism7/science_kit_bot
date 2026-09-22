CREATE TABLE physics_formulas (
    id          BIGSERIAL PRIMARY KEY,
    category    VARCHAR(100) NOT NULL,
    name        VARCHAR(200) NOT NULL,
    formula     TEXT         NOT NULL,
    variables   TEXT         NOT NULL,
    description TEXT,
    units       TEXT,
    keywords    TEXT,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_physics_formulas_category
    ON physics_formulas(category);

CREATE INDEX idx_physics_formulas_name
    ON physics_formulas(name);

CREATE INDEX idx_physics_formulas_keywords
    ON physics_formulas USING GIN (
    to_tsvector('simple', COALESCE(keywords, ''))
    );

INSERT INTO physics_formulas
(category, name, formula, variables, description, units, keywords)
VALUES

-- =========================================================
-- MECHANICS
-- =========================================================

(
    'Mechanics',
    'Newton''s Second Law',
    'F = m × a',
    'F = force, m = mass, a = acceleration',
    'Force equals mass multiplied by acceleration.',
    'F: N, m: kg, a: m/s²',
    'newton,force,mass,acceleration,dynamics'
),

(
    'Mechanics',
    'Velocity',
    'v = Δx / Δt',
    'v = velocity, Δx = displacement, Δt = time',
    'Average velocity is displacement divided by elapsed time.',
    'v: m/s, x: m, t: s',
    'velocity,speed,displacement,time'
),

(
    'Mechanics',
    'Acceleration',
    'a = Δv / Δt',
    'a = acceleration, Δv = change in velocity, Δt = time',
    'Acceleration is the change in velocity per unit time.',
    'a: m/s², v: m/s, t: s',
    'acceleration,velocity,time'
),

(
    'Mechanics',
    'Kinematic Equation',
    'v = v₀ + a × t',
    'v = final velocity, v₀ = initial velocity, a = acceleration, t = time',
    'Final velocity for constant acceleration.',
    'v: m/s, v₀: m/s, a: m/s², t: s',
    'kinematics,velocity,acceleration,time'
),

(
    'Mechanics',
    'Displacement with Constant Acceleration',
    'x = x₀ + v₀t + ½at²',
    'x = final position, x₀ = initial position, v₀ = initial velocity, a = acceleration, t = time',
    'Position under constant acceleration.',
    'x: m, v₀: m/s, a: m/s², t: s',
    'kinematics,displacement,position'
),

(
    'Mechanics',
    'Velocity Without Time',
    'v² = v₀² + 2aΔx',
    'v = final velocity, v₀ = initial velocity, a = acceleration, Δx = displacement',
    'Kinematic relation that does not require time.',
    'v: m/s, a: m/s², x: m',
    'kinematics,velocity,displacement'
),

(
    'Mechanics',
    'Momentum',
    'p = m × v',
    'p = momentum, m = mass, v = velocity',
    'Linear momentum of an object.',
    'p: kg·m/s, m: kg, v: m/s',
    'momentum,mass,velocity'
),

(
    'Mechanics',
    'Impulse',
    'J = F × Δt = Δp',
    'J = impulse, F = force, Δt = time interval, Δp = change in momentum',
    'Impulse equals the change in momentum.',
    'J: N·s, F: N, t: s',
    'impulse,momentum,force'
),

(
    'Mechanics',
    'Work',
    'W = F × d × cos(θ)',
    'W = work, F = force, d = displacement, θ = angle between force and displacement',
    'Work performed by a constant force.',
    'W: J, F: N, d: m',
    'work,force,displacement'
),

(
    'Mechanics',
    'Power',
    'P = W / t',
    'P = power, W = work, t = time',
    'Rate at which work is performed.',
    'P: W, W: J, t: s',
    'power,work,time'
),

(
    'Mechanics',
    'Kinetic Energy',
    'K = ½mv²',
    'K = kinetic energy, m = mass, v = velocity',
    'Energy associated with motion.',
    'K: J, m: kg, v: m/s',
    'kinetic,energy,mass,velocity'
),

(
    'Mechanics',
    'Gravitational Potential Energy',
    'U = mgh',
    'U = potential energy, m = mass, g = gravitational acceleration, h = height',
    'Potential energy near Earth''s surface.',
    'U: J, m: kg, g: m/s², h: m',
    'potential,energy,gravity,height'
),

(
    'Mechanics',
    'Hooke''s Law',
    'F = -kx',
    'F = restoring force, k = spring constant, x = displacement',
    'Restoring force of an ideal spring.',
    'F: N, k: N/m, x: m',
    'spring,hooke,elasticity,force'
),

(
    'Mechanics',
    'Centripetal Acceleration',
    'aᶜ = v² / r',
    'aᶜ = centripetal acceleration, v = speed, r = radius',
    'Acceleration directed toward the center of circular motion.',
    'aᶜ: m/s², v: m/s, r: m',
    'circular,centripetal,acceleration'
),

(
    'Mechanics',
    'Centripetal Force',
    'Fᶜ = mv² / r',
    'Fᶜ = centripetal force, m = mass, v = speed, r = radius',
    'Force required for circular motion.',
    'Fᶜ: N, m: kg, v: m/s, r: m',
    'circular,centripetal,force'
),

(
    'Mechanics',
    'Torque',
    'τ = rF sin(θ)',
    'τ = torque, r = lever arm, F = force, θ = angle',
    'Rotational effect of a force.',
    'τ: N·m, r: m, F: N',
    'torque,moment,rotation'
),

(
    'Mechanics',
    'Angular Velocity',
    'ω = Δθ / Δt',
    'ω = angular velocity, Δθ = angular displacement, Δt = time',
    'Rate of change of angular position.',
    'ω: rad/s, θ: rad, t: s',
    'angular,velocity,rotation'
),

(
    'Mechanics',
    'Rotational Kinetic Energy',
    'Kᵣ = ½Iω²',
    'Kᵣ = rotational kinetic energy, I = moment of inertia, ω = angular velocity',
    'Kinetic energy associated with rotation.',
    'Kᵣ: J, I: kg·m², ω: rad/s',
    'rotation,energy,moment,inertia'
),

(
    'Mechanics',
    'Universal Gravitation',
    'F = Gm₁m₂ / r²',
    'F = gravitational force, G = gravitational constant, m₁,m₂ = masses, r = distance',
    'Gravitational attraction between two masses.',
    'F: N, G: N·m²/kg², m: kg, r: m',
    'gravity,newton,gravitation'
),


-- =========================================================
-- FLUIDS
-- =========================================================

(
    'Fluids',
    'Density',
    'ρ = m / V',
    'ρ = density, m = mass, V = volume',
    'Mass per unit volume.',
    'ρ: kg/m³, m: kg, V: m³',
    'density,mass,volume,fluid'
),

(
    'Fluids',
    'Pressure',
    'p = F / A',
    'p = pressure, F = force, A = area',
    'Force per unit area.',
    'p: Pa, F: N, A: m²',
    'pressure,force,area'
),

(
    'Fluids',
    'Hydrostatic Pressure',
    'p = p₀ + ρgh',
    'p = pressure, p₀ = surface pressure, ρ = density, g = gravity, h = depth',
    'Pressure in a fluid at depth h.',
    'p: Pa, ρ: kg/m³, g: m/s², h: m',
    'fluid,pressure,depth,hydrostatic'
),

(
    'Fluids',
    'Buoyant Force',
    'Fᵦ = ρVg',
    'Fᵦ = buoyant force, ρ = fluid density, V = displaced volume, g = gravity',
    'Buoyant force from displaced fluid.',
    'Fᵦ: N, ρ: kg/m³, V: m³',
    'buoyancy,archimedes,fluid'
),

(
    'Fluids',
    'Continuity Equation',
    'A₁v₁ = A₂v₂',
    'A = cross-sectional area, v = fluid velocity',
    'Conservation of mass for steady incompressible flow.',
    'A: m², v: m/s',
    'fluid,flow,continuity'
),

(
    'Fluids',
    'Bernoulli Equation',
    'p + ½ρv² + ρgh = constant',
    'p = pressure, ρ = density, v = velocity, g = gravity, h = height',
    'Energy conservation for ideal steady fluid flow.',
    'p: Pa, ρ: kg/m³, v: m/s, h: m',
    'bernoulli,fluid,pressure,flow'
),


-- =========================================================
-- THERMODYNAMICS
-- =========================================================

(
    'Thermodynamics',
    'Heat',
    'Q = mcΔT',
    'Q = heat, m = mass, c = specific heat capacity, ΔT = temperature change',
    'Heat required to change the temperature of a substance.',
    'Q: J, m: kg, c: J/(kg·K), ΔT: K',
    'heat,temperature,specific,capacity'
),

(
    'Thermodynamics',
    'First Law of Thermodynamics',
    'ΔU = Q - W',
    'ΔU = change in internal energy, Q = heat added, W = work done by system',
    'Energy conservation for a thermodynamic system.',
    'ΔU: J, Q: J, W: J',
    'thermodynamics,energy,first,law'
),

(
    'Thermodynamics',
    'Ideal Gas Law',
    'pV = nRT',
    'p = pressure, V = volume, n = amount of substance, R = gas constant, T = absolute temperature',
    'Equation of state for an ideal gas.',
    'p: Pa, V: m³, n: mol, T: K',
    'gas,ideal,temperature,pressure,volume'
),

(
    'Thermodynamics',
    'Thermal Efficiency',
    'η = W_out / Q_in',
    'η = efficiency, W_out = useful work output, Q_in = heat input',
    'Efficiency of a heat engine.',
    'η: dimensionless',
    'efficiency,heat,engine,thermodynamics'
),

(
    'Thermodynamics',
    'Celsius to Kelvin',
    'T(K) = T(°C) + 273.15',
    'T(K) = temperature in Kelvin, T(°C) = temperature in Celsius',
    'Conversion from Celsius to Kelvin.',
    'K, °C',
    'temperature,celsius,kelvin'
),


-- =========================================================
-- ELECTRICITY
-- =========================================================

(
    'Electricity',
    'Ohm''s Law',
    'V = IR',
    'V = voltage, I = current, R = resistance',
    'Relationship between voltage, current and resistance.',
    'V: V, I: A, R: Ω',
    'ohm,voltage,current,resistance'
),

(
    'Electricity',
    'Electric Power',
    'P = VI',
    'P = power, V = voltage, I = current',
    'Electrical power consumed or delivered.',
    'P: W, V: V, I: A',
    'electricity,power,voltage,current'
),

(
    'Electricity',
    'Electrical Energy',
    'E = Pt = VIt',
    'E = electrical energy, P = power, t = time',
    'Electrical energy consumed over time.',
    'E: J, P: W, t: s',
    'electricity,energy,power'
),

(
    'Electricity',
    'Coulomb''s Law',
    'F = k|q₁q₂| / r²',
    'F = electric force, k = Coulomb constant, q₁,q₂ = charges, r = distance',
    'Electrostatic force between two point charges.',
    'F: N, q: C, r: m',
    'coulomb,electric,charge,force'
),

(
    'Electricity',
    'Electric Field',
    'E = F / q',
    'E = electric field, F = electric force, q = charge',
    'Electric force per unit charge.',
    'E: N/C, F: N, q: C',
    'electric,field,charge,force'
),

(
    'Electricity',
    'Electric Field of Point Charge',
    'E = k|q| / r²',
    'E = electric field, k = Coulomb constant, q = charge, r = distance',
    'Electric field produced by a point charge.',
    'E: N/C, q: C, r: m',
    'electric,field,coulomb,charge'
),

(
    'Electricity',
    'Capacitance',
    'C = Q / V',
    'C = capacitance, Q = charge, V = voltage',
    'Charge stored per unit voltage.',
    'C: F, Q: C, V: V',
    'capacitor,capacitance,charge,voltage'
),

(
    'Electricity',
    'Energy Stored in Capacitor',
    'U = ½CV²',
    'U = stored energy, C = capacitance, V = voltage',
    'Energy stored in a charged capacitor.',
    'U: J, C: F, V: V',
    'capacitor,energy,voltage'
),

(
    'Electricity',
    'Resistors in Series',
    'R_total = R₁ + R₂ + ... + Rₙ',
    'R₁...Rₙ = individual resistances',
    'Equivalent resistance of series-connected resistors.',
    'R: Ω',
    'resistor,series,resistance,circuit'
),

(
    'Electricity',
    'Resistors in Parallel',
    '1/R_total = 1/R₁ + 1/R₂ + ... + 1/Rₙ',
    'R₁...Rₙ = individual resistances',
    'Equivalent resistance of parallel-connected resistors.',
    'R: Ω',
    'resistor,parallel,resistance,circuit'
),


-- =========================================================
-- WAVES & OPTICS
-- =========================================================

(
    'Waves',
    'Wave Speed',
    'v = fλ',
    'v = wave speed, f = frequency, λ = wavelength',
    'Relationship between wave speed, frequency and wavelength.',
    'v: m/s, f: Hz, λ: m',
    'wave,wavelength,frequency,speed'
),

(
    'Waves',
    'Frequency',
    'f = 1 / T',
    'f = frequency, T = period',
    'Frequency is the reciprocal of the period.',
    'f: Hz, T: s',
    'frequency,period,wave'
),

(
    'Waves',
    'Period',
    'T = 1 / f',
    'T = period, f = frequency',
    'Time required for one complete cycle.',
    'T: s, f: Hz',
    'period,frequency,wave'
),

(
    'Optics',
    'Snell''s Law',
    'n₁ sin(θ₁) = n₂ sin(θ₂)',
    'n₁,n₂ = refractive indices, θ₁,θ₂ = angles from normal',
    'Describes refraction at an interface.',
    'n: dimensionless, θ: degrees or radians',
    'snell,refraction,optics'
),

(
    'Optics',
    'Thin Lens Equation',
    '1/f = 1/dₒ + 1/dᵢ',
    'f = focal length, dₒ = object distance, dᵢ = image distance',
    'Relationship for an ideal thin lens.',
    'f,dₒ,dᵢ: m',
    'lens,optics,focal,image'
),

(
    'Optics',
    'Magnification',
    'M = hᵢ / hₒ = -dᵢ / dₒ',
    'M = magnification, hᵢ = image height, hₒ = object height',
    'Ratio of image size to object size.',
    'M: dimensionless',
    'magnification,lens,optics'
),


-- =========================================================
-- MODERN PHYSICS
-- =========================================================

(
    'Modern Physics',
    'Mass-Energy Equivalence',
    'E = mc²',
    'E = energy, m = mass, c = speed of light',
    'Equivalence between mass and energy.',
    'E: J, m: kg, c: m/s',
    'einstein,relativity,mass,energy'
),

(
    'Modern Physics',
    'Photon Energy',
    'E = hf',
    'E = photon energy, h = Planck constant, f = frequency',
    'Energy of a photon.',
    'E: J, h: J·s, f: Hz',
    'photon,quantum,energy,frequency'
),

(
    'Modern Physics',
    'Photon Wavelength',
    'E = hc / λ',
    'E = photon energy, h = Planck constant, c = speed of light, λ = wavelength',
    'Photon energy expressed using wavelength.',
    'E: J, λ: m',
    'photon,wavelength,quantum'
),

(
    'Modern Physics',
    'de Broglie Wavelength',
    'λ = h / p',
    'λ = wavelength, h = Planck constant, p = momentum',
    'Matter-wave wavelength associated with a particle.',
    'λ: m, h: J·s, p: kg·m/s',
    'debroglie,quantum,wavelength,momentum'
),

(
    'Modern Physics',
    'Heisenberg Uncertainty Principle',
    'Δx Δp ≥ ħ/2',
    'Δx = position uncertainty, Δp = momentum uncertainty, ħ = reduced Planck constant',
    'Fundamental quantum-mechanical uncertainty relation.',
    'Δx: m, Δp: kg·m/s',
    'heisenberg,quantum,uncertainty'
),


-- =========================================================
-- CONSTANTS
-- =========================================================

(
    'Constants',
    'Speed of Light',
    'c = 299792458 m/s',
    'c = speed of light in vacuum',
    'Exact speed of light in vacuum.',
    'm/s',
    'constant,light,speed'
),

(
    'Constants',
    'Gravitational Acceleration',
    'g ≈ 9.80665 m/s²',
    'g = standard gravitational acceleration',
    'Standard gravitational acceleration near Earth''s surface.',
    'm/s²',
    'constant,gravity,g'
),

(
    'Constants',
    'Gravitational Constant',
    'G ≈ 6.67430 × 10⁻¹¹ N·m²/kg²',
    'G = gravitational constant',
    'Universal gravitational constant.',
    'N·m²/kg²',
    'constant,gravity,newton'
),

(
    'Constants',
    'Planck Constant',
    'h ≈ 6.62607015 × 10⁻³⁴ J·s',
    'h = Planck constant',
    'Fundamental constant used in quantum physics.',
    'J·s',
    'constant,planck,quantum'
),

(
    'Constants',
    'Boltzmann Constant',
    'k_B ≈ 1.380649 × 10⁻²³ J/K',
    'k_B = Boltzmann constant',
    'Relates temperature to energy at the microscopic scale.',
    'J/K',
    'constant,boltzmann,thermodynamics'
),

(
    'Constants',
    'Coulomb Constant',
    'k ≈ 8.9875517923 × 10⁹ N·m²/C²',
    'k = Coulomb constant',
    'Constant used in Coulomb''s law.',
    'N·m²/C²',
    'constant,coulomb,electric'
),

(
    'Constants',
    'Gas Constant',
    'R ≈ 8.314462618 J/(mol·K)',
    'R = universal gas constant',
    'Constant used in the ideal gas law.',
    'J/(mol·K)',
    'constant,gas,thermodynamics'
);


-- =========================================================
-- OPTIONAL SEARCH FUNCTION FOR JAVA TELEGRAM BOT
-- =========================================================

--changeset bot:003-search-function

CREATE OR REPLACE FUNCTION search_physics_formulas(search_text TEXT)
RETURNS TABLE (
    id BIGINT,
    category VARCHAR,
    name VARCHAR,
    formula TEXT,
    variables TEXT,
    description TEXT,
    units TEXT,
    keywords TEXT
)
LANGUAGE SQL
AS $$
SELECT
    f.id,
    f.category,
    f.name,
    f.formula,
    f.variables,
    f.description,
    f.units,
    f.keywords
FROM physics_formulas f
WHERE
    f.name ILIKE '%' || search_text || '%'
        OR f.category ILIKE '%' || search_text || '%'
        OR f.keywords ILIKE '%' || search_text || '%'
        OR f.description ILIKE '%' || search_text || '%'
ORDER BY f.category, f.name;
$$;
