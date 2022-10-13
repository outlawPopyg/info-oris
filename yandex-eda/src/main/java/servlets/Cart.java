package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import listeners.SessionCreatedListener;
import models.Product;
import repositories.EntityRepository;
import repositories.ProductsRepository;

import java.io.IOException;
import java.util.List;

@WebServlet("/cart")
public class Cart extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ready = req.getParameter("ready");

        SessionCreatedListener.TAKE.add(SessionCreatedListener.getActiveOrders().get(Integer.parseInt(ready)));
        SessionCreatedListener.deleteOrder(Integer.parseInt(ready));

        resp.sendRedirect("/take");
    }
}
