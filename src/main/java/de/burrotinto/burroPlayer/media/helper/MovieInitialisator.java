package de.burrotinto.burroPlayer.media.helper;

import de.burrotinto.burroPlayer.media.analysator.MovieAnalyser;
import de.burrotinto.burroPlayer.media.remote.IndexMediaRemoteService;
import de.burrotinto.burroPlayer.media.remote.IndexOrganizationMediaRemoteService;
import de.burrotinto.burroPlayer.values.BurroPlayerConfig;
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
    private final BurroPlayerConfig config;
    private final MovieAnalyser analyser;

    public void initAllClipsByNumberAndPath(String path, IndexOrganizationMediaRemoteService remote) throws IOException {
        log.info("Filewalk: " + path);
        //Rekursiv
        Stream<Path> st = Files.walk(Paths.get(path));
        // Verzeichniss
//        DirectoryStream<Path> st = Files.newDirectoryStream(new File(path).toPath());
        Iterator<Path> it = st.iterator();

        while (it.hasNext()) {
            Path p = it.next();
            log.info("    File: " + p.toFile().getAbsolutePath() + " " + Files.probeContentType(p));
//            if (Files.probeContentType(p).contains("video") || Files.probeContentType(p).contains("audio")) {
            if (!p.toFile().isDirectory() && !remote.getIndexForFile(p.toFile().getAbsolutePath()).isPresent() &&
                    analyser.isMovie(p.toFile().getAbsolutePath())) {
                log.info("       is Movie File: " + p.toFile().getAbsolutePath());
                int number = 0;
                boolean isNumber = false;
                if (p.toFile().getName().contains(" ")) {
                    File newFile = new File(p.toFile().getAbsolutePath().replace(" ", "_"));
                    p.toFile().renameTo(newFile);
                    log.info("    File: {} renamed to: {}", p.toFile().getAbsolutePath(), newFile.getAbsolutePath());
                    p = newFile.toPath();
                }

                try {
                    for (char c : p.toFile().getName().toCharArray()) {
                        int x = Integer.parseInt(c + "");
                        isNumber = true;
                        number = number * 10 + x;
                    }
                } catch (NumberFormatException e) {

                }

                if (!isNumber || remote.hasPlayerAt(number)) {
                    number = config.getMinNumber();
                    while (remote.hasPlayerAt(number)) {
                        number++;
                    }
                }
                log.info("            {} on pos {} hinzufügen", p.toFile().getAbsolutePath(), number);
                remote.addMovie(number, p.toFile().getAbsolutePath());
            }
        }
        st.close();
    }

    public void setPrefixMovieOnPos(String path, IndexMediaRemoteService remote, String prefix, int pos) throws IOException {
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
