package controller.book;

import model.Book;
import model.Member;

import java.util.List;

public interface BookService {
    boolean addBook(Book book);
    boolean updateBook(Book book);
    boolean deleteBook(String id);
    Book searchBook(String id);
    List<?> getAll();
}
