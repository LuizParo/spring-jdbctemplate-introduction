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
}