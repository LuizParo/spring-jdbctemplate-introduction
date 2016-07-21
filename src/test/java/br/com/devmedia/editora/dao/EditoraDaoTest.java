package br.com.devmedia.editora.dao;

import java.util.List;

import org.junit.Assert;
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

    @Autowired
    private EditoraDao editoraDao;
    
    @Test
    public void shouldPersistEditora() {
        Editora editora = new Editora("Editora Sul Ltda.", "Porto Alegre", "contato@ed-sul.com");
        int updatedRows = this.editoraDao.insert(editora);
        
        Assert.assertEquals(1, updatedRows);
    }
    
    @Test
    public void shouldPersistEditoraGeneratingAnId() {
        Editora editora = new Editora("Editora Sul Ltda.", "Porto Alegre", "contato@ed-sul.com");
        int generatedId = this.editoraDao.save(editora);
        
        Assert.assertTrue(generatedId > 0);
        Assert.assertNotNull(editora.getId());
        Assert.assertEquals(Integer.valueOf(generatedId), editora.getId());
    }
    
    @Test
    public void shouldFindEditoraById() {
        Editora editora = new Editora("Editora Sul Ltda.", "Porto Alegre", "contato@ed-sul.com");
        this.editoraDao.save(editora);
        
        Editora editoraRecuperada = this.editoraDao.findById(editora.getId());
        
        Assert.assertNotNull(editoraRecuperada);
        Assert.assertEquals(editora.getId(), editoraRecuperada.getId());
    }
    
    @Test
    public void shouldFindEditoraByCity() {
        Editora editoraOne = new Editora("Editora Sul Ltda.", "Porto Alegre", "contato@ed-sul.com");
        Editora editoraTwo = new Editora("Editora Copacabana Ltda.", "Rio de Janeiro", "contato@ed-copacabana.com");
        
        this.editoraDao.save(editoraOne);
        this.editoraDao.save(editoraTwo);
        
        List<Editora> editoras = this.editoraDao.findByCidade(editoraOne.getCidade(), editoraTwo.getCidade());
        
        Assert.assertNotNull(editoras);
        Assert.assertFalse(editoras.isEmpty());
        Assert.assertEquals(2, editoras.size());
        for (Editora editora : editoras) {
            Assert.assertTrue(editora.getCidade().equals("Porto Alegre") || editora.getCidade().equals("Rio de Janeiro"));
        }
    }
    
    @Test
    public void shouldFindEditoraByRazaoSocial() {
        this.editoraDao.save(new Editora("Editora Sul Ltda.", "Porto Alegre", "contato@ed-sul.com"));
        this.editoraDao.save(new Editora("Editora Copacabana Ltda.", "Rio de Janeiro", "contato@ed-copacabana.com"));
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
        this.editoraDao.save(new Editora("Editora Sul Ltda.", "Porto Alegre", "contato@ed-sul.com"));
        this.editoraDao.save(new Editora("Editora Copacabana Ltda.", "Rio de Janeiro", "contato@ed-copacabana.com"));
        List<Editora> editoras = this.editoraDao.findAll();
        
        Assert.assertNotNull(editoras);
        Assert.assertFalse(editoras.isEmpty());
        Assert.assertEquals(2, editoras.size());
    }
    
    @Test
    public void shouldCountAllEditoras() {
        this.editoraDao.save(new Editora("Editora Sul Ltda.", "Porto Alegre", "contato@ed-sul.com"));
        this.editoraDao.save(new Editora("Editora Copacabana Ltda.", "Rio de Janeiro", "contato@ed-copacabana.com"));
        int count = this.editoraDao.count();
        
        Assert.assertEquals(2, count);
    }
    
    @Test
    public void shouldfindEmailById() {
        Editora editora = new Editora("Editora Sul Ltda.", "Porto Alegre", "contato@ed-sul.com");
        
        this.editoraDao.save(editora);
        String email = this.editoraDao.findEmailById(editora.getId());
        
        Assert.assertNotNull(email);
        Assert.assertEquals(editora.getEmail(), email);
    }
    
    @Test
    public void shouldFindAllEmails() {
        this.editoraDao.save(new Editora("Editora Sul Ltda.", "Porto Alegre", "contato@ed-sul.com"));
        this.editoraDao.save(new Editora("Editora Copacabana Ltda.", "Rio de Janeiro", "contato@ed-copacabana.com"));
        List<String> emails = this.editoraDao.findAllEmails();
        
        Assert.assertNotNull(emails);
        Assert.assertFalse(emails.isEmpty());
        Assert.assertEquals(2, emails.size());
    }
    
    @Test
    public void shouldFindCidadeAndEmailById() {
        Editora editora = new Editora("Editora Sul Ltda.", "Porto Alegre", "contato@ed-sul.com");
        this.editoraDao.save(editora);
        List<String> cidadeAndEmail = this.editoraDao.findCidadeAndEmailById(editora.getId());
        
        Assert.assertNotNull(cidadeAndEmail);
        Assert.assertFalse(cidadeAndEmail.isEmpty());
        for (String string : cidadeAndEmail) {
            Assert.assertTrue(editora.getCidade().equals(string) || editora.getEmail().equals(string));
        }
    }
    
    @Test
    public void shouldFindEditoraWithCidadeAndEmailById() {
        Editora editora = new Editora("Editora Sul Ltda.", "Porto Alegre", "contato@ed-sul.com");
        this.editoraDao.save(editora);
        
        Editora editoraRecuperada = this.editoraDao.findEditoraWithCidadeAndEmailById(editora.getId());
        Assert.assertNotNull(editoraRecuperada);
        Assert.assertNotNull(editoraRecuperada.getId());
        Assert.assertEquals(editora.getCidade(), editoraRecuperada.getCidade());
        Assert.assertEquals(editora.getEmail(), editoraRecuperada.getEmail());
    }
    
    @Test
    public void shouldFindCidadesAndEmails() {
        Editora editoraOne = new Editora("Editora Sul Ltda.", "Porto Alegre", "contato@ed-sul.com");
        Editora editoraTwo = new Editora("Editora Copacabana Ltda.", "Rio de Janeiro", "contato@ed-copacabana.com");
        this.editoraDao.save(editoraOne);
        this.editoraDao.save(editoraTwo);
        
        List<Editora> editoras = this.editoraDao.findCidadesAndEmails();
        Assert.assertNotNull(editoras);
        Assert.assertFalse(editoras.isEmpty());
        Assert.assertEquals(2, editoras.size());
        for (Editora editora : editoras) {
            Assert.assertTrue(editoraOne.getCidade().equals(editora.getCidade()) || editoraTwo.getCidade().equals(editora.getCidade()));
            Assert.assertTrue(editoraOne.getEmail().equals(editora.getEmail()) || editoraTwo.getEmail().equals(editora.getEmail()));
        }
    }
}