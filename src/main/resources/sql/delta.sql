-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema editora-db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema editora-db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `editora-db` DEFAULT CHARACTER SET utf8 ;
USE `editora-db` ;

-- -----------------------------------------------------
-- Table `editora-db`.`editora`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `editora-db`.`editora` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `razao_social` VARCHAR(60) NOT NULL,
  `cidade` VARCHAR(60) NOT NULL,
  `email` VARCHAR(60) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `razao_social_UNIQUE` (`razao_social` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 563
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `editora-db`.`autor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `editora-db`.`autor` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `id_editora` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `nome_UNIQUE` (`nome` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  INDEX `fk_autor_editora_idx` (`id_editora` ASC),
  CONSTRAINT `fk_autor_editora`
    FOREIGN KEY (`id_editora`)
    REFERENCES `editora-db`.`editora` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `editora-db`.`livro`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `editora-db`.`livro` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `titulo` VARCHAR(45) NOT NULL,
  `edicao` INT NOT NULL,
  `paginas` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UQ_TITULO_EDICAO` (`titulo` ASC, `edicao` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `editora-db`.`livro_autor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `editora-db`.`livro_autor` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_livro` INT NOT NULL,
  `id_autor` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_LIVRO_AUTOR` (`id_autor` ASC),
  INDEX `FK_AUTOR_LIVRO` (`id_livro` ASC),
  UNIQUE INDEX `UQ_LIVRO_AUTOR` (`id_livro` ASC, `id_autor` ASC),
  CONSTRAINT `fk_livro_has_autor_livro1`
    FOREIGN KEY (`id_livro`)
    REFERENCES `editora-db`.`livro` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_livro_has_autor_autor1`
    FOREIGN KEY (`id_autor`)
    REFERENCES `editora-db`.`autor` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `editora-db`.`endereco`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `editora-db`.`endereco` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `logradouro` VARCHAR(60) NOT NULL,
  `numero` INT NOT NULL,
  `cidade` VARCHAR(60) NOT NULL,
  `autor_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_endereco_autor_idx` (`autor_id` ASC),
  CONSTRAINT `fk_endereco_autor`
    FOREIGN KEY (`autor_id`)
    REFERENCES `editora-db`.`autor` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
