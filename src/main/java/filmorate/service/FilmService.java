package filmorate.service;

import filmorate.model.Film;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FilmService {
    private Map<Integer, Film> filmCollection;

    public void addFilm(Film film) {
        filmCollection.put(film.getId(), film);
    }

    public void updateFilm(int idByFilmInMap, Film newFilm) {
        Film filmInMap = getFilm(idByFilmInMap);

    }

    public List<Film> getFilms() {
        return new ArrayList<>(filmCollection.values());
    }

    private Film getFilm(int id) {
        return filmCollection.get(id);
    }


}
