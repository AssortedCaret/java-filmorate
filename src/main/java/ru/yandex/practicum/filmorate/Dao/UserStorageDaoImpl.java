package ru.yandex.practicum.filmorate.Dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Dao.Interface.UserStorageDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Component
public class UserStorageDaoImpl implements UserStorageDao {
    private final Logger log = LoggerFactory.getLogger(FilmStorageDaoImpl.class);
    int id = 1;
    private final ArrayList<Integer> listIdUsers = new ArrayList<>();
    private final JdbcTemplate jdbcTemplate;

    public UserStorageDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User addUser(User user) throws ValidationException {
        String request = "insert into users (id, email, login, name, birthday) VALUES (?,?,?,?,?)";
        putIdUser(user);
        listIdUsers.add(user.getId());
        validateUser(user);
        jdbcTemplate.update(request,
                user.getId(),
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday());
        log.info("User {} добавлен в таблицу", user);
        return user;
    }

    @Override
    public Optional<User> updateUser(User user) throws ValidationException {
        String sql = "update users set email = ?, login = ?, name = ?,birthday = ? where id = ?";
        if (listIdUsers.size() < user.getId()) {
            log.error("Передан неверный id. Юзер не обновлен");
            throw new NotFoundException("Не выполнены условия обновления юзера. /n Убедитесь в правильности ввода" +
                    "данных.");
        }
        validateUser(user);
        jdbcTemplate.update(sql,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId());
        log.info("User {} обновлен", user);
        return Optional.ofNullable(getUserId(user.getId()));
    }

    @Override
    public List<User> getUsers() {
        String sql = "select * from users";
        List<User> users = jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs));
        log.info("Получены все Users: {}", users);
        if (users.isEmpty())
            log.error("Список User пуст");
        return users;
    }

    @Override
    public User getUserId(Integer id) {
        String str = "select * from users where id = ?";
        if (listIdUsers.size() < id) {
            log.error("Передан неверный id. Юзер не найден");
            throw new NotFoundException("Не выполнены условия получения юзера. /n Убедитесь в правильности ввода" +
                    "данных.");
        }
        List<User> users = jdbcTemplate.query(str, (rs, rowNum) -> makeUser(rs), id);
        if (users.isEmpty()) {
            return null;
        }
        log.info("Найден User: {}", users.get(0));
        return users.get(0);
    }

    @Override
    public Collection<User> getFriends(Integer id) {
        User user = getUserId(id);
        List<User> friendList = new ArrayList<>();
        for (Integer friendId : user.getFriends()) {
            friendList.add(getUserId(friendId));
        }
        return friendList;
    }

    @Override
    public Set<User> getCommonFriends(Integer userId, Integer otherId) {
        String string = "select * " +
                "from users " +
                "where id in " +
                "(select friend_id from users u " +
                "join friends f on u.id = f.user_id where u.id = ?) " +
                "and id in " +
                "(select friend_id from users u " +
                "join friends f on u.id = f.user_id where u.id = ?)";
        return Set.copyOf(jdbcTemplate.query(string, (rs, rowNum) -> makeUser(rs), userId, otherId));
    }

    @Override
    public Integer addFriend(Integer userId, Integer friendId) {
        if (userId <= 0 || friendId <= 0) {
            log.error("Передан неверный id. Юзер не найден");
            throw new NotFoundException("Не выполнены условия получения юзера. /n Убедитесь в правильности ввода" +
                    "данных.");
        }
        String sql = "insert into friends (user_id, friend_id) values (?,?)";
        jdbcTemplate.update(sql, userId, friendId);
        return friendId;
    }

    @Override
    public void deleteFriend(Integer userId, Integer friendId) {
        String sql = "delete from friends where user_id = ? and friend_id = ?";
        jdbcTemplate.update(sql,
                userId,
                friendId);
    }

    private int putIdUser(User user) {
        user.setId(id);
        id = id + 1;
        return id;
    }

    private User makeUser(ResultSet res) throws SQLException {
        Integer id = res.getInt("id");
        String email = res.getString("email");
        String login = res.getString("login");
        String name = res.getString("name");
        LocalDate birthday = res.getDate("birthday").toLocalDate();
        User user = new User(id, email, login, name, birthday);
        String getFriends = "select FRIEND_ID from FRIENDS where USER_ID = ?";
        user.getFriends().addAll(jdbcTemplate.queryForList(getFriends, Integer.class, user.getId()));
        return user;
    }

    private void validateUser(User user) throws ValidationException {
        if (!((user.getEmail().length() > 0) && user.getEmail().contains("@") && !(user.getLogin().contains(" ")) &&
                (user.getLogin().length() > 0))) {
            log.error("Не выполнены условия добавления пользователя. Пользователь не добавлен");
            throw new ValidationException("Не выполнены условия добавления пользователя.");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
