package org.example.servlet;

import com.google.gson.Gson;
import org.example.servlet.entities.Product;
import org.example.servlet.services.ProductService;
import org.example.servlet.services.ProductServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/products/*")
public class ProductServlet extends HttpServlet {
    private Gson gson = new Gson();

    ProductService service = new ProductServiceImpl();

   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = req.getRequestURI();
        Long id = Long.parseLong(uri.substring("/products/".length()));

        Product product = service.findById(2L);

        String json = gson.toJson(product);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json; charset=UTF-8");
        out.print(json);
        out.flush();

    }
}
