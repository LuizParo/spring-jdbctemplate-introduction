package br.com.devmedia.editora.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.com.devmedia.editora.entity.Autor;
import br.com.devmedia.editora.entity.Editora;

public class AutorWithEditoraMapper implements RowMapper<Autor> {

    @Override
    public Autor mapRow(ResultSet rs, int rowNum) throws SQLException {
        Editora editora = new Editora();
        editora.setId(rs.getInt("id_editora"));
        editora.setRazaoSocial(rs.getString("razao_social"));
        editora.setCidade(rs.getString("cidade"));
        editora.setEmail(rs.getString("email"));
        
        Autor autor = new Autor();
        autor.setId(rs.getInt("id"));
        autor.setNome(rs.getString("nome"));
        autor.setEmail(rs.getString("email_editora"));
        autor.setEditora(editora);
        
        return autor;
    }
}