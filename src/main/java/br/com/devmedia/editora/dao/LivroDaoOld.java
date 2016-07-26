package br.com.devmedia.editora.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

@Repository
public class LivroDaoOld {
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS livro("
                                             + "id INT(11) PRIMARY KEY, "
                                             + "titulo VARCHAR(45) NOT NULL, "
                                             + "autor VARCHAR(45) NOT NULL)";
    private JdbcTemplate template;
    
    @Autowired
    public LivroDaoOld(JdbcTemplate template) {
        this.template = template;
        this.template.execute(CREATE_TABLE);
    }
    
    public void insert() {
        this.template.execute("INSERT INTO livro(id, titulo, autor) VALUES (100, 'JdbcTemplate', 'Spring')");
    }
    
    public List<Object> select() {
        return this.template.execute("SELECT * FROM livro WHERE id = 100",
                new PreparedStatementCallback<List<Object>>() {

                    @Override
                    public List<Object> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                        List<Object> list = new ArrayList<>();
                        ResultSet rs = ps.executeQuery();
                        while(rs.next()) {
                            list.add(rs.getInt("id"));
                            list.add(rs.getString("titulo"));
                            list.add(rs.getString("autor"));
                        }
                        return list;
                    }
                });
    }
    
    public void dropTable() {
        this.template.execute("DROP TABLE livro");
    }
}