-- Create database if it doesn't exist
CREATE DATABASE IF NOT EXISTS project_genshin;

-- Use the database
USE project_genshin;

DROP TABLE IF EXISTS reactions;
DROP TABLE IF EXISTS resonances;
DROP TABLE IF EXISTS saved_teams;
DROP TABLE IF EXISTS recommended_teams;
DROP TABLE IF EXISTS characters;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    user_type ENUM('Normal', 'Admin') NOT NULL,
    owned_characters_ids VARCHAR(1024) default NULL
);

CREATE TABLE characters (
    character_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    element ENUM('Pyro', 'Electro', 'Hydro', 'Cryo', 'Dendro', 'Geo', 'Anemo') NOT NULL,
    weapon_type ENUM('Sword', 'Greatsword', 'Polearm', 'Catalyst', 'Bow') NOT NULL,
    rarity ENUM('5*', '4*') NOT NULL,
    role ENUM('MainDPS', 'SubDPS', 'Support') NOT NULL,
    photo VARCHAR(255) NOT NULL,
    artifact_set VARCHAR(100) NOT NULL,
    artifact_sands_main_stat VARCHAR(50) NOT NULL,
    artifact_goblet_main_stat VARCHAR(50) NOT NULL,
    artifact_circlet_main_stat VARCHAR(50) NOT NULL,
    recommended_weapon VARCHAR(100) NOT NULL
);

CREATE TABLE recommended_teams (
    maindpscharacter_id INT PRIMARY KEY,
    subcharacter1 INT,
    subcharacter2 INT,
    subcharacter3 INT,
    FOREIGN KEY (maindpscharacter_id) REFERENCES characters(character_id),
    FOREIGN KEY (subcharacter1) REFERENCES characters(character_id),
    FOREIGN KEY (subcharacter2) REFERENCES characters(character_id),
    FOREIGN KEY (subcharacter3) REFERENCES characters(character_id)
);

CREATE TABLE saved_teams (
    user_id INT,
    team_id INT AUTO_INCREMENT PRIMARY KEY,
    maindpscharacter_id INT,
    subcharacter1 INT,
    subcharacter2 INT,
    subcharacter3 INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (maindpscharacter_id) REFERENCES characters(character_id),
    FOREIGN KEY (subcharacter1) REFERENCES characters(character_id),
    FOREIGN KEY (subcharacter2) REFERENCES characters(character_id),
    FOREIGN KEY (subcharacter3) REFERENCES characters(character_id)
);

CREATE TABLE resonances (
    resonance_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE reactions (
    reaction_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255) NOT NULL
);

INSERT INTO characters (name, element, weapon_type, rarity, role, photo, artifact_set, artifact_sands_main_stat, artifact_goblet_main_stat, artifact_circlet_main_stat, recommended_weapon)
VALUES
    ('Albedo', 'Geo', 'Sword', '5*', 'SubDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/albedo/image.png?strip=all&quality=700&w=300', 'Husk of Opulent Dreams', 'DEF', 'Geo DMG Bonus', 'CRIT Rate/DMG', 'Uragu Misugiri'),
    ('Alhaitham', 'Dendro', 'Sword', '5*', 'MainDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/alhaitham/image.png?strip=all&quality=700&w=300','Gilded Dreams', 'EM', 'Dendro DMG Bonus', 'CRIT Rate/DMG', 'Light of Foliar Incision'),
    ('Amber', 'Pyro', 'Bow', '4*', 'Support', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/amber/image.png?strip=all&quality=700&w=300','Noblesse Oblige', 'ER', 'Pyro DMG Bonus', 'CRIT Rate/DMG', 'Elegy for the End'),
    ('Arataki Itto', 'Geo', 'Greatsword', '5*', 'MainDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/arataki_itto/image.png?strip=all&quality=700&w=300','Husk of Opulent Dreams', 'DEF', 'Geo DMG Bonus', 'CRIT Rate/DMG', 'Redhorn Stonethresher'),
    ('Arlecchino', 'Pyro', 'Polearm', '5*', 'MainDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/arlecchino/image.png?strip=all&quality=75&w=256','Fragment of Harmonic Whimsy', 'ATK', 'Pyro DMG Bonus', 'CRIT Rate/DMG', 'Crimson Moon\'s Semblance'),
    ('Baizhu', 'Dendro', 'Catalyst', '5*', 'Support', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/baizhu/image.png?strip=all&quality=700&w=300','Deepwood Memories', 'ER', 'HP', 'Healing Bonus', 'Jadefall\'s Splendor'),
    ('Barbara', 'Hydro', 'Catalyst', '4*', 'Support', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/barbara/image.png?strip=all&quality=700&w=300','Ocean-Hued Clam', 'HP', 'HP', 'Healing Bonus', 'Thrilling Tales of Dragon Slayers'),
    ('Beidou', 'Electro', 'GreatSword', '4*', 'SubDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/beidou/image.png?strip=all&quality=75&w=256','Emblem of Severed Fate', 'ER', 'Electro DMG Bonus', 'CRIT Rate/DMG', 'Wolf\'s Gravestone'),
    ('Bennet', 'Pyro', 'Sword', '4*', 'Support', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/bennett/image.png?strip=all&quality=75&w=256','Noblesse Oblige', 'ER', 'HP', 'HP', 'Aquila Favonia'),
    ('Candace', 'Hydro', 'Polearm', '4*', 'Support', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/candace/image.png?strip=all&quality=75&w=256','Emblem of Severed Fate', 'ER', 'HP', 'HP', 'Favonious Lance'),
    ('Charlotte', 'Cryo', 'Catalyst', '4*', 'Support', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/charlotte/image.png?strip=all&quality=75&w=256','Noblesse Oblige', 'ER', 'ATK', 'Healing Bonus', 'Prototype Amber'),
    ('Chevreuse', 'Pyro', 'Polearm', '4*', 'Support', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/chevreuse/image.png?strip=all&quality=75&w=256','Noblesse Oblige', 'HP', 'HP', 'HP', 'Favonious Lance'),
    ('Chiori', 'Geo', 'Sword', '5*', 'SubDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/chiori/image.png?strip=all&quality=700&w=300', 'Husk of Opulent Dreams', 'DEF', 'Geo DMG Bonus', 'CRIT Rate/DMG', 'Uragu Misugiri'),
    ('Chongyun', 'Cryo', 'GreatSword', '4*', 'SubDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/chongyun/image.png?strip=all&quality=700&w=300','Noblesse Oblige', 'ATK', 'Cryo DMG Bonus', 'CRIT Rate/DMG', 'Wolf\'s Gravestone'),
    ('Collei', 'Dendro', 'Bow', '4*', 'Support', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/collei/image.png?strip=all&quality=700&w=300','Deepwood Memories', 'ER', 'Dendro DMG Bonus', 'CRIT Rate/DMG', 'Elegy for the End'),
    ('Cyno', 'Electro', 'Polearm', '5*', 'MainDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/cyno/image.png?strip=all&quality=75&w=256','Thundering Fury', 'EM', 'Electro DMG Bonus', 'CRIT Rate/DMG', 'Staff of Scarlet Sands'),
    ('Dehya', 'Pyro', 'Greatsword', '5*', 'SubDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/dehya/image.png?strip=all&quality=75&w=256','Vourukasha\'s Dream', 'ATK', 'Pyro DMG Bonus', 'CRIT Rate/DMG', 'Beacon of the Reed Sea'),
    ('Diluc', 'Pyro', 'Greatsword', '5*', 'MainDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/diluc/image.png?strip=all&quality=75&w=256','Crimson Witch of Flames', 'ATK', 'Pyro DMG Bonus', 'CRIT Rate/DMG', 'Redhorn Stonethresher'),
    ('Diona', 'Cryo', 'Bow', '4*', 'Support', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/diona/image.png?strip=all&quality=75&w=256', 'Noblesse Oblige', 'ER', 'HP', 'HP', 'Sacrificial Bow'),
    ('Dori', 'Electro', 'Greatsword', '4*', 'Support', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/dori/image.png?strip=all&quality=75&w=256','Noblesse Oblige', 'ER', 'HP', 'Healing Bonus', 'Favonius Greatsword'),
    ('Eula', 'Cryo', 'Greatsword', '5*', 'MainDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/eula/image.png?strip=all&quality=75&w=256','Pale Flame', 'ATK', 'Physical DMG Bonus', 'CRIT Rate/DMG', 'Song of Broken Pines'),
    ('Faruzan', 'Anemo', 'Bow', '4*', 'SubDps', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/faruzan/image.png?strip=all&quality=75&w=256','Emblem of Severed Fate', 'ER', 'Anemo Dmg Bonus', 'CRIT Rate/DMG', 'Favonius Warbow'),
    ('Fischl', 'Electro', 'Bow', '4*', 'SubDps', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/fischl/image.png?strip=all&quality=75&w=256','Golden Troupe', 'ATK', 'Electro DMG Bonus', 'CRIT Rate/DMG', 'Polar Star'),
    ('Freminet', 'Cryo', 'Greatsword', '4*', 'MainDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/freminet/image.png?strip=all&quality=75&w=256','Pale Flame', 'ATK', 'Physical DMG Bonus', 'CRIT Rate/DMG', 'Skyward Pride'),
    ('Furina', 'Hydro', 'Sword', '5*', 'SubDps', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/furina/image.png?strip=all&quality=75&w=256','Golden Troupe', 'HP', 'HP/Hydro DMG Bonus', 'CRIT Rate/DMG', 'Splendor of Tranquil Waters'),
    ('Gaming', 'Pyro', 'Greatsword', '4*', 'MainDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/gaming/image.png?strip=all&quality=75&w=256','Crimson Witch of Flames', 'ATK', 'Pyro DMG Bonus', 'CRIT Rate/DMG', 'Sperpent Spine'),
    ('Ganyu', 'Cryo', 'Bow', '5*', 'MainDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/ganyu/image.png?strip=all&quality=75&w=256','Shimenawa\'s Reminiscence', 'ATK', 'Cryo DMG Bonus', 'CRIT Rate/DMG', 'Hunter\'s Path'),
    ('Gorou', 'Geo', 'Bow', '4*', 'Support', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/gorou/image.png?strip=all&quality=75&w=256','The Exile', 'ER', 'DEF', 'DEF', 'Favonius Warbow'),
    ('Hu Tao', 'Pyro', 'Polearm', '5*', 'MainDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/hu_tao/image.png?strip=all&quality=75&w=256','Crimson Witch of Flames', 'HP', 'Pyro DMG Bonus', 'CRIT Rate/DMG', 'Staff of Homa'),
    ('Jean', 'Anemo', 'Sword', '5*', 'Support', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/jean/image.png?strip=all&quality=75&w=256','Viridescent Venerer', 'ATK', 'ATK', 'CRIT Rate/DMG', 'Primordial Jade Cutter'),
    ('Kaedehara Kazuha', 'Anemo', 'Sword', '5*', 'Support', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/kaedehara_kazuha/image.png?strip=all&quality=75&w=256','Viridescent Venerer', 'EM', 'EM', 'EM', 'Xiphos\'s Moonlight'),
    ('Kaeya', 'Cryo', 'Sword', '4*', 'SubDps', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/kaeya/image.png?strip=all&quality=75&w=256','Blizzard Strayer', 'ATK', 'Cryo DMG Bonus', 'CRIT Rate/DMG', 'Mistsplitter Reforged'),
    ('Kamisato Ayaka', 'Cryo', 'Sword', '5*', 'MainDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/kamisato_ayaka/image.png?strip=all&quality=75&w=256','Blizzard Strayer', 'ATK', 'Cryo DMG Bonus', 'CRIT Rate/DMG', 'Mistsplitter Reforged'),
    ('Kamisato Ayato', 'Hydro', 'Sword', '5*', 'MainDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/kamisato_ayato/image.png?strip=all&quality=75&w=256','Heart of Depth', 'ATK', 'Hydro DMG Bonus', 'CRIT Rate/DMG', 'Haran Geppaku Futsu'),
    ('Kaveh', 'Dendro', 'Greatsword', '4*', 'SubDps', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/kaveh/image.png?strip=all&quality=75&w=256','Deepwood Memories', 'ER', 'EM', 'EM', 'Favonius Greatsword'),
    ('Keqing', 'Electro', 'Sword', '5*', 'MainDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/keqing/image.png?strip=all&quality=75&w=256','Thundering Fury', 'ATK', 'Electro DMG Bonus', 'CRIT Rate/DMG', 'Mistsplitter Reforged'),
    ('Kirara', 'Dendro', 'Sword', '4*', 'Support', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/kirara/image.png?strip=all&quality=700&w=300','Tenacity of the Millelith', 'HP', 'HP', 'HP', 'Key of Khaj-Nisut'),
    ('Klee', 'Pyro', 'Catalyst', '5*', 'MainDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/klee/image.png?strip=all&quality=700&w=300','Crimson With of Flames', 'ATK', 'Pyro DMG Bonus', 'CRIT Rate/DMG', 'Lost Prayer to the Sacred Winds'),
    ('Kujou Sara', 'Electro', 'Bow', '4*', 'Support', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/kujou_sara/image.png?strip=all&quality=700&w=300','Emblem of Severed Fate', 'ER', 'Electro DMG Bonus', 'CRIT Rate/DMG', 'Polar Star'),
    ('Kuki Shinobu', 'Electro', 'Sword', '4*', 'Support', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/kuki_shinobu/image.png?strip=all&quality=700&w=300','Flower of Paradise Lost', 'EM', 'EM', 'EM', 'Key of Khaj-Nisut'),
    ('Layla', 'Cryo', 'Sword', '4*', 'Support', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/layla/image.png?strip=all&quality=700&w=300','Tenacity of the Millelith', 'HP', 'HP', 'HP', 'Key of Khaj-Nisut'),
    ('Lisa', 'Electro', 'Catalyst', '4*', 'SubDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/lisa/image.png?strip=all&quality=700&w=300','Thundering Fury', 'EM', 'Electro DMG Bonus', 'CRIT Rate/DMG', 'Kagura\'s Verity'),
    ('Lynette', 'Anemo', 'Sword', '4*', 'SubDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/lynette/image.png?strip=all&quality=700&w=300','Thundering Fury', 'ER', 'Anemo DMG Bonus', 'CRIT Rate/DMG', 'Favonious Sword'),
    ('Lyney', 'Pyro', 'Bow', '5*', 'MainDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/lyney/image.png?strip=all&quality=700&w=300','Marechaussee Hunter', 'ATK', 'Pyro DMG Bonus', 'CRIT Rate/DMG', 'The First Great Magic'),
    ('Mika', 'Cryo', 'Polearm', '4*', 'Support', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/mika/image.png?strip=all&quality=700&w=300','Noblesse Oblige', 'ER', 'HP', 'CRIT Rate/DMG', 'Healing Bonus'),
    ('Mona', 'Hydro', 'Catalyst', '5*', 'Support', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/mona/image.png?strip=all&quality=700&w=300', 'Noblesse Oblige', 'ER', 'Hydro DMG Bonus', 'CRIT Rate/DMG', 'The Widsith'),
    ('Nahida', 'Dendro', 'Catalyst', '5*', 'SubDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/nahida/image.png?strip=all&quality=700&w=300', 'Deepwood Memories', 'EM', 'Dendro DMG Bonus', 'CRIT Rate/DMG', 'A Thousand Floating Dreams'),
    ('Navia', 'Geo', 'Greatsword', '5*', 'MainDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/navia/image.png?strip=all&quality=700&w=300', 'Nighttime Whispers in the Echoing Woods', 'ATK', 'Geo DMG Bonus', 'CRIT Rate/DMG', 'Verdict'),
    ('Neuvillette', 'Hydro', 'Catalyst', '5*', 'MainDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/neuvillette/image.png?strip=all&quality=700&w=300', 'Marechaussee Hunter', 'HP', 'Hydro DMG Bonus', 'CRIT Rate/DMG', 'Tome of the Eternal Flow'),
    ('Nilou', 'Hydro', 'Sword', '5*', 'MainDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/nilou/image.png?strip=all&quality=700&w=300', 'Tenacity of the Millelith', 'HP', 'HP', 'HP', 'Key of Khaj-Nisut'),
    ('Ningguang', 'Geo', 'Catalyst', '4*', 'SubDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/ningguang/image.png?strip=all&quality=700&w=300', 'Archaic Petra', 'ATK', 'Geo DMG Bonus', 'CRIT Rate/DMG', 'Lost Prayer to the Sacred Winds'),
    ('Noelle', 'Geo', 'Greatsword', '4*', 'SubDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/noelle/image.png?strip=all&quality=700&w=300', 'Husk of Opulent Dreams', 'DEF%', 'Geo DMG Bonus', 'CRIT Rate/DMG', 'Redhorn Stonethresher'),
    ('Qiqi', 'Cryo', 'Sword', '5*', 'Support', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/qiqi/image.png?strip=all&quality=700&w=300', 'Ocean-Hued Clam', 'ATK', 'ATK', 'Healing Bonus', 'Sacrificial Sword'),
    ('Raiden Shogun', 'Electro', 'Polearm', '5*', 'MainDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/raiden_shogun/image.png?strip=all&quality=700&w=300', 'Emblem of Severed Fate', 'ER', 'Electro DMG Bonus', 'CRIT Rate/DMG', 'Engulfing Lightning'),
    ('Razor', 'Electro', 'Greatsword', '4*', 'SubDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/razor/image.png?strip=all&quality=700&w=300', 'Gladiator\'s Finale', 'ATK', 'Physical DMG Bonus', 'CRIT Rate/DMG', 'Wolf\'s Gravestone'),
    ('Rosaria', 'Cryo', 'Polearm', '4*', 'SubDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/rosaria/image.png?strip=all&quality=700&w=300', 'Noblesse Oblige', 'ATK', 'Cryo DMG Bonus', 'CRIT Rate/DMG', 'Staff of Scarlet Sands'),
    ('Sangonomiya Kokomi', 'Hydro', 'Catalyst', '5*', 'Support', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/sangonomiya_kokomi/image.png?strip=all&quality=700&w=300', 'Ocean-Hued Clam', 'HP', 'HP', 'Healing Bonus', 'Everlasting Moonglow'),
    ('Sayu', 'Anemo', 'Greatsword', '4*', 'Support', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/sayu/image.png?strip=all&quality=700&w=300', 'Viridescent Venerer', 'ER', 'Anemo DMG Bonus', 'CRIT Rate/DMG', 'Wolf\'s Gravestone'),
    ('Shenhe', 'Cryo', 'Polearm', '5*', 'Support', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/shenhe/image.png?strip=all&quality=700&w=300', 'Noblesse Oblige', 'ATK', 'Cryo DMG Bonus', 'CRIT Rate/DMG', 'Calamity Queller'),
    ('Shikanoin Heizou', 'Anemo', 'Catalyst', '4*', 'MainDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/shikanoin_heizou/image.png?strip=all&quality=700&w=300', 'Viridescent Venerer', 'ATK%', 'Anemo DMG Bonus', 'CRIT Rate/DMG', 'Kagura\'s Verity'),
    ('Sucrose', 'Anemo', 'Catalyst', '4*', 'Support', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/sucrose/image.png?strip=all&quality=700&w=300', 'Viridescent Venerer', 'EM', 'Anemo DMG Bonus', 'CRIT Rate/DMG', 'Sacrificial Fragments'),
    ('Tartaglia', 'Hydro', 'Bow', '5*', 'MainDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/tartaglia/image.png?strip=all&quality=700&w=300', 'Heart of Depth', 'ATK', 'Hydro DMG Bonus', 'CRIT Rate/DMG', 'Polar Star'),
    ('Tighnari', 'Dendro', 'Bow', '5*', 'MainDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/tighnari/image.png?strip=all&quality=700&w=300', 'Wanderer\'s Troupe', 'EM', 'Dendro DMG Bonus', 'CRIT Rate/DMG', 'Hunter’s Path'),
    ('Thoma', 'Pyro', 'Polearm', '4*', 'SubDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/thoma/image.png?strip=all&quality=700&w=300', 'Emblem of Severed Fate', 'HP', 'Pyro DMG Bonus', 'CRIT Rate/DMG', 'Staff of Homa'),
    ('Venti', 'Anemo', 'Bow', '5*', 'Support', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/venti/image.png?strip=all&quality=700&w=300', 'Viridescent Venerer', 'ER', 'Anemo DMG Bonus', 'CRIT Rate/DMG', 'Elegy for the End'),
    ('Wanderer', 'Anemo', 'Catalyst', '5*', 'MainDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/wanderer/image.png?strip=all&quality=700&w=300', 'Desert Pavilion Chronicle', 'ATK', 'Anemo DMG Bonus', 'CRIT Rate/DMG', 'Tulaytullah\'s Remembrance'),
    ('Wriothesley', 'Cryo', 'Catalyst', '5*', 'MainDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/wriothesley/image.png?strip=all&quality=700&w=300', 'Marechaussee Hunter', 'ATK', 'Cryo DMG Bonus', 'CRIT Rate/DMG', 'Cashflow Supervision'),
    ('Xiangling', 'Pyro', 'Polearm', '4*', 'SubDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/xiangling/image.png?strip=all&quality=700&w=300', 'Emblem of Severed Fate', 'ATK', 'Pyro DMG Bonus', 'CRIT Rate/DMG', 'The Catch'),
    ('Xianyun', 'Anemo', 'Greatsword', '5*', 'Support', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/xianyun/image.png?strip=all&quality=700&w=300', 'Viridescent Venerer', 'ATK', 'ATK', 'ATK', 'Crane\'s Echoing Call'),
    ('Xiao', 'Anemo', 'Polearm', '5*', 'MainDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/xiao/image.png?strip=all&quality=700&w=300', 'Vermilion Hereafter', 'ATK', 'Anemo DMG Bonus', 'CRIT Rate/DMG', 'Primordial Jade Winged-Spear'),
    ('Xingqiu', 'Hydro', 'Sword', '4*', 'SubDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/xingqiu/image.png?strip=all&quality=700&w=300', 'Emblem of Severed Fate', 'ATK', 'Hydro DMG Bonus', 'CRIT Rate/DMG', 'Sacrificial Sword'),
    ('Xinyan', 'Pyro', 'Greatsword', '4*', 'SubDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/xinyan/image.png?strip=all&quality=700&w=300', 'Pale Flame', 'ATK', 'Physical DMG Bonus', 'CRIT Rate/DMG', 'Redhorn Stonethresher'),
    ('Yae Miko', 'Electro', 'Catalyst', '5*', 'SubDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/yae_miko/image.png?strip=all&quality=700&w=300', 'Golden Troupe', 'ATK', 'Electro DMG Bonus', 'CRIT Rate/DMG', 'Kagura\'s Verity'),
    ('Yanfei', 'Pyro', 'Catalyst', '4*', 'MainDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/yanfei/image.png?strip=all&quality=700&w=300', 'Crimson Witch of Flames', 'ATK', 'Pyro DMG Bonus', 'CRIT Rate/DMG', 'Lost Prayer to the Sacred Winds'),
    ('Yaoyao', 'Dendro', 'Polearm', '4*', 'Support', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/yaoyao/image.png?strip=all&quality=700&w=300', 'Deepwood Memories', 'HP', 'HP', 'Healing Bonus', 'Favonius Lance'),
    ('Yelan', 'Hydro', 'Bow', '5*', 'SubDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/yelan/image.png?strip=all&quality=700&w=300', 'Emblem of Severed Fate', 'HP', 'Hydro DMG Bonus', 'CRIT Rate/DMG', 'Aqua Simulacra'),
    ('Yoimiya', 'Pyro', 'Bow', '5*', 'MainDPS', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/yoimiya/image.png?strip=all&quality=700&w=300', 'Shimenawa\'s Reminiscence', 'ATK', 'Pyro DMG Bonus', 'CRIT Rate/DMG', 'Thundering Pulse'),
    ('Yun Jin', 'Geo', 'Polearm', '4*', 'Support', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/yun_jin/image.png?strip=all&quality=700&w=300', 'Husk of Opulent Dreams', 'DEF', 'DEF', 'DEF', 'Favonius Lance'),
    ('Zhongli', 'Geo', 'Polearm', '5*', 'Support', 'https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/zhongli/image.png?strip=all&quality=700&w=300', 'Tenacity of the Millelith', 'HP', 'Geo DMG Bonus', 'CRIT Rate/DMG', 'Staff of Homa');

INSERT INTO reactions(name, description)
VALUES
    ('Superconduct', 'Superconduct is a Cryo and Electro elemental reaction that deals AoE Cryo DMG and reduces the target\'s Physical RES by 40% for 12s.'),
    ('Overloaded', 'Overloaded is a Pyro and Electro elemental reaction that deals AoE Pyro DMG and causes an explosion.'),
    ('Melt', 'Melt is a Pyro and Cryo elemental reaction that deals increased DMG. If the Pyro element triggers the reaction, the target takes 2x the DMG. If the Cryo element triggers the reaction, the target takes 1.5x the DMG.'),
    ('Vaporize', 'Vaporize is a Pyro and Hydro elemental reaction that deals increased DMG. If the Pyro element triggers the reaction, the target takes 1.5x the DMG. If the Hydro element triggers the reaction, the target takes 2x the DMG.'),
    ('Swirl', 'Swirl is an elemental reaction triggered by inflicting Anemo on a target that is already affected by Pyro/Electro/Hydro/Cryo.This reaction deals Elemental DMG of the non-Anemo element involved and applies said element to targets within a 6 m radius.'),
    ('Electro-Charged', 'Electro-Charged is an Electro and Hydro elemental reaction that deals Electro DMG over time.'),
    ('Burning', 'Burning is a Pyro and Dendro elemental reaction that deals Pyro DMG over time.'),
    ('Crystallize', 'Crystallize is a Geo elemental reaction that creates a shield based on the element absorbed.'),
    ('Shattered', 'Shattered is a Cryo elemental reaction that deals Physical DMG to frozen enemies.'),
    ('Frozen', 'Frozen is a Cryo and Hydro elemental reaction that immobilizes enemies.'),
    ('Bloom','Bloom is a Dendro and Hydro elemental reaction that creates a Dendro Core which aids in other reactions.'),
    ('Burgeon','Burgeon is a reaction of Pyro applied to a Dendro Core created by the Bloom reaction. The core explodes and deals AoE Dendro damage'),
    ('Hyperbloom','Hyperbloom is a reaction of Electro applied to a Dendro Core created by the Bloom reaction. The core turns into a missile which flies to the nearby enemies and deals Dendro damage'),
    ('Quicken', 'Quicken is a reaction of Electro and Dendro. Enables follow-up catalyze reactions.'),
    ('Aggravate','Deals increased Electro damage to a Quickened enemy.'),
    ('Spread','Deals increased Dendro damage to a Quickened enemy.');


INSERT INTO resonances(name, description)
VALUES
    ('Pyro','Affected by Cryo for 40% less time. Increases ATK by 25%.'),
    ('Hydro','Affected by Pyro for 40% less time. Increases Max HP by 25%.'),
    ('Electro','Affected by Hydro for 40% less time. Superconduct, Overloaded, Electro-Charged, Quicken, Aggravate, or Hyperbloom have a 100% chance to generate an Electro Elemental Particle (CD: 5s).'),
    ('Cryo','Affected by Electro for 40% less time. Increases CRIT Rate against enemies that are Frozen or affected by Cryo by 15%'),
    ('Anemo','Decreases Stamina Consumption by 15%. Increases Movement SPD by 10%. Shortens Skill CD by 5%.'),
    ('Geo','Increases shield strength by 15%. Additionally, characters protected by a shield will have the following special characteristics: DMG dealt increased by 15%, dealing DMG to enemies will decrease their Geo RES by 20% for 15s.'),
    ('Dendro','Elemental Mastery increased by 50. After triggering Burning, Quicken, or Bloom, gain 30 Elemental Mastery for 6s. After triggering Aggravate, Spread, Hyperbloom, or Burgeon, gain 20 Elemental Mastery for 6s.'),
    ('Universal','Provides 15% additional elemental resistance.');

INSERT INTO recommended_teams(maindpscharacter_id, subcharacter1, subcharacter2, subcharacter3)
VALUES
    -- alhaitham nahida kuki_shinobu yelan
    (2,47,40,76),
    -- itto gorou chiori zhongli
    (4,28,13,79),
    -- arle bennet yelan kazuha
    (5,9,76,31),
    -- cyno nahida yelan baizhu
    (16,47,76,6),
    -- diluc furina xianyun yelan
    (18,25,69,76),
    -- eula raiden_shogun mika zhongli
    (21,54,45,79),
    -- freminet xingqiu fischl mika
    (24,71,23,45),
    -- gaming xianyun furina bennet
    (26,69,25,9),
    -- ganyu xiangling bennet zhongli
    (27,68,9,79),
    -- hutao xingqiu yelan zhongli
    (29,71,76,79),
    -- ayaka shenhe kokomi kazuha
    (33,59,57,31),
    -- ayato xiangling bennet kazuha
    (34,68,9,31),
    -- keqing baizhu fischl kazuha
    (36,6,23,31),
    -- klee xingqiu kazuha zhongli
    (38,71,31,79),
    -- lyney bennet kazuha dehya
    (44,9,31,17),
    -- navia furina bennet zhongli
    (48,25,9,79),
    -- neuvillette furina baizhu kazuha
    (49,25,6,31),
    -- nilou nahida furina baizhu
    (50,47,25,6),
    -- raiden_shogun xiangling bennet xingqiu
    (54,68,9,71),
    -- heizou yelan yae_miko kuki_shinobu
    (60,76,73,40),
    -- tartaglia xiangling bennet kazuha
    (62,68,9,31),
    -- tighnari nahida yae_miko zhongli
    (63,47,73,79),
    -- wanderer faruzan bennet zhongli
    (66,22,9,79),
    -- wriothesley furina shenhe bennet
    (67,25,59,9),
    -- xiao xianyun furina faruzan
    (70,69,25,22),
    -- yanfei xingqiu bennet kazuha
    (74,71,9,31),
    -- yoimiya bennet yun_jin xingqiu
    (77,9,78,71);

INSERT INTO users(username, password, user_type, owned_characters_ids)
VALUES
    ('admin', 'admin', 'Admin', '1, 2, 3, 4'),
    ('user', 'user', 'Normal', '1, 2, 3, 4');
