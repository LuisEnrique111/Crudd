
package vista;

import config.conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class principal extends javax.swing.JFrame {

    conexion con = new conexion();
    Connection cn;
    Statement st;
    ResultSet rs;
    DefaultTableModel modelo;
    int id;

    public principal() {  //método constructor
        initComponents();
        setLocationRelativeTo(null);
        listar();
    }

    void listar() {
        String sql = "select * from calendarie";
        try {
            cn = con.getConnection();
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            modelo = (DefaultTableModel) TablaDatos.getModel();
            modelo.setRowCount(0); // Limpiar la tabla antes de agregar nuevas filas

            while (rs.next()) {
                Object[] calendarie = new Object[13];
                calendarie[0] = rs.getInt("Id");
                calendarie[1] = rs.getString("dni");
                calendarie[2] = rs.getString("Nombre");
                calendarie[3] = rs.getString("Apellido");
                calendarie[4] = rs.getString("Fecha");
                calendarie[5] = rs.getString("Sexo");
                calendarie[6] = rs.getString("Celular");
                calendarie[7] = rs.getString("Correo");
                calendarie[8] = rs.getString("Direccion");
                calendarie[9] = rs.getString("Colesterol");
                calendarie[10] = rs.getString("Glucosa");
                calendarie[11] = rs.getString("Hemoglobina");
                calendarie[12] = rs.getString("Seguro");
                
                modelo.addRow(calendarie);
            }

            TablaDatos.setModel(modelo);
        } catch (Exception e) {
            System.out.println("Error al listar: " + e);
        }
    }

    void agregar() {
        String dni = txtDNI.getText();
        String nombre = txtNombre.getText();
        String appellido = txtApellido.getText();
        String fecha = txtFecha.getText();
        String sexo = txtSexo.getText();
        String celular = txtCelular.getText();
        String correo = txtCorreo.getText();
        String direccion = txtDireccion.getText();
        String colesterol = txtColesterol.getText();
        String glucosa = txtGlucosa.getText();
        String hemoglobina = txtHemoglobina.getText();
        String seguro = txtSeguro.getText();
        

       if (dni.equals("") || nombre.equals("") || appellido.equals("") || fecha.equals("") || sexo.equals("") || celular.equals("") || correo.equals("") || direccion.equals("") || colesterol.equals("") || glucosa.equals("") || hemoglobina.equals("") || seguro.equals("")) {
            JOptionPane.showMessageDialog(null, "Los campos están vacíos..!");
        } else {
            String sql = "INSERT INTO calendarie (dni, nombre, apellido, fecha, sexo, celular, correo, direccion, colesterol, glucosa, hemoglobina, seguro) VALUES('" + dni + "', '" + nombre + "','" + appellido + "', '" + fecha+ "' , '" + sexo+ "' , '" + celular+ "' , '" + correo+ "' , '" + direccion+ "' , '" + colesterol+ "' , '" + glucosa+ "' , '" + hemoglobina+ "' , '" + seguro+ "' )";
            try {
                cn = con.getConnection();
                st = cn.createStatement();
                st.executeUpdate(sql);

                JOptionPane.showMessageDialog(null, "Registro agregado");
                limpiartabla();

            } catch (Exception e) {
                System.out.println("Error al agregar: " + e);
            }
        }
    }

    void limpiartabla() {
        int rowCount = modelo.getRowCount(); //Se obtiene el número total de filas existentes
        for (int i = rowCount - 1; i >= 0; i--) {
            modelo.removeRow(i);
        }
    }

    void nuevo() {
        txtDNI.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtFecha.setText("");
        txtSexo.setText("");
        txtCelular.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
        txtColesterol.setText("");
        txtGlucosa.setText("");
        txtHemoglobina.setText("");
        txtSeguro.setText("");
    }

    void modificar() {
        String dni = txtDNI.getText();
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String fecha = txtFecha.getText();
        String sexo = txtSexo.getText();
        String celular = txtCelular.getText();
        String correo = txtCorreo.getText();
        String direccion = txtDireccion.getText();
        String colesterol = txtColesterol.getText();
        String glucosa = txtGlucosa.getText();
        String hemoglobina = txtHemoglobina.getText();
        String seguro = txtSeguro.getText();
        

        // Construimos la consulta SQL con los valores actualizados
        String sql = "UPDATE calendarie SET nombre = '" + nombre + "', apellido = '" + apellido + "', fecha = '" + fecha + "'                                                 WHERE Id = " + id;

        if (dni.equals("") || nombre.equals("") || apellido.equals("")  || fecha.equals("")   || sexo.equals("")  || celular.equals("")  || direccion.equals("")  || colesterol.equals("")  || glucosa.equals("")  || hemoglobina.equals("")   || seguro.equals("")          ) {
            JOptionPane.showMessageDialog(null, "Debe ingresar datos");
        } else {
            try {
                cn = con.getConnection();
                st = cn.createStatement();
                st.executeUpdate(sql);

                JOptionPane.showMessageDialog(null, "Registro actualizado");
                limpiartabla(); // Limpiamos la tabla y volvemos a cargar los datos actualizados
                listar(); // Llamamos a listar() para actualizar la tabla mostrada en la interfaz
            } catch (Exception e) {
                System.out.println("Error al modificar: " + e);
            }
        }
    }

    void eliminar() {
        int filaSeleccionado = TablaDatos.getSelectedRow();

        if (filaSeleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila para eliminar");
        } else {
            int confirmacion = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                try {
                    cn = con.getConnection();
                    st = cn.createStatement();

                    // Obtener el id del registro seleccionado en la tabla
                    int idEliminar = (int) TablaDatos.getValueAt(filaSeleccionado, 0);

                    // Construir la consulta SQL para eliminar el registro
                    String sql = "DELETE FROM calendarie WHERE Id = " + idEliminar;

                    // Ejecutar la consulta SQL
                    st.executeUpdate(sql);

                    JOptionPane.showMessageDialog(null, "Registro eliminado correctamente");

                    // Limpiar la tabla y actualizar los datos
                    limpiartabla();
                    listar();

                } catch (Exception e) {
                    System.out.println("Error al eliminar: " + e);
                }
            }
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        TablaDatos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btnAgregar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnModificar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnNuevo = new javax.swing.JButton();
        txtNombre = new javax.swing.JTextField();
        txtSexo = new javax.swing.JTextField();
        txtDNI = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        txtFecha = new javax.swing.JTextField();
        txtCelular = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtColesterol = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtGlucosa = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtSeguro = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtHemoglobina = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        TablaDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "DNI", "Nombre", "Apellido", "Fecha de naciemiento", "Sexo", "Celular", "Correo electronico", "Direcion", "Colesterol", "Clucosa", "Hemoglobina", "Tipo de seguro"
            }
        ));
        TablaDatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaDatosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TablaDatos);
        if (TablaDatos.getColumnModel().getColumnCount() > 0) {
            TablaDatos.getColumnModel().getColumn(0).setMinWidth(30);
            TablaDatos.getColumnModel().getColumn(0).setPreferredWidth(30);
            TablaDatos.getColumnModel().getColumn(0).setMaxWidth(30);
            TablaDatos.getColumnModel().getColumn(1).setMinWidth(40);
            TablaDatos.getColumnModel().getColumn(1).setPreferredWidth(40);
            TablaDatos.getColumnModel().getColumn(1).setMaxWidth(40);
            TablaDatos.getColumnModel().getColumn(2).setMinWidth(80);
            TablaDatos.getColumnModel().getColumn(2).setPreferredWidth(50);
            TablaDatos.getColumnModel().getColumn(2).setMaxWidth(50);
            TablaDatos.getColumnModel().getColumn(3).setMinWidth(80);
            TablaDatos.getColumnModel().getColumn(3).setPreferredWidth(50);
            TablaDatos.getColumnModel().getColumn(3).setMaxWidth(50);
            TablaDatos.getColumnModel().getColumn(4).setMinWidth(140);
            TablaDatos.getColumnModel().getColumn(4).setPreferredWidth(80);
            TablaDatos.getColumnModel().getColumn(4).setMaxWidth(80);
            TablaDatos.getColumnModel().getColumn(5).setMinWidth(70);
            TablaDatos.getColumnModel().getColumn(5).setPreferredWidth(50);
            TablaDatos.getColumnModel().getColumn(5).setMaxWidth(50);
            TablaDatos.getColumnModel().getColumn(6).setMinWidth(80);
            TablaDatos.getColumnModel().getColumn(6).setPreferredWidth(50);
            TablaDatos.getColumnModel().getColumn(6).setMaxWidth(50);
            TablaDatos.getColumnModel().getColumn(7).setMinWidth(150);
            TablaDatos.getColumnModel().getColumn(7).setPreferredWidth(50);
            TablaDatos.getColumnModel().getColumn(7).setMaxWidth(50);
            TablaDatos.getColumnModel().getColumn(8).setMinWidth(60);
            TablaDatos.getColumnModel().getColumn(8).setPreferredWidth(50);
            TablaDatos.getColumnModel().getColumn(8).setMaxWidth(50);
            TablaDatos.getColumnModel().getColumn(9).setMinWidth(80);
            TablaDatos.getColumnModel().getColumn(9).setPreferredWidth(50);
            TablaDatos.getColumnModel().getColumn(9).setMaxWidth(50);
            TablaDatos.getColumnModel().getColumn(10).setMinWidth(70);
            TablaDatos.getColumnModel().getColumn(10).setPreferredWidth(50);
            TablaDatos.getColumnModel().getColumn(10).setMaxWidth(50);
            TablaDatos.getColumnModel().getColumn(11).setMinWidth(70);
            TablaDatos.getColumnModel().getColumn(11).setPreferredWidth(80);
            TablaDatos.getColumnModel().getColumn(11).setMaxWidth(80);
            TablaDatos.getColumnModel().getColumn(12).setMinWidth(120);
            TablaDatos.getColumnModel().getColumn(12).setPreferredWidth(50);
            TablaDatos.getColumnModel().getColumn(12).setMaxWidth(50);
        }

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("CALENDARIO DE ACTIVIDADES CÍVICAS");

        btnAgregar.setText("AGREGAR");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        jLabel2.setText("Nombre:");

        btnModificar.setText("MODIFICAR");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        jLabel3.setText("Apellido");

        btnEliminar.setText("ELIMINAR");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        jLabel4.setText("DNI");

        btnNuevo.setText("NUEVO");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        jLabel5.setText("Id:");

        txtId.setEditable(false);
        txtId.setEnabled(false);

        jLabel6.setText("Celular");

        jLabel7.setText("Fecha de nacimiento");

        jLabel8.setText("Sexo");

        jLabel9.setText("Colesterol");

        jLabel10.setText("Correo electronico");

        jLabel11.setText("Direcion");

        jLabel12.setText("Clucosa");

        jLabel13.setText("Tipo de seguro");

        jLabel14.setText("Hemoglobina");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(223, 223, 223))
            .addGroup(layout.createSequentialGroup()
                .addGap(234, 234, 234)
                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(67, 67, 67)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(35, 35, 35)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel3)
                                                .addComponent(jLabel2)))
                                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtFecha)
                                        .addComponent(txtApellido)
                                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(61, 61, 61)
                            .addComponent(jLabel4)
                            .addGap(51, 51, 51)
                            .addComponent(txtDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 158, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel12)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel10)
                                        .addComponent(jLabel11)
                                        .addComponent(jLabel9))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtColesterol, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtGlucosa, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtHemoglobina, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtSeguro, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGap(1, 1, 1))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addGap(74, 74, 74)
                            .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel14))
                    .addComponent(jLabel13))
                .addGap(149, 149, 149))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtDNI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtColesterol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGlucosa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtHemoglobina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSeguro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))))
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar)
                    .addComponent(btnNuevo))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        agregar();
        listar();
        nuevo();
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        modificar();
        listar();
        nuevo();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminar();
        listar();
        nuevo();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
         nuevo();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void TablaDatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaDatosMouseClicked
                int fila = TablaDatos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Usuario no seleccionado");
        } else {

            id = Integer.parseInt((String) TablaDatos.getValueAt(fila, 0).toString()); //Tranformamos a un entero

            String dni = (String) TablaDatos.getValueAt(fila, 1);  //Obtenemos el valor
            String nombre = (String) TablaDatos.getValueAt(fila, 2);
            String apellido = (String) TablaDatos.getValueAt(fila, 3);
            String fecha = (String) TablaDatos.getValueAt(fila, 4);
            String sexo = (String) TablaDatos.getValueAt(fila, 5);
            String celular = (String) TablaDatos.getValueAt(fila, 6);
            String correo = (String) TablaDatos.getValueAt(fila, 7);
            String direccion = (String) TablaDatos.getValueAt(fila, 8);
            String colesterol = (String) TablaDatos.getValueAt(fila, 9);
            String glucosa = (String) TablaDatos.getValueAt(fila, 10);
            String hemoglobina = (String) TablaDatos.getValueAt(fila, 11);
            String seguro = (String) TablaDatos.getValueAt(fila, 12);

            txtId.setText("" + id);  //Colocar el texto
            txtDNI.setText(dni);
            txtNombre.setText(nombre);
            txtApellido.setText(apellido);
            txtFecha.setText(fecha);
            txtSexo.setText(sexo);
            txtCelular.setText(celular);
            txtCorreo.setText(correo);
            txtDireccion.setText(direccion);
            txtColesterol.setText(colesterol);
            txtGlucosa.setText(glucosa);
            txtHemoglobina.setText(hemoglobina);
            txtSeguro.setText(seguro);
            

        }
    }//GEN-LAST:event_TablaDatosMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TablaDatos;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCelular;
    private javax.swing.JTextField txtColesterol;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDNI;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtGlucosa;
    private javax.swing.JTextField txtHemoglobina;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtSeguro;
    private javax.swing.JTextField txtSexo;
    // End of variables declaration//GEN-END:variables
}
