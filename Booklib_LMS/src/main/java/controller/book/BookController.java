package controller.book;

import model.Book;

import java.util.List;

public class BookController implements BookService {
    @Override
    public boolean addBook(Book member) {

        return false;
    }

    @Override
    public boolean updateBook(Book member) {
        return false;
    }

    @Override
    public boolean deleteBook(String id) {
        return false;
    }

    @Override
    public Book searchBook(String id) {
        return null;
    }

    @Override
    public List<Book> getAll() {
        return null;
    }
}
