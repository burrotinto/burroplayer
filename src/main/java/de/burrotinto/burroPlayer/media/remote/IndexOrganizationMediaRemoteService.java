package de.burrotinto.burroPlayer.media.remote;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by derduke on 01.03.17.
 */
public interface IndexOrganizationMediaRemoteService {
    void addMovie(int pos, String path);

    List<Integer> getIndexList();

    Optional<String> getPathOfIndex(Integer index);

    Optional<Integer> getIndexForFile(String file);

    boolean hasPlayerAt(Integer introKey);

    void remove(int pos);
}
