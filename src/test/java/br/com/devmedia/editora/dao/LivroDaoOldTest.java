package br.com.devmedia.editora.dao;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.devmedia.editora.AppEditora;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppEditora.class)
public class LivroDaoOldTest {
    
    @Autowired
    private LivroDaoOld livroDao;
    
    @After
    public void tearDown() {
        this.livroDao.dropTable();
    }

    @Test
    public void shouldMakeOperationsOnTable() {
        this.livroDao.insert();
        
        List<Object> list = this.livroDao.select();
        for (Object object : list) {
            Assert.assertNotNull(object);
        }
    }
}