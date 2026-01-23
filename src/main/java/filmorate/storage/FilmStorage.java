package filmorate.storage;

import filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    void addFilm(Film film);

    void removeFilm(int id);

    void updateFilm(Film film);

    Film getFilm(int id);

    List<Film> getFilms();


}
