package br.com.devmedia.editora.dao;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.devmedia.editora.AppEditora;
import br.com.devmedia.editora.entity.Autor;
import br.com.devmedia.editora.entity.Editora;
import br.com.devmedia.editora.entity.Livro;
import br.com.devmedia.editora.entity.LivroAutor;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppEditora.class)
public class LivroAutorDaoTest {
    private Livro livroOne;
    private Livro livroTwo;
    private Editora editoraOne;
    private Editora editoraTwo;
    private Autor autorOne;
    private Autor autorTwo;

    @Autowired
    private EditoraDao editoraDao;

    @Autowired
    private AutorDao autorDao;
    
    @Autowired
    private LivroDao livroDao;
    
    @Autowired
    private LivroAutorDao livroAutorDao;

    @Before
    public void setUp() {
        this.livroOne = new Livro("Learn Spring JdbcTemplate", 1, 168);
        this.livroTwo = new Livro("Learn Spring Data JPA", 2, 210);
        this.livroDao.save(livroOne);
        this.livroDao.save(livroTwo);
        
        this.editoraOne = new Editora("Editora Sul Ltda.", "Porto Alegre", "contato@ed-sul.com");
        this.editoraTwo = new Editora("Editora Copacabana Ltda.", "Rio de Janeiro", "contato@ed-copacabana.com");
        this.editoraDao.save(this.editoraOne);
        this.editoraDao.save(this.editoraTwo);
        
        this.autorOne = new Autor("Luciana da Silva", "lucianas@email.com", this.editoraOne);
        this.autorTwo = new Autor("João da Silva", "joaos@email.com", this.editoraTwo);
        this.autorDao.save(this.autorOne);
        this.autorDao.save(this.autorTwo);
    }

    @Test
    public void shouldInsertLivroAutor() {
        List<Autor> authors = Arrays.asList(this.autorOne, this.autorTwo);
        for (Autor author : authors) {
            LivroAutor livroAutor = new LivroAutor(this.livroOne.getId(), author.getId());
            this.livroAutorDao.save(livroAutor);
            
            Assert.assertNotNull(livroAutor.getId());
        }
    }
}