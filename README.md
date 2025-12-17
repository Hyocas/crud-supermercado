# Supermercado

![Java](https://img.shields.io/badge/Java-red)
![MySQL](https://img.shields.io/badge/Mysql-blue)
![Status](https://img.shields.io/badge/Educational-success)

> Sistema de gerenciamento de produtos desenvolvido como parte da disciplina de **Engenharia de Software**. O projeto implementa um CRUD completo (Create, Read, Update, Delete) com persistência em banco de dados relacional e manipulação de imagens.

## Funcionalidades

O sistema foca na gestão de inventário do supermercado, permitindo:

* **Cadastro de Produtos:** Registro de nome, preço, quantidade em estoque e descrição.
* **Gestão de Imagens:** Upload e visualização de fotos dos produtos (armazenadas como BLOB no banco de dados).
* **Controle de Estoque:** Visualização rápida da quantidade disponível.
* **Interface Moderna:** UI customizada em Java Swing com tema escuro ("Dark Mode") para melhor usabilidade.
* **Persistência:** Conexão robusta com banco de dados MySQL.

## Arquitetura do Projeto

O software foi estruturado seguindo o padrão **MVC (Model-View-Controller)** com a camada adicional **DAO (Data Access Object)** para separar a lógica de negócios da persistência de dados.

* **Model:** Representação dos dados (`Produto.java`).
* **View:** Interface Gráfica construída com Java Swing (`ProdutoView.java`).
* **Controller:** Intermediação entre a interface e o banco de dados (`ProdutoController.java`).
* **DAO:** Execução de queries SQL e gerenciamento de conexões JDBC (`ProdutoDAO.java`).

## Tecnologias Utilizadas

* **Java 17+**
* **Java Swing** (GUI)
* **JDBC** (Java Database Connectivity)
* **MySQL** (Banco de Dados)

## Como Executar

### Pré-requisitos
* Java JDK instalado.
* Servidor MySQL rodando.

### 1. Configuração do Banco de Dados
Crie um banco de dados chamado `loja` e execute o script abaixo para criar a tabela necessária:

```sql
CREATE DATABASE loja;
USE loja;

CREATE TABLE produto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    preco FLOAT NOT NULL,
    estoque INT NOT NULL,
    descricao TEXT,
    imagem LONGBLOB
);
```

### 2. Rodando a aplicação
Compile e execute a classe main.java
```bash
    javac -d bin src/**/*.java
    java -cp bin:mysql-connector-java.jar view.Main
``` 

## Membros da Equipe

* [Jeanluca Caleare]
* [Leandro Balbino]
* [Yago Armand]
