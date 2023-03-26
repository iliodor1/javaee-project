package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.product.NewProduct;
import org.example.dto.product.ResponseProduct;
import org.example.services.product.ProductService;
import org.example.services.product.ProductServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/products/*")
public class ProductServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final ProductService service = ProductServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = req.getRequestURI();
        Long id = Long.parseLong(uri.substring("/products/".length()));

        ResponseProduct product = service.get(id);

        String json = objectMapper.writeValueAsString(product);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json; charset=UTF-8");
        out.print(json);
        out.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String uri = req.getRequestURI();
        Long id = Long.parseLong(uri.substring("/products/".length()));

        service.delete(id);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        NewProduct newProduct = objectMapper.readValue(req.getReader(), NewProduct.class);

        ResponseProduct product = service.add(newProduct);

        String json = objectMapper.writeValueAsString(product);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json; charset=UTF-8");
        out.print(json);
        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = req.getRequestURI();
        Long id = Long.parseLong(uri.substring("/products/".length()));
        NewProduct newProduct = objectMapper.readValue(req.getReader(), NewProduct.class);

        ResponseProduct product = service.update(id, newProduct);

        String json = objectMapper.writeValueAsString(product);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json; charset=UTF-8");
        out.print(json);
        out.flush();
    }

}
