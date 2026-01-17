package filmorate.controller;

import filmorate.model.Film;
import filmorate.service.FilmService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping("/films/film")
    public Film addFilm(@RequestBody Film film) {
        filmService.addFilm(film);
        return filmService.getFilm(film.getId());
    }

    @PutMapping("/films/film")
    public Film updateFilm(@RequestBody Film newFilm) {
        filmService.updateFilm(newFilm);
        return filmService.getFilm(newFilm.getId());
    }

    @GetMapping("/films")
    public List<Film> getFilms() {
        return filmService.getFilms();
    }


}
