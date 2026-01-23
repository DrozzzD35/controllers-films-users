package filmorate.service;

import filmorate.exception.ValidationException;
import filmorate.model.Film;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class FilmServiceTest {
    private FilmService filmService;
    private Film film;

    @BeforeEach
    void setUp() {
//        this.filmService = new FilmService();

        film = new Film();
        film.setName("test");
        film.setDescription("Description");
        film.setReleaseDate(LocalDate.of(2000, 7, 16));
        film.setDuration(120);
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
        Assertions.assertEquals("Ivan Ivanov", film.getName());

        film.setName("Petr");
        Assertions.assertEquals("Petr", film.getName());
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

        film.setDescription("Описание");
        Assertions.assertEquals("Описание", film.getDescription());
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

        film.setReleaseDate(LocalDate.of(1895, 12, 28));
        Assertions.assertEquals(LocalDate.of(1895, 12, 28),
                film.getReleaseDate());

    }

    @Test
    void duration() {
        film.setDuration(-1);
        Assertions.assertThrows(ValidationException.class, () -> {
            filmService.addFilm(film);
        });

        film.setDuration(3);
        Assertions.assertEquals(3,film.getDuration());

        film.setDuration(0);
        Assertions.assertEquals(0,film.getDuration());

    }
}
