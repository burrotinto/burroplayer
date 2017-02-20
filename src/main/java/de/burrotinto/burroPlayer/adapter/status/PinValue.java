package de.burrotinto.burroPlayer.adapter.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by derduke on 20.02.17.
 */
@Setter
@Getter
@Component
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "burroplayer.adapter.PI4J.pins")
public class PinValue {
    private String happening;
    private String moviestatus;
}
