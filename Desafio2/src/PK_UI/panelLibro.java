/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PK_UI;

import PK_Repositorios.CRUD;
import PK_Modelos.ObjetoLibro;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author admin
 */
public class panelLibro extends javax.swing.JPanel {

    private final CRUD insertarLibro;
    private final ObjetoLibro Setlibro;

    /**
     * Creates new form PanelLibro
     */
    public panelLibro() {
        initComponents();
        Setlibro = new ObjetoLibro();
        insertarLibro = new CRUD();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupTipo = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        AgregarDatos = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextAutor = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextEdit = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextPag = new javax.swing.JTextField();
        jTextCod = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextTitulo = new javax.swing.JTextField();
        buscarBtn = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jTextCantD = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jMostrar = new javax.swing.JTable();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        AgregarDatos.setText("Agregar");
        AgregarDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarDatosActionPerformed(evt);
            }
        });

        jLabel1.setText("Nombre del Autor:");

        jLabel2.setText("Nombre de la editorial:");

        jLabel3.setText("Numero de paginas:");

        jTextCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextCodActionPerformed(evt);
            }
        });

        jLabel4.setText("Codigo ISBN:");

        jLabel6.setText("Titulo:");

        buscarBtn.setText("Buscar");
        buscarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarBtnActionPerformed(evt);
            }
        });

        jLabel5.setText("Cantidad disponible:");

        jMostrar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jMostrar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(AgregarDatos)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextPag, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextEdit, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextCod)
                    .addComponent(jTextTitulo)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 33, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(jTextCantD, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(buscarBtn)))
                    .addComponent(jTextAutor))
                .addGap(25, 25, 25))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel4))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(jTextTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextPag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(jTextCantD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buscarBtn)
                    .addComponent(AgregarDatos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void AgregarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarDatosActionPerformed
        //variables
        int paginas;

        Setlibro.setTipo(1);
        Setlibro.setAutor(jTextAutor.getText());
        Setlibro.setEdit(jTextEdit.getText());
        Setlibro.setTitulo(jTextTitulo.getText());
        Setlibro.setCode(jTextCod.getText());

        paginas = Integer.parseInt(jTextPag.getText());
        Setlibro.setPaginas(paginas);


        insertarLibro.insertarDatos(Setlibro);
    }//GEN-LAST:event_AgregarDatosActionPerformed

    private void buscarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarBtnActionPerformed
        int paginas;

        Setlibro.setTipo(1);
        Setlibro.setAutor(jTextAutor.getText());
        Setlibro.setEdit(jTextEdit.getText());
        Setlibro.setTitulo(jTextTitulo.getText());
        Setlibro.setCode(jTextCod.getText());

        paginas = Integer.parseInt(jTextPag.getText());
        Setlibro.setPaginas(paginas);


        List<ObjetoLibro> materialLB = new ArrayList<ObjetoLibro>();
        jMostrar.setModel(insertarLibro.select(Setlibro));

      


    }//GEN-LAST:event_buscarBtnActionPerformed

    private void jTextCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextCodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextCodActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AgregarDatos;
    private javax.swing.JButton buscarBtn;
    private javax.swing.ButtonGroup buttonGroupTipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTable jMostrar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextAutor;
    private javax.swing.JTextField jTextCantD;
    private javax.swing.JTextField jTextCod;
    private javax.swing.JTextField jTextEdit;
    private javax.swing.JTextField jTextPag;
    private javax.swing.JTextField jTextTitulo;
    // End of variables declaration//GEN-END:variables
}
