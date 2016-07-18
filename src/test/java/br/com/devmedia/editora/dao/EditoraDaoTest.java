package br.com.devmedia.editora.dao;

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
}