package filmorate.service;

import filmorate.exception.NotFoundException;
import filmorate.exception.ValidationException;
import filmorate.model.Film;
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
    private final Map<Integer, Film> filmCollection = new HashMap<>();

    public Film addFilm(Film film) {
        validationFilm(film);
        film.setId(Identity.INSTANCE.generatedIdFilm());
        filmCollection.put(film.getId(), film);
        log.info("Фильм сохранён, id = {}", film.getId());
        return getFilm(film.getId());
    }

    public Film updateFilm(Film newFilm) {
        if (!filmCollection.containsKey(newFilm.getId())) {
            throw new NotFoundException("Фильм не найден");
        }
        validationFilm(newFilm);

        log.info("Фильм обновлён, id = {}", newFilm.getId());
        filmCollection.put(newFilm.getId(), newFilm);
        return getFilm(newFilm.getId());
    }

    public List<Film> getFilms() {
        return new ArrayList<>(filmCollection.values());
    }

    public Film getFilm(int id) {
        return filmCollection.get(id);
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
