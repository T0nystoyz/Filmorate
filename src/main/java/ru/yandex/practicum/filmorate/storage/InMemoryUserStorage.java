package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashSet;
import java.util.Set;

@Component
public class InMemoryUserStorage extends AbstractInMemoryStorage<User> implements UserStorage {
    private final Set<String> emails = new HashSet<>();

    @Override
    public User create(User user) {
        user = super.create(user);
        emails.add(user.getEmail());
        return user;
    }

    @Override
    public User update(User user) {
        user = super.update(user);
        emails.add(user.getEmail());
        return user;
    }

    @Override
    public boolean containsEmail(String email) {
        return emails.contains(email);
    }
}
