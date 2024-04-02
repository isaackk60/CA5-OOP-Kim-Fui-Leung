
package org.ca5.DAOs;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import org.ca5.DTOs.Book;
import org.ca5.Exceptions.DaoException;

public interface BookDaoInterface {

    public List<Book> getAllBooks() throws DaoException;

    public Book insertBook(Book newBook);

    public void deleteBookById(int id);

    public Book getBookById(int id) throws DaoException;

    public List<Book> findBooksUsingFilter(Comparator<Book> comparator) throws DaoException;

    public Book updateBook(int id, Book updatedBook) throws DaoException;

    public String bookToJson(Book b);

    public String booksListToJson(List<Book> bookList);
}

