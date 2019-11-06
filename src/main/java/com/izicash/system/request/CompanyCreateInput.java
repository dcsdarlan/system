package com.izicash.system.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CompanyCreateInput {
    @NotBlank(message = "Você precisa especificar um nome válido")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
