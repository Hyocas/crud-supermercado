CREATE DATABASE loja;
use loja;

CREATE TABLE produto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    preco FLOAT,
    estoque INT,
    descricao TEXT
);

ALTER TABLE produto ADD imagem LONGBLOB;

SELECT * FROM produto;