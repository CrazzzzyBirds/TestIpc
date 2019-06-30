package com.heima.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.Nullable;

public class BookManagerService extends Service {
    private static final String TAG = "BMS";
    //自动线程同步
    private CopyOnWriteArrayList<Book> bookList = new CopyOnWriteArrayList<Book>();
    private IBinder iBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return bookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            if (book!=null){
                Log.e(TAG,"addBook");
                bookList.add(book);

            }

        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        bookList.add(new Book(1,"第一本m"));
        bookList.add(new Book(2,"第二本m"));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }
}
