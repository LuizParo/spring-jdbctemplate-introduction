CREATE SCHEMA `editora-db` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `editora-db`.`editora` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `razao_social` VARCHAR(60) NOT NULL,
  `cidade` VARCHAR(60) NOT NULL,
  `email` VARCHAR(60) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `razao_social_UNIQUE` (`razao_social` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB;
