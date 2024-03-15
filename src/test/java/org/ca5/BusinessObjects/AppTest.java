package org.ca5.BusinessObjects;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import org.ca5.DAOs.MySqlBooks;
import org.ca5.DAOs.BookDaoInterface;
import org.ca5.DTOs.Book;
import org.ca5.Exceptions.DaoException;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    /*
     * Aoife Murphy
     * Date 13-03-2024
     */
    @Test
    public void testGetBookByIdTrue() {
        // Create an instance of the BookDaoInterface and use it to call getBookById

        BookDaoInterface IBookDao = new MySqlBooks();
        System.out.println("Test for getBookById where result is true:");

        try {
            // Call the getBookById method
            Book book = IBookDao.getBookById(3);

            // Assert that the returned book is not null
            assertNotNull(book);

            // Assert that the attributes of the returned book match the expected values
            assertEquals(3, book.getId());
            assertEquals("The Silent Observer", book.getTitle());
            assertEquals("Mystery", book.getGenre());
            assertEquals("Isabella Nightshade", book.getAuthor());
            assertEquals(320, book.getPages());
            assertFalse(book.isSeries());
            assertEquals(200, book.getStock());
            assertEquals(4.0, book.getRating(), 0.001);
            assertEquals("In the shadowy world of espionage, a detective races against time...", book.getDescription());
            assertEquals("Shadow Publications", book.getPublisher());
        } catch (DaoException e) {

            fail("DaoException occurred: " + e.getMessage());
        }
    }

    /*
     * Aoife Murphy
     * Date 13-03-2024
     */
    @Test
    public void testGetBookByIdFalse() {
        // Create an instance of the BookDaoInterface and use it to call getBookById
        BookDaoInterface IBookDao = new MySqlBooks();
        System.out.println("Test for getBookById where result is false:");

        try {
            // getBookById method with a non-existent book ID
            Book book = IBookDao.getBookById(-1); // -1 is a non-existent book ID

            // Assert that the returned book is null, indicating that the book doesn't exist
            assertNull(book);
        } catch (DaoException e) {

            fail("DaoException occurred: " + e.getMessage());
        }

    }

    /*
     * Aoife Murphy
     * Date 13-03-2024
     */
    @Test
    public void testUpdateBookTrue() {
        BookDaoInterface IBookDao = new MySqlBooks();

        System.out.println("Test for updateBook where result is true:");
        Book updatedBook = new Book(19, "Updated Title", "Updated Genre", "Updated Author", 300, false, 150, 4.5,
                "Updated Description", "Updated Publisher");

        // Call the updateBook method
        Book result;
        try {
            result = IBookDao.updateBook(19, updatedBook);

            // Verify that the updateBook method returns the updated book
            assertNotNull(result);
            assertEquals(updatedBook, result);
        } catch (DaoException e) {
            fail("DaoException occurred: " + e.getMessage());
        }
    }

    /*
     * Aoife Murphy
     * Date 13-03-2024
     */
    @Test
    public void testUpdateBookFalse() {
        // Create an instance of the BookDaoInterface
        BookDaoInterface IBookDao = new MySqlBooks();

        System.out.println("Test for updateBook where result is false:");

        // Create an updated book object
        Book updatedBook = new Book(24, "Updated Title", "Updated Genre", "Updated Author", 300, false, 150, 4.5,
                "Updated Description", "Updated Publisher");

        // Call the updateBook method
        try {
            Book result = IBookDao.updateBook(24, updatedBook);

            // The method should not return a book object, so if it does, fail the test
            assertNull(result);

        } catch (DaoException e) {
            // If a DaoException occurs, it's expected behavior, so the test should pass
            assertEquals("Error updating book with ID 24: Failed to update book with ID 24. Book not found.", e.getMessage());
        }
    }

}
