package com.team.app.backend.persistance.model;



import java.util.Date;

public class User {

    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    private String email;

    private byte[] image;

    private Date registrationDate;

    private String activateLink;

    private UserStatus status;

    private Role role;

    public User() {
    }

    public User(Long id, String firstname, String lastname, String username, String password, String email, byte[] image, Date registrationDate, String activateLink, UserStatus status, Role role) {
        this.id = id;
        this.firstName = firstname;
        this.lastName = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.image = image;
        this.registrationDate = registrationDate;
        this.activateLink = activateLink;
        this.status = status;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getActivateLink() {
        return activateLink;
    }

    public void setActivateLink(String activateLink) {
        this.activateLink = activateLink;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
