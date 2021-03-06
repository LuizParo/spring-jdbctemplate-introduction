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

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppEditora.class)
public class EditoraDaoTest {
    private Editora editoraOne;
    private Editora editoraTwo;

    @Autowired
    private EditoraDao editoraDao;
    
    @Autowired
    private AutorDao autorDao;
    
    @Before
    public void setUp() {
        this.editoraOne = new Editora("Editora Sul Ltda.", "Porto Alegre", "contato@ed-sul.com");
        this.editoraTwo = new Editora("Editora Copacabana Ltda.", "Rio de Janeiro", "contato@ed-copacabana.com");
        
        this.editoraDao.save(this.editoraOne);
        this.editoraDao.save(this.editoraTwo);
    }
    
    @Test
    public void shouldPersistEditora() {
        Editora editora = new Editora("Editora Minas Ltda.", "Minas Gerais", "contato@ed-minas.com");
        int updatedRows = this.editoraDao.insert(editora);
        
        Assert.assertEquals(1, updatedRows);
    }
    
    @Test
    public void shouldPersistEditoraGeneratingAnId() {
        Editora editora = new Editora("Editora Minas Ltda.", "Minas Gerais", "contato@ed-minas.com");
        int generatedId = this.editoraDao.save(editora);
        
        Assert.assertTrue(generatedId > 0);
        Assert.assertNotNull(editora.getId());
        Assert.assertEquals(Integer.valueOf(generatedId), editora.getId());
    }
    
    @Test
    public void shouldFindEditoraById() {
        Editora editoraRecuperada = this.editoraDao.findById(this.editoraOne.getId());
        
        Assert.assertNotNull(editoraRecuperada);
        Assert.assertEquals(this.editoraOne.getId(), editoraRecuperada.getId());
    }
    
    @Test
    public void shouldFindEditoraByCity() {
        List<Editora> editoras = this.editoraDao.findByCidade(this.editoraOne.getCidade(), this.editoraTwo.getCidade());
        
        Assert.assertNotNull(editoras);
        Assert.assertFalse(editoras.isEmpty());
        Assert.assertEquals(2, editoras.size());
        for (Editora editora : editoras) {
            Assert.assertTrue(editora.getCidade().equals("Porto Alegre") || editora.getCidade().equals("Rio de Janeiro"));
        }
    }
    
    @Test
    public void shouldFindEditoraByRazaoSocial() {
        List<Editora> editoras = this.editoraDao.findByRazaoSocial("Editora");
        
        Assert.assertNotNull(editoras);
        Assert.assertFalse(editoras.isEmpty());
        Assert.assertEquals(2, editoras.size());
        for (Editora editora : editoras) {
            Assert.assertTrue(editora.getRazaoSocial().contains("Editora"));
        }
    }
    
    @Test
    public void shouldFindAllEditorasWithRowMapper() {
        List<Editora> editoras = this.editoraDao.findAll();
        
        Assert.assertNotNull(editoras);
        Assert.assertFalse(editoras.isEmpty());
        Assert.assertEquals(2, editoras.size());
    }
    
    @Test
    public void shouldCountAllEditoras() {
        int count = this.editoraDao.count();
        Assert.assertEquals(2, count);
    }
    
    @Test
    public void shouldFindEmailById() {
        String email = this.editoraDao.findEmailById(this.editoraOne.getId());
        
        Assert.assertNotNull(email);
        Assert.assertEquals(this.editoraOne.getEmail(), email);
    }
    
    @Test
    public void shouldFindAllEmails() {
        List<String> emails = this.editoraDao.findAllEmails();
        
        Assert.assertNotNull(emails);
        Assert.assertFalse(emails.isEmpty());
        Assert.assertEquals(2, emails.size());
    }
    
    @Test
    public void shouldFindCidadeAndEmailById() {
        List<String> cidadeAndEmail = this.editoraDao.findCidadeAndEmailById(this.editoraOne.getId());
        
        Assert.assertNotNull(cidadeAndEmail);
        Assert.assertFalse(cidadeAndEmail.isEmpty());
        for (String string : cidadeAndEmail) {
            Assert.assertTrue(this.editoraOne.getCidade().equals(string) || this.editoraOne.getEmail().equals(string));
        }
    }
    
    @Test
    public void shouldFindEditoraWithCidadeAndEmailById() {
        Editora editoraRecuperada = this.editoraDao.findEditoraWithCidadeAndEmailById(this.editoraOne.getId());
        
        Assert.assertNotNull(editoraRecuperada);
        Assert.assertNotNull(editoraRecuperada.getId());
        Assert.assertEquals(this.editoraOne.getCidade(), editoraRecuperada.getCidade());
        Assert.assertEquals(this.editoraOne.getEmail(), editoraRecuperada.getEmail());
    }
    
    @Test
    public void shouldFindCidadesAndEmails() {
        List<Editora> editoras = this.editoraDao.findCidadesAndEmails();
        
        Assert.assertNotNull(editoras);
        Assert.assertFalse(editoras.isEmpty());
        Assert.assertEquals(2, editoras.size());
        for (Editora editora : editoras) {
            Assert.assertTrue(this.editoraOne.getCidade().equals(editora.getCidade()) || this.editoraTwo.getCidade().equals(editora.getCidade()));
            Assert.assertTrue(this.editoraOne.getEmail().equals(editora.getEmail()) || this.editoraTwo.getEmail().equals(editora.getEmail()));
        }
    }
    
    @Test
    public void shouldUpdateEditora() {
        Editora editoraToBeUpdated = new Editora("Editora Minas Ltda.", "Minas Gerais", "contato@ed-minas.com");
        editoraToBeUpdated.setId(this.editoraOne.getId());
        
        int rowsUpdated = this.editoraDao.update(editoraToBeUpdated);
        Assert.assertEquals(1, rowsUpdated);
        
        Editora editoraUpdated = this.editoraDao.findById(this.editoraOne.getId());
        Assert.assertEquals(editoraUpdated.getId(), this.editoraOne.getId());
        Assert.assertNotEquals(this.editoraOne.getRazaoSocial(), editoraUpdated.getRazaoSocial());
        Assert.assertNotEquals(this.editoraOne.getCidade(), editoraUpdated.getCidade());
        Assert.assertNotEquals(this.editoraOne.getEmail(), editoraUpdated.getEmail());
    }
    
    @Test
    public void shouldRemoveEditora() {
        int rowsDeleted = this.editoraDao.remove(this.editoraOne);
        Assert.assertEquals(1, rowsDeleted);
    }
    
    @Test
    public void shouldFindEditoraWithAutores() {
        List<Autor> autores = Arrays.asList(new Autor("Aline da Silva", "alines@email.com", this.editoraOne),
                new Autor("João da Silva", "joaos@email.com", this.editoraOne),
                new Autor("Alison Rodrigues", "alisonr@email.com", this.editoraOne),
                new Autor("Maria da Silva", "marias@email.com", this.editoraOne),
                new Autor("Roberto da Silva", "robertos@email.com", this.editoraOne),
                new Autor("Rafael da Silva", "rafaels@email.com", this.editoraOne),
                new Autor("Mario da Silva", "marios@email.com", this.editoraOne),
                new Autor("Ricardo da Silva", "ricardos@email.com", this.editoraOne),
                new Autor("José da Silva", "joses@email.com", this.editoraOne),
                new Autor("Pedro da Silva", "pedros@email.com", this.editoraOne)
        );
        for (Autor autor : autores) {
            this.autorDao.save(autor);
        }
        
        for(int i = 0; i < (autores.size() / 2); i++) {
            Editora editoraWithAutores = this.editoraDao.findEditoraWithAutores(this.editoraOne.getId(), i, 2);
            Assert.assertNotNull(editoraWithAutores);
            Assert.assertNotNull(editoraWithAutores.getId());
            Assert.assertNotNull(editoraWithAutores.getAutores());
            Assert.assertFalse(editoraWithAutores.getAutores().isEmpty());
            Assert.assertEquals(2, editoraWithAutores.getAutores().size());
            
            for (Autor autor : editoraWithAutores.getAutores()) {
                Assert.assertNotNull(autor.getId());
                Assert.assertNotNull(autor.getEditora());
            }
        }
    }
    
    @Test
    public void shouldInsertBatch() {
        List<Editora> editoras = Arrays.asList(new Editora("Editora Minas Ltda.", "Minas Gerais", "contato@ed-minas.com"),
                      new Editora("Editora São Paulo Ltda.", "São Paulo", "contato@ed-sp.com"));
        
        this.editoraDao.insertBatch(editoras);
        
        List<Editora> editorasOne = this.editoraDao.findByRazaoSocial("Editora Minas Ltda.");
        List<Editora> editorasTwo = this.editoraDao.findByRazaoSocial("Editora São Paulo Ltda.");
        
        Assert.assertNotNull(editorasOne);
        Assert.assertNotNull(editorasTwo);
        Assert.assertFalse(editorasOne.isEmpty());
        Assert.assertFalse(editorasTwo.isEmpty());
        
        Editora editoraOne = editorasOne.get(0);
        Editora editoraTwo = editorasTwo.get(0);
        
        Assert.assertNotNull(editoraOne);
        Assert.assertNotNull(editoraTwo);
        Assert.assertNotNull(editoraOne.getId());
        Assert.assertNotNull(editoraTwo.getId());
    }
    
    @Test
    public void shouldSaveBatch() {
        List<Editora> editoras = Arrays.asList(new Editora("Editora Minas Ltda.", "Minas Gerais", "contato@ed-minas.com"),
                new Editora("Editora São Paulo Ltda.", "São Paulo", "contato@ed-sp.com"));
        
        this.editoraDao.saveBatch(editoras);
        
        List<Editora> editorasOne = this.editoraDao.findByRazaoSocial("Editora Minas Ltda.");
        List<Editora> editorasTwo = this.editoraDao.findByRazaoSocial("Editora São Paulo Ltda.");
        
        Assert.assertNotNull(editorasOne);
        Assert.assertNotNull(editorasTwo);
        Assert.assertFalse(editorasOne.isEmpty());
        Assert.assertFalse(editorasTwo.isEmpty());
        
        Editora editoraOne = editorasOne.get(0);
        Editora editoraTwo = editorasTwo.get(0);
        
        Assert.assertNotNull(editoraOne);
        Assert.assertNotNull(editoraTwo);
        Assert.assertNotNull(editoraOne.getId());
        Assert.assertNotNull(editoraTwo.getId());
    }
}