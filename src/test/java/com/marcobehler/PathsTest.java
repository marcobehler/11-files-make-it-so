package com.marcobehler;


import org.junit.Test;

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
    public void paths() throws Exception {
        Path absolutePath = Paths.get("C:/dev/files/windows/readme.txt");  // operating system independent!

        Path relativePath = Paths.get("./windows/versions/../readme.txt");
        assertThat(relativePath.isAbsolute()).isFalse();
        assertThat(Files.exists(relativePath)).isTrue();

        System.out.println("relativePath = " + relativePath);;
        System.out.println("relativePath.toAbsolutePath() = " + relativePath.toAbsolutePath());
        System.out.println("relativePath.normalize().toAbsolutePath() = " + relativePath.normalize().toAbsolutePath());

        System.out.println(Paths.get("C:/dev/files").relativize(absolutePath));
    }
}
