package org.example;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void test2(){
        Integer i3 = 126;
        Integer i4 = 126;
        int i5 = 126;
        System.out.println(i3 == i4);//true
        System.out.println(i3 == i5); //true

        Integer i6 = 128;
        Integer i7 = 128;
        int i8 = 128;
        System.out.println(i6 == i7); //false
        System.out.println(i6 == i8); //true
    }
}
