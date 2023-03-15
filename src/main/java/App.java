import dao.BrandDAO;
import dao.ProductDAO;
import model.Brand;
import model.Product;

import java.util.List;
import java.util.Scanner;

public class App {

    private static ProductDAO productDAO = new ProductDAO();
    private static BrandDAO brandDAO = new BrandDAO();

    private static void mainMenu() {
        System.out.println("--- QUẢN LÝ SẢN PHẨM ---");
        System.out.println("1. Danh sách sản phẩm");
        System.out.println("2. Thêm sản phẩm");
        System.out.println("3. Xóa sản phẩm theo mã");
        System.out.println("4. Câp nhật thông tin sản phẩm");
        System.out.println("5. Lấy thông tin hãng sx");
        System.out.println("6. Top 5 sản phẩm có giá trị cao nhất");
        System.out.println("7. Danh sách hãng sản xuất");
        System.out.println("8. Thêm hãng sản xuất");
        System.out.println("9. Xóa hãng sản xuất theo mã");
    }

    private static void option1() {
        List<Product> productList = productDAO.getAll();
        System.out.printf("%-20s %-20s %-20s %-20s", "STT", "Tên sản phẩm", "Giá sản phẩm", "Màu sắc");
        System.out.println();
        for (int i = 0; i < productList.size(); i++) {
            Product p = productList.get(i);
            System.out.printf("%-20d %-20s %-20d %-20s\n", (i+1), p.getName(), p.getPrice(), p.getColor());
        };
    }
    private static void option2(Scanner in){
        Product p = new Product();
        System.out.print("\tNhập tên: ");
        p.setName(in.nextLine());
        System.out.print("\tNhập giá: ");
        p.setPrice(Long.parseLong(in.nextLine()));
        System.out.print("\tNhập size: ");
        p.setSize(in.nextLine());
        System.out.print("\tNhập màu sắc: ");
        p.setColor(in.nextLine());
        System.out.println("\tChọn hãng: ");
        List<Brand> brandList = brandDAO.getAll();
        for (int i = 0; i < brandList.size(); i++) {
            System.out.printf("\t\t%-5d %-20s \n", i+1, brandList.get(i).getName());
        }
        // Tam thoi nhap chinh xac
        long brand_id =  brandList.get(Integer.parseInt(in.nextLine()) - 1).getId();

        p.setBrandId(brand_id);

        productDAO.insert(p);
    }
    private static void option3(Scanner in){
        System.out.print("\tNhap id san pham muon xoa: ");
        int id=Integer.parseInt(in.nextLine());
        productDAO.delete(id);


    }

    private static void option7(){

        List<Brand> brandList = brandDAO.getAll();
        System.out.printf("%-20s %-20s %-20s", "STT", "Ten hang san xuat", "Xuat xu");
        System.out.println();
        for (int i=0; i< brandList.size(); i++) {
            Brand b = brandList.get(i);
            System.out.printf("%-20s %-20s %-20s\n", b.getId(), b.getName(), b.getAddress());
        };
    }

    private static void option8(Scanner in){
        Brand b = new Brand();
        System.out.print("\tNhap ten hang: ");
        b.setName(in.nextLine());
        System.out.print("\tNhap noi xuat xu: ");
        b.setAddress(in.nextLine());
        brandDAO.insert(b);

    }

    private static void option9(Scanner in){
        System.out.print("\tNhap id hang san pham muon xoa: ");
        long id= Long.parseLong(in.nextLine());
        brandDAO.delete(id);
    }


    private static void option4(Scanner in){
        Product p = new Product();
        System.out.print("Nhap id san pham can cap nhat: ");
        long id = Long.parseLong(in.nextLine());
        System.out.print("\tNhap ten san pham: ");
        p.setName(in.nextLine());
        System.out.print("\tNhap gia san pham: ");
        p.setPrice(Integer.parseInt(in.nextLine()));
        System.out.print("\tNhap size san pham: ");
        p.setSize(in.nextLine());
        System.out.print("\tNhap mau san pham: ");
        p.setColor(in.nextLine());
        System.out.print("\tNhap id hang san pham ");
        p.setBrandId(in.nextLong());
        in.close();
        productDAO.update(p, id);

    }

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int option = -1;

        do {
            mainMenu();
            System.out.print("Nhập lựa chọn: ");
            // Làm thêm phàn try-catch khi người dùng nhập lỗi
            try {
                option = Integer.parseInt(in.nextLine());

            } catch (Exception exception) {
                System.out.println("Nhap sai dinh dang");

            }
            if (option < 1 || option > 9) {
                System.out.println("Vui lòng nhập lại!");
                continue;
            }
            switch (option) {
                case 1:
                    option1();
                    break;
                case 2:
                    option2(in);
                    break;
                case 3:
                    option3(in);
                    break;
                case 4:
                    option4(in);
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    option7();
                    break;
                case 8:
                    option8(in);
                    break;
                case 9:
                    option9(in);
                    break;
            }

        }
        while (option != 0);
        in.close();

    }
}
