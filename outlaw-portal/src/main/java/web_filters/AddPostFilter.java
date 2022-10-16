package web_filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/posts/add")
public class AddPostFilter extends HttpFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        Boolean isAuth = (Boolean) req.getSession().getAttribute("isAuth");

        if (isAuth == null || !isAuth) {
            req.setAttribute("errorMessage", "Добавлять посты могут только авторизованные пользователи");
            req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, res);
            return;
        }

        chain.doFilter(req, res);
    }
}
