package de.burrotinto.burroPlayer.adapter.rs232.values;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by derduke on 16.02.17.
 */
@Setter
@Getter
@Component
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "PI4J.serial.sendingBytes")
public class SendingBytes {
    private int playerRunning;
    private int playerNotRunning;
}
