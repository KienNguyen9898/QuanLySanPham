package dao;

import connection.MyConnection;
import model.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    //in ra
    public List<Product> getAll() {
        final String sql = "SELECT * FROM `products`";

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
                p.setBrandId(rs.getLong("brand_id"));
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
// tim theo id
    public Product getById(long id) {
        final String sql = "SELECT * FROM `products` WHERE  `id` = " + id;
        Product p = null;

        try {
            Connection conn = MyConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                p = new Product();
                p.setId(rs.getLong("id"));
                p.setName(rs.getString("product_name"));
                p.setColor(rs.getString("product_color"));
                p.setSize(rs.getString("product_size"));
                p.setBrandId(rs.getLong("brand_id"));
                p.setPrice(rs.getLong("product_price"));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }
//them
    public void insert(Product p) {
        final String sql = String.format("INSERT  INTO `products` VALUES ( NULL,'%s','%d','%s','%s','%d' ) ",
                p.getName(), p.getPrice(), p.getSize(), p.getColor(), p.getBrandId()
        );
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
//sua
    public void update(Product product, long id) {
        Product tmp = getById(id);
        if (tmp == null) {
            throw new RuntimeException("Sản phẩm không tồn tại!");
        }

        final String sql = String.format("UPDATE `products` SET `product_name`='%s',`product_price`='%d',`product_size`='%s',`product_color`='%s',`brand_id`='%d' WHERE `id` = '%d'",
                product.getName(), product.getPrice(), product.getSize(), product.getColor(), product.getBrandId(), id
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

//xoa
    public void delete(long id) {
        Product product = getById(id);
        if (product == null) {
            throw new RuntimeException("Sản phẩm không tồn tại!");
        }

        final String sql = "DELETE FROM `products` WHERE  `id` = " + id;
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
