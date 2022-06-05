CREATE TABLE cliente (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(80) NOT NULL,
	tipo_pessoa VARCHAR(15)  NOT NULL,
	documento VARCHAR(30) NOT NULL,
	telefone VARCHAR(20),
	email VARCHAR(60) NOT NULL,
	logradouro VARCHAR(60),
	numero VARCHAR(15),
	complemento VARCHAR(20),
	cep VARCHAR(8),
	cidade_id BIGINT(20),
	FOREIGN KEY (cidade_id) REFERENCES cidade(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8; 
