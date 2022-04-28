/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PK_Repositorios;


import PK_Utilidades.ConeccionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class Modelar {
    private final String SQL_INSERT = "INSERT INTO libros(Titulo,autor,num_pag,editorial,Escrito_idEscrito,Escrito_Material_codigo) values(?,?,?,?,?,?);";
//    private PreparedStatement PS;
//    private final ConeccionBD CN;
//    
//    public Modelar() {
//      this.PS =null;
//      this.CN = new ConeccionBD();// constructor para mandar a llamar una instancia especifica
//    }
    //hay que buscar por que los datos antes de pedirlos los debemos de conver a int min 20.13
    public int insertarDatos(String titulo, String autor, int paginas, String edit,int tipo, int code){
     int rows= 0;
     Connection conn = null;
     PreparedStatement stmt = null;
     try{
    conn = ConeccionBD.getConexion();
    stmt = conn.prepareStatement(SQL_INSERT);
    int index = 1;
    System.out.println(titulo+autor+  paginas+  edit+ tipo+code);
    //PS = conn.prepareStatement(SQL_INSERT);
    System.out.println(SQL_INSERT);
    stmt.setString(index++, titulo);
    System.out.println(SQL_INSERT); 
    stmt.setString(index++, autor);
    System.out.println(SQL_INSERT);
    stmt.setInt(index++, paginas) ;
    System.out.println(SQL_INSERT);
    stmt.setString(index++, edit);
    System.out.println(SQL_INSERT);
    stmt.setInt(index++,tipo);
    System.out.println(SQL_INSERT);
    stmt.setInt(index, code);
    System.out.println(SQL_INSERT);

    rows = stmt.executeUpdate();
    
    if (rows>0) {
    JOptionPane.showMessageDialog(null, "Registro exitoso"+"/n"+"Registros afectados"+rows, "Ingresado", JOptionPane.INFORMATION_MESSAGE);

    }
    }catch (SQLException e){
    JOptionPane.showMessageDialog(null, "Error al guardar datos", "Alta", JOptionPane.INFORMATION_MESSAGE);
    }finally{
         ConeccionBD.closeConnection(conn);
         ConeccionBD.closeStatement(stmt);
     }
     return rows;
}
}