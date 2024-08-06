package ai.fluent.fluentai.User;

import ai.fluent.fluentai.Language.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LanguageRepository languageRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setName(userDTO.getName());
        user.setPhotoUrl(userDTO.getPhotoUrl());

        Optional<Language> language = languageRepository.findById(userDTO.getNativeLangId());
        language.ifPresent(user::setNativeLang);

        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public Optional<User> updateUser(Integer id, UserDTO userDTO) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());
            user.setName(userDTO.getName());
            user.setPhotoUrl(userDTO.getPhotoUrl());

            // optional: if language not found throw exception
            Optional<Language> language = languageRepository.findById(userDTO.getNativeLangId());
            language.ifPresent(user::setNativeLang);

            user.setUpdatedAt(LocalDateTime.now());
            return userRepository.save(user);
        });
    }

    public boolean deleteUser(Integer id) {
        return userRepository.findById(id).map(user -> {
            userRepository.delete(user);
            return true;
        }).orElse(false);
    }
}
