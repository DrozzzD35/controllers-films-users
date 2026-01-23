package filmorate.storage;

import filmorate.exception.NotFoundException;
import filmorate.model.Film;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> filmCollection = new HashMap<>();

    @Override
    public void addFilm(Film film) {
        filmCollection.put(film.getId(), film);
    }

    @Override
    public void removeFilm(int id) {
        if (!filmCollection.containsKey(id)) {
            System.out.println("Фильм с таким идентификатором не найден");
        }
        filmCollection.remove(id);
    }

    @Override
    public void updateFilm(Film film) {
        if (!filmCollection.containsKey(film.getId())) {
            throw new NotFoundException("Фильм с таким идентификатором не найден");
        }
        filmCollection.put(film.getId(), film);
    }

    @Override
    public Film getFilm(int id) {
        if (!filmCollection.containsKey(id)) {
            System.out.println("Фильм с таким идентификатором не найден");
        }
        return filmCollection.get(id);
    }

    @Override
    public List<Film> getFilms() {
        return List.of((Film) filmCollection.values());
    }

}
