package org.ca5.BusinessObjects;

import java.util.Comparator;

import org.ca5.DTOs.Book;

public class BookPageComparatorOver400 implements Comparator<Book> {

    @Override
    public int compare(Book book1, Book book2) {
        return Integer.compare(book1.getPages(), book2.getPages());
    }


}
