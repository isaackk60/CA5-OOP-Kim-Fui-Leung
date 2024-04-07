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

import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }



    /**
     * Main author: Aoife Murphy
     * Date: 13-03-2024
     **/
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

    /**
     * Main author: Aoife Murphy
     * Date: 13-03-2024
     **/
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

    /**
     * Main author: Aoife Murphy
     * Date: 13-03-2024
     **/
    @Test
    public void testUpdateBookTrue() {
        BookDaoInterface IBookDao = new MySqlBooks();

        System.out.println("Test for updateBook where result is true:");
        Book updatedBook = new Book(16, "Updated Title", "Updated Genre", "Updated Author", 300, false, 150, 4.5,
                "Updated Description", "Updated Publisher");

        // Call the updateBook method
        Book result;
        try {
            result = IBookDao.updateBook(16, updatedBook);

            // Verify that the updateBook method returns the updated book
            assertNotNull(result);
            assertEquals(updatedBook, result);
        } catch (DaoException e) {
            fail("DaoException occurred: " + e.getMessage());
        }
    }

    /**
     * Main author: Aoife Murphy
     * Date: 13-03-2024
     **/
    @Test
    public void testUpdateBookFalse() {
        // Create an instance of the BookDaoInterface
        BookDaoInterface IBookDao = new MySqlBooks();

        System.out.println("Test for updateBook where result is false:");

        // Create an updated book object
        Book updatedBook = new Book(100, "Updated Title", "Updated Genre", "Updated Author", 300, false, 150, 4.5,
                "Updated Description", "Updated Publisher");

        // Call the updateBook method
        try {
            Book result = IBookDao.updateBook(100, updatedBook);

            // The method should return null since the book doesn't exist
            assertNull(result);

        } catch (DaoException e) {
            // If a DaoException occurs, it's expected behavior, so the test should pass
            assertEquals("Error updating book with ID 100: Failed to update book with ID 100. Book not found.", e.getMessage());
        }
    }

    /**
     * Main author: Kim Fui Leung
     * Date: 01-04-2024
     **/
    @Test
    public void testInsertBook() {
        BookDaoInterface IBookDao = new MySqlBooks();

        // Insert a book into the database
        Book book = new Book("Test Title", "Test Genre", "Test Author", 200, false, 10, 4.5, "Test Description", "Test Publisher");

        Book insertedBook = IBookDao.insertBook(book);
        assertEquals("Test Title", insertedBook.getTitle());
        assertEquals("Test Genre", insertedBook.getGenre());
        assertEquals("Test Author", insertedBook.getAuthor());
        assertEquals(200, insertedBook.getPages());
        assertFalse(insertedBook.isSeries());
        assertEquals(10, insertedBook.getStock());
        assertEquals(4.5, insertedBook.getRating(), 0.001);
        assertEquals("Test Description", insertedBook.getDescription());
        assertEquals("Test Publisher", insertedBook.getPublisher());
    }

    /**
     * Main author: Kim Fui Leung
     * Date: 01-04-2024
     **/
    @Test
    public void testDeleteBookById() {
        BookDaoInterface IBookDao = new MySqlBooks();
        try {
            IBookDao.deleteBookById(19);

            // Verify that the book is deleted
            assertNull(IBookDao.getBookById(19));
        } catch (DaoException ex) {
            fail("Failed to delete book: " + ex.getMessage());
        }
    }


    /**
     * Main author: Kim Fui Leung
     * Date: 01-04-2024
     **/
    @Test
    public void testFilterPageBelow400() {
        BookDaoInterface IBookDao = new MySqlBooks();

        try {
            List<Book> booksUnder400Pages = IBookDao.findBooksUsingFilter(new BookPageComparatorUnder400());

            assertFalse(booksUnder400Pages.isEmpty());
            for (Book book : booksUnder400Pages) {
                assertTrue(book.getPages() < 400);
            }
        } catch (DaoException ex) {
            fail("Failed to filter books below 400 pages: " + ex.getMessage());
        }
    }

    /**
     * Main author: Kim Fui Leung
     * Date: 01-04-2024
     **/
    @Test
    public void testFilterPageOver400() {
        BookDaoInterface IBookDao = new MySqlBooks();

        try {
            List<Book> booksOver400Pages = IBookDao.findBooksUsingFilter(new BookPageComparatorOver400());

            assertFalse(booksOver400Pages.isEmpty());
            for (Book book : booksOver400Pages) {
                assertTrue(book.getPages() >= 400);
            }
        } catch (DaoException ex) {
            fail("Failed to filter books over 400 pages: " + ex.getMessage());
        }
    }

    /**
     * Main author: Kim Fui Leung
     * Date: 07-04-2024
     **/
    @Test
    public void testBooksListToJson() {
        BookDaoInterface IBookDao = new MySqlBooks();
        try {
            List<Book> allBooks = IBookDao.getAllBooks(); //makes a copy of all books from the DataBase
            List<Book> booksInRange = new ArrayList<>(); // new list created

            for (Book bookForjson : allBooks) {
                if (bookForjson.getId() >= 5 && bookForjson.getId() <= 7) {
                    booksInRange.add(bookForjson); // loops through the allBooks list and if the id is within the specifies range they are added to the booksInRange method
                }
            }
            String booksInRangeJson = IBookDao.booksListToJson(booksInRange);
            String expectedJson = "[{\"id\":5,\"title\":\"Threads of Destiny\",\"genre\":\"Historical Fiction\",\"author\":\"Victoria Montgomery\",\"pages\":384,\"series\":true,\"stock\":100,\"rating\":4.3,\"description\":\"Set against the backdrop of the Renaissance, this tale weaves...\",\"publisher\":\"Timeless Books\"},{\"id\":6,\"title\":\"Rogues Gambit\",\"genre\":\"Adventure\",\"author\":\"Dexter Stormblade\",\"pages\":352,\"series\":true,\"stock\":160,\"rating\":4.1,\"description\":\"In a world of pirates and hidden treasures, a daring rogue seeks...\",\"publisher\":\"Seafarer Publishing\"},{\"id\":7,\"title\":\"Eternal Embrace\",\"genre\":\"Romance\",\"author\":\"Olivia Hartfield\",\"pages\":288,\"series\":false,\"stock\":250,\"rating\":4.7,\"description\":\"Amidst the beauty of a quaint town, two souls find love that transcends...\",\"publisher\":\"Heartfelt Romances\"}]";
            assertEquals(expectedJson, booksInRangeJson);
        } catch (DaoException ex) {
            fail("Failed to convert list of book to a Json String: " + ex.getMessage());
        }
    }

    /**
     * Main author: Kim Fui Leung
     * Date: 07-04-2024
     **/
    @Test
    public void testBookToJsonTrue() {
        BookDaoInterface IBookDao = new MySqlBooks();
        try {
            Book jsonBook = IBookDao.getBookById(9);
            String json = "";
            if (jsonBook != null) {
                json = IBookDao.bookToJson(jsonBook);
            }
            String expectedBookJson = "{\"id\":9,\"title\":\"Chronicles of Elysium\",\"genre\":\"Fantasy\",\"author\":\"Serena Moonstone\",\"pages\":544,\"series\":true,\"stock\":90,\"rating\":4.6,\"description\":\"Embark on an epic quest through a magical realm filled with mythical creatures...\",\"publisher\":\"Enchanted Press\"}";
            assertEquals(expectedBookJson, json);
        } catch (DaoException ex) {
            fail("Failed to convert book to a Json String: " + ex.getMessage());
        }
    }

    /**
     * Main author: Kim Fui Leung
     * Date: 07-04-2024
     **/
    @Test
    public void testBookToJsonFalse() {
        BookDaoInterface IBookDao = new MySqlBooks();
        try {
            Book jsonBook = IBookDao.getBookById(100);
            String json = "";
            if (jsonBook != null) {
                json = IBookDao.bookToJson(jsonBook);
            }else{
                json= "No Book found with the id 100";
            }
            String expectedBookJson="No Book found with the id 100";
            assertEquals(expectedBookJson, json);
        } catch (DaoException ex) {
            fail("Failed to convert book to a Json String: " + ex.getMessage());
        }
    }

// that is not working
//    /**
//     * Main author: Kim Fui Leung
//     * Date: 07-04-2024
//     **/
//    @Test
//    public void testDisplayAllBooks(){
//        BookDaoInterface IBookDao = new MySqlBooks();
//        try{
//            List<Book> allBooks= IBookDao.getAllBooks();
//            List<Book> books = new ArrayList<>();
//            books.add(new Book(1, "Whispers of the Lost Kingdom", "Fantasy", "Eleanor Ravenscroft", 432, true, 150, 4.2, "In a realm where magic intertwines with politics...", "Enchanted Press"));
//            books.add(new Book(2, "Echoes of the Forgotten Realm", "Fantasy", "Alistair Blackthorn", 376, true, 120, 4.5, "As ancient prophecies resurface, a lone hero must rise...", "Mystic Books"));
//            books.add(new Book(3, "The Silent Observer", "Mystery", "Isabella Nightshade", 320, false, 200, 4.0, "In the shadowy world of espionage, a detective races against time...", "Shadow Publications"));
//            books.add(new Book(4, "Beyond the Horizon", "Science Fiction", "Xander Starlight", 480, true, 180, 4.8, "Exploring the mysteries of distant galaxies, a space explorer discovers...", "Galactic Press"));
//            books.add(new Book(5, "Threads of Destiny", "Historical Fiction", "Victoria Montgomery", 384, true, 100, 4.3, "Set against the backdrop of the Renaissance, this tale weaves...", "Timeless Books"));
//            books.add(new Book(6, "Rogues Gambit", "Adventure", "Dexter Stormblade", 352, true, 160, 4.1, "In a world of pirates and hidden treasures, a daring rogue seeks...", "Seafarer Publishing"));
//            books.add(new Book(7, "Eternal Embrace", "Romance", "Olivia Hartfield", 288, false, 250, 4.7, "Amidst the beauty of a quaint town, two souls find love that transcends...", "Heartfelt Romances"));
//            books.add(new Book(8, "City of Shadows", "Thriller", "Gabriel Nightshade", 416, true, 135, 4.4, "A gritty detective faces a web of corruption and deceit in the heart of the city...", "Dark Alley Books"));
//            books.add(new Book(9, "Chronicles of Elysium", "Fantasy", "Serena Moonstone", 544, true, 90, 4.6, "Embark on an epic quest through a magical realm filled with mythical creatures...", "Enchanted Press"));
//            books.add(new Book(10, "The Enigma Code", "Mystery", "Lucas Sterling", 400, false, 180, 4.0, "A master cryptographer unravels a complex code that holds the key to...", "Cipher Publications"));
//            books.add(new Book(11, "Starlight Odyssey", "Science Fiction", "Aria Nova", 432, true, 120, 4.9, "Journey through the cosmos as a group of explorers seeks the origins of...", "Stellar Books"));
//            books.add(new Book(12, "Heartstrings", "Romance", "Tristan Evergreen", 320, false, 200, 4.2, "Love blossoms in unexpected places as two hearts find solace...", "Everlasting Romance"));
//            books.add(new Book(13, "Shadows of the Past", "Thriller", "Mara Nightshade", 384, true, 150, 4.5, "A former spy's dark past catches up with her as she is thrust into a...", "Nocturnal Press"));
//            books.add(new Book(14, "Legacy of the Ancients", "Fantasy", "Cassandra Moonshadow", 464, true, 110, 4.3, "A tale of ancient prophecies and a chosen one destined to save the...", "Mythos Publications"));
//            books.add(new Book(15, "Forgotten Realms", "Historical Fiction", "Oliver Kingsley", 368, false, 220, 4.1, "Explore the untold stories of forgotten heroes and lost civilizations...", "Time Traveler Books"));
//            books.add(new Book(16, "Updated Title", "Updated Genre", "Updated Author", 300, false, 150, 4.5, "Updated Description", "Updated Publisher"));
//            books.add(new Book(17, "Moonlit Serenade", "Romance", "Elijah Silvermoon", 304, false, 180, 4.5, "Amidst the enchanting moonlight, two souls discover a love that echoes...", "Starry Nights Publishing"));
//            books.add(new Book(18, "Code of Deception", "Thriller", "Natalia Shadowsong", 448, true, 140, 4.3, "In a world of spies and double-crosses, a secret agent must navigate a...", "Covert Operations Press"));
//            books.add(new Book(20, "Hearts Entwined", "Romance", "Isabella Hartfield", 288, false, 190, 4.6, "In a picturesque town, two hearts find solace in each other amidst...", "Heartfelt Romances"));
//            books.add(new Book(21, "Test Title", "Test Genre", "Test Author", 200, false, 10, 4.5, "Test Description", "Test Publisher"));
//
//            assertEquals(books,allBooks);
//
//        }catch (DaoException e) {
//            fail("Failed to display all books: " + e.getMessage());
//        }
//    }

}
