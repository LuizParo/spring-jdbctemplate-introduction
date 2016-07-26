package br.com.devmedia.editora.entity;

public class LivroAutor {
    private Integer id;
    private Integer idLivro;
    private Integer idAutor;

    public LivroAutor() {
        // default constructor
    }

    public LivroAutor(Integer idLivro, Integer idAutor) {
        this.idLivro = idLivro;
        this.idAutor = idAutor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(Integer idLivro) {
        this.idLivro = idLivro;
    }

    public Integer getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Integer idAutor) {
        this.idAutor = idAutor;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idAutor == null) ? 0 : idAutor.hashCode());
        result = prime * result + ((idLivro == null) ? 0 : idLivro.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof LivroAutor))
            return false;
        LivroAutor other = (LivroAutor) obj;
        if (idAutor == null) {
            if (other.idAutor != null)
                return false;
        } else if (!idAutor.equals(other.idAutor))
            return false;
        if (idLivro == null) {
            if (other.idLivro != null)
                return false;
        } else if (!idLivro.equals(other.idLivro))
            return false;
        return true;
    }
}