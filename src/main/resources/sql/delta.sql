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


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
