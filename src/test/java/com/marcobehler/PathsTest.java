package com.marcobehler;


import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

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
    public void createTemporaryFile() {
        try {
            Path tempFile1 = Files.createTempFile(null, ".myapp");
            System.out.format("The temporary file" +
                    " has been created: %s%n", tempFile1);

            Path tempFile2 = Files.createTempFile(Paths.get("c:/dev/files/"), null, ".myapp");
            System.out.format("The temporary file" +
                    " has been created: %s%n", tempFile2);

            tempFile1.toFile().deleteOnExit();

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    Files.delete(tempFile1);
                } catch (IOException e) {}
            }));


        } catch (IOException e) {
            e.printStackTrace();
        }




    }



















}
