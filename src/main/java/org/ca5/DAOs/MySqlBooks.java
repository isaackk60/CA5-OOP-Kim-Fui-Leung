package org.ca5.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import org.ca5.BusinessObjects.BookPageComparatorOver400;
import org.ca5.BusinessObjects.BookPageComparatorUnder400;
import org.ca5.DTOs.Book;
import org.ca5.Exceptions.DaoException;

public class MySqlBooks extends MySqlDao implements BookDaoInterface {

    public List<Book> getAllBooks() throws DaoException {
        List<Book> booksList = new ArrayList<>();
        try (Connection connection = this.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM books");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            System.out.println("\nViewing Database:");

            while (resultSet.next()) {
                int bookId = resultSet.getInt("id");
                String bookTitle = resultSet.getString("Title");
                String bookGenre = resultSet.getString("Genre");
                String bookAuthor = resultSet.getString("Author");
                int bookPages = resultSet.getInt("Pages");
                boolean bookSeries = resultSet.getBoolean("Series");
                int bookStock = resultSet.getInt("Stock");
                double bookRating = resultSet.getDouble("Rating");
                String bookDescription = resultSet.getString("Description");
                String bookPublisher = resultSet.getString("Publisher");

                Book book = new Book(bookId, bookTitle, bookGenre, bookAuthor, bookPages,
                        bookSeries, bookStock,
                        bookRating, bookDescription, bookPublisher);
                booksList.add(book);
            }
            return booksList;

        } catch (SQLException ex) {
            throw new DaoException();
        }

    }


    /**

     * Author:  Jamie Duffy Creagh
     * Date: 8-03-24

     */
    public void insertBook(Scanner scanner) {
        System.out.println("\nAdding to Database:");

        System.out.println(" ");
        scanner.nextLine();

        System.out.println("Enter book title:");
        String title = scanner.nextLine();

        System.out.println("Enter book genre:");
        String genre = scanner.nextLine();

        System.out.println("Enter book author:");
        String author = scanner.nextLine();

        System.out.println("Enter number of pages:");
        int pages = scanner.nextInt();

        System.out.println("Is the book part of a series? (true/false):");
        boolean series = scanner.nextBoolean();

        System.out.println("Enter stock quantity:");
        int stock = scanner.nextInt();

        System.out.println("Enter book rating:");
        double rating = scanner.nextDouble();

        scanner.nextLine();

        System.out.println("Enter book description:");
        String description = scanner.nextLine();

        System.out.println("Enter book publisher:");
        String publisher = scanner.nextLine();

        String query1 = "INSERT INTO library.books (Title, Genre, Author, Pages, Series, Stock, Rating, Description, Publisher) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = this.getConnection();

             PreparedStatement preparedStatement1 = connection.prepareStatement(query1)) {

            preparedStatement1.setString(1, title);
            preparedStatement1.setString(2, genre);
            preparedStatement1.setString(3, author);
            preparedStatement1.setInt(4, pages);
            preparedStatement1.setBoolean(5, series);
            preparedStatement1.setInt(6, stock);
            preparedStatement1.setDouble(7, rating);
            preparedStatement1.setString(8, description);
            preparedStatement1.setString(9, publisher);

            preparedStatement1.executeUpdate();

            System.out.println("Book added successfully!");

        } catch (SQLException ex) {
            System.out.println(
                    "Failed to connect to the database - check MySQL is running and that you are using the correct database details");
            ex.printStackTrace();
        }
    }


    /**

     * Author:  Kim Fui Leung
     * Date: 8-03-24

     */
    public void deleteBookById(int id) {

        String queryDelete = "DELETE FROM books WHERE id = ?";
        try (Connection connection = this.getConnection();

             PreparedStatement preparedStatement = connection.prepareStatement(queryDelete);) {

            preparedStatement.setInt(1, id);

            System.out.println("Connected to the database");
            System.out.println("Building a PreparedStatement to delete row " + id +
                    " in database.");

            preparedStatement.executeUpdate();
        }

        catch (SQLException ex) {
            System.out.println(
                    "Failed to connect to the database - check MySQL is running and that you are using the correct database details");
            ex.printStackTrace();
        }
    }


    /**

     * Author:  Aoife Murphy
     * Date: 8-03-24

     */
    public Book getBookById(int id) throws DaoException {
        String query = "SELECT * FROM books WHERE id = ?";
        Book book = null;

        try (Connection connection = this.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int bookId = resultSet.getInt("id");
                    String bookTitle = resultSet.getString("Title");
                    String bookGenre = resultSet.getString("Genre");
                    String bookAuthor = resultSet.getString("Author");
                    int bookPages = resultSet.getInt("Pages");
                    boolean bookSeries = resultSet.getBoolean("Series");
                    int bookStock = resultSet.getInt("Stock");
                    double bookRating = resultSet.getDouble("Rating");
                    String bookDescription = resultSet.getString("Description");
                    String bookPublisher = resultSet.getString("Publisher");

                    book = new Book(bookId, bookTitle, bookGenre, bookAuthor, bookPages, bookSeries, bookStock,
                            bookRating, bookDescription, bookPublisher);
                } else {
                    throw new DaoException("Book with ID " + id + " not found.");
                }
            }
        } catch (SQLException ex) {
            System.out.println(
                    "Failed to connect to the database - check MySQL is running and that you are using the correct database details");
            ex.printStackTrace();
        }

        return book;
    }

    /**

     * Author:  Aoife Murphy
     * Date: 13-03-24

     */
    public Book updateBook(int id, Book updatedBook) throws DaoException {
        String query = "UPDATE books SET Title=?, Genre=?, Author=?, Pages=?, Series=?, Stock=?, Rating=?, Description=?, Publisher=? WHERE id=?";

        try (Connection connection = this.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, updatedBook.getTitle());
            preparedStatement.setString(2, updatedBook.getGenre());
            preparedStatement.setString(3, updatedBook.getAuthor());
            preparedStatement.setInt(4, updatedBook.getPages());
            preparedStatement.setBoolean(5, updatedBook.isSeries());
            preparedStatement.setInt(6, updatedBook.getStock());
            preparedStatement.setDouble(7, updatedBook.getRating());
            preparedStatement.setString(8, updatedBook.getDescription());
            preparedStatement.setString(9, updatedBook.getPublisher());
            preparedStatement.setInt(10, id);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated == 0) {
                throw new DaoException("Failed to update book with ID " + id + ". Book not found.");
            } else {
                System.out.println("Book updated successfully!");
                return updatedBook;
            }

        } catch (SQLException ex) {
            throw new DaoException("Error updating book with ID " + id + ": " + ex.getMessage());
        }
    }





    /**

     * Author:  Kim Fui Leung
     * Date: 13-03-24

     */
    public List<Book> findBooksUsingFilter(Comparator<Book> comparator) throws DaoException {
        List<Book> booksList = new ArrayList<>();
        String query = "SELECT * FROM books";

        try (Connection connection = this.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int bookId = resultSet.getInt("id");
                String bookTitle = resultSet.getString("Title");
                String bookGenre = resultSet.getString("Genre");
                String bookAuthor = resultSet.getString("Author");
                int bookPages = resultSet.getInt("Pages");
                boolean bookSeries = resultSet.getBoolean("Series");
                int bookStock = resultSet.getInt("Stock");
                double bookRating = resultSet.getDouble("Rating");
                String bookDescription = resultSet.getString("Description");
                String bookPublisher = resultSet.getString("Publisher");

                Book book = new Book(bookId, bookTitle, bookGenre, bookAuthor, bookPages, bookSeries, bookStock,
                        bookRating, bookDescription, bookPublisher);

                if (comparator != null) {
                    if(comparator instanceof BookPageComparatorOver400 && bookPages >= 400){
                    booksList.add(book);
                    }else if(comparator instanceof BookPageComparatorUnder400 && bookPages < 400){
                    booksList.add(book);
                    }
                } else {
                    booksList.add(book);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Failed to connect to the database - check MySQL is running and that you are using the correct database details");
            ex.printStackTrace();
        }


        if (comparator != null) {
            booksList.sort(comparator);
        }

        return booksList;
    }

}

