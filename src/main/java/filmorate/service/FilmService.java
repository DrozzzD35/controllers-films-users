package filmorate.service;

import filmorate.exception.NotFoundException;
import filmorate.exception.ValidationException;
import filmorate.model.Film;
import filmorate.model.User;
import filmorate.storage.FilmStorage;
import filmorate.storage.UserStorage;
import filmorate.utils.Identity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FilmService {
    private final FilmStorage filmCollection;
    private final UserStorage userCollection;

    public FilmService(FilmStorage filmCollection, UserStorage userCollection) {
        this.filmCollection = filmCollection;
        this.userCollection = userCollection;
    }

    public Film addFilm(Film film) {
        validationFilm(film);
        film.setId(Identity.INSTANCE.generatedIdFilm());
        filmCollection.addFilm(film);
        log.info("Фильм сохранён, id = {}", film.getId());
        return getFilm(film.getId());
    }

    public Film updateFilm(Film newFilm) {
        validationFilm(newFilm);
        Film filmInCollection = getFilm(newFilm.getId());
        newFilm.getLikes().addAll(filmInCollection.getLikes());
        filmCollection.updateFilm(newFilm);
        log.info("Фильм обновлён, id = {}", newFilm.getId());
        return getFilm(newFilm.getId());
    }

    public List<Film> getFilms() {
        log.info("Получена коллекция фильмов");
        return new ArrayList<>(filmCollection.getFilms().values());
    }

    public Film getFilm(int id) {
        Film film = filmCollection.getFilm(id);
        if (film == null) {
            log.error("Фильм с id {} не найден", id);
            throw new NotFoundException("Фильм с id " + id + " не найден");
        }
//        log.info("Получен фильм с id {}", id);
        return film;
    }

    public User getUser(int id) {
        User user = userCollection.getUser(id);
        if (user == null) {
            log.info("Пользователь с id {} не найден", id);
            throw new NotFoundException("Пользователь с id " + id + " не найден");
        }
//        log.info("Получен пользователь с id {}", id);
        return user;
    }

    public void removeFilm(int id) {
        Film film = getFilm(id);
        filmCollection.removeFilm(film.getId());
        log.info("Фильм удалён с id - {}", id);
    }

    public void addLike(int filmId, int userId) {
        User user = getUser(userId);
        Film film = getFilm(filmId);
        film.getLikes().add(user.getId());
        log.info("Фильм id - {}, понравился пользователю id - {}", film.getId(), user.getId());
    }

    public void removeLike(int filmId, int userId) {
        User user = getUser(userId);
        Film film = getFilm(filmId);
        film.getLikes().remove(user.getId());
        log.info("Пользователь id - {}, пересмотрел своё мнение относительно фильма id - {},", user.getId(), film.getId());
    }

    public List<Film> getPopularFilms(int count) {
        log.info("Топ {} популярных фильмов", count);
        return getFilms().stream()
                .sorted((f1, f2) -> f2.getLikes().size() - f1.getLikes().size())
                .limit(count)
                .toList();
    }

    private void validationFilm(Film film) {
        LocalDate filmBirthday = LocalDate.of(1895, 12, 28);

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
