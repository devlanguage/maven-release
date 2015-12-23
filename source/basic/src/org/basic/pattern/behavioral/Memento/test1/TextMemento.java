/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.pattern.Memento.src.TextMemento.java is created on 2008-6-12
 */
package org.basic.pattern.behavioral.Memento.test1;

import java.util.LinkedList;

/**
 * 
 */
public class TextMemento {

    // String state;
    int i = 0;
    LinkedList<String> memoList;

    TextMemento() {

        // state = null;
        memoList = new LinkedList<String>();
    }

    void addMemo(String state) {

        memoList.addLast(state);
    }

    String getState() {

        if (memoList.size() != 0) {
            String tmp = memoList.getLast();
            memoList.removeLast();
            return tmp;
        } else {
            return null;
        }
    }
}