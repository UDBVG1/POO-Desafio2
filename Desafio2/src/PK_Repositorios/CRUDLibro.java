/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PK_Repositorios;

import PK_Modelos.ObjetoLibro;
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
public class CRUDLibro {
    private int id=0;
    private int cantDq=0;
    int cantTD=0;
    private final String SQL_INSERTLIRBOS = "insert into libros(Titulo, autor, num_pag,editorial, ISBN, idEscrito) values(?,?,?,?,?,?);";
    private final String SQL_SELECTLIBROS = "SELECT titulo,autor,num_pag,editorial,isbn from libros where titulo like ? or autor like ? or editorial like ? or isbn like ?;";
    private final String SQL_SELECTRN = "select count(*) from material where codigo = ?;"; //buscar si no esta repetido el id
    private final String SQL_INSERTM = "insert into material (codigo,cantidad_total,cantidad_disponible,idlibros) values(?,?,?,?);";//insertar a la tabla matrial para libro
    private final String SQL_SELECTID = "SELECT titulo,autor,num_pag,editorial,isbn,l.idlibros,m.cantidad_total,m.cantidad_disponible from libros l\n" +
                                        "inner join material m ON l.idlibros =m.idlibros\n" +
                                        "where codigo= ?;";
    private final String SQL_UPDATETLIRBOS = "update libros set titulo =?, autor =?, num_pag =? ,editorial =?, isbn =? where idlibros =?;";
    private final String SQL_UPDATEMATERIAL = "update material  set cantidad_total =?, cantidad_disponible =? where codigo = ?;";
    
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
            cadena = "LIB" + String.valueOf(fiveDigits);
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
    
    public int insertmaterialdisponible(String IDcantd,int CantD,int CantT,int IDlibro){//++++++++
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
            stmt.setInt(index, IDlibro);

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
    
    public DefaultTableModel select(ObjetoLibro libro){
        DefaultTableModel dtm = new DefaultTableModel();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
       // ObjetoLibro materialLb;
        try {
            conn = ConeccionBD.getConexion();
            stmt = conn.prepareStatement(SQL_SELECTLIBROS);
            int index = 1;
            stmt.setString(index++, libro.titulo);
            stmt.setString(index++, libro.autor);
            stmt.setString(index++, libro.edit);
            stmt.setString(index, libro.code);
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
    
    public ObjetoLibro selectId(String codigo) {
        ObjetoLibro libroMod = new ObjetoLibro();
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
             
                    libroMod.setTitulo(rs.getObject(1).toString());
                    libroMod.setAutor(rs.getObject(2).toString());
                    libroMod.setPaginas(Integer.parseInt(rs.getObject(3).toString()));
                    libroMod.setEdit(rs.getObject(4).toString());
                    libroMod.setCode(rs.getObject(5).toString());
                    id=(Integer.parseInt(rs.getObject(6).toString()));
                    cantTD=(Integer.parseInt(rs.getObject(7).toString()));
                    cantDq=(Integer.parseInt(rs.getObject(8).toString()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConeccionBD.closeStatement(stmt);
            ConeccionBD.closeConnection(conn);
            ConeccionBD.closeResulset(rs);
        }
        return libroMod;
    }
    
    public int selectCant(){
        return cantTD;
    }
    
    public int updateDatos(ObjetoLibro libro) {
        int rows = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = ConeccionBD.getConexion();
            stmt = conn.prepareStatement(SQL_UPDATETLIRBOS);
            int index = 1;
            System.out.println(libro.toString());
            
            stmt.setString(index++, libro.titulo);
            stmt.setString(index++, libro.autor);
            stmt.setInt(index++, libro.paginas);
            stmt.setString(index++, libro.edit);
            stmt.setString(index++, libro.code);
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
    };
}
