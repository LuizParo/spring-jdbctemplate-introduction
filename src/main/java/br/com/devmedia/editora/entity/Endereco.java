package br.com.devmedia.editora.entity;

public class Endereco {
    private Integer id;
    private String logradouro;
    private Integer numero;
    private String cidade;
    private Integer idAutor;
    
    public Endereco() {
        // default constructor
    }

    public Endereco(String logradouro, Integer numero, String cidade, Integer idAutor) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.cidade = cidade;
        this.idAutor = idAutor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
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
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Endereco))
            return false;
        Endereco other = (Endereco) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Endereco [id=" + id + ", logradouro=" + logradouro + ", numero=" + numero + ", cidade=" + cidade
                + ", idAutor=" + idAutor + "]";
    }
}