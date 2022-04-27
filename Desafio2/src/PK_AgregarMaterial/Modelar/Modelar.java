/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PK_AgregarMaterial.Modelar;

import PK_Conexion.ConeccionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class Modelar {
    private final String SQL_INSERT = "INSERT INTO libros(id_libros,Titulo,autor,num_pag,editorial,Escrito_idEscrito,Escrito_Material_codigo) values(?,?,?,?,?,?,?)";
    private PreparedStatement PS;
    private Connection CN;
    
    public Modelar() {
        this.PS =null;
        this.CN =null;
    }
    //hay que buscar por que los datos antes de pedirlos los debemos de conver a int min 20.13
    public int insertarDatos(int id,String titulo, String autor, int paginas, String edit,int tipo, int code){
    int res= 0;
    try{
        PS = ConeccionBD.getConexion().prepareStatement(SQL_INSERT);
        PS.setInt(1,id);
        PS.setString(2, titulo);
        PS.setString(3, autor);
        PS.setInt(4, paginas) ;
        PS.setString(5, edit);
        PS.setInt(6,tipo);
        PS.setInt(7, code);

        res = PS.executeUpdate();
        if (res>0) {
        JOptionPane.showMessageDialog(null, "Registro exitoso", "Alta", JOptionPane.INFORMATION_MESSAGE);
        }
        }catch (SQLException e){
        JOptionPane.showMessageDialog(null, "Error al guardar datos", "Alta", JOptionPane.INFORMATION_MESSAGE);
        }finally {
             ConeccionBD.closeConnection(CN);
             ConeccionBD.closeStatement(PS);
        }
    return res;
    }
}