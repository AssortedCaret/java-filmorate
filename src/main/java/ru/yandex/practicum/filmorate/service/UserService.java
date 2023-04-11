package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UserService {
    private final UserStorage inMemoryUserStorage;
    @Autowired
    public UserService(InMemoryUserStorage inMemoryUserStorage){
        this.inMemoryUserStorage = inMemoryUserStorage;
    }
//    public User addFriend(Integer userId, Integer otherUserId) throws NotFoundException {
//        if(userId < 0 || otherUserId < 0){
//            log.error("Передан отрицательный UserId в UserService, addFriend()");
//            throw new NotFoundException("Отрицательный UserId");
//        }
//        HashMap<Integer, User> users;
//        users = inMemoryUserStorage.getMap();
//        if(users.get(userId) == null) {
//            log.error("Отсутствует указанный UserId в UserService, addFriend()");
//            throw new NotFoundException("Отсутствует указанный UserId");
//        }
//        if(users.get(otherUserId) == null) {
//            log.error("Отсутствует указанный otherUserId в UserService, addFriend()");
//            throw new NotFoundException("Отсутствует указанный otherUserId");
//        }
//        User user = users.get(userId);
//        User userFriend = users.get(otherUserId);
//        user.setFriends(otherUserId);
//        userFriend.setFriends(userId);
//        log.info("Метод отработал положительно в UserService, addFriend()");
//        return user;
//    }
//
//    public void deleteFriend(Integer userId, Integer otherUserId) throws NotFoundException {
//        HashMap<Integer, User> users;
//        users = inMemoryUserStorage.getMap();
//        if(users.get(userId) == null) {
//            log.error("Отсутствует указанный UserId в UserService, deleteFriend()");
//            throw new NotFoundException("Отсутствует указанный UserId");
//        }
//        User user = users.get(userId);
//        user.deleteFriend(otherUserId);
//        if(users.get(otherUserId) == null) {
//            log.error("Отсутствует указанный otherUserId в UserService, deleteFriend()");
//            throw new NotFoundException("Отсутствует указанный otherUserId");
//        }
//        User userFriend = users.get(otherUserId);
//        userFriend.deleteFriend(userId);
//        log.info("Метод отработал положительно в UserService, deleteFriend()");
//    }

    public List<User> returnsFriend(Integer id) throws NotFoundException {
        HashMap<Integer, User> users = inMemoryUserStorage.getMap();
        if(users.get(id) == null) {
            log.error("Отсутствует указанный UserId в UserService, returnFriend()");
            throw new NotFoundException("Отсутствует указанный UserId");
        }
        User user = users.get(id);
        List<User> friendList = new ArrayList<>();
        for(Integer numb: user.getFriends())
        friendList.add(users.get(numb));
        log.info("Метод отработал положительно в UserService, returnFriend()");
        return friendList;
    }

    public List<User> returnSameFriend(Integer userId, Integer otherUserId) throws NotFoundException{
        HashMap<Integer, User> users = inMemoryUserStorage.getMap();
        if(users.get(userId) == null) {
            log.error("\"Отсутствует указанный UserId {} в UserService, returnSameFriend()\", userId");
            throw new NotFoundException("Отсутствует указанный UserId");
        }
        User user = users.get(userId);
        User otherUser = users.get(otherUserId);
        List<User> userFriend = new ArrayList<>();
        for(Integer numb: user.getFriends())
            userFriend.add(users.get(numb));
        if(users.get(otherUserId) == null) {
            log.error("\"Отсутствует указанный otherUserId {} в UserService, returnSameFriend()\", otherUserId");
            throw new NotFoundException("Отсутствует указанный otherUserId");
        }
        List<User> otherUserFriend = new ArrayList<>();
        for(Integer numb: otherUser.getFriends())
            otherUserFriend.add(users.get(numb));
        if(users.size() == 0 || otherUserFriend.size() == 0){
            log.info("Один из список друзей пуст в UserService, returnSameFriend()");
            return userFriend;
        }
        userFriend.retainAll(otherUserFriend);
        log.info("Метод отработал положительно в UserService, returnSameFriend()");
        return userFriend;
    }

    public User getUser(Integer userId) throws NotFoundException {
        Map<Integer, User> users = inMemoryUserStorage.getMap();
        if(users.size() < userId){
            log.error("Такого User не существует, getUser()");
            throw new NotFoundException("Такого User не существует");
        }
        return users.get(userId);
    }

}
