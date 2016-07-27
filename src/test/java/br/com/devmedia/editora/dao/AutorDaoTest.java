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
public class AutorDaoTest {
    private Autor authorOne;
    private Autor authorTwo;
    private Editora editoraOne;
    private Editora editoraTwo;
    
    @Autowired
    private AutorDao authorDao;
    
    @Autowired
    private LivroDao bookDao;
    
    @Autowired
    private EditoraDao editoraDao;
    
    @Autowired
    private LivroAutorDao bookAuthorDao;

    @Before
    public void setUp() {
        this.editoraOne = new Editora("Editora Sul Ltda.", "Porto Alegre", "contato@ed-sul.com");
        this.editoraTwo = new Editora("Editora Copacabana Ltda.", "Rio de Janeiro", "contato@ed-copacabana.com");
        this.editoraDao.save(this.editoraOne);
        this.editoraDao.save(this.editoraTwo);
        
        this.authorOne = new Autor("Aline da Silva", "alines@email.com", this.editoraOne);
        this.authorTwo = new Autor("João da Silva", "joaos@email.com", this.editoraTwo);
        this.authorDao.save(this.authorOne);
        this.authorDao.save(this.authorTwo);
    }

    @Test
    public void shouldInsertAutor() {
        Autor autor = new Autor("Ricardo da Silva", "ricardos@email.com", this.editoraOne);
        this.authorDao.save(autor);
        
        Assert.assertNotNull(autor.getId());
        Assert.assertTrue(autor.getId() > 0);
    }
    
    @Test(expected = NullPointerException.class)
    public void shouldNotInsertAutorWithoutEditora() {
        Autor autor = new Autor("Ricardo da Silva", "ricardos@email.com", null);
        this.authorDao.save(autor);
    }
    
    @Test
    public void shouldFindAllAutoresWithRowMapper() {
        List<Autor> autores = this.authorDao.findAll();
        
        Assert.assertNotNull(autores);
        Assert.assertFalse(autores.isEmpty());
        Assert.assertEquals(2, autores.size());
        
        for (Autor autor : autores) {
            Assert.assertNotNull(autor.getEditora());
        }
    }
    
    @Test
    public void shouldFindAutoresByEditoraWithRowMapper() {
        List<Autor> autores = this.authorDao.findAutoresByEditora(this.editoraOne);
        
        Assert.assertNotNull(autores);
        Assert.assertFalse(autores.isEmpty());
        Assert.assertEquals(1, autores.size());
        
        for (Autor autor : autores) {
            Assert.assertNotNull(autor.getEditora());
        }
    }
    
    @Test
    public void shouldUpdateAutor() {
        Autor autorToBeUpdated = new Autor("José da Silva", "joses@email.com", this.editoraTwo);
        autorToBeUpdated.setId(this.authorOne.getId());
        
        int rowsUpdated = this.authorDao.update(autorToBeUpdated);
        Assert.assertEquals(1, rowsUpdated);
        
        Autor autorUpdated = this.authorDao.findById(this.authorOne.getId());
        Assert.assertEquals(autorUpdated.getId(), this.authorOne.getId());
        Assert.assertNotEquals(this.authorOne.getNome(), autorUpdated.getNome());
        Assert.assertNotEquals(this.authorOne.getEmail(), autorUpdated.getEmail());
        Assert.assertNotEquals(this.authorOne.getEditora(), autorUpdated.getEditora());
    }
    
    @Test
    public void shouldRemoveAutor() {
        int rowsDeleted = this.authorDao.remove(this.authorOne);
        Assert.assertEquals(1, rowsDeleted);
    }
    
    @Test
    public void shouldGetIdAutorByNome() {
        Integer idRecovered = this.authorDao.getIdByNome(this.authorOne.getNome());
        
        Assert.assertNotNull(idRecovered);
        Assert.assertEquals(idRecovered, this.authorOne.getId());
    }
    
    @Test
    public void testFindAutorWithLivros() {
        List<Livro> books = Arrays.asList(new Livro("Learn Spring JdbcTemplate", 1, 168),
                                          new Livro("Learn Spring Data JPA", 2, 210));
        
        for (Livro book : books) {
            this.bookDao.save(book);
            
            LivroAutor bookAuthor = new LivroAutor(book.getId(), this.authorOne.getId());
            this.bookAuthorDao.save(bookAuthor);
        }
        
        Autor authorWithBooks = this.authorDao.findAutorWithLivros(this.authorOne.getId());
        Assert.assertNotNull(authorWithBooks);
        Assert.assertNotNull(authorWithBooks.getLivros());
        Assert.assertFalse(authorWithBooks.getLivros().isEmpty());
        
        for (Livro book : authorWithBooks.getLivros()) {
            Assert.assertNotNull(book.getId());
        }
    }
}