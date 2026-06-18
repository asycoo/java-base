package phase2.arraylist;

import java.util.Arrays;

/**
 * 实操 2.1 - 手写简易 ArrayList
 *
 * 要求：
 * - 底层用 Object[] 存储
 * - 实现 add / get / remove / size
 * - 容量满时 expand（扩容 1.5 倍）
 * - get/remove 越界抛 IndexOutOfBoundsException
 *
 * 不要用 java.util.ArrayList！
 */
public class SimpleArrayList<E> {

    private static final int DEFAULT_CAPACITY = 10;

    private Object[] elements;
    private int size;

    public SimpleArrayList() {
        elements = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    public E get(int index) {
        // TODO
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (E) elements[index];
        // throw new UnsupportedOperationException("TODO: 实现 get");
    }

    public boolean add(E element) {
        // TODO: 满则 expand，放入 elements[size]，size++
        if (size >= elements.length) {
            expand();
        }
        elements[size] = element;
        size++;
        return true;
        // throw new UnsupportedOperationException("TODO: 实现 add");
    }

    @SuppressWarnings("unchecked")
    public E remove(int index) {
        // TODO: 删除 index 处元素，后面元素前移
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        E oldValue = (E) elements[index];
        System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        elements[--size] = null;
        return oldValue;
        // throw new UnsupportedOperationException("TODO: 实现 remove");
    }

    private void expand() {
        // TODO: 新数组长度为 old * 1.5（至少 +1）
        int newCapacity = elements.length + (elements.length >> 1);
        if (newCapacity - elements.length < 1) {
            newCapacity = elements.length + 1;
        }
        elements = Arrays.copyOf(elements, newCapacity);
        // throw new UnsupportedOperationException("TODO: 实现 expand");
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOf(elements, size));
    }
}
