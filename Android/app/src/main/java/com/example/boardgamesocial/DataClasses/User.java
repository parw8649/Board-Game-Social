package com.example.boardgamesocial.DataClasses;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Objects;

public class User implements DataClass {
    @SerializedName("id")
    private Integer id;
    @SerializedName("date_joined")
    private Date dateJoined;
    @SerializedName("email")
    private String email;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("is_active")
    private Boolean isActive;
    @SerializedName("is_staff")
    private Boolean isStaff;
    @SerializedName("is_superuser")
    private Boolean isSuperuser;
    @SerializedName("last_login")
    private Date lastLogin;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("password")
    private String password;
    @SerializedName("username")
    private String username;

    public User(Integer id, Date dateJoined, String email, String firstName, Boolean isActive, Boolean isStaff, Boolean isSuperuser, Date lastLogin, String lastName, String password, String username) {
        this.id = id;
        this.dateJoined = dateJoined;
        this.email = email;
        this.firstName = firstName;
        this.isActive = isActive;
        this.isStaff = isStaff;
        this.isSuperuser = isSuperuser;
        this.lastLogin = lastLogin;
        this.lastName = lastName;
        this.password = password;
        this.username = username;
    }

    public User(Date dateJoined, String email, String firstName, Boolean isActive, Boolean isStaff, Boolean isSuperuser, Date lastLogin, String lastName, String username, String password) {
        this.dateJoined = dateJoined;
        this.email = email;
        this.firstName = firstName;
        this.isActive = isActive;
        this.isStaff = isStaff;
        this.isSuperuser = isSuperuser;
        this.lastLogin = lastLogin;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    //Used for Login
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //Used for signUp
    public User(String firstName, String lastName, String email, String username,  String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getStaff() {
        return isStaff;
    }

    public void setStaff(Boolean staff) {
        isStaff = staff;
    }

    public Boolean getSuperuser() {
        return isSuperuser;
    }

    public void setSuperuser(Boolean superuser) {
        isSuperuser = superuser;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Integer getPrimaryKey() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId())
                && Objects.equals(getDateJoined(), user.getDateJoined())
                && Objects.equals(getEmail(), user.getEmail())
                && Objects.equals(getFirstName(), user.getFirstName())
                && Objects.equals(isActive, user.isActive)
                && Objects.equals(isStaff, user.isStaff)
                && Objects.equals(isSuperuser, user.isSuperuser)
                && Objects.equals(getLastLogin(), user.getLastLogin())
                && Objects.equals(getLastName(), user.getLastName())
                && Objects.equals(getPassword(), user.getPassword())
                && Objects.equals(getUsername(), user.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getDateJoined(),
                getEmail(),
                getFirstName(),
                isActive,
                isStaff,
                isSuperuser,
                getLastLogin(),
                getLastName(),
                getPassword(),
                getUsername());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", dateJoined=" + dateJoined +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", isActive=" + isActive +
                ", isStaff=" + isStaff +
                ", isSuperuser=" + isSuperuser +
                ", lastLogin=" + lastLogin +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
