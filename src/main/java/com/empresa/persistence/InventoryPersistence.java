package com.empresa.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.empresa.Dto.InventoryDto;

public class InventoryPersistence {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/inventario_db";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    public static List<InventoryDto> getAllProducts() throws SQLException {
        List<InventoryDto> productList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM inventario")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int cantidad = rs.getInt("cantidad");
                double precio = rs.getDouble("precio");
                productList.add(new InventoryDto(id, nombre, cantidad, precio));
            }
        }
        return productList;
    }

    public static boolean addProduct(InventoryDto product) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO inventario (nombre, cantidad, precio) VALUES (?, ?, ?)")) {

            stmt.setString(1, product.getNombre());
            stmt.setInt(2, product.getCantidad());
            stmt.setDouble(3, product.getPrecio());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public static boolean updateProduct(InventoryDto product) throws SQLException {
        if (productExists(product.getNombre()) && !getProductNameById(product.getId()).equals(product.getNombre())) {
            return false;
        }
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement("UPDATE inventario SET nombre=?, cantidad=?, precio=? WHERE id=?")) {

            stmt.setString(1, product.getNombre());
            stmt.setInt(2, product.getCantidad());
            stmt.setDouble(3, product.getPrecio());
            stmt.setInt(4, product.getId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public static boolean deleteProduct(int productId) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM inventario WHERE id=?")) {

            stmt.setInt(1, productId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    public static boolean productExists(String productName) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) AS count FROM inventario WHERE nombre=?")) {

            stmt.setString(1, productName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    return count > 0;
                }
            }
        }
        return false;
    }

    private static String getProductNameById(int productId) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement("SELECT nombre FROM inventario WHERE id=?")) {

            stmt.setInt(1, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nombre");
                }
            }
        }
        return null;
    }
}
