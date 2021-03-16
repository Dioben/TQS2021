package stack;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class TqsStackTest {

    private TqsStack<String> testStack;
    private TqsStack<String> size3Stack;
    @BeforeEach
    void setUp() {
    testStack = new TqsStack<>();
    size3Stack = new TqsStack<>();
    size3Stack.push("1");
    size3Stack.push("2");
    size3Stack.push("3");

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void emptyStart(){assertTrue(testStack.isEmpty(),"A stack is empty on construction");}

    @Test
    void size0Start(){assertEquals(0,testStack.size(),"A stack starts with 0 elements");}

    @Test
    void pushSizeChangeTest(){
        testStack.push("1");
        assertEquals(1,testStack.size());
        assertFalse(testStack.isEmpty(),"After n pushes to an empty stack, n > 0, the stack is not empty and its size is n");
    }

    @Test
    void instantPopTest(){
        testStack.push("1");
        assertEquals("1",testStack.pop());
    }
    @Test
    void peekSizeTest(){
        assertEquals(size3Stack.size(),3);
        assertEquals(size3Stack.peek(),"3");
        assertEquals(size3Stack.size(),3);
    }
    @Test
    void nPushTest() {
        assertEquals(testStack.size(),0);
        for (int n=0;n<100;n++){testStack.push("test"+n);}
        assertEquals(testStack.size(),100);
        assertNotEquals(testStack.isEmpty(),true);
        for (int n=99;n>=0;n--){assertEquals("test"+n,testStack.pop());}
        assertEquals(testStack.size(),0);
        assertEquals(0,testStack.size());
        assertTrue(testStack.isEmpty());
    }

    @Test
    void emptyStackPopTest(){ assertThrows(NoSuchElementException.class,()-> {testStack.pop();}); }

    @Test
    void emptyStackPeekTest(){ assertThrows(NoSuchElementException.class,()->testStack.peek());}


    @Test
    @DisplayName("Bound size testing")
    void bindSizeTesting(){
        TqsStack<String> stack = new TqsStack<>(0);
        assertThrows(IllegalStateException.class,()->stack.push("1"));
    }

}