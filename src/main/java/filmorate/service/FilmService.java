package filmorate.service;

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
        if (!validationFilm(film)) {
            log.error("Ошибка валидации при добавлении фильма {}", film.getName());
            throw new ValidationException();
        }
        film.setId(Identity.INSTANCE.generatedIdFilm());
        filmCollection.put(film.getId(), film);
        log.info("Фильм сохранён, id = {}", film.getId());
        return getFilm(film.getId());
    }

    public Film updateFilm(Film newFilm) {
        if (!filmCollection.containsKey(newFilm.getId())) {
            log.error("Фильм не найден");
            throw new ValidationException();
        }
        if (!validationFilm(newFilm)) {
            log.error("Ошибка валидации при обновлении фильма {}", newFilm.getName());
            throw new RuntimeException("Фильм не найден");
        }
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

    private boolean validationFilm(Film film) {
        LocalDate filmBirthday = LocalDate.of(1895, 12, 28);

        if (film == null) return false;
        if (film.getName().isBlank()) return false;
        if (film.getDescription().length() > 200) return false;
        if (film.getReleaseDate().isBefore(filmBirthday)) return false;
        return !film.getDuration().isNegative();
    }


}
