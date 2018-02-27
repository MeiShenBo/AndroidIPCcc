// IBookM.aidl
package com.demo.mei.androidipc;

// Declare any non-default types here with import statements
import com.demo.mei.androidipc.Book;
import com.demo.mei.androidipc.IOnNewBookArrviedListener;
interface IBookM {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     List<Book> getBookList();
     void addBook(in Book book);
     void registerListener(IOnNewBookArrviedListener listener);
     void unregisterListener(IOnNewBookArrviedListener listener);
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

}
