package br.com.devmedia.editora.dao;

import java.util.List;
import java.util.Objects;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import br.com.devmedia.editora.dao.mapper.AutorMapper;
import br.com.devmedia.editora.dao.mapper.AutorWithEditoraMapper;
import br.com.devmedia.editora.entity.Autor;
import br.com.devmedia.editora.entity.Editora;

@Repository
@PropertySource("classpath:sql/autor.xml")
public class AutorDao {
    private JdbcTemplate template;
    private SimpleJdbcInsert simpleJdbcInsert;
    
    @Autowired
    private EditoraDao editoraDao;
    
    @Value("${sql.autor.findBy.id}")
    private String sqlFindById;
    
    @Value("${sql.autor.findAll}")
    private String sqlFindAll;
    
    @Value("${sql.autor.findAutoresBy.editora}")
    private String sqlFindAutoresByEditora;
    
    @Value("${sql.autor.update}")
    private String sqlUpdateAutor;
    
    @Value("${sql.autor.delete}")
    private String sqlDelete;
    
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("autor")
                //.usingColumns("nome", "email", "id_editora")
                .usingGeneratedKeyColumns("id");
    }
    
    public Autor save(Autor autor) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("nome", autor.getNome())
                .addValue("email", autor.getEmail())
                .addValue("id_editora", autor.getEditora().getId());
        
        Number key = this.simpleJdbcInsert.executeAndReturnKey(parameterSource);
        autor.setId(key.intValue());
        
        return autor;
    }
    
    public Autor findById(Integer id) {
        return this.template.queryForObject(this.sqlFindById, new AutorMapper(this.editoraDao), id);
    }
    
    public List<Autor> findAll() {
        return this.template.query(this.sqlFindAll, new AutorMapper(this.editoraDao));
    }
    
    public List<Autor> findAutoresByEditora(Editora editora) {
        return this.template.query(this.sqlFindAutoresByEditora, new AutorWithEditoraMapper(), editora.getRazaoSocial());
    }
    
    public int update(Autor autor) {
        return this.template.update(this.sqlUpdateAutor,
                autor.getNome(),
                autor.getEmail(),
                Objects.requireNonNull(autor.getEditora(), "'Editora' wasn't provided in this Autor").getId(),
                autor.getId());
    }
    
    public int remove(Autor autor) {
        return this.template.update(this.sqlDelete, autor.getId());
    }
}