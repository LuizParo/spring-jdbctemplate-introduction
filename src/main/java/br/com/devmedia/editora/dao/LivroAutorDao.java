package br.com.devmedia.editora.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import br.com.devmedia.editora.entity.LivroAutor;

@Repository
public class LivroAutorDao {
    private JdbcTemplate template;
    
    @Autowired
    public LivroAutorDao(JdbcTemplate template) {
        this.template = template;
    }
    
    public LivroAutor save(LivroAutor livroAutor) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.template)
                .withTableName("livro_autor")
                .usingColumns("id_livro", "id_autor")
                .usingGeneratedKeyColumns("id");
        
        Number key = insert.executeAndReturnKey(new BeanPropertySqlParameterSource(livroAutor));
        livroAutor.setId(key.intValue());
        
        return livroAutor;
    }
}