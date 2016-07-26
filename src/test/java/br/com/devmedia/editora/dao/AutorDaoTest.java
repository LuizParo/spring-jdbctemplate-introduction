package br.com.devmedia.editora.dao;

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

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppEditora.class)
public class AutorDaoTest {
    private Autor autorOne;
    private Autor autorTwo;
    private Editora editoraOne;
    private Editora editoraTwo;
    
    @Autowired
    private AutorDao autorDao;
    
    @Autowired
    private EditoraDao editoraDao;

    @Before
    public void setUp() {
        this.editoraOne = new Editora("Editora Sul Ltda.", "Porto Alegre", "contato@ed-sul.com");
        this.editoraTwo = new Editora("Editora Copacabana Ltda.", "Rio de Janeiro", "contato@ed-copacabana.com");
        this.editoraDao.save(this.editoraOne);
        this.editoraDao.save(this.editoraTwo);
        
        this.autorOne = new Autor("Aline da Silva", "alines@email.com", this.editoraOne);
        this.autorTwo = new Autor("João da Silva", "joaos@email.com", this.editoraTwo);
        this.autorDao.save(this.autorOne);
        this.autorDao.save(this.autorTwo);
    }

    @Test
    public void shouldInsertAutor() {
        Autor autor = new Autor("Ricardo da Silva", "ricardos@email.com", this.editoraOne);
        this.autorDao.save(autor);
        
        Assert.assertNotNull(autor.getId());
        Assert.assertTrue(autor.getId() > 0);
    }
    
    @Test(expected = NullPointerException.class)
    public void shouldNotInsertAutorWithoutEditora() {
        Autor autor = new Autor("Ricardo da Silva", "ricardos@email.com", null);
        this.autorDao.save(autor);
    }
    
    @Test
    public void shouldFindAllAutoresWithRowMapper() {
        List<Autor> autores = this.autorDao.findAll();
        
        Assert.assertNotNull(autores);
        Assert.assertFalse(autores.isEmpty());
        Assert.assertEquals(2, autores.size());
        
        for (Autor autor : autores) {
            Assert.assertNotNull(autor.getEditora());
        }
    }
    
    @Test
    public void shouldFindAutoresByEditoraWithRowMapper() {
        List<Autor> autores = this.autorDao.findAutoresByEditora(this.editoraOne);
        
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
        autorToBeUpdated.setId(this.autorOne.getId());
        
        int rowsUpdated = this.autorDao.update(autorToBeUpdated);
        Assert.assertEquals(1, rowsUpdated);
        
        Autor autorUpdated = this.autorDao.findById(this.autorOne.getId());
        Assert.assertEquals(autorUpdated.getId(), this.autorOne.getId());
        Assert.assertNotEquals(this.autorOne.getNome(), autorUpdated.getNome());
        Assert.assertNotEquals(this.autorOne.getEmail(), autorUpdated.getEmail());
        Assert.assertNotEquals(this.autorOne.getEditora(), autorUpdated.getEditora());
    }
    
    @Test
    public void shouldRemoveAutor() {
        int rowsDeleted = this.autorDao.remove(this.autorOne);
        Assert.assertEquals(1, rowsDeleted);
    }
    
    @Test
    public void shouldGetIdAutorByNome() {
        Integer idRecovered = this.autorDao.getIdByNome(this.autorOne.getNome());
        
        Assert.assertNotNull(idRecovered);
        Assert.assertEquals(idRecovered, this.autorOne.getId());
    }
}