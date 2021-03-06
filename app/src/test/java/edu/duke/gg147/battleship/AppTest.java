/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.gg147.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;

class AppTest {

  /*@Test
  void test_attacking_phase(){
    Board<Character> b1 = new BattleShipBoard<>(2,2,'X');
    Board<Character> b2 = new BattleShipBoard<>(2,2,'X');
    }*/

  //Method to test main in app
  //gets input.txt and output.txt from dir
  @Test
  @ResourceLock(value = Resources.SYSTEM_OUT, mode = ResourceAccessMode.READ_WRITE)
  void test_main() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes, true);

    //find input.txt
    InputStream input = getClass().getClassLoader().getResourceAsStream("input.txt");
    assertNotNull(input);

    //find output.txt
    InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("output.txt");
    assertNotNull(expectedStream);
    
    //setup in,out to default
    InputStream oldIn = System.in;
    PrintStream oldOut = System.out;
    
    //try to setup in,out to our own
    try {
      System.setIn(input);
      System.setOut(out);
      App.main(new String[0]);
    }
    finally {
      System.setIn(oldIn);
      System.setOut(oldOut);
    }

    //Compare expected and actual output
    String expected = new String(expectedStream.readAllBytes());
    String actual = bytes.toString();

    assertEquals(expected, actual);
    bytes.flush();
    
  }

  @Test
  @ResourceLock(value = Resources.SYSTEM_OUT, mode = ResourceAccessMode.READ_WRITE)
  void test_main1() throws IOException {
  ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes, true);

    //find input.txt
    InputStream input = getClass().getClassLoader().getResourceAsStream("input1.txt");
    assertNotNull(input);

    //find output.txt
    InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("output1.txt");
    assertNotNull(expectedStream);
    
    //setup in,out to default
    InputStream oldIn = System.in;
    PrintStream oldOut = System.out;
    
    //try to setup in,out to our own
    try {
      System.setIn(input);
      System.setOut(out);
      App.main(new String[0]);
    }
    finally {
      System.setIn(oldIn);
      System.setOut(oldOut);
    }

    //Compare expected and actual output
    String expected = new String(expectedStream.readAllBytes());
    String actual = bytes.toString();

    assertEquals(expected, actual);
    bytes.flush();
  }


  @Test
  @ResourceLock(value = Resources.SYSTEM_OUT, mode = ResourceAccessMode.READ_WRITE)
  void test_main2() throws IOException {
  ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes, true);

    //find input.txt
    InputStream input = getClass().getClassLoader().getResourceAsStream("input2.txt");
    assertNotNull(input);

    //find output.txt
    InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("output2.txt");
    assertNotNull(expectedStream);
    
    //setup in,out to default
    InputStream oldIn = System.in;
    PrintStream oldOut = System.out;
    
    //try to setup in,out to our own
    try {
      System.setIn(input);
      System.setOut(out);
      App.main(new String[0]);
    }
    finally {
      System.setIn(oldIn);
      System.setOut(oldOut);
    }

    //Compare expected and actual output
    String expected = new String(expectedStream.readAllBytes());
    String actual = bytes.toString();

    assertEquals(expected, actual);
    bytes.flush();
  }

  
  @Test
  @ResourceLock(value = Resources.SYSTEM_OUT, mode = ResourceAccessMode.READ_WRITE)
  void test_main3() throws IOException {
  ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes, true);

    //find input.txt
    InputStream input = getClass().getClassLoader().getResourceAsStream("input3.txt");
    assertNotNull(input);

    //find output.txt
    InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("output3.txt");
    assertNotNull(expectedStream);
    
    //setup in,out to default
    InputStream oldIn = System.in;
    PrintStream oldOut = System.out;
    
    //try to setup in,out to our own
    try {
      System.setIn(input);
      System.setOut(out);
      App.main(new String[0]);
    }
    finally {
      System.setIn(oldIn);
      System.setOut(oldOut);
    }

    //Compare expected and actual output
    String expected = new String(expectedStream.readAllBytes());
    String actual = bytes.toString();

    assertEquals(expected, actual);
    bytes.flush();
    }

  
  @Test
  @ResourceLock(value = Resources.SYSTEM_OUT, mode = ResourceAccessMode.READ_WRITE)
  void test_main4() throws IOException {
  ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes, true);

    //find input.txt
    InputStream input = getClass().getClassLoader().getResourceAsStream("input4.txt");
    assertNotNull(input);

    //find output.txt
    InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("output4.txt");
    assertNotNull(expectedStream);
    
    //setup in,out to default
    InputStream oldIn = System.in;
    PrintStream oldOut = System.out;
    
    //try to setup in,out to our own
    try {
      System.setIn(input);
      System.setOut(out);
      App.main(new String[0]);
    }
    finally {
      System.setIn(oldIn);
      System.setOut(oldOut);
    }

    //Compare expected and actual output
    String expected = new String(expectedStream.readAllBytes());
    String actual = bytes.toString();

    //assertEquals(expected, actual);
    bytes.flush();
    }
  
}
