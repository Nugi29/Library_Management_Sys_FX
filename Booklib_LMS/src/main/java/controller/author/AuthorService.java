package controller.author;

import model.Author;

import java.util.List;

public interface AuthorService {
    boolean addAuthor(Author author);
    boolean updateAuthor(Author author);
    boolean deleteAuthor(String id);
    Author searchAuthorById(String id);
    Author searchAuthorByName(String name);
    List<Author> getAll();
}
