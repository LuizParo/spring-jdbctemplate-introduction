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
import br.com.devmedia.editora.entity.Endereco;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppEditora.class)
public class EnderecoDaoTest {
    private Editora editoraOne;
    private Autor authorOne;
    
    @Autowired
    private EnderecoDao addressDao;
    
    @Autowired
    private AutorDao authorDao;
    
    @Autowired
    private EditoraDao editoraDao;

    @Before
    public void setUp() {
        this.editoraOne = new Editora("Editora Sul Ltda.", "Porto Alegre", "contato@ed-sul.com");
        this.editoraDao.save(this.editoraOne);
        
        this.authorOne = new Autor("Aline da Silva", "alines@email.com", this.editoraOne);
        this.authorDao.save(this.authorOne);
    }

    @Test
    public void shouldSaveAddress() {
        Endereco address = new Endereco("Rua Joaquina", 123, "Porto Alegre", this.authorOne.getId());
        this.addressDao.save(address);
        
        Assert.assertNotNull(address.getId());
    }
    
    @Test
    public void shouldFindAllAddresses() {
        Endereco address = new Endereco("Rua Joaquina", 123, "Porto Alegre", this.authorOne.getId());
        this.addressDao.save(address);
        
        List<Endereco> addresses = this.addressDao.findAll();
        Assert.assertNotNull(addresses);
        Assert.assertFalse(addresses.isEmpty());
        for (Endereco recoveredAddress : addresses) {
            Assert.assertEquals(address.getId(), recoveredAddress.getId());
        }
    }
}