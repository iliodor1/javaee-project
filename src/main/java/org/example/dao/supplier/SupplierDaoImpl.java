package org.example.dao.supplier;

import org.example.entities.Supplier;
import org.example.utils.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class SupplierDaoImpl implements SupplierDao {
    private static final SupplierDao INSTANCE = new SupplierDaoImpl();

    private SupplierDaoImpl() {
    }

    public static SupplierDao getInstance() {
        return INSTANCE;
    }

    @Override
    public Supplier save(Supplier supplier) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO suppliers(company_name, country) VALUES (?, ?)",
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
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE suppliers SET company_name = ?, country = ? WHERE id = ?")) {

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
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM suppliers WHERE id = ?"
             )) {

            preparedStatement.setLong(1, id);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Supplier> findById(Long id) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT s.*, p.id product_id " +
                             "FROM suppliers s " +
                             "LEFT JOIN products p ON s.id = p.supplier_id " +
                             "WHERE s.id = ?"
             )) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Supplier supplier = null;
            List<Long> productIds = new ArrayList<>();

            while (resultSet.next()) {
                if (supplier == null) {
                    supplier = Supplier.builder()
                                       .id(resultSet.getLong("id"))
                                       .companyName(resultSet.getString("company_name"))
                                       .country(resultSet.getString("country"))
                                       .build();
                }

                Long productId = resultSet.getLong("product_id");
                if (!resultSet.wasNull()) {
                    productIds.add(productId);
                }
            }
            if (supplier != null) {
                supplier.setProductIds(productIds);
            }

            return Optional.ofNullable(supplier);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<Supplier> findAll() {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT s.*, p.id product_id " +
                             "FROM suppliers s " +
                             "LEFT JOIN products p ON s.id = p.supplier_id"
             )) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Supplier> suppliers = new ArrayList<>();
            List<Long> productIds = new ArrayList<>();

            Supplier supplier;
            while (resultSet.next()) {
                supplier = Supplier.builder()
                                   .id(resultSet.getLong("id"))
                                   .companyName(resultSet.getString("company_name"))
                                   .country(resultSet.getString("country"))
                                   .build();

                Long productId = resultSet.getLong("product_id");
                if (!resultSet.wasNull()) {
                    productIds.add(productId);
                    supplier.setProductIds(productIds);
                }

                suppliers.add(supplier);
            }

            return suppliers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
