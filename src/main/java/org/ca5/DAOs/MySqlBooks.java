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
    /**
     * Main author: Jamie Duffy Creagh
     * Date: 08-03-2024
     **/
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
     * Main author: Jamie Duffy Creagh
     * Date: 08-03-2024
     **/

    public void insertBook(Scanner scanner) {
        System.out.println("\nAdding to Database:");

        System.out.println("Enter book title:");
        String title = scanner.nextLine().trim();

        while (title.trim().isEmpty()) {
            System.out.println("Enter book title:");
            title = scanner.nextLine().trim();
            if (title.trim().isEmpty()) {
                System.out.println("Invalid input please try again");
            }
        }


        String genre = "";
        while (genre.trim().isEmpty()) {
            System.out.println("Enter book genre:");
            genre = scanner.nextLine().trim();

            if (genre.trim().isEmpty()) {
                System.out.println("Invalid input please try again");
            }
        }

        String author = "";
        while (author.trim().isEmpty()) {
            System.out.println("Enter book author:");
            author = scanner.nextLine().trim();
            if (author.trim().isEmpty()) {
                System.out.println("Invalid input please try again");
            }
        }


        int pages = 0;
        while (pages <= 0) {
            System.out.println("Enter number of pages:");
            if (scanner.hasNextInt()) {
                pages = scanner.nextInt();
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // clear the invalid input
            }
        }


        boolean series = false;
        System.out.println("Is the book part of a series? (true/false):");
        while (!scanner.hasNextBoolean()) {
            System.out.println("Invalid input. Please enter true or false.");
            scanner.next(); // clear the invalid input
        }
        series = scanner.nextBoolean();


        int stock = 0;
        while (stock < 0) {
            System.out.println("Enter stock quantity:");
            if (scanner.hasNextInt()) {
                stock = scanner.nextInt();
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // clear the invalid input
            }
        }

        // Validate and get book rating
        double rating = 0.0;
        while (rating < 0) {
            System.out.println("Enter book rating:");
            if (scanner.hasNextDouble()) {
                rating = scanner.nextDouble();
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // clear the invalid input
            }
        }

        scanner.nextLine();

        // Validate and get book description
        String description = "";
        while (description.trim().isEmpty()) {
            System.out.println("Enter book description:");
            description = scanner.nextLine().trim();
            if (description.trim().isEmpty()) {
                System.out.println("Invalid input please try again");
            }
        }

        // Validate and get book publisher
        String publisher = "";
        while (publisher.trim().isEmpty()) {
            System.out.println("Enter book publisher:");
            publisher = scanner.nextLine().trim();
            if (publisher.trim().isEmpty()) {
                System.out.println("Invalid input please try again");
            }
        }


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
     * Main author: Kim Fui Leung
     * Date: 08-03-2024
     **/
    public void deleteBookById(int id) {

        String queryDelete = "DELETE FROM books WHERE id = " + id;
        try (Connection connection = this.getConnection();

             PreparedStatement preparedStatement1 = connection.prepareStatement(queryDelete);) {
            System.out.println("Connected to the database");

            System.out.println("Building a PreparedStatement to delete row" + id +
                    "in database.");

            preparedStatement1.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(
                    "Failed to connect to the database - check MySQL is running and that you are using the correct database details");
            ex.printStackTrace();
        }
    }


    /**
     * Main author: Aoife Murphy
     * Date: 08-03-2024
     **/
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

        }

        return book;
    }

    /**
     * Main author: Aoife Murphy
     * Date: 15-03-2024
     **/
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
     * Main author: Kim Fui Leung
     * Date: 15-03-2024
     **/
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
                    if ((comparator instanceof BookPageComparatorOver400) && (bookPages >= 400)) {
                        booksList.add(book);
                    } else if (comparator instanceof BookPageComparatorUnder400 && bookPages < 400) {
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