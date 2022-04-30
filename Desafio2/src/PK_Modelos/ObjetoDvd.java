/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PK_Modelos;

/**
 *
 * @author admin
 */
public class ObjetoDvd {
    public String titulo, director,genero,duracion;
    public int tipo;

    @Override
    public String toString() {
        return "ObjetoDvd{" + "titulo=" + titulo + ", director=" + director + ", genero=" + genero + ", duracion=" + duracion + ", tipo=" + tipo + '}';
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
