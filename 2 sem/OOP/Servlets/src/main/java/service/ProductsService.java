package service;

import data.*;
import connection.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ProductsService {
    private static final Logger log = Logger.getLogger(ProductsService.class.getName());

    private static final String editProductQuery =
            "UPDATE product SET name = ?, price = ?, description = ?, product_type_id = ? WHERE id = ?";
    private static final String addProductQuery =
            "INSERT INTO product(name, price, description, product_type_id) VALUES (?, ?, ?, ?)";
    private static final String removeProductWithIdQuery =
            "DELETE FROM product WHERE product.id = ?";
    private static final String getProductWithIdQuery =
            "SELECT product.id, name, price, description, product_type_id FROM product WHERE product.id = ?";
    private static final String allProductsQuery =
            "SELECT product.id, product.name, product.price, product.description, product_type.id, product_type.name, product_type.description FROM product INNER JOIN product_type ON product.product_type_id = product_type.id";
    private static final String allProductTypesQuery =
            "SELECT product_type.id, name, description FROM product_type";
    private static final String getProductTypeQuery =
            "SELECT product_type.id, name, description FROM product_type WHERE product_type.id = ?";

    public static void editProduct(Product product) {
        if (product == null) {
            log.warning("Cannot add product because it was null.");
            return;
        }
        DatabaseConnection cp = DatabaseConnection.getConnectionPool();
        try (Connection connection = cp.getConnection()) {
            PreparedStatement prepareStatement = connection.prepareStatement(editProductQuery);
            prepareStatement.setString(1, product.getName());
            prepareStatement.setInt(2, product.getPrice());
            prepareStatement.setString(3, product.getDescription());
            prepareStatement.setInt(4, product.getType().getId());
            prepareStatement.setInt(5, product.getId());
            if (prepareStatement.executeUpdate() <= 0) {
                log.warning("Cannot add product.");
            }
            cp.releaseConnection(connection);
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Product getProductWithId(int id) {
        Product product = null;
        DatabaseConnection cp = DatabaseConnection.getConnectionPool();
        try (Connection connection = cp.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(getProductWithIdQuery);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                int price = resultSet.getInt(3);
                String description = resultSet.getString(4);
                int productTypeId = resultSet.getInt(5);
                List<ProductType> productTypes = getProductTypes();
                ProductType productType = null;
                for (ProductType pt : productTypes) {
                    if (pt.getId() == productTypeId) productType = pt;
                }
                product = new Product(id, name, price, description, productType);
                log.info("Found product with id.");
                cp.releaseConnection(connection);
            } else {
                log.info("Couldn't find product with the given id.");
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
        return product;
    }

    public static void removeProductWithId(int id) {
        DatabaseConnection cp = DatabaseConnection.getConnectionPool();
        try (Connection connection = cp.getConnection()) {
            PreparedStatement prepareStatement = connection.prepareStatement(removeProductWithIdQuery);
            prepareStatement.setInt(1, id);
            if (prepareStatement.executeUpdate() <= 0) {
                log.warning("Cannot remove product with id " + id);
            }
            cp.releaseConnection(connection);
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void addProduct(Product product) {
        if (product == null) {
            log.warning("Cannot add product because it was null.");
            return;
        }
        DatabaseConnection cp = DatabaseConnection.getConnectionPool();
        try (Connection connection = cp.getConnection()) {
            PreparedStatement prepareStatement = connection.prepareStatement(addProductQuery);
            prepareStatement.setString(1, product.getName());
            prepareStatement.setInt(2, product.getPrice());
            prepareStatement.setString(3, product.getDescription());
            prepareStatement.setInt(4, product.getType().getId());
            if (prepareStatement.executeUpdate() <= 0) {
                log.warning("Cannot add product.");
            }
            cp.releaseConnection(connection);
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static ProductType getProductType(int id) {
        log.info("Getting product type from the database.");
        ProductType type = null;
        DatabaseConnection cp = DatabaseConnection.getConnectionPool();
        try (Connection connection = cp.getConnection()) {
            log.info("Connected to the database.");
            PreparedStatement preparedStatement = connection.prepareStatement(getProductTypeQuery);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                type = getProductTypeFromResultSet(resultSet);
                log.info("Found product with name.");
            } else {
                log.info("Couldn't find product type with the given name.");
            }
            cp.releaseConnection(connection);
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
        return type;
    }

    public static List<ProductType> getProductTypes() {
        log.info("Getting product types from the database.");
        List<ProductType> productTypes = new ArrayList<>();
        DatabaseConnection cp = DatabaseConnection.getConnectionPool();
        try (Connection connection = cp.getConnection()) {
            log.info("Connected to the database.");
            PreparedStatement preparedStatement = connection.prepareStatement(allProductTypesQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                productTypes.add(getProductTypeFromResultSet(rs));
            }
            cp.releaseConnection(connection);
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
        return productTypes;
    }

    public static List<Product> getProducts() {
        log.info("Getting products from the database.");
        List<Product> products = new ArrayList<>();
        DatabaseConnection cp = DatabaseConnection.getConnectionPool();
        try (Connection connection = cp.getConnection()) {
            log.info("Connected to the database.");
            PreparedStatement ps = connection.prepareStatement(allProductsQuery);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                int price = rs.getInt(3);
                String description = rs.getString(4);
                int productTypeId = rs.getInt(5);
                String productTypeName = rs.getString(6);
                String productTypeDescription = rs.getString(7);
                Product product =
                        new Product(
                                id,
                                name,
                                price,
                                description,
                                new ProductType(productTypeId, productTypeName, productTypeDescription));
                products.add(product);
            }
            cp.releaseConnection(connection);
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
        return products;
    }

    private static ProductType getProductTypeFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt(1);
        String name = rs.getString(2);
        String description = rs.getString(3);
        return new ProductType(id, name, description);
    }
}
