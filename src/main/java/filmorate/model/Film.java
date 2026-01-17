package filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;

@Data
public class Film {
    private int id;

    @NotNull
    @NotBlank
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Duration duration;


}
