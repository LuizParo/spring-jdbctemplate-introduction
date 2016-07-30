USE `editora-db`;
DROP function IF EXISTS `function_conta_livros_autor`;

DELIMITER $$
USE `editora-db`$$
CREATE FUNCTION `function_conta_livros_autor` (idAutor INT)
RETURNS VARCHAR(200)
BEGIN
	DECLARE retorno, nome VARCHAR(200);
    DECLARE count INT;
    
    SELECT COUNT(l.id), a.nome
    INTO count, nome
    FROM autor a
    LEFT JOIN livro_autor la ON la.id_autor = a.id
    LEFT JOIN livro l ON l.id = la.id_livro
    WHERE a.id = idAutor;
    
    SET retorno = CONCAT("The author ", nome, " has ", count, " book(s) published!");
RETURN retorno;
END$$

DELIMITER ;
