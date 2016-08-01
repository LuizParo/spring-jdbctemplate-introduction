package br.com.devmedia.editora.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import br.com.devmedia.editora.entity.Endereco;

@Repository
public class EnderecoDao extends JdbcDaoSupport {

    @Autowired
    public EnderecoDao(DataSource dataSource) {
        this.setDataSource(dataSource);
    }
    
    public Endereco save(Endereco endereco) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("logradouro", endereco.getLogradouro())
                .addValue("numero", endereco.getNumero())
                .addValue("cidade", endereco.getCidade())
                .addValue("autor_id", endereco.getIdAutor());
        
        Number key = new SimpleJdbcInsert(this.getJdbcTemplate())
                .withTableName("endereco")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(parameterSource);
        endereco.setId(key.intValue());
        
        return endereco;
    }
    
    public List<Endereco> findAll() {
        String sql = "SELECT * FROM endereco";
        
        return this.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(Endereco.class));
    }
}