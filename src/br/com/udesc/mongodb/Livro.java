package br.com.udesc.mongodb;

public class Livro {

    private int id;
    private String titulo;
    private String autor;
    private String editora;
    private int paginas;
    private String disponivel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public int getPaginas() {
        return paginas;
    }

    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }

    public String getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(String disponivel) {
        this.disponivel = disponivel;
    }

    @Override
    public String toString() {
        return "Código: " + getId()
                + "\nTítulo: " + getTitulo()
                + "\nAutor: " + getAutor()
                + "\nEditora: " + getEditora()
                + "\nNº de Pág.: " + getPaginas()
                + "\nDisponibilidade: " + getDisponivel();
    }

}
