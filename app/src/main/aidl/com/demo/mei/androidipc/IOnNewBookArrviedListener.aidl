// IOnNewBookArrviedListener.aidl
package com.demo.mei.androidipc;

// Declare any non-default types here with import statements
import com.demo.mei.androidipc.Book;


interface IOnNewBookArrviedListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     void addNewBookArrivied(in Book book);
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}
