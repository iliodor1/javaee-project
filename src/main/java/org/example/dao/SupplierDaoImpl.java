package org.example.dao;

import org.example.entities.Product;
import org.example.entities.Supplier;
import org.example.utils.ConnectionPool;

import java.sql.*;
import java.util.Collection;

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
        return false;
    }

    @Override
    public Supplier findById(Long id) {
        return null;
    }

    @Override
    public Collection<Supplier> findAll() {
        return null;
    }
}
