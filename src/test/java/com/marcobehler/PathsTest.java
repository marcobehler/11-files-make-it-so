package com.marcobehler;


import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Thanks for watching this episode! Send any feedback to info@marcobehler.com!
 */
public class PathsTest {

    @Test
    public void list_files() throws Exception {
        Files.list(Paths.get("C:\\dev\\files\\windows")).forEach(System.out::println);
        System.out.println("======================");

        Files.newDirectoryStream(Paths.get("C:\\dev\\files\\windows"),
                path -> Files.isRegularFile(path) && path.toString().endsWith("txt")
        ).forEach(System.out::println);

        System.out.println("======================");
        Files.walk(Paths.get("C:\\dev\\files\\windows")).forEach(System.out::println);

    }

    @Test // path == file
    public void path_exists() throws Exception {
        Path path = Paths.get("C:\\dev\\files\\windows\\license.txt");
        assertThat(Files.exists(path)).isTrue();

        path = Paths.get("C:/dev/files/windows/license.txt");  // operating system independent!
        assertThat(Files.exists(path)).isTrue();

        path = Paths.get("C:", "dev", "files", "windows", "license.txt");
        assertThat(Files.exists(path)).isTrue();

        path = Paths.get("C:", "dev", "files", "windows").resolve("license.txt"); // resolve == getchild
        assertThat(Files.exists(path)).isTrue();
    }

    @Test
    public void can_read_path() throws Exception {
        Path path = Paths.get("C:/dev/files/windows/license.txt");  // operating system independent!
        String fileContent = new String(Files.readAllBytes(path), StandardCharsets.ISO_8859_1);
        assertThat(fileContent).isEqualTo("hello, this is our grand license! öööö ääää üüüüü");
    }

    @Test
    public void can_write_to_path() throws Exception {
        Path path = Paths.get("C:/dev/files/windows/readme.txt");  // operating system independent!
        Files.write(path, "what is going on...ääüüüöö".getBytes(StandardCharsets.ISO_8859_1));
        assertThat(Files.exists(path)).isTrue();
    }

    @Test
    public void move_and_delete_file() throws Exception {
        Path path = Paths.get("C:/dev/files/windows/");  // operating system independent!
        Files.walk(path)
                .sorted(Comparator.reverseOrder())
                .forEach(p -> {
                    try {
                        Files.delete(p);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }); // :(
    }


    @Test
    public void reading_files() {
        Charset charset = Charset.forName("ISO-8859-1");

        try (InputStream in = Files.newInputStream(Paths.get("C:\\dev\\files\\windows\\readme.txt"));
             BufferedReader reader =
                     new BufferedReader(new InputStreamReader(in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException x) {
            System.err.println(x);
        }


        try (BufferedReader reader = Files.newBufferedReader(Paths.get("C:\\dev\\files\\windows\\readme.txt"), charset)) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    @Test
    public void writingFiles() {
        String string = "testme";
        try (OutputStream out = new BufferedOutputStream(
                Files.newOutputStream(Paths.get("C:\\dev\\files\\windows\\versions\\hulu.txt")))) {

            out.write(string.getBytes(), 0, "testme".length());
        } catch (IOException x) {
            System.err.println(x);
        }
        /*Charset charset = Charset.forName("US-ASCII");
        String s = ...;
        try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
            writer.write(s, 0, s.length());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }*/
    }
}
