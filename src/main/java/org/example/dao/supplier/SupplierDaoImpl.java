package org.example.dao.supplier;

import org.example.entities.Product;
import org.example.entities.Supplier;
import org.example.utils.ConnectionPool;

import java.sql.*;
import java.util.*;

public class SupplierDaoImpl implements SupplierDao {
    private static final SupplierDao INSTANCE = new SupplierDaoImpl();
    private static final String INSERT_SUPPLIER =
            "INSERT INTO suppliers(company_name, country) " +
                    "VALUES (?, ?)";
    private static final String UPDATE_SUPPLIER =
            "UPDATE suppliers " +
                    "SET company_name = ?, country = ? " +
                    "WHERE id = ?";

    private static final String FIND_SUPPLIER_BY_ID =
            "SELECT s.*, p.id product_id, product_name, quantity, price, supplier_id " +
                    "FROM suppliers s " +
                    "LEFT JOIN products p ON s.id = p.supplier_id " +
                    "WHERE s.id = ?";

    private static final String FIND_ALL_SUPPLIERS =
            "SELECT s.*, p.id product_id, product_name, quantity, price, supplier_id " +
                    "FROM suppliers s " +
                    "LEFT JOIN products p ON s.id = p.supplier_id";


    private static final String DELETE_SUPPLIER =
            "DELETE FROM suppliers " +
                    "WHERE id = ?";

    private SupplierDaoImpl() {
    }

    public static SupplierDao getInstance() {
        return INSTANCE;
    }

    @Override
    public Supplier save(Supplier supplier) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SUPPLIER,
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, supplier.getCompanyName());
            preparedStatement.setString(2, supplier.getCountry());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                supplier.setId(generatedKeys.getLong("id"));
            }

            return supplier;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Supplier update(Supplier supplier) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SUPPLIER)) {

            preparedStatement.setString(1, supplier.getCompanyName());
            preparedStatement.setString(2, supplier.getCountry());
            preparedStatement.setLong(3, supplier.getId());
            preparedStatement.executeUpdate();

            return supplier;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SUPPLIER)) {

            preparedStatement.setLong(1, id);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Supplier> findById(Long id) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_SUPPLIER_BY_ID)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Supplier supplier = null;
            Set<Product> products = new HashSet<>();

            while (resultSet.next()) {
                if (supplier == null) {
                    supplier = Supplier.builder()
                                       .id(resultSet.getLong("id"))
                                       .companyName(resultSet.getString("company_name"))
                                       .country(resultSet.getString("country"))
                                       .build();
                }

                Product product = Product.builder()
                                         .id(resultSet.getLong("product_id"))
                                         .supplier(Supplier.builder().id(supplier.getId()).build())
                                         .price(resultSet.getBigDecimal("price"))
                                         .quantity(resultSet.getInt("quantity"))
                                         .name(resultSet.getString("product_name"))
                                         .build();

                if (!resultSet.wasNull()) {
                    products.add(product);
                }
            }
            if (supplier != null) {
                supplier.setProducts(products);
            }

            return Optional.ofNullable(supplier);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<Supplier> findAll() {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SUPPLIERS)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            Set<Supplier> suppliers = new HashSet<>();
            Set<Product> products = new HashSet<>();
            Supplier supplier;

            while (resultSet.next()) {
                supplier = Supplier.builder()
                                   .id(resultSet.getLong("id"))
                                   .companyName(resultSet.getString("company_name"))
                                   .country(resultSet.getString("country"))
                                   .build();

                Product product = Product.builder()
                                         .id(resultSet.getLong("product_id"))
                                         .supplier(Supplier.builder().id(supplier.getId()).build())
                                         .price(resultSet.getBigDecimal("price"))
                                         .quantity(resultSet.getInt("quantity"))
                                         .name(resultSet.getString("product_name"))
                                         .build();

                if (!resultSet.wasNull()) {
                    products.add(product);
                    supplier.setProducts(products);
                }
                suppliers.add(supplier);
            }

            return suppliers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
