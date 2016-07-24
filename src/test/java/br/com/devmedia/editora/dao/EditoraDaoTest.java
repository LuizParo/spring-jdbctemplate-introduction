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
import br.com.devmedia.editora.entity.Editora;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppEditora.class)
public class EditoraDaoTest {
    private Editora editoraOne;
    private Editora editoraTwo;

    @Autowired
    private EditoraDao editoraDao;
    
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
}