package filmorate.storage;

import filmorate.model.Film;

import java.util.List;
import java.util.Map;

public interface FilmStorage {

    void addFilm(Film film);

    void removeFilm(int id);

    void updateFilm(Film film);

    Film getFilm(int id);

    Map<Integer, Film> getFilms();


}
