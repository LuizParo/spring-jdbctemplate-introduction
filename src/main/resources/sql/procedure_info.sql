USE `editora-db`;
DROP procedure IF EXISTS `procedure_info`;

DELIMITER $$
USE `editora-db`$$
CREATE PROCEDURE `procedure_info` (
	IN in_id INT,
    OUT out_titulo VARCHAR(45),
    OUT out_autor VARCHAR(45),
    OUT out_editora VARCHAR(45)
)
BEGIN
	SELECT l.titulo, a.nome, e.razao_social
    INTO out_titulo, out_autor, out_editora
    FROM livro l
    INNER JOIN livro_autor la ON la.id_livro = l.id
    INNER JOIN autor a ON a.id = la.id_autor
    INNER JOIN editora e ON e.id = a.id_editora
    WHERE l.id = in_id;
END$$

DELIMITER ;
