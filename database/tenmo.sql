BEGIN TRANSACTION;

DROP TABLE IF EXISTS tenmo_user, account;

DROP SEQUENCE IF EXISTS seq_user_id, seq_account_id;

-- Sequence to start user_id values at 1001 instead of 1
CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  START WITH 1001
  NO MAXVALUE;

CREATE TABLE tenmo_user (
	user_id int NOT NULL DEFAULT nextval('seq_user_id'),
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	CONSTRAINT PK_tenmo_user PRIMARY KEY (user_id),
	CONSTRAINT UQ_username UNIQUE (username)
);

-- Sequence to start account_id values at 2001 instead of 1
-- Note: Use similar sequences with unique starting values for additional tables
CREATE SEQUENCE seq_account_id
  INCREMENT BY 1
  START WITH 2001
  NO MAXVALUE;

CREATE TABLE account (
	account_id int NOT NULL DEFAULT nextval('seq_account_id'),
	user_id int NOT NULL,
	balance decimal(13, 2) NOT NULL,
	CONSTRAINT PK_account PRIMARY KEY (account_id),
	CONSTRAINT FK_account_tenmo_user FOREIGN KEY (user_id) REFERENCES tenmo_user (user_id)
);

CREATE TABLE transaction ( 
	transaction_id serial NOT NULL,
	send_account_id int NOT NULL,
	receive_account_id int NOT NULL, 
	transfer_type VARCHAR(10) NOT NULL,
	transfer_amount numeric(7,2) NOT NULL, 
	status VARCHAR(10) NOT NULL,
	CONSTRAINT PK_transaction_id PRIMARY KEY (transaction_id),
	CONSTRAINT FK_send_account_id FOREIGN KEY (send_account_id) REFERENCES account (account_id),
	CONSTRAINT FK_receive_account_id FOREIGN KEY (receive_account_id) REFERENCES account (account_id)
);


COMMIT;
