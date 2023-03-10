package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;

import javax.validation.constraints.*;
import lombok.Data;

@Data
public class User {
    private  int id;
    @NotBlank(message = "Email не должен быть пустым")
    @Email
    private  String email;
    @Pattern(regexp = "\\S*", message = "Логин не должен содержать пробелов")
    @NotBlank(message = "Login не должен быть пустым")
    private  String login;
    private  String name;
    @Past
    private  LocalDate birthday;

  public User(int id, String email, String login, String name, LocalDate date){
        this.id = id;
        this.email = email;
        this.login = login;
        this.birthday = date;
        this.name = name;
    }

    private int putIdUser(){
        id += 1;
        return id;
    }
}
