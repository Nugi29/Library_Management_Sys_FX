package controller.book;

import model.Book;
import model.Member;

import java.util.List;

public interface BookService {
    boolean addBook(Book member);
    boolean updateBook(Book member);
    boolean deleteBook(String id);
    Book searchBook(String id);
    List<?> getAll();
}
