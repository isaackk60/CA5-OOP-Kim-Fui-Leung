
package org.ca5.DAOs;

import java.util.List;
import java.util.Scanner;

import org.ca5.DTOs.Book;
import org.ca5.Exceptions.DaoException;

public interface BookDaoInterface {

    public List<Book> getAllBooks() throws DaoException;

    public void insertBook(Scanner scanner);

    public void deleteBookById(int id);

    public Book getBookById(int id) throws DaoException;
}

