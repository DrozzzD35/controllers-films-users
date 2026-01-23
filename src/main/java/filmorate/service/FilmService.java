package filmorate.service;

import filmorate.exception.NotFoundException;
import filmorate.exception.ValidationException;
import filmorate.model.Film;
import filmorate.storage.FilmStorage;
import filmorate.utils.Identity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class FilmService {
    private final FilmStorage filmCollection;

    public FilmService(FilmStorage filmCollection) {
        this.filmCollection = filmCollection;
    }

    public Film addFilm(Film film) {
        validationFilm(film);
        filmCollection.addFilm(film);
        film.setId(Identity.INSTANCE.generatedIdFilm());
        log.info("Фильм сохранён, id = {}", film.getId());
        return getFilm(film.getId());
    }

    public Film updateFilm(Film newFilm) {
        validationFilm(newFilm);
        filmCollection.updateFilm(newFilm);
        log.info("Фильм обновлён, id = {}", newFilm.getId());
        return getFilm(newFilm.getId());
    }

    public List<Film> getFilms() {
        log.info("Получена коллекция фильмов");
        return filmCollection.getFilms();
    }

    public Film getFilm(int id) {
        log.info("Получен фильм, id: {}", id);
        return filmCollection.getFilm(id);
    }

    public void removeFilm(int id) {
        filmCollection.removeFilm(id);
        log.info("Фильм удалён, id фильма: {}", id);
    }

    private void validationFilm(Film film) {
        LocalDate filmBirthday = LocalDate.of(1895, 12, 28);

        if (film == null) {
            throw new ValidationException("Фильм не обнаружен");
        }
        if (film.getName() == null || film.getName().isBlank()) {
            throw new ValidationException("Ошибка названия фильма");
        }
        if (film.getDescription() == null || film.getDescription().length() > 200) {
            throw new ValidationException("Ошибка описания фильма");
        }
        if (film.getReleaseDate() == null || film.getReleaseDate().isBefore(filmBirthday)) {
            throw new ValidationException("Ошибка даты релиза");
        }
        if (film.getDuration() < 0) {
            throw new ValidationException("Ошибка продолжительности фильма");
        }
    }

}
