/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PK_Repositorios;

import PK_Modelos.ObjetoLibro;
import PK_Utilidades.ConeccionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author admin
 */
public class CRUD {
    private final String SQL_INSERT = "insert into libros(Titulo, autor, num_pag,editorial, ISBN, idEscrito) values(?,?,?,?,?,?);";
    
     public int insertarDatos(ObjetoLibro libro){
     int rows= 0;
     Connection conn = null;
     PreparedStatement stmt = null;
     try{
    conn = ConeccionBD.getConexion();
    stmt = conn.prepareStatement(SQL_INSERT);
    int index = 1;
    System.out.println(super.toString());
    
    stmt.setString(index++, libro.titulo);
    stmt.setString(index++, libro.autor);
    stmt.setInt(index++, libro.paginas) ;
    stmt.setString(index++, libro.edit);
    stmt.setString(index, libro.code);
    stmt.setInt(index++,libro.tipo);
    
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
