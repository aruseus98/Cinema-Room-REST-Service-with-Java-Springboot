package cinema;

import cinema.model.CinemaRoom;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CinemaConfig {
    @Bean
    public CinemaRoom cinemaRoom() {
        return new CinemaRoom(9, 9);
    }
}
