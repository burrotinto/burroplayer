package de.burrotinto.burroPlayer.media;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 * Created by derduke on 03.10.16.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MovieInitialisator {

    public void initAllClipsByNumberAndPath(String path, MediaRemote remote) throws IOException {

        //Rekursiv
        Stream<Path> st = Files.walk(Paths.get(path));
        // Verzeichniss
//        DirectoryStream<Path> st = Files.newDirectoryStream(new File(path).toPath());
        Iterator<Path> it = st.iterator();

        while (it.hasNext()) {
            Path p = it.next();
            if (Files.probeContentType(p).contains("video") || Files.probeContentType(p).contains("audio")) {
                int number = 0;
                boolean insert = false;

                try {
                    for (char c : p.toFile().getName().toCharArray()) {
                        int x = Integer.parseInt(c + "");
                        insert = true;
                        number = number * 10 + x;
                    }
                } catch (NumberFormatException e) {

                }

                if (insert) {
                    log.info(p.toFile().getAbsolutePath() + " on pos " + number + " hinzufügen");
                    remote.addMovie(number, p.toFile().getAbsolutePath());
                }

            }
        }
        st.close();
    }

    public void setPrefixMovieOnPos(String path, MediaRemote remote, String prefix, int pos) throws IOException {
        DirectoryStream<Path> st = Files.newDirectoryStream(new File(path).toPath());
        Iterator<Path> it = st.iterator();

        while (it.hasNext()) {
            Path p = it.next();
            if ((Files.probeContentType(p).contains("video") || Files.probeContentType(p).contains("audio")) && p
                    .toFile().getName().toLowerCase().startsWith(prefix)) {
                log.info("Prefix: " + prefix + " " + p.toFile().getAbsolutePath() + " on pos " + pos + " hinzufügen");
                remote.addMovie(pos, p.toFile().getAbsolutePath());

            }
        }
        st.close();
    }
}
