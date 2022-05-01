/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PK_Repositorios;

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
public class CRUDRev {
    private int id=0;
    private int cantDq=0;
    int cantTD=0;

    private final String SQL_INSERTREV = "insert into revista (titulo, editorial,periodicidad,publicacion,idEscrito) values(?,?,?,?,?);";
    private final String SQL_SELECTREV = "SELECT titulo,editorial,periodicidad,publicacion from revista where titulo like ? or editorial like ? or periodicidad like ?;";
    private final String SQL_SELECTRN = "select count(*) from material where codigo = ?;"; //buscar si no esta repetido el id
    private final String SQL_INSERTM = "insert into material (codigo,cantidad_total,cantidad_disponible,idrevistas) values(?,?,?,?);";//insertar a la tabla matrial para revista
    private final String SQL_SELECTID = "SELECT titulo,editorial,periodicidad,publicacion,l.idrevistas,m.cantidad_total,m.cantidad_disponible from revista l\n" +
                                        "inner join material m ON l.idrevistas =m.idrevistas\n" +
                                        "where codigo= ?";
    private final String SQL_UPDATEREVISTA = "update revista set titulo =?, editorial =?, periodicidad =?, publicacion =? where idrevistas =?;";
    private final String SQL_UPDATEMATERIAL = "update material  set cantidad_total =?, cantidad_disponible =? where codigo = ?;";
    
    public int insertarDatos(ObjetoRevista revista) {
        int rows = 0;
        int idRevista=0;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = ConeccionBD.getConexion();
            stmt = conn.prepareStatement(SQL_INSERTREV,Statement.RETURN_GENERATED_KEYS);
            int index = 1;
            System.out.println(revista.toString());

            stmt.setString(index++, revista.titulo);
            stmt.setString(index++, revista.Editorial);
            stmt.setString(index++, revista.Periodicidad);
            stmt.setInt(index++, revista.Fecha_publ);
            stmt.setInt(index, revista.tipo);

            rows = stmt.executeUpdate();
            System.out.println(SQL_INSERTREV);

            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Registro exitoso" + "/n" + "Registros afectados" + rows, "Ingresado", JOptionPane.INFORMATION_MESSAGE);
            }
            ResultSet getidrevistas = stmt.getGeneratedKeys();
            if(getidrevistas.next()){ 
                idRevista = getidrevistas.getInt(1);
                        //getIdLibro.getInt(1);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar datos", "Alta", JOptionPane.INFORMATION_MESSAGE);
        } finally {
            ConeccionBD.closeConnection(conn);
            ConeccionBD.closeStatement(stmt);
        }
        
        return idRevista;
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
            cadena = "REV" + String.valueOf(fiveDigits);
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
    
    public int insertmaterialdisponible(String IDcantd,int CantD,int CantT,int IDrevista){//++++++++
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
            stmt.setInt(index, IDrevista);

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
    
    public DefaultTableModel select(ObjetoRevista revista){
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
            stmt = conn.prepareStatement(SQL_SELECTREV);
            int index = 1;
            stmt.setString(index++, revista.titulo);
            stmt.setString(index++, revista.Editorial);
            stmt.setString(index++, revista.Periodicidad);
//            stmt.setString(index, "%"+revista.Fecha_publ+"%");
     
            System.out.println("Ejecutando query:" + SQL_SELECTREV);
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
   
             
    public ObjetoRevista selectId(String codigo) {
     ObjetoRevista revistaMod = new ObjetoRevista();
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

                 revistaMod.setTitulo(rs.getObject(1).toString());
                 revistaMod.setEditorial(rs.getObject(2).toString());
                 revistaMod.setPeriodicidad(rs.getObject(3).toString());
                 revistaMod.setFecha_publ(Integer.parseInt(rs.getObject(4).toString()));
                 id=(Integer.parseInt(rs.getObject(5).toString()));
                 cantTD=(Integer.parseInt(rs.getObject(6).toString()));
                 cantDq=(Integer.parseInt(rs.getObject(7).toString()));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        ConeccionBD.closeStatement(stmt);
        ConeccionBD.closeConnection(conn);
        ConeccionBD.closeResulset(rs);
    }
        return revistaMod;
    }
    
    public int selectCant(){
        return cantTD;
    }
       
      public int updateDatos(ObjetoRevista revista) {
        int rows = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = ConeccionBD.getConexion();
            stmt = conn.prepareStatement(SQL_UPDATEREVISTA);
            int index = 1;
            System.out.println(revista.toString());
            
             stmt.setString(index++, revista.titulo);
            stmt.setString(index++, revista.Editorial);
            stmt.setString(index++, revista.Periodicidad);
            stmt.setInt(index++, revista.Fecha_publ);
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
