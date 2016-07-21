package br.com.devmedia.editora.dao;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import br.com.devmedia.editora.dao.mapper.CidadeAndEmailEditoraMapper;
import br.com.devmedia.editora.dao.mapper.EditoraMapper;
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
}