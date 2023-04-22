package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.order.ResponseOrder;
import org.example.exceptions.NotFoundException;
import org.example.services.order.OrderService;
import org.example.services.order.OrderServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/orders/*")
public class OrderServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger("Logger");

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final OrderService service;

    public OrderServlet() {
        service = OrderServiceImpl.getInstance();
    }

    private OrderServlet(OrderService service) {
        this.service = service;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        logger.info("Получен запрос на добавление заказа");

        List<Long> productIds = Arrays.stream(req.getParameterValues("productId"))
                                      .map(Long::parseLong)
                                      .collect(Collectors.toList());

        ResponseOrder order = service.add(productIds);

        String json;
        PrintWriter out;
        try {
            json = objectMapper.writeValueAsString(order);
            out = resp.getWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        resp.setContentType("application/json; charset=UTF-8");
        out.print(json);
        out.flush();
        logger.info("Заказ добавлен");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Получен get запрос");
        String uri = req.getRequestURI();
        String json;

        if (uri.contains("/orders/")) {
            Long id = getIdFromUri(resp, uri);
            if (id == null) return;
            ResponseOrder responseOrder;
            try {
                responseOrder = service.get(id);
            } catch (NotFoundException e) {
                logger.warning(e.getMessage());
                resp.sendError(404, e.getMessage());
                return;
            }
            json = objectMapper.writeValueAsString(responseOrder);
        } else {
            Collection<ResponseOrder> responseOrders = service.getAll();
            json = objectMapper.writeValueAsString(responseOrders);
        }

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json; charset=UTF-8");
        out.print(json);
        out.flush();
        logger.info("Отправлен ответ по get запросу");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Получен запрос на удаление заказа");
        String uri = req.getRequestURI();
        Long id = getIdFromUri(resp, uri);
        if (id == null) return;

        try {
            service.delete(id);
        } catch (NotFoundException e) {
            logger.warning(e.getMessage());
            resp.sendError(404, e.getMessage());
            return;
        }
        logger.info("Заказ успешно удален");
    }

    private Long getIdFromUri(HttpServletResponse resp, String uri) throws IOException {
        Long id;
        try {
            id = Long.parseLong(uri.substring("/orders/".length()));
        } catch (NumberFormatException e) {
            logger.warning("id заказа имеет некорректный формат");
            resp.sendError(400, "id заказа имеет некорректный формат");
            return null;
        }
        return id;
    }
}
