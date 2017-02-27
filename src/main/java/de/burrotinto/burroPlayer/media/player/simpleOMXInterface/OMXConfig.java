package de.burrotinto.burroPlayer.media.player.simpleOMXInterface;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by derduke on 27.02.17.
 */
@Setter
@Getter
@Component
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "omxplayer")
public class OMXConfig {
    private String exe;
    private String options;
    private String pause;
}
