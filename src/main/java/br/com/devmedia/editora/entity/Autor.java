package br.com.devmedia.editora.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Autor {
    private Integer id;
    private String nome;
    private String email;
    private Editora editora;
    private List<Livro> livros = new ArrayList<>();
    
    public Autor() {
        // default constructor
    }

    public Autor(String nome, String email, Editora editora) {
        this.nome = nome;
        this.email = email;
        this.editora = Objects.requireNonNull(editora, "Editora must not be null!");
    }

    public Autor(Integer id, String nome, String email) {
        this(nome, email, null);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = Objects.requireNonNull(editora, "Editora must not be null!");
    }
    
    public List<Livro> getLivros() {
        return livros;
    }
    
    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Autor))
            return false;
        Autor other = (Autor) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Autor [id=" + id + ", nome=" + nome + ", email=" + email + ", editora=" + editora + "]";
    }
}