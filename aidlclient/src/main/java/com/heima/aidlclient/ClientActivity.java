package com.heima.aidlclient;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.heima.aidlserver.Book;
import com.heima.aidlserver.IBookManager;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class ClientActivity extends AppCompatActivity {
    private static final String TAG="ClientActivity";
    TextView tv_show;
    //远程服务Binder的代理
    private IBookManager mIBookManager;

    //创建连接
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //获取远程服务Binder的代理
            mIBookManager = IBookManager.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    /**
     * 绑定服务
     *
     * @param view
     */
    public void bindService(View view) {
        Intent intent = new Intent();
        intent.setAction("com.heima.aidlserver.service");
        intent.setPackage("com.heima.aidlserver");
        startService(intent);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    /**
     * 添加图书
     *
     * @param view
     */
    public void addBook(View view) {
        try {
            mIBookManager.addBook(new Book(666, "ClientBook"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取图书列表
     *
     * @param view
     */
    public void getBookList(View view) {
        StringBuffer buffer=new StringBuffer();
        try {
            List<Book> books = mIBookManager.getBookList();
            //Log.e(TAG, "books:" + books.toString());
            for (Book book:books){
              buffer.append(book.toString()+"\n");
            }
       tv_show.setText(buffer.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_show=findViewById(R.id.tv_show);
    }
}
