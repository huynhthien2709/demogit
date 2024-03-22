package com.example.demopostman.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entit
@Table(name = "users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @NotNull(message = "name is required")
    @Column(name = "name")
    private  String name;
    @NotNull(message = "email is required")
    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z]+$")
    @Column(name = "email")
    private  String email;
    @NotNull(message = "password is required")
    @Column(name = "password")
    private  String password;
    @Column(name = "address")
    private  String address;
    @Column(name = "phone")
    private  String phone;
    @Column(name = "avatar")
    private  String avatar;
    @Column(name = "gender")
    private  String gender;
    @Column(name = "description")
    private  String description;
    @Column(name = "is_active")
    private  int isActive;

    @Column(name = "created_at")
    private  String createdAt;
    @Column(name = "update_at")
    private  String updateAt;
    @Column(name = "delete_at")
    private  String deleteAt;

    @JsonBackReference(value = "role_id") //fix lỗi khóa ngoại (https://stackoverflow.com/questions/52782071/spring-boot-error-cannot-call-senderror-after-the-response-has-been-committ)
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public User(String name, String email, String password, String address, String phone, String avatar, String gender,
                String description, int isActive, String createdAt, String updateAt, String deleteAt) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.avatar = avatar;
        this.gender = gender;
        this.description = description;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.deleteAt = deleteAt;
    }

    public User() {
    }

/*    public User(String userName, String s, Set<SimpleGrantedAuthority> authorities) {
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(String deleteAt) {
        this.deleteAt = deleteAt;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
