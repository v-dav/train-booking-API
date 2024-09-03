package org.example.trainplanet.user;

import org.example.trainplanet.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * User service - Service layer
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundException(id, "User"));
    }

    public List<User> searchUsers(UserSearchCriteria criteria) {
        StringBuilder queryString = new StringBuilder("SELECT u FROM User u WHERE 1=1");

        if (criteria.getGoogleId() != null) {
            queryString.append(" AND u.googleId = :googleId");
        }
        if (criteria.getEmail() != null) {
            queryString.append(" AND u.email = :email");
        }
        if (criteria.getFirstname() != null) {
            queryString.append(" AND u.firstname LIKE :firstname");
        }
        if (criteria.getLastname() != null) {
            queryString.append(" AND u.lastname LIKE :lastname");
        }
        if (criteria.getRole() != null) {
            queryString.append(" AND u.role = :role");
        }
        if (criteria.getPhone() != null) {
            queryString.append(" AND u.phone = :phone");
        }
        if (criteria.getFirstLogin() != null) {
            queryString.append(" AND u.firstLogin >= :firstLogin");
        }
        if (criteria.getLastLogin() != null) {
            queryString.append(" AND u.lastLogin <= :lastLogin");
        }
        if (criteria.getAuthMethod() != null) {
            queryString.append(" AND u.authMethod = :authMethod");
        }

        TypedQuery<User> query = entityManager.createQuery(queryString.toString(), User.class);

        if (criteria.getGoogleId() != null) {
            query.setParameter("googleId", criteria.getGoogleId());
        }
        if (criteria.getEmail() != null) {
            query.setParameter("email", criteria.getEmail());
        }
        if (criteria.getFirstname() != null) {
            query.setParameter("firstname", "%" + criteria.getFirstname() + "%");
        }
        if (criteria.getLastname() != null) {
            query.setParameter("lastname", "%" + criteria.getLastname() + "%");
        }
        if (criteria.getRole() != null) {
            query.setParameter("role", criteria.getRole());
        }
        if (criteria.getPhone() != null) {
            query.setParameter("phone", criteria.getPhone());
        }
        if (criteria.getFirstLogin() != null) {
            query.setParameter("firstLogin", criteria.getFirstLogin());
        }
        if (criteria.getLastLogin() != null) {
            query.setParameter("lastLogin", criteria.getLastLogin());
        }
        if (criteria.getAuthMethod() != null) {
            query.setParameter("authMethod", criteria.getAuthMethod());
        }

        return query.getResultList();
    }

    public User postUser(User user) {
        Optional<User> userByEmail = userRepository
                .findUserByEmail(user.getEmail());
        if (userByEmail.isPresent()) {
            throw new IllegalStateException("User already exists or there is a conflict with the data.");
        }
        userRepository.save(user);
        return user;
    }

    public void deleteUser(Long id) {
        boolean exists = userRepository.existsById(id);
        if (!exists) {
            throw new NotFoundException(id, "User");
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public User updateUser(Long id, Map<String, Object> updates) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "User"));

        updates.forEach((key, value) -> {
            switch (key) {
                case "phone":
                    user.setPhone((String) value);
                    break;
                case "profilePictureUrl":
                    user.setProfilePictureUrl((String) value);
                    break;
                case "role":
                    user.setRole((String) value);
                    break;
                case "firstname":
                    user.setFirstname((String) value);
                    break;
                case "lastname":
                    user.setLastname((String) value);
                    break;
                default:
                    throw new IllegalArgumentException("Field " + key + " is not supported for update.");
            }
        });

        return userRepository.save(user);
    }
}
