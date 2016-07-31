package br.com.devmedia.editora.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import br.com.devmedia.editora.dao.mapper.AutorMapper;
import br.com.devmedia.editora.dao.mapper.AutorWithEditoraMapper;
import br.com.devmedia.editora.entity.Autor;
import br.com.devmedia.editora.entity.Editora;
import br.com.devmedia.editora.entity.Livro;

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
    
    @Value("${sql.autor.getIdByNome}")
    private String sqlGetIdByNome;
    
    @Value("${sql.autor.findAutorWithLivros}")
    private String sqlFindAutorWithLivros;
    
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

    public Integer getIdByNome(String nome) {
        return this.template.queryForObject(this.sqlGetIdByNome, Integer.class, nome);
    }
    
    public Autor findAutorWithLivros(int id) {
        Autor autor = null;
        List<Livro> livros = new ArrayList<>();
        
        List<Map<String, Object>> rows = this.template.queryForList(this.sqlFindAutorWithLivros, Objects.requireNonNull(id, "id for Autor cannot be null"));
        for (Map<String, Object> row : rows) {
            if(autor == null) {
                autor = new Autor();
                autor.setId((Integer)row.get("id"));
                autor.setNome((String)row.get("nome"));
                autor.setEmail((String)row.get("email_autor"));
            }
            Editora editora = new Editora();
            editora.setId((Integer) row.get("id_editora"));
            editora.setRazaoSocial((String) row.get("razao_social"));
            editora.setCidade((String) row.get("cidade"));
            editora.setEmail((String) row.get("email_editora"));
            autor.setEditora(editora);
            
            Livro livro = new Livro();
            livro.setId((Integer) row.get("id_livro"));
            livro.setTitulo((String) row.get("titulo"));
            livro.setEdicao((Integer) row.get("edicao"));
            livro.setPaginas((Integer) row.get("paginas"));
            
            livros.add(livro);
        }
        if(autor != null) {
            autor.setLivros(livros);
        }
        
        return autor;
    }
    
    public void updateBatch(final List<Autor> autores) {
        this.template.batchUpdate(this.sqlUpdateAutor,
                new BatchPreparedStatementSetter() {
                    
                    @Override
                    public void setValues(PreparedStatement ps, int index) throws SQLException {
                        Autor autor = autores.get(index);
                        ps.setString(1, autor.getNome());
                        ps.setString(2, autor.getEmail());
                        ps.setInt(3, autor.getEditora().getId());
                        ps.setInt(4, autor.getId());
                    }
                    
                    @Override
                    public int getBatchSize() {
                        return autores.size();
                    }
                });
    }
    
    public void removeBatch(final List<Integer> ids) {
        this.template.batchUpdate(this.sqlDelete,
                new BatchPreparedStatementSetter() {
                    
                    @Override
                    public void setValues(PreparedStatement ps, int index) throws SQLException {
                        ps.setInt(1, ids.get(index));
                    }
                    
                    @Override
                    public int getBatchSize() {
                        return ids.size();
                    }
                });
    }
}