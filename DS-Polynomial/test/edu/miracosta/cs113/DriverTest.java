package edu.miracosta.cs113;

import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class DriverTest {
    private InputStream stream = new FileInputStream("./resource/input.txt");
    public DriverTest() throws IOException {
        System.setIn(stream);
    }

}
