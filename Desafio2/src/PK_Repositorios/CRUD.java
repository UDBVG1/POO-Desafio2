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
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author admin
 */
public class CRUD {

    private final String SQL_INSERTLIRBOS = "insert into libros(Titulo, autor, num_pag,editorial, ISBN, idEscrito) values(?,?,?,?,?,?);";
    private final String SQL_SELECTLIBROS = "SELECT titulo,autor,num_pag,editorial,isbn from libros where titulo like ? and autor like ? and num_pag like ? and editorial like ? and isbn like ?;";
    private final String SQL_SELECTDSIPONIBILIDAD = "SELECT 	m.codigo,case when m.idlibros is not null then l.titulo\n" +
                                                    "when m.idrevistas is not null then r.titulo\n" +
                                                    "when m.idm_cd is not null then mc.titulo\n" +
                                                    "when m.idm_dvd is not null then md.titulo\n" +
                                                    "else null end AS titulo,cantidad_total,cantidad_disponible\n" +
                                                    " FROM material m\n" +
                                                    "LEFT join libros l on m.idlibros=l.idlibros\n" +
                                                    "LEFT join revista r on m.idrevistas=r.idrevistas\n" +
                                                    "LEFT join m_cd mc on m.idm_cd=mc.idm_cd\n" +
                                                    "LEFT join m_dvd md on m.idm_dvd=md.idm_dvd;";
    
    public int insertarDatos(ObjetoLibro libro) {
        int rows = 0;
        int idLibro=0;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = ConeccionBD.getConexion();
            stmt = conn.prepareStatement(SQL_INSERTLIRBOS,Statement.RETURN_GENERATED_KEYS);
            int index = 1;
            System.out.println(libro.toString());

            stmt.setString(index++, libro.titulo);
            stmt.setString(index++, libro.autor);
            stmt.setInt(index++, libro.paginas);
            stmt.setString(index++, libro.edit);
            stmt.setString(index++, libro.code);
            stmt.setInt(index, libro.tipo);

            rows = stmt.executeUpdate();
            System.out.println(SQL_INSERTLIRBOS);

            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Registro exitoso" + "/n" + "Registros afectados" + rows, "Ingresado", JOptionPane.INFORMATION_MESSAGE);
            }
            ResultSet getIdLibro = stmt.getGeneratedKeys();
            if(getIdLibro.next()){ 
                idLibro = getIdLibro.getInt(1);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar datos", "Alta", JOptionPane.INFORMATION_MESSAGE);
        } finally {
            ConeccionBD.closeConnection(conn);
            ConeccionBD.closeStatement(stmt);
        }
        
        return idLibro;
    }

    public DefaultTableModel select(ObjetoLibro libro){
        DefaultTableModel dtm = new DefaultTableModel();
//        String titulo,autor;
//        int paginas;
//        String edit;
//        int tipo;
//        String code;
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
       // ObjetoLibro materialLb;
        try {
            conn = ConeccionBD.getConexion();
            stmt = conn.prepareStatement(SQL_SELECTLIBROS);
            int index = 1;
            stmt.setString(index++, "%"+libro.titulo+"%");
            stmt.setString(index++, "%"+libro.autor+"%");
            stmt.setString(index++, "%"+libro.paginas+"%");
            stmt.setString(index++, "%"+libro.edit+"%");
            stmt.setString(index, "%"+libro.code+"%");
            System.out.println("Ejecutando query:" + SQL_SELECTLIBROS);
            rs = stmt.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            int numberOfColumns = meta.getColumnCount();
            for (int i = 1; i<= numberOfColumns; i++) {
            dtm.addColumn(meta.getColumnLabel(i));
            }
            while (rs.next()) {
                    
                    Object[] fila = new Object[numberOfColumns];
                    for (int i = 0; i<numberOfColumns; i++) {
                    fila[i]=rs.getObject(i+1);
                    }
                    dtm.addRow(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConeccionBD.closeStatement(stmt);
            ConeccionBD.closeConnection(conn);
            ConeccionBD.closeResulset(rs);
        }
        return dtm;

    }
    
    public DefaultTableModel selectall(){
        DefaultTableModel dtm = new DefaultTableModel();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
       
        try {
            conn = ConeccionBD.getConexion();
            stmt = conn.prepareStatement(SQL_SELECTDSIPONIBILIDAD);
            rs = stmt.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            int numberOfColumns = meta.getColumnCount();
            for (int i = 1; i<= numberOfColumns; i++) {
            dtm.addColumn(meta.getColumnLabel(i));
            }
            while (rs.next()) {
                    
                    Object[] fila = new Object[numberOfColumns];
                    for (int i = 0; i<numberOfColumns; i++) {
                    fila[i]=rs.getObject(i+1);
                    }
                    dtm.addRow(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConeccionBD.closeStatement(stmt);
            ConeccionBD.closeConnection(conn);
            ConeccionBD.closeResulset(rs);
        }
        return dtm;

    }
}
