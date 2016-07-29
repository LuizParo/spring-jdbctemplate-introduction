USE `editora-db`;
DROP procedure IF EXISTS `procedure_uppercase_titulo`;

DELIMITER $$
USE `editora-db`$$
CREATE PROCEDURE `procedure_uppercase_titulo` (IN idLivro INT)
BEGIN
	UPDATE livro
    SET titulo = UPPER(titulo)
    WHERE id = idLivro;
END$$

DELIMITER ;
