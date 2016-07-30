package br.com.devmedia.editora.dao;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
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
    private SimpleJdbcCall simpleJdbcCall;
    
    @Value("${sql.livro.findLivroWithAutores}")
    private String sqlFindLivroWithAutores;
    
    @Value("${sql.livro.findBy.id}")
    private String sqlFindLivroById;
    
    @Value("${sql.livro.findBy.edicao}")
    private String sqlFindLivroByEdicao;
    
    @Value("${sql.livro.findBy.paginas}")
    private String sqlFindByPaginas;
    
    @Value("${sql.livro.update}")
    private String sqlUpdate;
    
    @Value("${sql.livro.findBy.tituloAndEdicao}")
    private String sqlFindByTitutloAndEdicao;
    
    @Autowired
    public LivroDao(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
        this.namedParameter = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcCall = new SimpleJdbcCall(dataSource);
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
    
    public Livro findById(Integer id) {
        return this.namedParameter.queryForObject(this.sqlFindLivroById,
                new MapSqlParameterSource("id", id),
                new LivroMapper());
    }
    
    public List<Livro> findLivrosByEdicao(int edicao) {
        return this.namedParameter.query(this.sqlFindLivroByEdicao,
                                         new MapSqlParameterSource("edicao", edicao),
                                         new LivroMapper());
    }
    
    public List<Livro> findLivrosByPaginas(int min, int max) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("minimum", min).addValue("maximum", max);
        
        return this.namedParameter.query(this.sqlFindByPaginas,
                sqlParameterSource,
                new LivroMapper());
    }
    
    public Livro findByTitutloAndEdicao(String titulo, int edicao) {
        Livro livro = new Livro(titulo, edicao);
        
        return this.namedParameter.queryForObject(this.sqlFindByTitutloAndEdicao,
                new BeanPropertySqlParameterSource(livro),
                new LivroMapper());
    }
    
    public int update(Livro livro) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", livro.getId())
                .addValue("titulo", livro.getTitulo())
                .addValue("edicao", livro.getEdicao())
                .addValue("paginas", livro.getPaginas());
        
        return this.namedParameter.update(this.sqlUpdate, sqlParameterSource);
    }
    
    public int updateWithLivroAsNamedParameter(Livro livro) {
        return this.namedParameter.update(this.sqlUpdate, new BeanPropertySqlParameterSource(livro));
    }
    
    public Map<String, Object> callProcedureUppercaseTitulo(int idLivro) {
        Map<String, Object> map = this.simpleJdbcCall
                .withProcedureName("procedure_uppercase_titulo")
                .execute(idLivro);
        
        return map;
    }
    
    public List<String> callProcedureInfoLivro(int idLivro) {
        SqlParameterSource parameterSource = new MapSqlParameterSource("in_id", idLivro);
        
        Map<String, Object> info = this.simpleJdbcCall
                .withProcedureName("procedure_info")
                .execute(parameterSource);
        
        String titulo = (String) info.get("out_titulo");
        String autor = (String) info.get("out_autor");
        String editora = (String) info.get("out_editora");
        
        return Arrays.asList(titulo, autor, editora);
    }
    
    public String callFunctionTotalLivrosByAutor(int idAutor) {
        return this.simpleJdbcCall
                .withFunctionName("function_conta_livros_autor")
                .executeFunction(String.class, idAutor);
    }
}