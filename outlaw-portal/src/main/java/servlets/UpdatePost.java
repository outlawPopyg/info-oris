package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import models.Post;
import models.User;
import repositories.EntityRepository;
import repositories.PostsRepository;
import util.Util;

import java.io.IOException;
import java.io.InputStream;

@WebServlet("/posts/update")
@MultipartConfig
public class UpdatePost extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Util.handleAddAndUpdate(req, true);

        resp.sendRedirect("/posts");
    }
}
