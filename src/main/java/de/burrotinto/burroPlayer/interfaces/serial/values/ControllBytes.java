package de.burrotinto.burroPlayer.interfaces.serial.values;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by derduke on 14.02.17.
 */
@Setter
@Getter
@Component
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix="burroplayer.adapter.PI4J.serial.controllBytes")
public class ControllBytes {
    private int startRange;
    private int endRange;
    private int stop;
    private int pause;
    private int status;
    private int random;
}
