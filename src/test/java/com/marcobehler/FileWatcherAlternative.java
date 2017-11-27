package com.marcobehler;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Thanks for watching this episode! Send any feedback to info@marcobehler.com!
 */
public class FileWatcherAlternative {

    public static void main(String[] args) throws Exception {
        Path dir = Paths.get("C:\\dev\\files\\windows");

        FileAlterationMonitor monitor = new FileAlterationMonitor(1000); // ms
        FileAlterationObserver observer = new FileAlterationObserver(dir.toFile());
        observer.addListener(new FileAlterationListener() {
            @Override
            public void onStart(FileAlterationObserver observer) {

            }

            @Override
            public void onDirectoryCreate(File directory) {

            }

            @Override
            public void onDirectoryChange(File directory) {

            }

            @Override
            public void onDirectoryDelete(File directory) {

            }

            @Override
            public void onFileCreate(File file) {
                try {
                    System.out.println("File created: " + file.getCanonicalPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFileChange(File file) {
                try {
                    System.out.println("File changed: " + file.getCanonicalPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFileDelete(File file) {

            }

            @Override
            public void onStop(FileAlterationObserver observer) {

            }
        });
        monitor.addObserver(observer);
        monitor.start();
    }
}
