package br.com.devmedia.editora.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.devmedia.editora.AppEditora;
import br.com.devmedia.editora.entity.Livro;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppEditora.class)
public class LivroDaoTest {
    private Livro livroOne;
    private Livro livroTwo;
    
    @Autowired
    private LivroDao livroDao;

    @Before
    public void setUp() {
        this.livroOne = new Livro("Learn Spring JdbcTemplate", 1, 168);
        this.livroTwo = new Livro("Learn Spring Data JPA", 2, 210);
        this.livroDao.save(livroOne);
        this.livroDao.save(livroTwo);
    }

    @Test
    public void shouldInsertLivro() {
        Livro book = new Livro("Learn Spring MVC", 1, 168);
        this.livroDao.save(book);
        
        Assert.assertNotNull(book.getId());
    }
}