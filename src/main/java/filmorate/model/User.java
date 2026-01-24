package filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private int id;

    @NotNull(message = "почта не может отсутствовать")
    @Email(message = "некорректный формат почты")
    private String email;

    @NotNull(message = "логин не может быть пустым")
    @NotBlank(message = "логин не может быть пустым")
    private String login;

    private String name;

    @NotNull(message = "день рождения не может отсутствовать")
    private LocalDate birthday;

    private Set<Integer> friend = new HashSet<>();

}
