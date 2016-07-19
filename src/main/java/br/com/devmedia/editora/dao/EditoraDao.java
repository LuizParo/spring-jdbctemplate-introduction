package br.com.devmedia.editora.dao;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import br.com.devmedia.editora.dao.mapper.EditoraMapper;
import br.com.devmedia.editora.entity.Editora;

@Repository
public class EditoraDao {
    
    @Autowired
    private JdbcTemplate template;
    
    public int insert(Editora editora) {
        String sql = "INSERT INTO editora(razao_social, cidade, email) VALUES (?, ?, ?)";
        return this.template.update(sql, editora.getRazaoSocial(), editora.getCidade(), editora.getEmail());
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
    
    public List<Editora> findAll() {
        return this.template.query("SELECT * FROM editora", new EditoraMapper());
    }
}