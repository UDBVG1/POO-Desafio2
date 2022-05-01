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
public class ObjetoRevista {
    public int  Fecha_publ,tipo;
    public String titulo, Editorial, Periodicidad; 

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEditorial() {
        return Editorial;
    }

    public void setEditorial(String Editorial) {
        this.Editorial = Editorial;
    }

    public String getPeriodicidad() {
        return Periodicidad;
    }

    public void setPeriodicidad(String Periodicidad) {
        this.Periodicidad = Periodicidad;
    }

    public int getFecha_publ() {
        return Fecha_publ;
    }

    public void setFecha_publ(int Fecha_publ) {
        this.Fecha_publ = Fecha_publ;
    }

    @Override
    public String toString() {
        return "ObjetoRevista{" + "titulo=" + titulo + ", Editorial=" + Editorial + ", Periodicidad=" + Periodicidad + ", Fecha_publ=" + Fecha_publ + '}';
    }
    
}
