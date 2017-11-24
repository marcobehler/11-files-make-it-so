package com.marcobehler;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * Thanks for watching this episode! Send any feedback to info@marcobehler.com!
 */
public class FileWatcher {

    public static void main(String[] args) throws IOException, InterruptedException {
        Path dir = Paths.get("C:\\dev\\files\\windows");

        WatchService watcher = FileSystems.getDefault().newWatchService();
        dir.register(watcher,
                ENTRY_CREATE,
                ENTRY_DELETE,
                ENTRY_MODIFY);

        for (; ; ) {

            try {
                WatchKey key = watcher.take();

                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();

                    if (kind == OVERFLOW) {
                        continue;
                    }

                    // The relative(!) filename is the
                    // context of the event.
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path filename = ev.context();

                    Path child = dir.resolve(filename);
                    System.out.format("File %s was modified %s: !%n", child.toAbsolutePath(), ev.kind());
                }

                boolean valid = key.reset();
                if (!valid) {
                    break;
                }

            } catch (InterruptedException x) {
                throw new RuntimeException(x); // TODO handle
            }
        }
    }
}
