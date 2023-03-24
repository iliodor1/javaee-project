package org.example.servlet;

import com.google.gson.Gson;
import org.example.dto.ResponseProduct;
import org.example.entities.Product;
import org.example.services.ProductService;
import org.example.services.ProductServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/products/*")
public class ProductServlet extends HttpServlet {
    private final Gson gson = new Gson();

    private final ProductService service = ProductServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = req.getRequestURI();
        Long id = Long.parseLong(uri.substring("/products/".length()));

        ResponseProduct product = service.get(id);

        String json = gson.toJson(product);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json; charset=UTF-8");
        out.print(json);
        out.flush();
    }


}
