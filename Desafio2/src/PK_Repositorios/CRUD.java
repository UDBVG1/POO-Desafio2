package PK_Repositorios;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import PK_Modelos.ObjetoLibro;
import PK_Utilidades.ConeccionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author admin
 */
public class CRUD {
    
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
    private final String SQL_DELETE_LIBROS="DELETE a,b FROM material a LEFT JOIN libros b ON b.id_libros = a.idlibros WHERE a.idlibros = ? ;";
    private final String SQL_DELETE_REVISTA="DELETE a,b FROM material a LEFT JOIN REVISTA b ON b.idrevistas = a.idrevistas WHERE a.idrevistas = ? ;";
    private final String SQL_DELETE_CD="DELETE a,b FROM material a LEFT JOIN m_cd b ON b.idm_cd = a.idm_cd WHERE a.idm_cd = ? ;";
    private final String SQL_DELETE_DVD="DELETE a,b FROM material a LEFT JOIN m_dvd b ON b.idm_dvd = a.idm_dvd WHERE a.idm_dvd = ? ;";
    private final String SQL_SELECTmaterial="SELECT m.codigo,m.idlibros,m.idrevistas,m.idm_cd,m.idm_dvd, case when m.idlibros is not null then l.titulo " +
                                            "WHEN m.idrevistas is not null THEN r.titulo WHEN m.idm_cd is not null THEN " +
                                            "mc.titulo when m.idm_dvd is not null then md.titulo else null end AS titulo,cantidad_total,cantidad_disponible "+
                                            "FROM material m LEFT join libros l on m.idlibros=l.idlibros LEFT join revista r on m.idrevistas=r.idrevistas "+
                                            "LEFT join m_cd mc on m.idm_cd=mc.idm_cd LEFT join m_dvd md on m.idm_dvd=md.idm_dvd " +
                                            "WHERE m.codigo= ?;";
    private final String SQL_SELECTSOCIO = "select idsocio from socio where usuario = ?;";//BUSCAR SI EL USUARIO EXISTE
    private final String SQL_INSERTPRESTAMO = "insert into prestamos (fechaprestamo,tipomov,idsocio,codigo,estado) values(sysdate(),?,?,?,?) ;";//INSERTAR A LA TABLA PRESTAMO
    private final String SQL_SELECTMATERIAL = "select cantidad_disponible from material where codigo = ?;";//SELECCIONAR MATERIAL
    private final String SQL_UPDATEDIS = "update material set cantidad_disponible= ? where codigo = ?;";

    private int id=0;
    private int idSocio = 0;
    
        public void BorrarDatos(int valor) {
        int rows;
        Connection conn = null;
        PreparedStatement stmt = null;
        rows=0;
        try {
            conn = ConeccionBD.getConexion();
            switch (valor) {
                case 1: stmt = conn.prepareStatement(SQL_DELETE_LIBROS);
                        stmt.setInt(1,id);
                        System.out.print(stmt);
                        rows = stmt.executeUpdate();
                        break;
                case 2: stmt = conn.prepareStatement(SQL_DELETE_REVISTA);
                        stmt.setInt(1,id);
                        rows = stmt.executeUpdate();
                        break;
                case 3: stmt = conn.prepareStatement(SQL_DELETE_CD);
                        stmt.setInt(1,id);
                        rows = stmt.executeUpdate();
                        break;
                case 4: stmt = conn.prepareStatement(SQL_DELETE_DVD);
                        stmt.setInt(1,id);
                        rows = stmt.executeUpdate();
                        break;
                       
            }

        } catch (SQLException e) {
            e.printStackTrace(); 
        } finally {
            ConeccionBD.closeConnection(conn);
            ConeccionBD.closeStatement(stmt);
        }
    }
 
      
      public ArrayList select_material(String codigo){
        ArrayList<String> consulta = new ArrayList<String>();
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
 
        try {
            System.out.print(codigo);
            conn = ConeccionBD.getConexion();


                stmt = conn.prepareStatement(SQL_SELECTmaterial);
                stmt.setString(1,codigo);
                System.out.print(stmt);
                rs = stmt.executeQuery();
                while(rs.next()) {
                for (int x=1;x<=rs.getMetaData().getColumnCount();x++)
                    consulta.add(rs.getString(x));
                    }
                if(consulta.get(1)!=null){
                    id=Integer.parseInt(consulta.get(1));
                }
                else if(consulta.get(2)!=null){
                    id=Integer.parseInt(consulta.get(2));
                }
                else if(consulta.get(3)!=null){
                    id=Integer.parseInt(consulta.get(3));
                }
                else if(consulta.get(4)!=null){
                    id=Integer.parseInt(consulta.get(4));
                }
              
          } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConeccionBD.closeStatement(stmt);
            ConeccionBD.closeConnection(conn);
 
        }
        return consulta;

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
    
    public boolean usuario(String usuario){
        System.out.println(usuario);
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean Resolucion = false;

        try {
            conn = ConeccionBD.getConexion();
            stmt = conn.prepareStatement(SQL_SELECTSOCIO);
            int index = 1;

            
            stmt.setString(index, usuario);
            
            rs = stmt.executeQuery();
            
            while (rs.next()){
            idSocio = rs.getInt(1);
            System.out.println(idSocio);
            Resolucion = true;
            }
         
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConeccionBD.closeStatement(stmt);
            ConeccionBD.closeConnection(conn);
            ConeccionBD.closeResulset(rs);
        }
        return Resolucion;
    }
    
    public int RidSocio(){
        return idSocio;
    }
    
    public int SELECTDisponibilidad(String codigo){
        Connection conn = null;
        PreparedStatement stmt = null;
        PreparedStatement stmt1 = null;
        ResultSet rs = null;
        int cantDisNow = 0, index=1;
        
        try {
            conn = ConeccionBD.getConexion();
            stmt = conn.prepareStatement(SQL_SELECTMATERIAL);
            
            stmt.setString(index,codigo);
            rs = stmt.executeQuery();
            
            while (rs.next()){
            cantDisNow = rs.getInt(1);
            System.out.println(cantDisNow);
            }

                        
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConeccionBD.closeStatement(stmt);
            ConeccionBD.closeConnection(conn);
        }
        
        return cantDisNow;
    }
    
    public void UPDATEDisponibilidad(int cantDisNow,int prestamo, int devolucion, String codigo, String estado){
        int rows = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        int cantUpdate = 0,index=1;
        
        try {
            conn = ConeccionBD.getConexion();
            stmt = conn.prepareStatement(SQL_UPDATEDIS);
            if(prestamo > 0){           
            cantUpdate = cantDisNow - prestamo;
            estado = "A"; //Prestamo abierto
            }else{ 
            cantUpdate = cantDisNow + devolucion;
            estado = "C"; //Prestamo cerrado
            }
            //Evita ambiguedad, luego sera añadido a las consultas
        stmt.setInt(index++, cantUpdate);
        stmt.setString(index,codigo);
        rows = stmt.executeUpdate();    
        System.out.println(cantUpdate); 
        if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Registro exitoso" + "/n" + "Registros afectados" + rows, "Ingresado", JOptionPane.INFORMATION_MESSAGE);
        }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConeccionBD.closeStatement(stmt);
            ConeccionBD.closeConnection(conn);
        }
    }
    
    public void TablaPrestamo(boolean Resolucion,String tipomov,int idsocio,String codigo, String estado){
        Connection conn = null;
        PreparedStatement stmt = null;
        String idLibro = "";
        int rows = 0;
        try {
            conn = ConeccionBD.getConexion();
            stmt = conn.prepareStatement(SQL_INSERTPRESTAMO);
            
            if(Resolucion){ 
            int index = 1;
            
            stmt.setString(index++, tipomov);
            stmt.setInt(index++, idsocio);
            stmt.setString(index, codigo);

            rows = stmt.executeUpdate();
            System.out.println("Registro exitoso Usuario valido" + "/n" + "Registros afectados" + rows);
            
             }else{
            JOptionPane.showMessageDialog(null, "Error al guardar datos, Usuario no existente", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
            }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConeccionBD.closeStatement(stmt);
            ConeccionBD.closeConnection(conn);
        }

    }   
}
