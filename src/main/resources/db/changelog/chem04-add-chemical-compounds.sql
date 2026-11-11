
--liquibase formatted sql

--changeset science-kit:chem04-add-compounds

INSERT INTO chemical_compounds
(name, formula, molar_mass, compound_type, description, appearance)
VALUES

-- =========================================
-- ORGANIC COMPOUNDS: HYDROCARBONS
-- =========================================

('Ethane', 'C2H6', 30.070, 'Alkane',
 'A saturated hydrocarbon found in natural gas.',
 'Colorless gas'),

('Ethene', 'C2H4', 28.054, 'Alkene',
 'An unsaturated hydrocarbon used in plastic production.',
 'Colorless gas'),

('Ethyne', 'C2H2', 26.038, 'Alkyne',
 'Also known as acetylene, used in welding.',
 'Colorless gas'),

('Pentane', 'C5H12', 72.151, 'Alkane',
 'A five-carbon saturated hydrocarbon.',
 'Colorless liquid'),

('Hexane', 'C6H14', 86.178, 'Alkane',
 'A hydrocarbon commonly used as an industrial solvent.',
 'Colorless liquid'),

('Heptane', 'C7H16', 100.205, 'Alkane',
 'A saturated hydrocarbon used as a solvent and fuel component.',
 'Colorless liquid'),

('Octane', 'C8H18', 114.232, 'Alkane',
 'A hydrocarbon used as a component of gasoline.',
 'Colorless liquid'),

('Nonane', 'C9H20', 128.259, 'Alkane',
 'A nine-carbon saturated hydrocarbon.',
 'Colorless liquid'),

('Decane', 'C10H22', 142.286, 'Alkane',
 'A hydrocarbon found in petroleum.',
 'Colorless liquid'),

('Cyclopentane', 'C5H10', 70.135, 'Cycloalkane',
 'A cyclic saturated hydrocarbon used in industrial applications.',
 'Colorless liquid'),

('Cyclohexane', 'C6H12', 84.162, 'Cycloalkane',
 'A cyclic hydrocarbon used in chemical manufacturing.',
 'Colorless liquid'),

('Propene', 'C3H6', 42.081, 'Alkene',
 'A three-carbon alkene used in polypropylene production.',
 'Colorless gas'),

('1-Butene', 'C4H8', 56.108, 'Alkene',
 'An unsaturated hydrocarbon used in polymer production.',
 'Colorless gas'),

('1-Pentene', 'C5H10', 70.135, 'Alkene',
 'An alkene used in organic synthesis.',
 'Colorless liquid'),

('1-Hexyne', 'C6H10', 82.146, 'Alkyne',
 'A terminal alkyne used in organic synthesis.',
 'Colorless liquid'),

('1-Heptyne', 'C7H12', 96.173, 'Alkyne',
 'A seven-carbon terminal alkyne.',
 'Colorless liquid'),

('1-Heptene', 'C7H14', 98.189, 'Alkene',
 'An alkene used in chemical synthesis.',
 'Colorless liquid'),

('1-Octene', 'C8H16', 112.216, 'Alkene',
 'An important intermediate in polymer manufacturing.',
 'Colorless liquid'),

('1-Nonene', 'C9H18', 126.243, 'Alkene',
 'An unsaturated hydrocarbon used in chemical synthesis.',
 'Colorless liquid'),

('1-Decyne', 'C10H18', 138.254, 'Alkyne',
 'A terminal alkyne used in organic chemistry.',
 'Colorless liquid'),

-- =========================================
-- ORGANIC COMPOUNDS: AROMATICS
-- =========================================

('Toluene', 'C7H8', 92.141, 'Aromatic hydrocarbon',
 'An aromatic solvent used in industrial chemical production.',
 'Colorless liquid'),

('Ethylbenzene', 'C8H10', 106.168, 'Aromatic hydrocarbon',
 'An aromatic hydrocarbon used in styrene production.',
 'Colorless liquid'),

('Styrene', 'C8H8', 104.152, 'Aromatic hydrocarbon',
 'An aromatic compound used to manufacture polystyrene.',
 'Colorless to pale yellow liquid'),

('Naphthalene', 'C10H8', 128.174, 'Aromatic hydrocarbon',
 'A polycyclic aromatic hydrocarbon.',
 'White crystalline solid'),

('Anthracene', 'C14H10', 178.234, 'Aromatic hydrocarbon',
 'A polycyclic aromatic hydrocarbon used in research and chemical synthesis.',
 'Colorless crystalline solid'),

('Phenol', 'C6H6O', 94.113, 'Phenol',
 'An aromatic compound containing a hydroxyl group.',
 'White crystalline solid'),

('Aniline', 'C6H7N', 93.129, 'Aromatic amine',
 'An aromatic amine used in dye and chemical manufacturing.',
 'Colorless to pale yellow liquid'),

('Nitrobenzene', 'C6H5NO2', 123.111, 'Aromatic nitro compound',
 'An aromatic compound used as an industrial chemical intermediate.',
 'Pale yellow oily liquid'),

('Chlorobenzene', 'C6H5Cl', 112.557, 'Halogenated aromatic',
 'An aromatic compound used in chemical synthesis.',
 'Colorless liquid'),

('Bromobenzene', 'C6H5Br', 157.010, 'Halogenated aromatic',
 'A halogenated aromatic compound used in organic synthesis.',
 'Colorless liquid'),

-- =========================================
-- ORGANIC COMPOUNDS: ACIDS AND DERIVATIVES
-- =========================================

('Benzoic acid', 'C7H6O2', 122.123, 'Carboxylic acid',
 'An aromatic carboxylic acid used as a preservative.',
 'White crystalline solid'),

('Salicylic acid', 'C7H6O3', 138.122, 'Carboxylic acid',
 'An aromatic hydroxy acid used in chemical and pharmaceutical manufacturing.',
 'White crystalline solid'),

('Lactic acid', 'C3H6O3', 90.078, 'Hydroxy acid',
 'An organic acid produced during fermentation and metabolism.',
 'Colorless to pale yellow liquid'),

('Oxalic acid', 'C2H2O4', 90.034, 'Dicarboxylic acid',
 'A simple organic dicarboxylic acid.',
 'Colorless crystalline solid'),

('Tartaric acid', 'C4H6O6', 150.087, 'Dicarboxylic acid',
 'An organic acid naturally found in many fruits.',
 'White crystalline solid'),

('Urea', 'CH4N2O', 60.056, 'Amide',
 'An organic compound used extensively in fertilizer production.',
 'White crystalline solid'),

('Acetamide', 'C2H5NO', 59.068, 'Amide',
 'A simple organic amide used in chemical research.',
 'Colorless crystalline solid'),

('Ethyl acetate', 'C4H8O2', 88.106, 'Ester',
 'An ester widely used as an industrial solvent.',
 'Colorless liquid'),

('Dimethyl sulfoxide', 'C2H6OS', 78.134, 'Sulfoxide',
 'A polar organic solvent used in laboratories.',
 'Colorless liquid'),

('Ethylene glycol', 'C2H6O2', 62.068, 'Alcohol',
 'A diol used in antifreeze formulations.',
 'Colorless viscous liquid'),

-- =========================================
-- INORGANIC COMPOUNDS: HALIDES
-- =========================================

('Lithium chloride', 'LiCl', 42.394, 'Ionic salt',
 'An ionic compound used in industrial and laboratory applications.',
 'White crystalline solid'),

('Potassium chloride', 'KCl', 74.551, 'Ionic salt',
 'An ionic salt used in fertilizers and laboratory chemistry.',
 'White crystalline solid'),

('Potassium bromide', 'KBr', 119.002, 'Ionic salt',
 'An ionic compound used in infrared spectroscopy.',
 'White crystalline solid'),

('Sodium iodide', 'NaI', 149.894, 'Ionic salt',
 'An ionic compound used in radiation detection and laboratory chemistry.',
 'White crystalline solid'),

('Sodium fluoride', 'NaF', 41.988, 'Ionic salt',
 'An inorganic fluoride used in dental products.',
 'White crystalline solid'),

('Calcium fluoride', 'CaF2', 78.075, 'Ionic salt',
 'A naturally occurring mineral known as fluorite.',
 'Colorless or white crystalline solid'),

('Magnesium chloride', 'MgCl2', 95.211, 'Ionic salt',
 'An inorganic salt used in industrial applications.',
 'White crystalline solid'),

('Aluminum chloride', 'AlCl3', 133.341, 'Ionic compound',
 'A Lewis acid used as a catalyst in chemical synthesis.',
 'White to pale yellow solid'),

('Iron(II) chloride', 'FeCl2', 126.751, 'Ionic salt',
 'An iron salt used in chemical and industrial processes.',
 'Pale green crystalline solid'),

('Iron(III) chloride', 'FeCl3', 162.204, 'Ionic salt',
 'An iron compound used in water treatment and chemical synthesis.',
 'Brown crystalline solid'),

-- =========================================
-- INORGANIC COMPOUNDS: SULFATES AND CARBONATES
-- =========================================

('Iron(II) sulfate', 'FeSO4', 151.901, 'Ionic salt',
 'An iron salt commonly encountered as a hydrated crystalline compound.',
 'Pale green solid'),

('Iron(III) sulfate', 'Fe2(SO4)3', 399.873, 'Ionic salt',
 'An iron sulfate used in water treatment and industrial chemistry.',
 'Yellowish solid'),

('Sodium sulfate', 'Na2SO4', 142.042, 'Ionic salt',
 'An inorganic sulfate used in detergents and glass production.',
 'White crystalline solid'),

('Potassium sulfate', 'K2SO4', 174.259, 'Ionic salt',
 'A potassium salt used as a fertilizer.',
 'White crystalline solid'),

('Sodium carbonate', 'Na2CO3', 105.988, 'Carbonate salt',
 'Also known as soda ash, used in glass and detergent manufacturing.',
 'White powder'),

('Potassium carbonate', 'K2CO3', 138.205, 'Carbonate salt',
 'An alkaline salt used in glass and soap production.',
 'White hygroscopic solid'),

('Ammonium sulfate', '(NH4)2SO4', 132.140, 'Ammonium salt',
 'An inorganic salt commonly used as a nitrogen fertilizer.',
 'White crystalline solid'),

('Ammonium nitrate', 'NH4NO3', 80.043, 'Ammonium salt',
 'An inorganic salt used in fertilizer production.',
 'White crystalline solid'),

('Sodium phosphate', 'Na3PO4', 163.940, 'Phosphate salt',
 'An inorganic phosphate used in cleaning products and chemical processing.',
 'White crystalline solid'),

('Calcium phosphate', 'Ca3(PO4)2', 310.174, 'Phosphate salt',
 'An important mineral component of bones and teeth.',
 'White solid'),

-- =========================================
-- INORGANIC COMPOUNDS: OXIDES
-- =========================================

('Iron(III) oxide', 'Fe2O3', 159.687, 'Metal oxide',
 'An iron oxide commonly known as hematite.',
 'Reddish-brown solid'),

('Iron(II,III) oxide', 'Fe3O4', 231.531, 'Metal oxide',
 'A mixed-valence iron oxide known as magnetite.',
 'Black solid'),

('Aluminum oxide', 'Al2O3', 101.961, 'Metal oxide',
 'A hard oxide used in ceramics and abrasives.',
 'White crystalline solid'),

('Magnesium oxide', 'MgO', 40.304, 'Metal oxide',
 'A refractory oxide used in industrial materials.',
 'White powder'),

('Calcium oxide', 'CaO', 56.077, 'Metal oxide',
 'Also known as quicklime, used in cement and construction.',
 'White solid'),

('Zinc oxide', 'ZnO', 81.379, 'Metal oxide',
 'An oxide used in ceramics, rubber and sunscreen formulations.',
 'White powder'),

('Copper(II) oxide', 'CuO', 79.545, 'Metal oxide',
 'A black copper oxide used in ceramics and chemical synthesis.',
 'Black solid'),

('Copper(I) oxide', 'Cu2O', 143.091, 'Metal oxide',
 'A copper oxide used in pigments and semiconductor research.',
 'Red solid'),

('Lead(II) oxide', 'PbO', 223.199, 'Metal oxide',
 'An inorganic oxide used in specialized industrial applications.',
 'Yellow or reddish solid'),

('Titanium dioxide', 'TiO2', 79.865, 'Metal oxide',
 'A white pigment used in paints, coatings and materials.',
 'White powder'),

-- =========================================
-- INORGANIC COMPOUNDS: OTHER COMPOUNDS
-- =========================================

('Hydrogen peroxide', 'H2O2', 34.014, 'Peroxide',
 'A compound with oxidizing properties used in bleaching and disinfection.',
 'Colorless liquid'),

('Ozone', 'O3', 47.997, 'Oxide',
 'A reactive form of oxygen found in the atmosphere.',
 'Pale blue gas'),

('Boric acid', 'H3BO3', 61.833, 'Inorganic acid',
 'A weak inorganic acid used in glass manufacturing and laboratory chemistry.',
 'White crystalline solid'),

('Hydrofluoric acid', 'HF', 20.006, 'Inorganic acid',
 'An aqueous solution of hydrogen fluoride used in specialized industrial processes.',
 'Colorless liquid'),

('Hydrobromic acid', 'HBr', 80.912, 'Inorganic acid',
 'A strong binary acid of hydrogen and bromine.',
 'Colorless to pale yellow solution'),

('Sodium hypochlorite', 'NaClO', 74.442, 'Oxidizing salt',
 'An inorganic compound commonly used in aqueous bleach solutions.',
 'Pale yellow solution'),

('Sodium chlorate', 'NaClO3', 106.441, 'Oxidizing salt',
 'An inorganic salt used in industrial chemical processes.',
 'White crystalline solid'),

('Potassium permanganate', 'KMnO4', 158.034, 'Oxidizing salt',
 'A strong oxidizing agent used in laboratory chemistry.',
 'Dark purple crystalline solid'),

('Potassium dichromate', 'K2Cr2O7', 294.185, 'Oxidizing salt',
 'An inorganic chromium compound used in specialized laboratory applications.',
 'Orange crystalline solid'),

('Manganese dioxide', 'MnO2', 86.936, 'Metal oxide',
 'A manganese oxide used in batteries and chemical processes.',
 'Black or brown-black solid')

ON CONFLICT (formula) DO NOTHING;

--rollback DELETE FROM chemical_compounds
--rollback WHERE formula IN (
--rollback 'C2H6', 'C2H4', 'C2H2', 'C5H12', 'C6H14',
--rollback 'C7H16', 'C8H18', 'C9H20', 'C10H22', 'C5H10',
--rollback 'C6H12', 'C3H6', 'C4H8', 'C6H10', 'C7H12',
--rollback 'C7H14', 'C8H16', 'C9H18', 'C10H18', 'C7H8',
--rollback 'C8H10', 'C8H8', 'C10H8', 'C14H10', 'C6H6O',
--rollback 'C6H7N', 'C6H5NO2', 'C6H5Cl', 'C6H5Br',
--rollback 'C7H6O2', 'C7H6O3', 'C3H6O3', 'C2H2O4',
--rollback 'C4H6O6', 'CH4N2O', 'C2H5NO', 'C4H8O2',
--rollback 'C2H6OS', 'C2H6O2', 'LiCl', 'KCl', 'KBr',
--rollback 'NaI', 'NaF', 'CaF2', 'MgCl2', 'AlCl3',
--rollback 'FeCl2', 'FeCl3', 'FeSO4', 'Fe2(SO4)3',
--rollback 'Na2SO4', 'K2SO4', 'Na2CO3', 'K2CO3',
--rollback '(NH4)2SO4', 'NH4NO3', 'Na3PO4', 'Ca3(PO4)2',
--rollback 'Fe2O3', 'Fe3O4', 'Al2O3', 'MgO', 'CaO',
--rollback 'ZnO', 'CuO', 'Cu2O', 'PbO', 'TiO2',
--rollback 'H2O2', 'O3', 'H3BO3', 'HF', 'HBr',
--rollback 'NaClO', 'NaClO3', 'KMnO4', 'K2Cr2O7', 'MnO2'
--rollback );
