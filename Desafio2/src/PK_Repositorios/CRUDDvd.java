/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PK_Repositorios;

import PK_Modelos.ObjetoDvd;
import PK_Modelos.ObjetoRevista;
import PK_Utilidades.ConeccionBD;
import static java.lang.Math.floor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author admin
 */
public class CRUDDvd {
    private final String SQL_INSERTLIRBOS = "insert into m_dvd (titulo, director,duracion,genero,idAudioVisual) values(?,?,?,?,?);";
    private final String SQL_SELECTLIBROS = "SELECT titulo, director,duracion,genero from m_dvd where titulo like ? and director like ? and duracion like ? and genero like ?;";

    private final String SQL_SELECTRN = "select count(*) from material where codigo = ?;"; //buscar si no esta repetido el id
    private final String SQL_INSERTM = "insert into material (codigo,cantidad_total,cantidad_disponible,idm_dvd) values(?,?,?,?);";//insertar a la tabla matrial para DVD
    
    public int insertarDatos(ObjetoDvd DVD) {
        int rows = 0;
        int iddvd=0;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = ConeccionBD.getConexion();
            stmt = conn.prepareStatement(SQL_INSERTLIRBOS,Statement.RETURN_GENERATED_KEYS);
            int index = 1;
            System.out.println(DVD.toString());

            stmt.setString(index++, DVD.titulo);
            stmt.setString(index++, DVD.director);
            stmt.setString(index++, DVD.duracion);
            stmt.setString(index++, DVD.genero);
            stmt.setInt(index, DVD.tipo);

            rows = stmt.executeUpdate();
            System.out.println(SQL_INSERTLIRBOS);

            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Registro exitoso" + "/n" + "Registros afectados" + rows, "Ingresado", JOptionPane.INFORMATION_MESSAGE);
            }
            ResultSet getIddvd = stmt.getGeneratedKeys();
            if(getIddvd.next()){ 
                iddvd = getIddvd.getInt(1);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar datos", "Alta", JOptionPane.INFORMATION_MESSAGE);
        } finally {
            ConeccionBD.closeConnection(conn);
            ConeccionBD.closeStatement(stmt);
        }
        
        return iddvd;
    }
    
    public String NumRandom(){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String cadena = "";
        try{

            while(rs == null){
            conn = ConeccionBD.getConexion();
            stmt = conn.prepareStatement(SQL_SELECTRN); 
            int fiveDigits = (int) floor(10000 + Math.random() * 90000);
            cadena = "DVD" + String.valueOf(fiveDigits);
            stmt.setString(1, cadena);
            rs = stmt.executeQuery();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar el id", "extraer", JOptionPane.INFORMATION_MESSAGE);
        } finally {
            ConeccionBD.closeConnection(conn);
            ConeccionBD.closeStatement(stmt);
        }
        return cadena;
    }
    
    public int insertmaterialdisponible(String IDcantd,int CantD,int CantT,int IDDVD){//++++++++
        //SQL_INSERTM 4 incongnitas
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = ConeccionBD.getConexion();
            stmt = conn.prepareStatement(SQL_INSERTM);
            int index = 1;
            stmt.setString(index++, IDcantd);
            stmt.setInt(index++, CantD);
            stmt.setInt(index++, CantD);
            stmt.setInt(index, IDDVD);

        rows = stmt.executeUpdate();
        
        if (rows > 0) {
                System.out.println("Registro exitoso de material" + "/n" + "Registros afectados" + rows);
            }
        else{
            System.out.println("Registro NO exitoso del material!!");
        }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConeccionBD.closeStatement(stmt);
            ConeccionBD.closeConnection(conn);

        }
        return rows;
    }
    
    public DefaultTableModel select(ObjetoDvd DVD){
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
            stmt.setString(index++, "'%"+DVD.titulo+"%'");
            stmt.setString(index++, "%"+DVD.director+"%");
            stmt.setString(index++, "%"+DVD.duracion+"%");
            stmt.setString(index, "%"+DVD.genero+"%");

     
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
        
}