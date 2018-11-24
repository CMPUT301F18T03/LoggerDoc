package android.util;

import java.util.HashMap;
/**
 * @source https://stackoverflow.com/a/48101071
 * @author Adeel Ahmad
 * Mocks the SparseArray from android.
 */
public class SparseArray<T> {

    private HashMap<Integer, T> FakeArray;

    public SparseArray() {
        FakeArray = new HashMap<>();
    }

    public void put(int key, T value) {
        FakeArray.put(key, value);
    }

    public T get(int key) {
        return FakeArray.get(key);
    }
    public int size(){
        return FakeArray.size();
    }

    public void remove(int targ){
        FakeArray.remove(targ);
    }


}