package ru.spbstu.telematics.malyarenko.lab_2;

import static org.junit.Assert.*;
import org.junit.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class MyLinkedHashSetTest 
{
    private MyLinkedHashSet<Integer> set;
    private MyLinkedHashSet<Integer> setFilled;
	private Vector<Integer> vector;
    private ArrayList<Integer> list;
    private Integer[] array = {1, 2, 3, 5, 7, 11, 13};
	
	@Before
	public void setUp() {
        set = new MyLinkedHashSet<>();
        setFilled = new MyLinkedHashSet<>(Arrays.asList(array));
        vector = new Vector<>();
        list = new ArrayList<>();
    }
    
    @Test
    public void clearTest() {
        assertTrue(set.isEmpty());
        assertEquals(0, set.size());

        set.addAll(Arrays.asList(array));

        assertFalse(set.isEmpty());
        set.clear();
        assertTrue(set.isEmpty());
        assertEquals(0, set.size());
    }

    @Test
    public void addAllTest() {
        for(int i = 0; i < 10; i++) {
            vector.add(i);
        }

        for(int i = 0; i < 10; i++) {
            list.add(i);
        }

        set.addAll(vector);
        
        int count = 0;
        for (Integer i : set) {
            assertEquals(vector.get(count), i);
            count++;
        }

        set.clear();
        set.addAll(list);

        count = 0;
        for (Integer i : set) {
            assertEquals(list.get(count), i);
            count++;
        }
    }

    @Test
    public void containsAllTest() {
        vector.add(2);
        vector.add(5);
        vector.add(7);
        vector.add(11);

        list.add(2);
        list.add(5);
        list.add(7);
        list.add(11);

        assertTrue(setFilled.containsAll(vector));
        assertTrue(setFilled.containsAll(list));

        setFilled.remove(7);

        assertFalse(setFilled.containsAll(vector));
        assertFalse(setFilled.containsAll(list));
    }

    @Test
    public void removeAllTest() {
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(5);
        set.add(7);
        set.add(11);

        int sizeOld = set.size();

        vector.add(2);
        vector.add(5);
        vector.add(7);
        vector.add(11);
        
        set.removeAll(vector);

        int sizeNew = set.size();

        assertEquals(vector.size(), sizeOld - sizeNew);

        Vector<Integer> expected = new Vector<>();
        expected.add(1);
        expected.add(3);
        expected.add(11);

        int count = 0;
        for (Integer i : set) {
            assertEquals(expected.get(count), i);
            count++;
        }
    }

    @Test
    public void sizeTest() {
        assertEquals(0, set.size());

        set.addAll(Arrays.asList(array));

        assertEquals(array.length, set.size());

        set.clear();

        assertEquals(0, set.size());
    }

    @Test
    public void toArrayTest() {

        set.addAll(Arrays.asList(array));

        Object[] arrayObj = set.toArray();

        assertArrayEquals(array, arrayObj);
    } 


}
