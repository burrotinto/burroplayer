package de.burrotinto.burroPlayer.interfaces.serial.values;

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
@ConfigurationProperties(prefix = "burroplayer.adapter.serial.statusByte")
public class StatusByteConfiguration {
    private int playerRunningBit;
    private int playerPausedBit;
}
