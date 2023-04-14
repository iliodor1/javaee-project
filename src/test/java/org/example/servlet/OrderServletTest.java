package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.order.ResponseOrder;
import org.example.exceptions.NotFoundException;
import org.example.services.order.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServletTest {

    @Mock
    private OrderService service;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private OrderServlet servlet;

    @Mock
    private PrintWriter printWriter;

    private ResponseOrder responseOrder;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @BeforeEach
    void setUp() {
        responseOrder = ResponseOrder.builder()
                                     .id(1L)
                                     .date(LocalDate.now().format(FORMATTER))
                                     .products(List.of(1L, 2L))
                                     .build();

    }

    @Test
    void whenGetExistOrder_thenReturnOrder() throws IOException, NotFoundException {
        when(request.getRequestURI()).thenReturn("/orders/1");
        when(service.get(1L)).thenReturn(responseOrder);
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doGet(request, response);

        verify(response).setContentType("application/json; charset=UTF-8");
        verify(printWriter).print(new ObjectMapper().writeValueAsString(responseOrder));
        verify(printWriter).flush();
    }

  @Test
    void whenGetNotExistOrder_thenReturnNotFoundStatus() throws IOException, NotFoundException {
        when(request.getRequestURI()).thenReturn("/orders/999");
        when(service.get(999L))
                .thenThrow(new NotFoundException("Заказ с id=999 не найден"));

        servlet.doGet(request, response);

        verify(response).sendError( 404, "Заказ с id=999 не найден");
    }

    @Test
    public void whenGetOrderWithInvalidId_thenReturnBadRequestStatus() throws Exception {
        when(request.getRequestURI()).thenReturn("/orders/invalidId");

        servlet.doGet(request, response);

        verify(response).sendError( 400, "id заказа имеет некорректный формат");
    }

    @Test
    void whenGetAllOrders_thenReturnAllOrders() throws IOException {
        when(request.getRequestURI()).thenReturn("/orders");
        when(service.getAll()).thenReturn(List.of(responseOrder));
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doGet(request, response);

        verify(response).setContentType("application/json; charset=UTF-8");
        verify(printWriter).print(new ObjectMapper().writeValueAsString(List.of(responseOrder)));
        verify(printWriter).flush();
    }

    @Test
    void whenAddOrder_thenReturnAddedOrder() throws IOException {
        when(request.getParameterValues("productId")).thenReturn(new String[]{"1", "2"});
        when(service.add(anyList())).thenReturn(responseOrder);
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doPost(request, response);

        verify(response).setContentType("application/json; charset=UTF-8");
        verify(printWriter).print(new ObjectMapper().writeValueAsString(responseOrder));
        verify(printWriter).flush();
    }



    @Test
    void whenDeleteExistOrder_thenReturnOkStatus() throws IOException, NotFoundException {
        when(request.getRequestURI()).thenReturn("/orders/1");

        servlet.doDelete(request, response);

        verify(service).delete(1L);
    }

    @Test
    void whenDeleteNotExistOrder_thenReturnNotFoundStatus() throws IOException, NotFoundException {
        when(request.getRequestURI()).thenReturn("/orders/999");
        when(service.delete(999L))
                .thenThrow(new NotFoundException("Заказ с id=999 не найден"));

        servlet.doDelete(request, response);

        verify(response).sendError( 404, "Заказ с id=999 не найден");
    }

    @Test
    public void whenDeleteOrderWithInvalidId_thenReturnBadRequestStatus() throws IOException {
        when(request.getRequestURI()).thenReturn("/orders/invalidId");

        servlet.doDelete(request, response);

        verify(response).sendError( 400, "id заказа имеет некорректный формат");
    }
}