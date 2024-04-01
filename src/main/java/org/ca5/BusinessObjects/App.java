package org.ca5.BusinessObjects;


import org.ca5.DAOs.MySqlBooks;

import org.ca5.DAOs.BookDaoInterface;
import org.ca5.DTOs.Book;
import org.ca5.Exceptions.DaoException;

import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws DaoException {

        BookDaoInterface IBookDao = new MySqlBooks();

        // Book book = IBookDao.getBookById(2);
        // System.out.println(book.toString());

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("====================================================");
            System.out.println("                       Menu:                        ");
            System.out.println("====================================================");
            System.out.println("=            1. View Database                      =");
            System.out.println("=            2. Add to Database                    =");
            System.out.println("=            3. Delete from Database               =");
            System.out.println("=            4. Get Book from Database Using ID    =");
            System.out.println("=            5. Update Book                        =");
            System.out.println("=            6. Filter Book                        =");
            System.out.println("=            0. Exit                               =");
            System.out.println("====================================================");


            int choice = -1;
            while (choice < 0) {
                System.out.println("Enter your choice:");
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    if (choice < 0) {
                        System.out.println("Invalid Choice. Please enter a positive integer.");
                    }
                } else {
                    System.out.println("Invalid choice. Please enter a valid integer for choice.");
                    scanner.next(); // Clear the invalid input
                }

                switch (choice) {
                    case 1:
                        System.out.println(IBookDao.getAllBooks());
                        break;
                    case 2:

                        System.out.println("\nAdding to Database:");
                        String newTitle=scanner.nextLine().trim();
                        while (newTitle.trim().isEmpty()) {
                            System.out.println("Enter book Title:");
                            newTitle = scanner.nextLine().trim();

                            if (newTitle.trim().isEmpty()) {
                                System.out.println("Invalid input please try again");
                            }
                        }


                        String newGenre = "";
                        while (newGenre.trim().isEmpty()) {
                            System.out.println("Enter book Genre:");
                            newGenre = scanner.nextLine().trim();

                            if (newGenre.trim().isEmpty()) {
                                System.out.println("Invalid input please try again");
                            }
                        }

                        String newAuthor = "";
                        while (newAuthor.trim().isEmpty()) {
                            System.out.println("Enter book Author:");
                            newAuthor = scanner.nextLine().trim();
                            if (newAuthor.trim().isEmpty()) {
                                System.out.println("Invalid input please try again");
                            }
                        }


                        int newPages = -1;
                        while (newPages < 0) {
                            System.out.println("Enter number of pages:");
                            if (scanner.hasNextInt()) {
                                newPages = scanner.nextInt();
                            } else {
                                System.out.println("Invalid input. Please enter a valid number.");
                                scanner.next(); // clear the invalid input
                            }
                        }


                        boolean newSeries = false;
                        System.out.println("Is the book part of a series? (true/false):");
                        while (!scanner.hasNextBoolean()) {
                            System.out.println("Invalid input. Please enter true or false.");
                            scanner.next(); // clear the invalid input
                        }
                        newSeries = scanner.nextBoolean();


                        int newStock = -1;
                        while (newStock < 0) {
                            System.out.println("Enter stock quantity:");
                            if (scanner.hasNextInt()) {
                                newStock = scanner.nextInt();
                            } else {
                                System.out.println("Invalid input. Please enter a valid number.");
                                scanner.next(); // clear the invalid input
                            }
                        }

                        double newRating = -1;
                        while (newRating < 0) {
                            System.out.println("Enter book rating:");
                            if (scanner.hasNextDouble()) {
                                newRating = scanner.nextDouble();
                            } else {
                                System.out.println("Invalid input. Please enter a valid number.");
                                scanner.next(); // clear the invalid input
                            }
                        }

                        scanner.nextLine();

                        String newDescription = "";
                        while (newDescription.trim().isEmpty()) {
                            System.out.println("Enter book description:");
                            newDescription = scanner.nextLine().trim();
                            if (newDescription.trim().isEmpty()) {
                                System.out.println("Invalid input please try again");
                            }
                        }

                        String newPublisher = "";
                        while (newPublisher.trim().isEmpty()) {
                            System.out.println("Enter book publisher:");
                            newPublisher = scanner.nextLine().trim();
                            if (newPublisher.trim().isEmpty()) {
                                System.out.println("Invalid input please try again");
                            }
                        }

                        Book newBook = new Book(newTitle, newGenre, newAuthor, newPages, newSeries, newStock, newRating, newDescription, newPublisher);

                        Book insertedBook = IBookDao.insertBook(newBook);
                        System.out.println(insertedBook.toString());
                        break;
                    case 3:
                        System.out.println("Enter the ID of the book to delete:");
                        int deleteId = scanner.nextInt();
                        IBookDao.deleteBookById(deleteId);
                        break;
                    case 4:
                        System.out.println("Enter the ID of the book to display details:");
                        int bookId = scanner.nextInt();

                        Book book = IBookDao.getBookById(bookId);
                        if (book != null) {
                            System.out.println(book.toString());
                        } else {
                            System.out.println("No Book found with the id " + bookId);
                        }

                        break;

                    case 5:
                        System.out.println("Enter the ID of the book to update details:");
                        int id = 0;
                        while (id < 1) {
                            System.out.println("Enter the ID of the book to update details:");
                            if (scanner.hasNextInt()) {
                                id = scanner.nextInt();
                                if (id < 1) {
                                    System.out.println("Invalid ID. Please enter a positive integer.");
                                }
                            } else {
                                System.out.println("Invalid input. Please enter a valid integer ID.");
                                scanner.next(); // Clear the invalid input
                            }
                        }


                        scanner.nextLine();

                        String title = "";
                        while (title.trim().isEmpty()) {
                            System.out.println("Enter new book title:");
                            title = scanner.nextLine().trim();
                            if (title.trim().isEmpty()) {
                                System.out.println("Invalid input. Please try again.");
                            }
                        }

                        // Prompt for book genre
                        String genre = "";
                        while (genre.trim().isEmpty()) {
                            System.out.println("Enter new book genre:");
                            genre = scanner.nextLine().trim();
                            if (genre.trim().isEmpty()) {
                                System.out.println("Invalid input. Please try again.");
                            }
                        }

                        // Prompt for book author
                        String author = "";
                        while (author.trim().isEmpty()) {
                            System.out.println("Enter new book author:");
                            author = scanner.nextLine().trim();
                            if (author.trim().isEmpty()) {
                                System.out.println("Invalid input. Please try again.");
                            }
                        }

                        // Prompt for number of pages
                        int pages = 0;
                        while (pages <= 0) {
                            System.out.println("Enter number of pages:");
                            if (scanner.hasNextInt()) {
                                pages = scanner.nextInt();
                            } else {
                                System.out.println("Invalid input. Please enter a valid number.");
                                scanner.next();
                            }
                        }

                        // Prompt for whether the book is part of a series
                        boolean series = false;
                        System.out.println("Is the book part of a series? (true/false):");
                        while (!scanner.hasNextBoolean()) {
                            System.out.println("Invalid input. Please enter true or false.");
                            scanner.next(); // clear the invalid input
                        }
                        series = scanner.nextBoolean();

                        // Prompt for stock quantity
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

                        // Prompt for book rating

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


                        // Prompt for book description
                        String description = "";
                        while (description.trim().isEmpty()) {
                            System.out.println("Enter new book description:");
                            description = scanner.nextLine().trim();
                            if (description.trim().isEmpty()) {
                                System.out.println("Invalid input. Please try again.");
                            }
                        }

                        // Prompt for book publisher
                        String publisher = "";
                        while (publisher.trim().isEmpty()) {
                            System.out.println("Enter new book publisher:");
                            publisher = scanner.nextLine().trim();
                            if (publisher.trim().isEmpty()) {
                                System.out.println("Invalid input. Please try again.");
                            }
                        }

                        Book updatedBook = new Book(id, title, genre, author, pages, series, stock, rating, description,
                                publisher);
                        Book b = IBookDao.updateBook(id, updatedBook);
                        if (b != null) {
                            System.out.println("Book details updated successfully:");
                            System.out.println(b.toString());
                        } else {
                            System.out.println("No Book found with the id " + id);
                        }
                        break;


                    case 6:
                        System.out.println("\nMenu:");
                        System.out.println("====================================================");
                        System.out.println("=           1. Filter by page over 400              ");
                        System.out.println("=           2. Filter by page below 400             ");
                        System.out.println("====================================================");
                        System.out.print("Enter your choice: ");
                        int filterChoice = scanner.nextInt();
                        switch (filterChoice) {
                            case 1:
                                List<Book> booksOver400Pages = IBookDao.findBooksUsingFilter(new BookPageComparatorOver400());
                                if (!booksOver400Pages.isEmpty()) {
                                    for (Book bookFilter : booksOver400Pages) {
                                        System.out.println(bookFilter.toString());
                                    }
                                } else {
                                    System.out.println("No book found with page over 400");
                                }
                                break;
                            case 2:
                                List<Book> booksUnder400Pages = IBookDao.findBooksUsingFilter(new BookPageComparatorUnder400());
                                if (!booksUnder400Pages.isEmpty()) {
                                    for (Book bookFilter : booksUnder400Pages) {
                                        System.out.println(bookFilter.toString());
                                    }
                                } else {
                                    System.out.println("No book found with page below 400");
                                }
                                break;
                            default:
                                System.out.println("Invalid choice. Please enter a valid option.");
                        }
                        break;


                    case 0:
                        System.out.println("Exiting the program. Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            }

        }
    }
}
