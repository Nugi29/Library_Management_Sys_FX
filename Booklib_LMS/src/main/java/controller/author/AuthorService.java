package controller.author;

import model.Author;

import java.util.List;

public interface AuthorService {
    boolean addAuthor(Author author);
    boolean updateAuthor(Author author);
    boolean deleteAuthor(String id);
    Author searchAuthor(String id);
    List<Author> getAll();
}
