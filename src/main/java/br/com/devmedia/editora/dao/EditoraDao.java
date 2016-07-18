package br.com.devmedia.editora.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EditoraDao {
    
    @Autowired
    private JdbcTemplate template;
}