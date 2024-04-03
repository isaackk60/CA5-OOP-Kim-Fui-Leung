-- Create a MySQL database named 'library'
CREATE DATABASE IF NOT EXISTS library;

-- Switch to the newly created database
USE library;

-- Create a table named 'books'

CREATE TABLE IF NOT EXISTS books (
id INT AUTO_INCREMENT PRIMARY KEY,
title VARCHAR(255) NOT NULL,
genre VARCHAR(50) NOT NULL,
author VARCHAR(100) NOT NULL,
pages INT NOT NULL,
series BOOLEAN NOT NULL,
stock INT NOT NULL,
rating DECIMAL(3,1) NOT NULL,
description TEXT NOT NULL,
publisher VARCHAR(100) NOT NULL
);

INSERT INTO books (id, title, genre, author, pages, series, stock, rating, description, publisher) VALUES
(1, 'Whispers of the Lost Kingdom', 'Fantasy', 'Eleanor Ravenscroft', 432, true, 150, 4.2, 'In a realm where magic intertwines with politics...', 'Enchanted Press' ),
(2, 'Echoes of the Forgotten Realm', 'Fantasy', 'Alistair Blackthorn', 376, true, 120, 4.5, 'As ancient prophecies resurface, a lone hero must rise...', 'Mystic Books'),
(3, 'The Silent Observer', 'Mystery', 'Isabella Nightshade', 320, false, 200, 4.0, 'In the shadowy world of espionage, a detective races against time...', 'Shadow Publications'),
(4, 'Beyond the Horizon', 'Science Fiction', 'Xander Starlight', 480, true, 180, 4.8, 'Exploring the mysteries of distant galaxies, a space explorer discovers...', 'Galactic Press'),
(5, 'Threads of Destiny', 'Historical Fiction', 'Victoria Montgomery', 384, true, 100, 4.3, 'Set against the backdrop of the Renaissance, this tale weaves...', 'Timeless Books'),
(6, 'Rogues Gambit', 'Adventure', 'Dexter Stormblade', 352, true, 160, 4.1, 'In a world of pirates and hidden treasures, a daring rogue seeks...', 'Seafarer Publishing'),
(7, 'Eternal Embrace', 'Romance', 'Olivia Hartfield', 288, false, 250, 4.7, 'Amidst the beauty of a quaint town, two souls find love that transcends...', 'Heartfelt Romances'),
(8, 'City of Shadows', 'Thriller', 'Gabriel Nightshade', 416, true, 135, 4.4, 'A gritty detective faces a web of corruption and deceit in the heart of the city...', 'Dark Alley Books'),
(9, 'Chronicles of Elysium', 'Fantasy', 'Serena Moonstone', 544, true, 90, 4.6, 'Embark on an epic quest through a magical realm filled with mythical creatures...', 'Enchanted Press'),
(10, 'The Enigma Code', 'Mystery', 'Lucas Sterling', 400, false, 180, 4.0, 'A master cryptographer unravels a complex code that holds the key to...', 'Cipher Publications'),
(11, 'Starlight Odyssey', 'Science Fiction', 'Aria Nova', 432, true, 120, 4.9, 'Journey through the cosmos as a group of explorers seeks the origins of...', 'Stellar Books'),
(12, 'Heartstrings', 'Romance', 'Tristan Evergreen', 320, false, 200, 4.2, 'Love blossoms in unexpected places as two hearts find solace...', 'Everlasting Romance'),
(13, 'Shadows of the Past', 'Thriller', 'Mara Nightshade', 384, true, 150, 4.5, 'A former spys dark past catches up with her as she is thrust into a...', 'Nocturnal Press'),
(14, 'Legacy of the Ancients', 'Fantasy', 'Cassandra Moonshadow', 464, true, 110, 4.3, 'A tale of ancient prophecies and a chosen one destined to save the...', 'Mythos Publications'),
(15, 'Forgotten Realms', 'Historical Fiction', 'Oliver Kingsley', 368, false, 220, 4.1, 'Explore the untold stories of forgotten heroes and lost civilizations...', 'Time Traveler Books'),
(16, 'Rogues Redemption', 'Adventure', 'Lila Stormwind', 336, true, 130, 4.7, 'A swashbuckling adventure unfolds as a rogue seeks redemption and...', 'Seafarer Publishing'),
(17, 'Moonlit Serenade', 'Romance', 'Elijah Silvermoon', 304, false, 180, 4.5, 'Amidst the enchanting moonlight, two souls discover a love that echoes...', 'Starry Nights Publishing'),
(18, 'Code of Deception', 'Thriller', 'Natalia Shadowsong', 448, true, 140, 4.3, 'In a world of spies and double-crosses, a secret agent must navigate a...', 'Covert Operations Press'),
(19, 'The Quantum Paradox', 'Science Fiction', 'Dr. Alexander Quantum', 512, true, 100, 4.8, 'Delve into the mind-bending world of quantum physics as a scientist...', 'Quantum Books'),
(20, 'Hearts Entwined', 'Romance', 'Isabella Hartfield', 288, false, 190, 4.6, 'In a picturesque town, two hearts find solace in each other amidst...', 'Heartfelt Romances');