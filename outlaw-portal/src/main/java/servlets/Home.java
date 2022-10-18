package servlets;

import config.PostgresConnectionProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import models.Post;
import models.User;
import orm.SQLGenerator;
import repositories.EntityRepository;
import repositories.PostsRepository;
import repositories.UserRepository;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Logger;

@WebServlet(value = "/home")
@MultipartConfig()
public class Home extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("isAuth", req.getSession().getAttribute("isAuth"));
        req.setAttribute("authUser", req.getSession().getAttribute("authUser"));

        req.getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(req, resp);
    }

}
