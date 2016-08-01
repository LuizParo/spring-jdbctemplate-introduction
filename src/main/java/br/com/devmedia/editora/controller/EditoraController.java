package br.com.devmedia.editora.controller;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.devmedia.editora.dao.EditoraDao;
import br.com.devmedia.editora.entity.Editora;

@RestController
public class EditoraController implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private EditoraDao editoraDao;

    @RequestMapping("/")
    public String hello() {
        return "Hello World!";
    }
    
    @RequestMapping("/editoras")
    public List<Editora> getEditoras() {
        return this.editoraDao.findAll();
    }
    
    @RequestMapping("/editora/{id}")
    public Editora getEditora(@PathVariable("id") Integer id) {
        return this.editoraDao.findById(id);
    }
    
    @RequestMapping("/editora/{id}/{page}")
    public Editora getEditoraWithAutor(@PathVariable("id") Integer id, @PathVariable("page") Integer page) {
        return this.editoraDao.findEditoraWithAutores(id, page, 2);
    }
}