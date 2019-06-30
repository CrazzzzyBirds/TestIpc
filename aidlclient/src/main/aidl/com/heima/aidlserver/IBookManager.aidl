// IBookManager.aidl
package com.heima.aidlserver;

import com.heima.aidlserver.Book;

interface IBookManager {
    List<Book>getBookList();
    // in/out 输入/输出类型
    void addBook(in Book book);
}
