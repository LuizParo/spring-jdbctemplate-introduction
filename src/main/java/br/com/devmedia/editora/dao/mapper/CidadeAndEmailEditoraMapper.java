package br.com.devmedia.editora.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.com.devmedia.editora.entity.Editora;

public class CidadeAndEmailEditoraMapper implements RowMapper<Editora> {

    @Override
    public Editora mapRow(ResultSet rs, int rowNum) throws SQLException {
        Editora editora = new Editora();
        editora.setId(rs.getInt("id"));
        editora.setEmail(rs.getString("email"));
        editora.setCidade(rs.getString("cidade"));
        
        return editora;
    }
}