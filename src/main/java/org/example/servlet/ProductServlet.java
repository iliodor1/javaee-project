package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.product.NewProduct;
import org.example.dto.product.ResponseProduct;
import org.example.dto.supplier.ResponseSupplier;
import org.example.exceptions.NotFoundException;
import org.example.services.product.ProductService;
import org.example.services.product.ProductServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.logging.Logger;

@WebServlet(urlPatterns = "/products/*")
public class ProductServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ProductService service;

    private static final Logger logger = Logger.getLogger("Logger");

    private ProductServlet(ProductService service) {
        this.service = service;
    }

    public ProductServlet() {
        this.service = ProductServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Получен get запрос");
        String uri = req.getRequestURI();
        String json;

        if (uri.contains("/products/")) {
            Long id = getIdFromUri(resp, uri);
            if (id == null) return;
            ResponseProduct responseProduct;
            try {
                responseProduct = service.get(id);
            } catch (NotFoundException e) {
                logger.warning(e.getMessage());
                resp.sendError(404, e.getMessage());
                return;
            }
            json = objectMapper.writeValueAsString(responseProduct);
        } else {
            Collection<ResponseProduct> responseProducts = service.getAll();
            json = objectMapper.writeValueAsString(responseProducts);
        }

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json; charset=UTF-8");
        out.print(json);
        out.flush();
        logger.info("Отправлен ответ по get запросу");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Получен запрос на удаление продукта");
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
        logger.info("Продукт успешно удален");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Получен запрос на добавление продукта");
        NewProduct newProduct = objectMapper.readValue(req.getReader(), NewProduct.class);

        ResponseProduct product;
        try {
            product = service.add(newProduct);
        } catch (NotFoundException e) {
            logger.warning(e.getMessage());
            resp.sendError(404, e.getMessage());
            return;
        }

        String json = objectMapper.writeValueAsString(product);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json; charset=UTF-8");
        out.print(json);
        out.flush();
        logger.info("Продукт добавлен");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Получен запрос на обновление продукта");
        String uri = req.getRequestURI();
        Long id = getIdFromUri(resp, uri);
        if (id == null) return;

        NewProduct newProduct = objectMapper.readValue(req.getReader(), NewProduct.class);

        ResponseProduct product;
        try {
            product = service.update(id, newProduct);
        } catch (NotFoundException e) {
            logger.warning(String.format(e.getMessage()));
            resp.sendError(404, e.getMessage());
            return;
        }

        String json = objectMapper.writeValueAsString(product);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json; charset=UTF-8");
        out.print(json);
        out.flush();
        logger.info("Данные продукта успешно обновлены");
    }

    private Long getIdFromUri(HttpServletResponse resp, String uri) throws IOException {
        Long id;
        try {
            id = Long.parseLong(uri.substring("/products/".length()));
        } catch (NumberFormatException e) {
            logger.warning("id продукта имеет некорректный формат");
            resp.sendError(400, "id продукта имеет некорректный формат");
            return null;
        }
        return id;
    }

}
