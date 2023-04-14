package org.example.dao.supplier;

import org.example.entities.Supplier;
import org.example.utils.ConnectionPool;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class SupplierDaoTest {

    SupplierDao supplierDao = SupplierDaoImpl.getInstance();
    String SUPPLIER_SQL = "insert into suppliers(company_name, country) values (?, ?)";

    Supplier newSupplier;

    @Test
    void whenFindByIdExistSupplier_thenReturnOptionalSupplier() {
        Optional<Supplier> supplier = supplierDao.findById(1L);

        assertTrue(supplier.isPresent());
        assertEquals(1L, supplier.get().getId());
        assertEquals("Company1", supplier.get().getCompanyName());
        assertEquals("Country1", supplier.get().getCountry());
        assertEquals(2, supplier.get().getProducts().size());
        assertTrue(
                supplier.get()
                        .getProducts()
                        .stream()
                        .anyMatch(p -> p.getName().equals("Name1"))
        );
        assertTrue(
                supplier.get()
                        .getProducts()
                        .stream()
                        .anyMatch(p -> p.getName().equals("Name2"))
        );
    }

    @Test
    void whenFindByIdNotExistSupplier_thenReturnOptionalEmpty() {
        Optional<Supplier> supplier = supplierDao.findById(999L);

        assertTrue(supplier.isEmpty());
    }

    @Test
    void whenFindAllSuppliers_thenReturnListOfSuppliers() {
        Collection<Supplier> suppliers = supplierDao.findAll();

        assertEquals(3, suppliers.size());
    }


    @Test
    void whenSaveSupplier_thenReturnSavedSupplier() {
        Supplier supplier = Supplier.builder()
                                    .companyName("Company2")
                                    .country("Country2")
                                    .build();

        Supplier savedSupplier = supplierDao.save(supplier);

        assertEquals(supplier.getCompanyName(), savedSupplier.getCompanyName());
        assertEquals(supplier.getCountry(), savedSupplier.getCountry());

        deleteSupplier(savedSupplier.getId());
    }

    @Test
    void whenSaveEmptySupplier_thenThrowRuntimeException() {
        Supplier supplier = Supplier.builder()
                                    .build();

        assertThrows(RuntimeException.class, () -> supplierDao.save(supplier));
    }

    @Test
    void whenUpdateExistSupplier_thenReturnUpdatedSupplier() {
        insertNewSupplier("NewCompany", "Country");

        String newCompanyName = "NewCompanyName";
        String newCountry = "NewCountry";
        Long id = newSupplier.getId();

        newSupplier.setCompanyName(newCompanyName);
        newSupplier.setCountry(newCountry);

        Supplier updatedSupplier = supplierDao.update(newSupplier);

        assertEquals(id, updatedSupplier.getId());
        assertEquals(newCompanyName, newSupplier.getCompanyName());
        assertEquals(newCountry, newSupplier.getCountry());

        deleteSupplier(newSupplier.getId());
    }

    @Test
    void whenDeleteExistSupplier_thenReturnTrue() {
        insertNewSupplier("NewCompany", "Country");

        boolean isDelete = supplierDao.delete(newSupplier.getId());

        assertTrue(isDelete);
    }

    @Test
    void whenDeleteNotExistSupplier_thenReturnFalse() {
        boolean isDelete = supplierDao.delete(999L);

        assertFalse(isDelete);
    }

    private void insertNewSupplier(String companyName, String country) {
        newSupplier = Supplier.builder()
                              .companyName(companyName)
                              .country(country)
                              .build();

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SUPPLIER_SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, newSupplier.getCompanyName());
            preparedStatement.setString(2, newSupplier.getCountry());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                newSupplier.setId(generatedKeys.getLong("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteSupplier(Long id) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement("delete from suppliers where id = ?")) {
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
