package web_filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;

import java.io.IOException;

@WebFilter("/posts/add/check")
public class PostCheckFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        User user = (User) req.getSession().getAttribute("authUser");
        if (user != null) {
            if (!user.getRole().equals("admin")) {
                req.setAttribute("errorMessage", "Доступ на эту страницу разрешен только администрации сайта");
                req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, res);
                return;
            }
        } else {
            req.setAttribute("errorMessage", "Доступ на эту страницу разрешен только администрации сайта");
            req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, res);
            return;
        }

        chain.doFilter(req, res);
    }
}
