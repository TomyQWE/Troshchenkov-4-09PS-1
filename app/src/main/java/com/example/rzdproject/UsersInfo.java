package com.example.rzdproject;

public class UsersInfo {
    public static String getLogin() {
        return Login;
    }

    public static void setLogin(String login) {
        Login = login;
    }

    public static String Login = "dispatcher";

    public static String getPassword() {
        return Password;
    }

    public static void setPassword(String password) {
        Password = password;
    }

    public static String Password = "rzd123";

    public static String getRole() {
        return role;
    }

    public static void setRole(String role) {
        UsersInfo.role = role;
    }

    public static String role = "Диспетчер";
}
