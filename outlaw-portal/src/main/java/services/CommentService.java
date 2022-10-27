package services;

import models.Comment;
import repositories.CommentRepository;

import java.util.List;

public class CommentService {
    private static final CommentRepository repository = new CommentRepository();

    public void saveComment(Comment comment) {
        repository.save(comment);
    }

    public List<Comment> getAllComments() {
        return (List<Comment>) repository.findAll();
    }

    public void deleteComment(Long id) {
        repository.delete(id);
    }
}
