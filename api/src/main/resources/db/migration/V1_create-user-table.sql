
CREATE TABLE "users" (
                        id UUID PRIMARY KEY,
                        user_name VARCHAR(255) NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        email VARCHAR(255) NOT NULL UNIQUE,
                        birth_date DATE NOT NULL,
                        gender VARCHAR(10) NOT NULL,
                        country VARCHAR(100) NOT NULL,
                        city VARCHAR(100) NOT NULL,
                        address VARCHAR(255) NOT NULL,
                        phone VARCHAR(15) NOT NULL,
                        email_recover VARCHAR(255) NOT NULL
);


CREATE TABLE accounts (
                         id UUID PRIMARY KEY,
                         account_description VARCHAR(255),
                         credit_card_number VARCHAR(20),
                         card_expiration_date DATE,
                         card_cvv VARCHAR(4),
                         account_status VARCHAR(50),
                         balance FLOAT NOT NULL,
                         user_id UUID UNIQUE,
                         FOREIGN KEY (user_id) REFERENCES "users"(id) ON DELETE SET NULL
);


CREATE TABLE friends (
                         id UUID PRIMARY KEY,
                         friend_name VARCHAR(255) NOT NULL
);


CREATE TABLE games (
                      id UUID PRIMARY KEY,
                      game_title VARCHAR(255) NOT NULL,
                      game_description TEXT NOT NULL,
                      game_price DECIMAL(10, 2) NOT NULL,
                      minimum_age INT NOT NULL,
                      account_id UUID,
                      FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE SET NULL
);


CREATE TABLE comments (
                         id UUID PRIMARY KEY,
                         content TEXT NOT NULL,
                         dislikes INT DEFAULT 0,
                         likes INT DEFAULT 0,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         account_id UUID,
                         game_id UUID,
                         replies_id UUID,
                         FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE SET NULL,
                         FOREIGN KEY (game_id) REFERENCES games(id) ON DELETE SET NULL,
                         FOREIGN KEY (replies_id) REFERENCES comments(id) ON DELETE SET NULL
);


CREATE TABLE transactions (
                             id UUID PRIMARY KEY,
                             payment_type VARCHAR(50) NOT NULL,
                             amount DECIMAL(10, 2) NOT NULL,
                             timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             is_valid BOOLEAN DEFAULT FALSE,
                             account_id UUID,
                             FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE SET NULL
);


CREATE TABLE purchases (
                          id UUID PRIMARY KEY,
                          purchase_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          account_id UUID,
                          game_id UUID,
                          transaction_id UUID,
                          FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE SET NULL,
                          FOREIGN KEY (game_id) REFERENCES games(id) ON DELETE SET NULL,
                          FOREIGN KEY (transaction_id) REFERENCES transactions(id) ON DELETE SET NULL
);


CREATE TABLE account_friends (
                                 account_id UUID,
                                 friend_id UUID,
                                 PRIMARY KEY (account_id, friend_id),
                                 FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE,
                                 FOREIGN KEY (friend_id) REFERENCES friends(id) ON DELETE CASCADE
);
