package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.supplier.NewSupplier;
import org.example.dto.supplier.ResponseSupplier;
import org.example.services.supplier.SupplierService;
import org.example.services.supplier.SupplierServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

@WebServlet(urlPatterns = "/suppliers/*")
public class SupplierServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final SupplierService service = SupplierServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = req.getRequestURI();
        String json;

        if (uri.contains("/suppliers/")) {
            Long id = Long.parseLong(uri.substring("/suppliers/".length()));
            ResponseSupplier responseSupplier = service.get(id);
            json = objectMapper.writeValueAsString(responseSupplier);
        } else {
            Collection<ResponseSupplier> responseSuppliers = service.getAll();
            json = objectMapper.writeValueAsString(responseSuppliers);
        }

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json; charset=UTF-8");
        out.print(json);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        NewSupplier newSupplier = objectMapper.readValue(req.getReader(), NewSupplier.class);

        ResponseSupplier supplier = service.add(newSupplier);

        String json = objectMapper.writeValueAsString(supplier);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json; charset=UTF-8");
        out.print(json);
        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = req.getRequestURI();
        Long id = Long.parseLong(uri.substring("/suppliers/".length()));

        NewSupplier newSupplier = objectMapper.readValue(req.getReader(), NewSupplier.class);

        ResponseSupplier supplier = service.update(id, newSupplier);

        String json = objectMapper.writeValueAsString(supplier);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json; charset=UTF-8");
        out.print(json);
        out.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String uri = req.getRequestURI();
        Long id = Long.parseLong(uri.substring("/suppliers/".length()));

        service.delete(id);
    }
}
