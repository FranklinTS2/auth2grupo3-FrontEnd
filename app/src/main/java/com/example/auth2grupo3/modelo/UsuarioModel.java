package com.example.auth2grupo3.modelo;

public class UsuarioModel {
    private int id;
    private String username;
    private String accessToken;

    public UsuarioModel() {
    }

    public UsuarioModel(int id, String username, String accessToken) {
        this.id = id;
        this.username = username;
        this.accessToken = accessToken;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return accessToken;
    }

    public void setToken(String token) {
        this.accessToken = token;
    }
}
