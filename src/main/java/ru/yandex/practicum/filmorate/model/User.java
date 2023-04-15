package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private  Integer id;
    @NotBlank(message = "Email не должен быть пустым")
    @Email
    private  String email;
    @Pattern(regexp = "\\S*", message = "Логин не должен содержать пробелов")
    @NotBlank(message = "Login не должен быть пустым")
    private  String login;
    private  String name;
    @Past
    private LocalDate birthday;
    private final Set<Integer> friends = new HashSet<>();
}
