package br.com.devmedia.editora.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class EnderecoDao extends JdbcDaoSupport {

    @Autowired
    public EnderecoDao(DataSource dataSource) {
        this.setDataSource(dataSource);
    }
}