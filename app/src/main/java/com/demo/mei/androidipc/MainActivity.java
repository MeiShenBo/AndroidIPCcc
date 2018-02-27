package com.demo.mei.androidipc;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String TAG ="MainActivity";
    private static final  int MESSAGE_NEW_BOOK_ARRIVED = 1;
    
    private IBookM mBookM;
    
    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_NEW_BOOK_ARRIVED:
                    Log.d(TAG, "handleMessage: "+msg.obj);
                    break;
                default:
                    super.handleMessage(msg);

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this,BookManagerService.class);
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
    }
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IBookM iBookM = IBookM.Stub.asInterface(service);

            try {
                mBookM = iBookM;
                List<Book> list = iBookM.getBookList();
                Log.i(TAG, "onServiceConnected: "+list.toString());
                Book newBook = new Book("Android开发艺术探索",3);
                iBookM.addBook(newBook);
                List<Book> newlist = iBookM.getBookList();
                Log.i(TAG, "onServiceConnected: "+newBook);
                Log.i(TAG, "onServiceConnected: "+list.getClass().getCanonicalName() );
                Log.i(TAG, "onServiceConnected: "+newlist.toString());

            } catch (RemoteException e) {
                 e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBookM = null;
            Log.d(TAG, "onServiceDisconnected:  ");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
