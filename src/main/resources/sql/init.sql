CREATE TABLE IF NOT EXISTS stats (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nickname VARCHAR(250) NOT NULL,
    level VARCHAR(250) NOT NULL,
    score DOUBLE NOT NULL,
    game_date TIMESTAMP NOT NULL
);