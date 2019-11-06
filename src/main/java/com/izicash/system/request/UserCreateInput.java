package com.izicash.system.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserCreateInput {
    @NotBlank(message = "Você precisa especificar um nome válido")
    private String name;
    @NotBlank(message = "Você precisa especificar um email válido")
    private String email;
    @NotBlank(message = "Você precisa especificar uma senha válida")
    private String password;
    @NotNull(message = "Você precisa especificar um status de usuário válido")
    private boolean active;

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
