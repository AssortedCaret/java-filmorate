package model;

import java.time.LocalDate;
import lombok.Data;

@Data
public class User {
    private  int id;
    private  String email;
    private  String login;
    private  String name;
    private  LocalDate birthday;

    public User(int id, String email, String login, String name, int year, int month, int day){
        this.id = id;
        this.email = email;
        this.login = login;
        this.birthday = LocalDate.of(year, month, day);
        this.name = name;
    }
}
