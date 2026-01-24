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
        filmCollection.remove(id);
    }

    @Override
    public void updateFilm(Film film) {
        filmCollection.put(film.getId(), film);
    }

    @Override
    public Film getFilm(int id) {
        return filmCollection.get(id);
    }

    @Override
    public Map<Integer, Film> getFilms() {
        return new HashMap<>(filmCollection);
    }

}
