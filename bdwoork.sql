create database hotel;
use hotel;


CREATE TABLE funcionarios(
id INT AUTO_INCREMENT,
nome VARCHAR(40) NOT NULL,
apelido VARCHAR(40) NOT NULL,
nif INT,
sexo VARCHAR(20)NOT NULL,
cargo VARCHAR(20) NOT NULL,
telefone VARCHAR(20) NOT NULL,
cidade VARCHAR(40) NOT NULL,
pais VARCHAR(40) NOT NULL,
utilizador VARCHAR(40) unique,
senha VARCHAR(40),
primary key(id)

);

CREATE TABLE hospedes (
    id INT AUTO_INCREMENT,
    nome VARCHAR(40) NOT NULL,
    apelido VARCHAR(40) NOT NULL,
    nif INT,
    sexo VARCHAR(10) NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    cidade VARCHAR(40) NOT NULL,
    pais VARCHAR(40) NOT NULL,
    email VARCHAR(100),
    PRIMARY KEY(id)
);

CREATE TABLE quartos (
    numero INT,
    disponivel BOOL,
    hospede INT NULL,
    FOREIGN KEY (hospede) REFERENCES hospedes(id),
    PRIMARY KEY(numero)
    );


INSERT INTO quartos (numero, disponivel, hospede) 
VALUES 
(1, true, NULL),
(2, true, NULL),
(3, true, NULL),
(4, true, NULL),
(5, true, NULL),
(6, true, NULL),
(7, true, NULL),
(8, true, NULL),
(9, true, NULL),
(10, true, NULL),
(11, true, NULL),
(12, true, NULL),
(13, true, NULL),
(14, true, NULL),
(15, true, NULL),
(16, true, NULL),
(17, true, NULL),
(18, true, NULL),
(19, true, NULL),
(20, true, NULL),
(21, true, NULL),
(22, true, NULL),
(23, true, NULL),
(24, true, NULL),
(25, true, NULL),
(26, true, NULL),
(27, true, NULL),
(28, true, NULL),
(29, true, NULL);

CREATE TABLE reservas (
    id INT AUTO_INCREMENT,
    quarto_numero INT,
    hospede_id INT,
    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,
    numero_pessoas INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (quarto_numero) REFERENCES quartos(numero),
    FOREIGN KEY (hospede_id) REFERENCES hospedes(id)
);

INSERT INTO funcionarios (nome, apelido, nif, sexo, cargo, telefone, cidade, pais, utilizador, senha) 
VALUES ('Administrador', 'Sistema', NULL, 'Masculino', 'Admin', '000000000', 'Cidade Exemplo', 'País Exemplo', 'admin', 'admin');
