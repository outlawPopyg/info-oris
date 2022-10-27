package services;

import models.Comment;
import models.Entity;
import models.Post;
import repositories.EntityRepository;
import repositories.PostsRepository;

import java.util.List;
import java.util.Optional;

public class PostsService {
    private static final EntityRepository repository = new PostsRepository();

    public List<Post> getAllPosts() {
        return (List<Post>) repository.findAll();
    }

    public Post getPost(Long id) {
        Optional<Entity> optional = repository.findById(id);

        if (optional.isPresent()) {
            return (Post) optional.get();
        } else {
            throw new IllegalArgumentException("No such post");
        }
    }

    public void updatePost(Post post) {
        repository.update(post);
    }

    public void deletePost(Long id) {
        repository.delete(id);
    }

    public Post savePost(Post post) {
        return (Post) repository.save(post);
    }
}
