package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.product.NewProduct;
import org.example.dto.product.ResponseProduct;
import org.example.exceptions.NotFoundException;
import org.example.services.product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServletTest {

    @InjectMocks
    private ProductServlet servlet;

    @Mock
    private ProductService service;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private PrintWriter printWriter;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private ResponseProduct responseProduct;
    private NewProduct newProduct;

    @BeforeEach
    void setUp() {
        responseProduct = ResponseProduct.builder()
                                         .id(1L)
                                         .price(BigDecimal.valueOf(100))
                                         .supplierId(1L)
                                         .build();

        newProduct = new NewProduct("name", 1, BigDecimal.valueOf(100), 1L);

    }

    @Test
    public void whenGetProduct_thenReturnProduct() throws Exception {
        when(request.getRequestURI()).thenReturn("/products/1");
        when(service.get(1L)).thenReturn(responseProduct);
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doGet(request, response);

        verify(response).setContentType("application/json; charset=UTF-8");
        verify(printWriter).print(new ObjectMapper().writeValueAsString(responseProduct));
        verify(printWriter).flush();
    }

    @Test
    void whenGetNotExistProduct_thenReturnNotFoundStatus() throws IOException, NotFoundException {
        when(request.getRequestURI()).thenReturn("/products/999");
        when(service.get(999L))
                .thenThrow(new NotFoundException("Продукт с id=999 не найден"));

        servlet.doGet(request, response);

        verify(response).sendError( 404, "Продукт с id=999 не найден");
    }

    @Test
    public void whenGetProductWithInvalidId_thenReturnBadRequestStatus() throws Exception {
        when(request.getRequestURI()).thenReturn("/products/invalidId");

        servlet.doGet(request, response);

        verify(response).sendError(400, "id продукта имеет некорректный формат");
    }

    @Test
    void whenGetAllProducts_thenReturnAllProducts() throws IOException {
        when(request.getRequestURI()).thenReturn("/products");
        when(service.getAll()).thenReturn(List.of(responseProduct));
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doGet(request, response);

        verify(response).setContentType("application/json; charset=UTF-8");
        verify(printWriter).print(new ObjectMapper().writeValueAsString(List.of(responseProduct)));
        verify(printWriter).flush();
    }

    @Test
    void whenAddSupplier_thenReturnAddedSupplier() throws IOException, NotFoundException {
        String jsonProduct = "{\"name\":\"Name1\",\"quantity\":1," +
                "\"price\":100,\"supplierId\":1}";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonProduct)));
        when(service.add(any(NewProduct.class))).thenReturn(responseProduct);
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doPost(request, response);

        verify(response).setContentType("application/json; charset=UTF-8");
        verify(printWriter).print(new ObjectMapper().writeValueAsString(responseProduct));
        verify(printWriter).flush();
    }

    @Test
    void whenUpdateProduct_thenReturnUpdatedProduct() throws IOException, NotFoundException {
        newProduct.setName("new name");
        newProduct.setPrice(BigDecimal.valueOf(200));
        responseProduct.setName("new name");
        responseProduct.setPrice(BigDecimal.valueOf(200));

        String json = objectMapper.writeValueAsString(newProduct);

        when(request.getRequestURI()).thenReturn("/products/1");
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        when(service.update(anyLong(), any(NewProduct.class))).thenReturn(responseProduct);
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doPut(request, response);

        verify(response).setContentType("application/json; charset=UTF-8");
        verify(printWriter).print(new ObjectMapper().writeValueAsString(responseProduct));
        verify(printWriter).flush();
    }

    @Test
    public void whenUpdateNotExistSupplier_thenReturnNotFoundStatus() throws Exception {
        newProduct.setName("new name");
        newProduct.setPrice(BigDecimal.valueOf(200));
        responseProduct.setName("new name");
        responseProduct.setPrice(BigDecimal.valueOf(200));

        String json = objectMapper.writeValueAsString(newProduct);

        when(request.getRequestURI()).thenReturn("/products/1");
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        when(service.update(anyLong(), any(NewProduct.class)))
                .thenThrow(new NotFoundException("Продукт с id=999 не найден"));

        servlet.doPut(request, response);

        verify(response).sendError( 404, "Продукт с id=999 не найден");
    }

    @Test
    void whenDeleteExistProduct_thenReturnOkStatus() throws IOException, NotFoundException {
        when(request.getRequestURI()).thenReturn("/products/1");

        servlet.doDelete(request, response);

        verify(service).delete(1L);
    }

    @Test
    void whenDeleteNotExistSupplier_thenReturnNotFoundStatus() throws IOException, NotFoundException {
        when(request.getRequestURI()).thenReturn("/products/999");
        when(service.delete(999L))
                .thenThrow(new NotFoundException("Продукт с id=999 не найден"));

        servlet.doDelete(request, response);

        verify(response).sendError( 404, "Продукт с id=999 не найден");
    }

    @Test
    public void whenDeleteNotExistProduct_thenReturnBadRequestStatus() throws IOException {
        when(request.getRequestURI()).thenReturn("/products/invalidId");

        servlet.doDelete(request, response);

        verify(response).sendError(400, "id продукта имеет некорректный формат");
    }
}