package br.com.devmedia.editora.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.com.devmedia.editora.entity.Livro;

public class LivroMapper implements RowMapper<Livro> {

    @Override
    public Livro mapRow(ResultSet rs, int rowNum) throws SQLException {
        Livro livro = new Livro();
        livro.setId(rs.getInt("id"));
        livro.setTitulo(rs.getString("titulo"));
        livro.setEdicao(rs.getInt("edicao"));
        livro.setPaginas(rs.getInt("paginas"));
        
        return livro;
    }
}