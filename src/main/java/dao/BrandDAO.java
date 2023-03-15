package dao;

import connection.MyConnection;
import model.Brand;
import model.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BrandDAO {
    // CRUD

    public List<Brand> getAll() {
        final String sql = "SELECT * FROM `brands`";

        List<Brand> brandList = new ArrayList<>();

        try {
            Connection conn = MyConnection.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Brand b = new Brand();
                b.setId(rs.getLong("id"));
                b.setName(rs.getString("brand_name"));
                b.setAddress(rs.getString("brand_address"));
                brandList.add(b);
            }
            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return brandList;
    }

    public Brand getById(long id) {
        final String sql = "SELECT * FROM `brands` WHERE  `id` = " + id;
        Brand b = null;

        try {
            Connection conn = MyConnection.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                b = new Brand();
                b.setId(rs.getLong("id"));
                b.setName(rs.getString("brand_name"));
                b.setAddress(rs.getString("brand_address"));
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    public List<Product> getAllProductByBrand(long brandID) {
        Brand b = getById(brandID);
        if (b == null) {
            throw new RuntimeException("Hãng không tồn tại!");
        }
        // SQL
        final String sql = "SELECT * FROM `products` WHERE `brand_id` = " + brandID;

        List<Product> productList = new ArrayList<>();
        try {
            Connection conn = MyConnection.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getLong("id"));
                p.setName(rs.getString("product_name"));
                p.setColor(rs.getString("product_color"));
                p.setSize(rs.getString("product_size"));
                p.setBrandId(brandID);
                p.setPrice(rs.getLong("product_price"));
                productList.add(p);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return productList;
    }

    public void insert(Brand brand) {
        final String sql = String.format("INSERT INTO `brands` VALUES (NULL,'%s','%s')",
                brand.getName(), brand.getAddress());
        try {
            Connection conn = MyConnection.getConnection();
            Statement stmt = conn.createStatement();
            long rs = stmt.executeUpdate(sql);

            if (rs == 0) {
                System.out.println("Thêm thất bại");
            }

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Brand brand, long id) {
        Brand b = getById(id);
        if (b == null) {
            throw new RuntimeException("Hãng không tồn tại!");
        }

        final String sql = String.format(
                "UPDATE `brands` SET `brand_name`='%s', `brand_address`='%s' WHERE `id` = '%d'",
                brand.getName(), brand.getAddress(), id
        );
        try {
            Connection conn = MyConnection.getConnection();
            Statement stmt = conn.createStatement();
            long rs = stmt.executeUpdate(sql);

            if (rs == 0) {
                System.out.println("Cập nhật thất bại");
            }
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void delete(long id) {
        final String sql = "DELETE FROM `brands` WHERE `id` = " + id;
        try {
            Connection conn = MyConnection.getConnection();
            Statement stmt = conn.createStatement();
            long rs = stmt.executeUpdate(sql);

            if (rs == 0) {
                System.out.println("Xoá thất bại");
            }

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
