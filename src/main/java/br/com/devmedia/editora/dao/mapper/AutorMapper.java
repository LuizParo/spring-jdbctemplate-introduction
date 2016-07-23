package br.com.devmedia.editora.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.com.devmedia.editora.dao.EditoraDao;
import br.com.devmedia.editora.entity.Autor;

public class AutorMapper implements RowMapper<Autor> {
    private EditoraDao editoraDao;
    
    public AutorMapper(EditoraDao editoraDao) {
        this.editoraDao = editoraDao;
    }

    @Override
    public Autor mapRow(ResultSet rs, int rowNum) throws SQLException {
        Autor autor = new Autor();
        autor.setId(rs.getInt("id"));
        autor.setNome(rs.getString("nome"));
        autor.setEmail(rs.getString("email"));
        autor.setEditora(this.editoraDao.findById(rs.getInt("id_editora")));
        
        return autor;
    }
}