package br.com.devmedia.editora.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import br.com.devmedia.editora.dao.mapper.LivroMapper;
import br.com.devmedia.editora.entity.Autor;
import br.com.devmedia.editora.entity.Editora;
import br.com.devmedia.editora.entity.Livro;

@Repository
@PropertySource("classpath:sql/livro.xml")
public class LivroDao {
    
    private JdbcTemplate template;
    private NamedParameterJdbcTemplate namedParameter;
    
    @Value("${sql.livro.findLivroWithAutores}")
    private String sqlFindLivroWithAutores;
    
    @Value("${sql.livro.findByEdicao}")
    private String sqlFindLivroByEdicao;
    
    @Autowired
    public LivroDao(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
        this.namedParameter = new NamedParameterJdbcTemplate(dataSource);
    }
    
    public Livro save(Livro livro) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.template)
                .withTableName("livro")
                .usingColumns("titulo", "edicao", "paginas")
                .usingGeneratedKeyColumns("id");
        
        Number key = insert.executeAndReturnKey(new BeanPropertySqlParameterSource(livro));
        livro.setId(key.intValue());
        
        return livro;
    }
    
    public Livro findLivroWithAutores(int id) {
        Livro livro = null;
        List<Autor> autores = new ArrayList<>();
        
        List<Map<String, Object>> rows = this.template.queryForList(this.sqlFindLivroWithAutores, Objects.requireNonNull(id, "id for Livro cannot be null"));
        for (Map<String, Object> row : rows) {
            if(livro == null) {
                livro = new Livro((Integer)row.get("id"),
                                  (String)row.get("titulo"),
                                  (Integer)row.get("edicao"),
                                  (Integer)row.get("paginas"));
            }
            Editora editora = new Editora();
            editora.setId((Integer) row.get("id_editora"));
            editora.setRazaoSocial((String) row.get("razao_social"));
            editora.setCidade((String) row.get("cidade"));
            editora.setEmail((String) row.get("email_editora"));
            
            Autor autor = new Autor();
            autor.setId((Integer) row.get("id_autor"));
            autor.setNome((String) row.get("nome"));
            autor.setEmail((String) row.get("email_autor"));
            autor.setEditora(editora);
            
            autores.add(autor);
        }
        if(livro != null) {
            livro.setAutores(autores);
        }
        
        return livro;
    }
    
    public List<Livro> findByEdicao(int edicao) {
        return this.namedParameter.query(this.sqlFindLivroByEdicao,
                                         new MapSqlParameterSource("edicao", edicao),
                                         new LivroMapper());
    }
}