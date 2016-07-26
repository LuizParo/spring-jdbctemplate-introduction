package br.com.devmedia.editora.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import br.com.devmedia.editora.entity.Livro;

@Repository
public class LivroDao {
    
    @Autowired
    private JdbcTemplate template;
    
    public Livro save(Livro livro) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.template)
                .withTableName("livro")
                .usingColumns("titulo", "edicao", "paginas")
                .usingGeneratedKeyColumns("id");
        
        Number key = insert.executeAndReturnKey(new BeanPropertySqlParameterSource(livro));
        livro.setId(key.intValue());
        
        return livro;
    }
}