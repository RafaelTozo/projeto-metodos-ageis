drop database if exists projetobd;
create database projetobd;
use projetobd;

CREATE TABLE Usuario (
                         idUsuario INT AUTO_INCREMENT PRIMARY KEY,
                         nome VARCHAR(255) NOT NULL,
                         email VARCHAR(255) UNIQUE NOT NULL,
                         senha VARCHAR(255) NOT NULL
);

CREATE TABLE Grupo (
                       idGrupo INT AUTO_INCREMENT PRIMARY KEY,
                       nome VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE Senhas (
                        idSenha INT AUTO_INCREMENT PRIMARY KEY,
                        nomeAplicacao VARCHAR(255) NOT NULL,
                        emailLogin VARCHAR(255) NOT NULL
                        senha VARCHAR(255) NOT NULL
);

CREATE TABLE Grupo_Usuario (
                               idGrupo INT,
                               idUsuario INT,
                               PRIMARY KEY (idGrupo, idUsuario),
                               FOREIGN KEY (idGrupo) REFERENCES Grupo(idGrupo) ON DELETE CASCADE,
                               FOREIGN KEY (idUsuario) REFERENCES Usuario(idUsuario) ON DELETE CASCADE
);

CREATE TABLE Usuario_Senha (
                               idUsuario INT,
                               idSenha INT,
                               PRIMARY KEY (idUsuario, idSenha),
                               FOREIGN KEY (idUsuario) REFERENCES Usuario(idUsuario) ON DELETE CASCADE,
                               FOREIGN KEY (idSenha) REFERENCES Senhas(idSenha) ON DELETE CASCADE
);

CREATE TABLE Grupo_Senha (
                             idGrupo INT,
                             idSenha INT,
                             PRIMARY KEY (idGrupo, idSenha),
                             FOREIGN KEY (idGrupo) REFERENCES Grupo(idGrupo) ON DELETE CASCADE,
                             FOREIGN KEY (idSenha) REFERENCES Senhas(idSenha) ON DELETE CASCADE
);
