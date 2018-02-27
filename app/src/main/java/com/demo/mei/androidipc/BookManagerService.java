package com.demo.mei.androidipc;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookManagerService extends Service {
    private String TAG = "BookManagerService";
    public BookManagerService() {
    }
    private AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<IOnNewBookArrviedListener> mlistener = new CopyOnWriteArrayList<>();
    private Binder mBinder = new IBookM.Stub(){
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrviedListener listener) throws RemoteException {
        if(!mlistener.contains(listener)){
            mlistener.add(listener);
        }else{
            Log.d(TAG, "registerListener: exists");
        }
            Log.i(TAG, "registerListener: "+mlistener.size());
        }

        @Override
        public void unregisterListener(IOnNewBookArrviedListener listener) throws RemoteException {
            if(mlistener.contains(listener)){
                mlistener.remove(listener);
            }else {
                Log.d(TAG, "unregisterListener: unregister");
            }
            Log.i(TAG, "registerListener: "+mlistener.size());
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book("android",1));
        mBookList.add(new Book("android2",2));
        new Thread(new ServiceWork()).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
       return mBinder;
    }
    public void addNewBookArrivied(Book book) throws RemoteException {
        mBookList.add(book);
        Log.d(TAG, "addNewBookArrivied: "+mlistener.size());
        for (int i = 0; i < mlistener.size(); i++) {
            IOnNewBookArrviedListener listener = mlistener.get(i);
            listener.addNewBookArrivied(book);
        }
    }

    private class  ServiceWork implements Runnable{

        @Override
        public void run() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int bookId = mBookList.size() + 1;
            Book newBook = new Book("new Book#"+bookId,bookId);
            try {
                addNewBookArrivied(newBook);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
    }

}
