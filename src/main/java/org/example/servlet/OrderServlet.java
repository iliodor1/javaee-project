package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.order.ResponseOrder;
import org.example.services.order.OrderService;
import org.example.services.order.OrderServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/orders/*")
public class OrderServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final OrderService service = OrderServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Long> productIds = Arrays.stream(req.getParameterValues("productId"))
                                      .map(Long::parseLong)
                                      .collect(Collectors.toList());

        ResponseOrder order = service.add(productIds);

        String json = objectMapper.writeValueAsString(order);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json; charset=UTF-8");
        out.print(json);
        out.flush();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = req.getRequestURI();

        Long id = Long.parseLong(uri.substring("/orders/".length()));
        ResponseOrder responseOrder = service.get(id);
        String json = objectMapper.writeValueAsString(responseOrder);

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json; charset=UTF-8");
        out.print(json);
        out.flush();
    }
}
