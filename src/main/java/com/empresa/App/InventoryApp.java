package com.empresa.App;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

import com.empresa.Dto.InventoryDto;
import com.empresa.persistence.InventoryPersistence;

public class InventoryApp {

    private JFrame frmGestinDeInventario;
    private JTextField txtNombre;
    private JTextField txtCantidad;
    private JTextField txtPrecio;
    private JTable tablaInventario;

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    InventoryApp window = new InventoryApp();
                    window.frmGestinDeInventario.setVisible(true);
                    window.cargarDatosInventario();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public InventoryApp() {
        initialize();
    }

    private void initialize() {
        frmGestinDeInventario = new JFrame();
        frmGestinDeInventario.setTitle("Gestión de Inventario");
        frmGestinDeInventario.setBounds(0, 0, 1920, 1080);
        frmGestinDeInventario.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmGestinDeInventario.getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel panelFormulario = new JPanel();
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        frmGestinDeInventario.getContentPane().add(panelFormulario, BorderLayout.NORTH);
        GridBagLayout gbl_panelFormulario = new GridBagLayout();
        gbl_panelFormulario.columnWidths = new int[] { 0, 150, 150, 0 };
        gbl_panelFormulario.rowHeights = new int[] { 0, 0, 0, 0 };
        gbl_panelFormulario.columnWeights = new double[] { 0.0, 1.0, 1.0, Double.MIN_VALUE };
        gbl_panelFormulario.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
        panelFormulario.setLayout(gbl_panelFormulario);

        JLabel lblNewLabel = new JLabel("Nombre:");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 20);
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 0;
        panelFormulario.add(lblNewLabel, gbc_lblNewLabel);

        txtNombre = new JTextField();
        txtNombre.setFont(new Font("Tahoma", Font.PLAIN, 20));
        GridBagConstraints gbc_txtNombre = new GridBagConstraints();
        gbc_txtNombre.insets = new Insets(0, 0, 5, 5);
        gbc_txtNombre.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtNombre.gridx = 1;
        gbc_txtNombre.gridy = 0;
        panelFormulario.add(txtNombre, gbc_txtNombre);
        txtNombre.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("Cantidad:");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
        GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
        gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 20);
        gbc_lblNewLabel_1.gridx = 0;
        gbc_lblNewLabel_1.gridy = 1;
        panelFormulario.add(lblNewLabel_1, gbc_lblNewLabel_1);

        txtCantidad = new JTextField();
        txtCantidad.setFont(new Font("Tahoma", Font.PLAIN, 20));
        GridBagConstraints gbc_txtCantidad = new GridBagConstraints();
        gbc_txtCantidad.insets = new Insets(0, 0, 5, 5);
        gbc_txtCantidad.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtCantidad.gridx = 1;
        gbc_txtCantidad.gridy = 1;
        panelFormulario.add(txtCantidad, gbc_txtCantidad);
        txtCantidad.setColumns(10);

        JLabel lblNewLabel_2 = new JLabel("Precio:");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
        GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
        gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_2.insets = new Insets(0, 0, 0, 20);
        gbc_lblNewLabel_2.gridx = 0;
        gbc_lblNewLabel_2.gridy = 2;
        panelFormulario.add(lblNewLabel_2, gbc_lblNewLabel_2);

        txtPrecio = new JTextField();
        txtPrecio.setFont(new Font("Tahoma", Font.PLAIN, 20));
        GridBagConstraints gbc_txtPrecio = new GridBagConstraints();
        gbc_txtPrecio.insets = new Insets(0, 0, 0, 5);
        gbc_txtPrecio.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtPrecio.gridx = 1;
        gbc_txtPrecio.gridy = 2;
        panelFormulario.add(txtPrecio, gbc_txtPrecio);
        txtPrecio.setColumns(10);

        JPanel panelBotones = new JPanel();
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        frmGestinDeInventario.getContentPane().add(panelBotones, BorderLayout.SOUTH);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setFont(new Font("Tahoma", Font.PLAIN, 20));
        panelBotones.add(btnAgregar);

        JButton btnModificar = new JButton("Modificar");
        btnModificar.setFont(new Font("Tahoma", Font.PLAIN, 20));
        panelBotones.add(btnModificar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setFont(new Font("Tahoma", Font.PLAIN, 20));
        panelBotones.add(btnEliminar);

        JButton btnGenerarInforme = new JButton("Generar Informe");
        btnGenerarInforme.setFont(new Font("Tahoma", Font.PLAIN, 20));
        panelBotones.add(btnGenerarInforme);

        JPanel panelTabla = new JPanel();
        frmGestinDeInventario.getContentPane().add(panelTabla, BorderLayout.CENTER);
        panelTabla.setLayout(new BorderLayout(0, 0));

        tablaInventario = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaInventario.setFont(new Font("Tahoma", Font.PLAIN, 20));
        tablaInventario.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(tablaInventario);
        scrollPane.setFont(new Font("Tahoma", Font.PLAIN, 20));
        scrollPane.setAutoscrolls(true);
        panelTabla.add(scrollPane, BorderLayout.CENTER);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tablaInventario.setDefaultRenderer(Object.class, centerRenderer);

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tablaInventario.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tablaInventario.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
        
        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarProducto();
            }
        });

        btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modificarProducto();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });

        btnGenerarInforme.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generarInforme();
            }
        });

        tablaInventario.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    int selectedRow = tablaInventario.getSelectedRow();
                    if (selectedRow != -1) {
                        DefaultTableModel model = (DefaultTableModel) tablaInventario.getModel();
                        txtNombre.setText(model.getValueAt(selectedRow, 1).toString());
                        txtCantidad.setText(model.getValueAt(selectedRow, 2).toString());
                        txtPrecio.setText(model.getValueAt(selectedRow, 3).toString());
                    }
                }
            }
        });
    }

    private void cargarDatosInventario() {
        try {
            List<InventoryDto> productList = InventoryPersistence.getAllProducts();
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Nombre");
            model.addColumn("Cantidad");
            model.addColumn("Precio");

            for (InventoryDto product : productList) {
                model.addRow(new Object[] { product.getId(), product.getNombre(), product.getCantidad(), product.getPrecio() });
            }

            tablaInventario.setModel(model);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frmGestinDeInventario, "Error al cargar los datos del inventario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarProducto() {
        try {
            String nombre = txtNombre.getText();
            int cantidad = Integer.parseInt(txtCantidad.getText());
            double precio = Double.parseDouble(txtPrecio.getText());

            boolean productoExistente = InventoryPersistence.productExists(nombre);
            if (productoExistente) {
                JOptionPane.showMessageDialog(frmGestinDeInventario, "Ya existe un producto con el mismo nombre.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            InventoryDto product = new InventoryDto(0, nombre, cantidad, precio);

            if (InventoryPersistence.addProduct(product)) {
                JOptionPane.showMessageDialog(frmGestinDeInventario, "Producto agregado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                cargarDatosInventario();
            } else {
                JOptionPane.showMessageDialog(frmGestinDeInventario, "No se pudo agregar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frmGestinDeInventario, "Por favor, ingrese valores numéricos válidos para cantidad y precio.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frmGestinDeInventario, "Error al agregar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarProducto() {
        try {
            int filaSeleccionada = tablaInventario.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(frmGestinDeInventario, "Por favor, seleccione un producto para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int id = (int) tablaInventario.getValueAt(filaSeleccionada, 0);
            String nombre = txtNombre.getText();
            int cantidad = Integer.parseInt(txtCantidad.getText());
            double precio = Double.parseDouble(txtPrecio.getText());

            InventoryDto product = new InventoryDto(id, nombre, cantidad, precio);

            if (InventoryPersistence.updateProduct(product)) {
                JOptionPane.showMessageDialog(frmGestinDeInventario, "Producto modificado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                cargarDatosInventario();
            } else {
                JOptionPane.showMessageDialog(frmGestinDeInventario, "No se pudo modificar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frmGestinDeInventario, "Por favor, ingrese valores numéricos válidos para cantidad y precio.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frmGestinDeInventario, "Error al modificar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarProducto() {
        try {
            int filaSeleccionada = tablaInventario.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(frmGestinDeInventario, "Por favor, seleccione un producto para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int id = (int) tablaInventario.getValueAt(filaSeleccionada, 0);

            if (InventoryPersistence.deleteProduct(id)) {
                JOptionPane.showMessageDialog(frmGestinDeInventario, "Producto eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatosInventario();
            } else {
                JOptionPane.showMessageDialog(frmGestinDeInventario, "No se pudo eliminar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frmGestinDeInventario, "Error al eliminar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generarInforme() {
        try {
            JDialog dialog = new JDialog(frmGestinDeInventario, "Informe de Inventario", true);
            dialog.setSize(800, 600);
            dialog.getContentPane().setLayout(new BorderLayout());

            DefaultTableModel model = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            model.addColumn("Nombre");
            model.addColumn("Cantidad");
            model.addColumn("Precio");

            for (int i = 0; i < tablaInventario.getRowCount(); i++) {
                String nombre = tablaInventario.getValueAt(i, 1).toString();
                int cantidad = Integer.parseInt(tablaInventario.getValueAt(i, 2).toString());
                double precio = Double.parseDouble(tablaInventario.getValueAt(i, 3).toString());
                model.addRow(new Object[] { nombre, cantidad, precio });
            }

            JTable table = new JTable(model);
            dialog.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
            dialog.setVisible(true);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frmGestinDeInventario, "Error al generar el informe. Por favor, asegúrese de que los datos sean numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtCantidad.setText("");
        txtPrecio.setText("");
    }
}