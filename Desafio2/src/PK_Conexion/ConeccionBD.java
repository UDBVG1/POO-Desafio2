/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PK_Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ConeccionBD {
   public static Connection conn=null;//conn es la conexion
   private static String driver ="com.mysql.cj.jdbc.Driver";//The name of the class that implements java.sql.Driver in MySQL Connector/J has changed from com.mysql.jdbc.Driver to com.mysql.cj.jdbc.Driver. The old class name has been deprecated.
   private static String user = "root";
   private static String password = "hola";
   private static String server = "jdbc:mysql://localhost:3306/poo";//server el cual establece la 
   private static Statement PS;
    /**
     * @return 
     * @throws java.sql.SQLException
     */
   //*************************creamos la conexion************************
    public static Connection getConexion() throws SQLException{//asi se crea las exepciones personalizadas
    conn = null;
    try{
        Class.forName(driver);//driver
        conn=DriverManager.getConnection (server, user, password);//conexion
        PS = conn.createStatement(); //cuando queramso invocar sentencias de tipo mysql
        //deberemos de usar stm. por que es el que invoca la opcion para nosotros usar las sentencias mysql desde la intefaz
    }
    catch(ClassNotFoundException | SQLException e){//estas son las exepcione que se pueden unir ocn ese palito 
    System.out.println("ERROR:No encuentro el driver de la BD: " + e.getMessage());
    }
    return conn;
    }//esta no se manda a llamar solo los close y la interaccion con la b

    //*********************cerrar la bd****************************
    
    public static void closeResulset(ResultSet rs) throws SQLException{//cerrar conexion para mandar a llamar info a la db
    try{
        if (rs != null){
        rs.close();
    }
    }catch (SQLException e) {
    System.out.println("ERROR:Fallo en SQL: " + e.getMessage());
        }
    }
            
    public static void closeStatement(PreparedStatement PS){//cerrar el statement para el update delete y select
    try{
        if (PS != null){
        PS.close();
    }
    } catch (SQLException e) {
    System.out.println("ERROR:Fallo en SQL: " + e.getMessage());
      }
    }
    public static void closeConnection(Connection conn){//cerrar la conexion
        try {
            if (conn != null){
            conn.close();
            }
        } catch (SQLException e) {
    System.out.println("ERROR:Fallo en SQL: " + e.getMessage());
        }
    }
    //cuando ya se quiera cerrar las conexiones solo se manda a llamar a las funciones de close
}
