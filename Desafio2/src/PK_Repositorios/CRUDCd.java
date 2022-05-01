/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PK_Repositorios;

import PK_Modelos.ObjetoCd;
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
public class CRUDCd {

    private int id=0;
    private int cantDq=0;
    int cantTD=0;
    private final String SQL_INSERTCDA = "insert into m_cd (titulo, artista,genero,duracion,canciones,idaudioVisual) values(?,?,?,?,?,?);";
    private final String SQL_SELECTCDA = "SELECT titulo, artista,genero,duracion,canciones from m_cd where titulo like ? or artista like ? or genero like ? or duracion like ?;";
    private final String SQL_SELECTRN = "select count(*) from material where codigo = ?;"; //buscar si no esta repetido el id
    private final String SQL_INSERTM = "insert into material (codigo,cantidad_total,cantidad_disponible,idm_cd) values(?,?,?,?);";//insertar a la tabla matrial para CD
    private final String SQL_SELECTID = "SELECT titulo,artista,genero,duracion,canciones,l.idm_cd,m.cantidad_total,m.cantidad_disponible from m_cd l\n"
                                        + "inner join material m ON l.idm_cd =m.idm_cd\n"
                                        + "where codigo= ?";
    //private final String SQL_UPDATEMCD = "update m_cd set titulo =?, artista =?, genero =? ,duracion =?, canciones =? where idm_cd =?;";
    private final String SQL_UPDATETCDs = "update m_cd set titulo =?, artista =?, genero =? ,duracion =?, canciones =? where idm_cd =?;";
    private final String SQL_UPDATEMATERIAL = "update material  set cantidad_total =?, cantidad_disponible =? where codigo = ?;";
    
    public int insertarDatos(ObjetoCd CD) {
        int rows = 0;
        int iddvd = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = ConeccionBD.getConexion();
            stmt = conn.prepareStatement(SQL_INSERTCDA, Statement.RETURN_GENERATED_KEYS);
            int index = 1;
            System.out.println(CD.toString());

            stmt.setString(index++, CD.titulo);
            stmt.setString(index++, CD.artista);
            stmt.setString(index++, CD.genero);
            stmt.setString(index++, CD.duracion);
            stmt.setInt(index++, CD.numCanciones);
            stmt.setInt(index, CD.tipo);

            rows = stmt.executeUpdate();
            System.out.println(SQL_INSERTCDA);

            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Registro exitoso" + "/n" + "Registros afectados" + rows, "Ingresado", JOptionPane.INFORMATION_MESSAGE);
            }
            ResultSet getIddvd = stmt.getGeneratedKeys();
            if (getIddvd.next()) {
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

    public String NumRandom() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String cadena = "";
        try {

            while (rs == null) {
                conn = ConeccionBD.getConexion();
                stmt = conn.prepareStatement(SQL_SELECTRN);
                int fiveDigits = (int) floor(10000 + Math.random() * 90000);
                cadena = "CDA" + String.valueOf(fiveDigits);
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

    public int insertmaterialdisponible(String IDcantd, int CantD, int CantT, int IDCD) {//++++++++
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
            stmt.setInt(index, IDCD);

            rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Registro exitoso de material" + "/n" + "Registros afectados" + rows);
            } else {
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

    public DefaultTableModel select(ObjetoCd CD) {
        DefaultTableModel dtm = new DefaultTableModel();
//        String titulo,autor;
//        int paginas;
//        String edit;
//        int tipo;
//        String code;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        // ObjetoCd materialLb;
        try {
            conn = ConeccionBD.getConexion();
            stmt = conn.prepareStatement(SQL_SELECTCDA);
            int index = 1;
            stmt.setString(index++,  CD.titulo );
            stmt.setString(index++,  CD.artista );
            stmt.setString(index++,  CD.genero );
            stmt.setString(index,  CD.duracion );

            System.out.println("Ejecutando query:" + SQL_SELECTCDA);
            rs = stmt.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            int numberOfColumns = meta.getColumnCount();
            for (int i = 1; i <= numberOfColumns; i++) {
                dtm.addColumn(meta.getColumnLabel(i));
            }
            while (rs.next()) {

                Object[] fila = new Object[numberOfColumns];
                for (int i = 0; i < numberOfColumns; i++) {
                    fila[i] = rs.getObject(i + 1);
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
            
    public ObjetoCd selectId(String codigo) {
     ObjetoCd cdMod = new ObjetoCd();
     Connection conn = null;
     PreparedStatement stmt = null;
     ResultSet rs=null;
        try {
            conn = ConeccionBD.getConexion();
            stmt = conn.prepareStatement(SQL_SELECTID);
            int index = 1;
            stmt.setString(index++, codigo);
            rs = stmt.executeQuery();
            while (rs.next()){
             
                    cdMod.setTitulo(rs.getObject(1).toString());
                    cdMod.setArtista(rs.getObject(2).toString());
                    cdMod.setGenero((rs.getObject(3).toString()));
                    cdMod.setDuracion(rs.getObject(4).toString());
                    cdMod.setNumCanciones(Integer.parseInt(rs.getObject(5).toString()));
                    id=(Integer.parseInt(rs.getObject(6).toString()));
                    cantDq=(Integer.parseInt(rs.getObject(7).toString()));
                    cantTD=(Integer.parseInt(rs.getObject(8).toString()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConeccionBD.closeStatement(stmt);
            ConeccionBD.closeConnection(conn);
            ConeccionBD.closeResulset(rs);
        }
        return cdMod;
    }  
    
        public int selectCant(){
        return cantTD;
    }
    
    public int updateDatos(ObjetoCd CD) {
        int rows = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = ConeccionBD.getConexion();
            stmt = conn.prepareStatement(SQL_UPDATETCDs);
            int index = 1;
            System.out.println(CD.toString());
            
            stmt.setString(index++, CD.titulo);
            stmt.setString(index++, CD.artista);
            stmt.setString(index++, CD.genero);
            stmt.setString(index++, CD.duracion);
            stmt.setInt(index++, CD.numCanciones);
            stmt.setInt(index, id);
            System.out.println(id);
            rows = stmt.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Registro exitoso" + "/n" + "Registros afectados" + rows, "Ingresado", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar datos", "Alta", JOptionPane.INFORMATION_MESSAGE);
        } finally {
            ConeccionBD.closeConnection(conn);
            ConeccionBD.closeStatement(stmt);
        }
        
        return rows;
    }
    
    public void updateMaterial(int cantT, String Cod){
        Connection conn = null;
        PreparedStatement stmt = null;
        if (cantT>cantTD) {
            cantDq = (cantT-cantTD)+cantDq;
        } else {
            cantDq = cantDq-(cantTD-cantT);
        }
        int rows = 0;
        try {
            conn = ConeccionBD.getConexion();
            stmt = conn.prepareStatement(SQL_UPDATEMATERIAL);
            int index = 1;
            stmt.setInt(index++, cantT);
            stmt.setInt(index++, cantDq);
            stmt.setString(index, Cod);

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
    }
}


