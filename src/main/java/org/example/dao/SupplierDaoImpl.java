package org.example.dao;

import org.example.entities.Supplier;
import org.example.utils.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        return null;
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
    public Supplier findById(Long id) {
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

            return supplier;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<Supplier> findAll() {
        return null;
    }
}
