package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.*;
import lombok.Data;

@Data
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
    private  LocalDate birthday;
    private Set<Integer> friends = new HashSet<>();

  public User(Integer id, String email, String login, String name, LocalDate date){
        this.id = id;
        this.email = email;
        this.login = login;
        this.birthday = date;
        this.name = name;
    }
    public Set<Integer> getFriends() {
        return friends;
    }

    public void setFriends(Integer user) {
        friends.add(user);
    }

    public void deleteFriend(Integer id){
      friends.remove(id);
    }
}
