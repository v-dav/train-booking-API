package org.example.trainplanet.user;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * USer entity/model - Resource layer
 */

@Entity
@Table(name = "users")
public class User {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    private String googleId;
    private String email;
    private String firstname;
    private String lastname;
    private String profilePictureUrl;
    private String role;
    private String phone;
    private LocalDateTime firstLogin;
    private LocalDateTime lastLogin;
    private String passwordHash;
    private String authMethod;


    // Constructor with all fields
    public User(Long id, String googleId, String email, String firstname, String lastname, String profilePictureUrl, String role, String phone, LocalDateTime firstLogin, LocalDateTime lastLogin, String passwordHash, String authMethod) {
        this.id = id;
        this.googleId = googleId;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.profilePictureUrl = profilePictureUrl;
        this.role = role;
        this.phone = phone;
        this.firstLogin = firstLogin;
        this.lastLogin = lastLogin;
        this.passwordHash = passwordHash;
        this.authMethod = authMethod;
    }

    // Constructor with none fields
    public User() {
    }

    // Constructor with all fields except id
    public User(String googleId, String email, String firstname, String lastname, String profilePictureUrl, String role, String phone, LocalDateTime firstLogin, LocalDateTime lastLogin, String passwordHash, String authMethod) {
        this.googleId = googleId;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.profilePictureUrl = profilePictureUrl;
        this.role = role;
        this.phone = phone;
        this.firstLogin = firstLogin;
        this.lastLogin = lastLogin;
        this.passwordHash = passwordHash;
        this.authMethod = authMethod;
    }

    // Add a method with @PrePersist to set createdAt automatically before the save
    @PrePersist
    protected void onCreate() {
        this.firstLogin = LocalDateTime.now();
    }

    // Getters, setters and toString

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(LocalDateTime firstLogin) {
        this.firstLogin = firstLogin;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getAuthMethod() {
        return authMethod;
    }

    public void setAuthMethod(String authMethod) {
        this.authMethod = authMethod;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", googleId=" + googleId +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", profilePictureUrl='" + profilePictureUrl + '\'' +
                ", role='" + role + '\'' +
                ", phone='" + phone + '\'' +
                ", firstLogin=" + firstLogin +
                ", lastLogin=" + lastLogin +
                ", passwordHash='" + passwordHash + '\'' +
                ", authMethod='" + authMethod + '\'' +
                '}';
    }
}
