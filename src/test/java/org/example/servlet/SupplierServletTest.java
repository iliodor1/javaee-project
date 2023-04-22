package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.supplier.NewSupplier;
import org.example.dto.supplier.ResponseSupplier;
import org.example.exceptions.NotFoundException;
import org.example.services.supplier.SupplierService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SupplierServletTest {

    @Mock
    private SupplierService service;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private SupplierServlet servlet;

    @Mock
    private PrintWriter printWriter;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private ResponseSupplier responseSupplier;
    private NewSupplier newSupplier;

    @BeforeEach
    void setUp() {
        responseSupplier = ResponseSupplier.builder()
                                           .id(1L)
                                           .companyName("Company 1")
                                           .country("Country")
                                           .build();

        newSupplier = new NewSupplier("Company 1", "Country");

    }

    @Test
    void whenGetExistSupplier_thenReturnSupplier() throws IOException, NotFoundException {
        when(request.getRequestURI()).thenReturn("/suppliers/1");
        when(service.get(1L)).thenReturn(responseSupplier);
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doGet(request, response);

        verify(response).setContentType("application/json; charset=UTF-8");
        verify(printWriter).print(new ObjectMapper().writeValueAsString(responseSupplier));
        verify(printWriter).flush();
    }

    @Test
    void whenGetNotExistSupplier_thenReturnNotFoundStatus() throws IOException, NotFoundException {
        when(request.getRequestURI()).thenReturn("/suppliers/999");
        when(service.get(999L))
                .thenThrow(new NotFoundException("Поставщик с id=999 не найден"));

        servlet.doGet(request, response);

        verify(response).sendError( 404, "Поставщик с id=999 не найден");
    }

    @Test
    public void whenGetSupplierWithInvalidId_thenReturnBadRequestStatus() throws Exception {
        when(request.getRequestURI()).thenReturn("/suppliers/invalidId");

        servlet.doGet(request, response);

        verify(response).sendError( 400, "id поставщика имеет некорректный формат");
    }

    @Test
    void whenGetAllSuppliers_thenReturnAllSuppliers() throws IOException {
        when(request.getRequestURI()).thenReturn("/suppliers");
        when(service.getAll()).thenReturn(List.of(responseSupplier));
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doGet(request, response);

        verify(response).setContentType("application/json; charset=UTF-8");
        verify(printWriter).print(new ObjectMapper().writeValueAsString(List.of(responseSupplier)));
        verify(printWriter).flush();
    }

    @Test
    void whenAddSupplier_thenReturnAddedSupplier() throws IOException {
        String jsonSupplier = "{\"companyName\":\"Company 1\",\"country\":\"Country\"}";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonSupplier)));
        when(service.add(any(NewSupplier.class))).thenReturn(responseSupplier);
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doPost(request, response);

        verify(response).setContentType("application/json; charset=UTF-8");
        verify(printWriter).print(new ObjectMapper().writeValueAsString(responseSupplier));
        verify(printWriter).flush();
    }

    @Test
    void whenUpdateSupplier_thenReturnUpdatedSupplier() throws IOException, NotFoundException {
        newSupplier.setCompanyName("Company 2");
        responseSupplier.setCompanyName("Company 2");

        String json = objectMapper.writeValueAsString(newSupplier);

        when(request.getRequestURI()).thenReturn("/suppliers/1");
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        when(service.update(anyLong(), any(NewSupplier.class))).thenReturn(responseSupplier);
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doPut(request, response);

        verify(response).setContentType("application/json; charset=UTF-8");
        verify(printWriter).print(new ObjectMapper().writeValueAsString(responseSupplier));
        verify(printWriter).flush();
    }

    @Test
    public void whenUpdateNotExistSupplier_thenReturnNotFoundStatus() throws Exception {
        newSupplier.setCompanyName("Company 2");
        responseSupplier.setCompanyName("Company 2");

        String json = objectMapper.writeValueAsString(newSupplier);

        when(request.getRequestURI()).thenReturn("/suppliers/999");
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        when(service.update(anyLong(), any(NewSupplier.class)))
                .thenThrow(new NotFoundException("Поставщик с id=999 не найден"));

        servlet.doPut(request, response);

        verify(response).sendError( 404, "Поставщик с id=999 не найден");
    }

    @Test
    void whenDeleteExistSupplier_thenReturnOkStatus() throws IOException, NotFoundException {
        when(request.getRequestURI()).thenReturn("/suppliers/1");

        servlet.doDelete(request, response);

        verify(service).delete(1L);
    }

    @Test
    void whenDeleteNotExistSupplier_thenReturnNotFoundStatus() throws IOException, NotFoundException {
        when(request.getRequestURI()).thenReturn("/suppliers/999");
        when(service.delete(999L))
                .thenThrow(new NotFoundException("Поставщик с id=999 не найден"));

        servlet.doDelete(request, response);

        verify(response).sendError( 404, "Поставщик с id=999 не найден");
    }

    @Test
    public void whenDeleteProductWithInvalidId_thenReturnBadRequestStatus() throws IOException {
        when(request.getRequestURI()).thenReturn("/suppliers/invalidId");

        servlet.doDelete(request, response);

        verify(response).sendError( 400, "id поставщика имеет некорректный формат");
    }
}