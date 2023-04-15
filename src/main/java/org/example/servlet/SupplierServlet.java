package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.supplier.NewSupplier;
import org.example.dto.supplier.ResponseSupplier;
import org.example.exceptions.NotFoundException;
import org.example.services.supplier.SupplierService;
import org.example.services.supplier.SupplierServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.logging.Logger;

@WebServlet(urlPatterns = "/suppliers/*")
public class SupplierServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = Logger.getLogger("Logger");

    private final SupplierService service;

    public SupplierServlet() {
        service = SupplierServiceImpl.getInstance();
    }

    private SupplierServlet(SupplierService service) {
        this.service = service;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Получен get запрос");
        String uri = req.getRequestURI();
        String json;

        if (uri.contains("/suppliers/")) {
            Long id = getIdFromUri(resp, uri);
            if (id == null) return;
            ResponseSupplier responseSupplier;
            try {
                responseSupplier = service.get(id);
            } catch (NotFoundException e) {
                logger.warning(e.getMessage());
                resp.sendError(404, e.getMessage());
                return;
            }
            json = objectMapper.writeValueAsString(responseSupplier);
        } else {
            Collection<ResponseSupplier> responseSuppliers = service.getAll();
            json = objectMapper.writeValueAsString(responseSuppliers);
        }

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json; charset=UTF-8");
        out.print(json);
        out.flush();
        logger.info("Отправлен ответ по get запросу");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Получен запрос на добавление поставщика");
        NewSupplier newSupplier = objectMapper.readValue(req.getReader(), NewSupplier.class);

        ResponseSupplier supplier = service.add(newSupplier);

        String json = objectMapper.writeValueAsString(supplier);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json; charset=UTF-8");
        out.print(json);
        out.flush();
        logger.info("Поставщик добавлен");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Получен запрос на обновление поставщика");
        String uri = req.getRequestURI();
        Long id = getIdFromUri(resp, uri);
        if (id == null) return;

        NewSupplier newSupplier = objectMapper.readValue(req.getReader(), NewSupplier.class);

        ResponseSupplier supplier;
        try {
            supplier = service.update(id, newSupplier);
        } catch (NotFoundException e) {
            logger.warning(String.format("Поставщик с id=%s не найден", id));
            resp.sendError(404, e.getMessage());
            return;
        }

        String json = objectMapper.writeValueAsString(supplier);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json; charset=UTF-8");
        out.print(json);
        out.flush();
        logger.info("Данные поставщика успешно обновлены");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Получен запрос на удаление поставщика");
        String uri = req.getRequestURI();
        Long id = getIdFromUri(resp, uri);
        if (id == null) return;

        try {
            service.delete(id);
        } catch (NotFoundException e) {
            logger.warning(e.getMessage());
            resp.sendError(404, e.getMessage());
        }
        logger.info("Поставщик успешно удален");
    }

    private Long getIdFromUri(HttpServletResponse resp, String uri) throws IOException {
        Long id;
        try {
            id = Long.parseLong(uri.substring("/suppliers/".length()));
        } catch (NumberFormatException e) {
            logger.warning(e.getMessage());
            resp.sendError(400, "id поставщика имеет некорректный формат");
            return null;
        }
        return id;
    }


}
