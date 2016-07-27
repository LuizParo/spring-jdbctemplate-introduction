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
public class LivroDaoTest {
    private Livro bookOne;
    private Livro bookTwo;
    
    @Autowired
    private LivroDao bookDao;
    
    @Autowired
    private EditoraDao editoraDao;
    
    @Autowired
    private AutorDao authorDao;
    
    @Autowired
    private LivroAutorDao bookAuthorDao;

    @Before
    public void setUp() {
        this.bookOne = new Livro("Learn Spring JdbcTemplate", 1, 168);
        this.bookTwo = new Livro("Learn Spring Data JPA", 2, 210);
        this.bookDao.save(bookOne);
        this.bookDao.save(bookTwo);
    }

    @Test
    public void shouldInsertLivro() {
        Livro book = new Livro("Learn Spring MVC", 1, 168);
        this.bookDao.save(book);
        
        Assert.assertNotNull(book.getId());
    }
    
    @Test
    public void testFindLivroWithAutores() {
        Editora editoraOne = new Editora("Editora Sul Ltda.", "Porto Alegre", "contato@ed-sul.com");
        Editora editoraTwo = new Editora("Editora Copacabana Ltda.", "Rio de Janeiro", "contato@ed-copacabana.com");
        this.editoraDao.save(editoraOne);
        this.editoraDao.save(editoraTwo);
        
        List<Autor> authors = Arrays.asList(new Autor("Luciana da Silva", "lucianas@email.com", editoraOne),
                                            new Autor("João da Silva", "joaos@email.com", editoraTwo));
        
        for (Autor author : authors) {
            this.authorDao.save(author);
            
            LivroAutor bookAuthor = new LivroAutor(this.bookOne.getId(), author.getId());
            this.bookAuthorDao.save(bookAuthor);
        }
        
        Livro bookWithAuthors = this.bookDao.findLivroWithAutores(this.bookOne.getId());
        
        Assert.assertNotNull(bookWithAuthors);
        Assert.assertNotNull(bookWithAuthors.getAutores());
        Assert.assertFalse(bookWithAuthors.getAutores().isEmpty());
        
        for (Autor author : bookWithAuthors.getAutores()) {
            Assert.assertNotNull(author.getId());
        }
    }
}