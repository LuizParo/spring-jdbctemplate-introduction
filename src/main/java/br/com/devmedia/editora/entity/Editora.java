package br.com.devmedia.editora.entity;

public class Editora {
    private Integer id;
    private String razaoSocial;
    private String cidade;
    private String email;

    public Editora() {
        // default constructor
    }

    public Editora(String razaoSocial, String cidade, String email) {
        this.razaoSocial = razaoSocial;
        this.cidade = cidade;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        if (!(obj instanceof Editora))
            return false;
        Editora other = (Editora) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Editora [id=" + id + ", razaoSocial=" + razaoSocial + ", cidade=" + cidade + ", email=" + email + "]";
    }
}