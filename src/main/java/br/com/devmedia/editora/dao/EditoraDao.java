package br.com.devmedia.editora.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import br.com.devmedia.editora.dao.mapper.CidadeAndEmailEditoraMapper;
import br.com.devmedia.editora.dao.mapper.EditoraMapper;
import br.com.devmedia.editora.entity.Autor;
import br.com.devmedia.editora.entity.Editora;

@Repository
@PropertySource("classpath:sql/editora.xml")
public class EditoraDao {

    @Autowired
    private JdbcTemplate template;
    
    @Value("${sql.insert}")
    private String sqlInsert;
    
    @Value("${sql.findBy.razaoSocial}")
    private String sqlFindByRazaoSocial;
    
    @Value("${sql.findBy.cidade}")
    private String sqlFindByCidade;
    
    @Value("${sql.findBy.id}")
    private String sqlFindById;
    
    @Value("${sql.findAll}")
    private String sqlFindAll;
    
    @Value("${sql.count}")
    private String sqlCount;
    
    @Value("${sql.findEmailBy.id}")
    private String sqlFindEmailById;
    
    @Value("${sql.sqlFindAllEmails}")
    private String sqlFindAllEmails;
    
    @Value("${sql.sqlFindCidadeAndEmailBy.Id}")
    private String sqlFindCidadeAndEmailById;
    
    @Value("${sql.sqlFindCidadesAndEmails}")
    private String sqlFindCidadesAndEmails;
    
    @Value("${sql.update}")
    private String sqlUpdate;
    
    @Value("${sql.delete}")
    private String sqlDelete;
    
    @Value("${sql.findEditoraWithAutores}")
    private String sqlFindEditoraWithAutores;
    
    public int insert(Editora editora) {
        return this.template.update(this.sqlInsert, editora.getRazaoSocial(), editora.getCidade(), editora.getEmail());
    }
    
    public int save(Editora editora) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.template);
        insert.setTableName("editora");
        insert.setColumnNames(Arrays.asList("razao_social", "cidade", "email"));
        insert.setGeneratedKeyName("id");
        
        Number key = insert.executeAndReturnKey(new BeanPropertySqlParameterSource(editora));
        editora.setId(key.intValue());
        
        return key.intValue();
    }
    
    public Editora findById(int id) {
        return this.template.queryForObject(this.sqlFindById, new EditoraMapper(), id);
    }
    
    public List<Editora> findByRazaoSocial(String razao) {
        return this.template.query(this.sqlFindByRazaoSocial, new String[] {razao}, new EditoraMapper());
    }
    
    public List<Editora> findByCidade(String cityOne, String cityTwo) {
        return this.template.query(this.sqlFindByCidade, new EditoraMapper(), cityOne, cityTwo);
    }
    
    public List<Editora> findAll() {
        return this.template.query(this.sqlFindAll, new EditoraMapper());
    }
    
    public int count() {
        return this.template.queryForObject(this.sqlCount, int.class);
    }
    
    public String findEmailById(int id) {
        return this.template.queryForObject(this.sqlFindEmailById, String.class, id);
    }
    
    public List<String> findAllEmails() {
        return this.template.queryForList(this.sqlFindAllEmails, String.class);
    }
    
    public List<String> findCidadeAndEmailById(int id) {
        return this.template.queryForObject(this.sqlFindCidadeAndEmailById,
                                            new Integer[] {id},
                                            (rs, rowNum) -> {
                                                String cidade = rs.getString("cidade");
                                                String email = rs.getString("email");
                                                return Arrays.asList(cidade, email);
                                            });
    }
    
    public Editora findEditoraWithCidadeAndEmailById(int id) {
        return this.template.queryForObject(this.sqlFindCidadeAndEmailById, new Integer[] {id}, new CidadeAndEmailEditoraMapper());
    }
    
    public List<Editora> findCidadesAndEmails() {
        return this.template.query(this.sqlFindCidadesAndEmails, new CidadeAndEmailEditoraMapper());
    }
    
    public int update(Editora editora) {
        return this.template.update(this.sqlUpdate,
                                    editora.getRazaoSocial(),
                                    editora.getCidade(),
                                    editora.getEmail(),
                                    editora.getId());
    }
    
    public int remove(Editora editora) {
        return this.template.update(this.sqlDelete, editora.getId());
    }
    
    public Editora findEditoraWithAutores(int id, int page, int size) {
        List<Map<String,Object>> rows = this.template.queryForList(this.sqlFindEditoraWithAutores, id, page * size, size);
        
        Editora editora = null;
        List<Autor> autores = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            if(editora == null) {
                editora = new Editora();
                editora.setId((Integer) row.get("id"));
                editora.setRazaoSocial((String) row.get("razao_social"));
                editora.setCidade((String) row.get("cidade"));
                editora.setEmail((String) row.get("email"));
            }
            
            Autor autor = new Autor();
            autor.setId((Integer) row.get("id_autor"));
            autor.setNome((String) row.get("nome"));
            autor.setEmail((String) row.get("email_autor"));
            autor.setEditora(editora);
            
            autores.add(autor);
        }
        if(editora != null) {
            editora.setAutores(autores);
            return editora;
        }
        return null;
    }
    
    public void insertBatch(final List<Editora> editoras) {
        this.template.batchUpdate(this.sqlInsert, new BatchPreparedStatementSetter() {
            
            @Override
            public void setValues(PreparedStatement ps, int index) throws SQLException {
                Editora editora = editoras.get(index);
                ps.setString(1, editora.getRazaoSocial());
                ps.setString(2, editora.getCidade());
                ps.setString(3, editora.getEmail());
            }
            
            @Override
            public int getBatchSize() {
                return editoras.size();
            }
        });
    }
    
    public void saveBatch(final List<Editora> editoras) {
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(editoras.toArray());
        
        new SimpleJdbcInsert(this.template)
                .withTableName("editora")
                .usingColumns("razao_social", "cidade", "email")
                .executeBatch(batch);
    }
}