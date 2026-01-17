package filmorate.service;

import filmorate.exception.ValidationException;
import filmorate.model.Film;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;

public class FilmServiceTest {
    private FilmService filmService;
    private Film film;

    @BeforeEach
    void SetUp() {
        this.filmService = new FilmService();

        film = new Film();
        film.setName("test");
        film.setDescription("Description");
        film.setReleaseDate(LocalDate.of(2000, 7, 16));
        film.setDuration(Duration.ofMinutes(120));
    }

    @Test
    void name() {
        Assertions.assertNotNull(film);

        film.setName(null);
        Assertions.assertThrows(ValidationException.class, () -> {
            filmService.addFilm(film);
        });

        film.setName("");
        Assertions.assertThrows(ValidationException.class, () -> {
            filmService.addFilm(film);
        });

        film.setName(" ");
        Assertions.assertThrows(ValidationException.class, () -> {
            filmService.addFilm(film);
        });

        film.setName("Ivan Ivanov");
        Assertions.assertThrows(ValidationException.class, () -> {
            filmService.addFilm(film);
        });
    }

    @Test
    void description() {
        film.setDescription(null);
        Assertions.assertThrows(ValidationException.class, () -> {
            filmService.addFilm(film);
        });

        film.setDescription("50101010101010101010101010101010101010101010101010" +
                "50101010101010101010101010101010101010101010101010" +
                "50101010101010101010101010101010101010101010101010" +
                "50101010101010101010101010101010101010101010101010" + "1");

        Assertions.assertThrows(ValidationException.class, () -> {
            filmService.addFilm(film);
        });
    }

    @Test
    void releaseDate() {
        film.setReleaseDate(null);
        Assertions.assertThrows(ValidationException.class, () -> {
            filmService.addFilm(film);
        });

        film.setReleaseDate(LocalDate.of(1895, 12, 27));
        Assertions.assertThrows(ValidationException.class, () -> {
            filmService.addFilm(film);
        });
    }

    @Test
    void duration() {
        film.setDuration(null);
        Assertions.assertThrows(ValidationException.class, () -> {
            filmService.addFilm(film);
        });

        film.setDuration(Duration.ofMinutes(-1));
        Assertions.assertThrows(ValidationException.class, () -> {
            filmService.addFilm(film);
        });

        film.setDuration(Duration.ofMinutes(0));
        Assertions.assertThrows(ValidationException.class, () -> {
            filmService.addFilm(film);
        });
    }


}
