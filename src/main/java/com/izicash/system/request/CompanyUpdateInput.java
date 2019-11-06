package com.izicash.system.request;

import javax.validation.constraints.NotBlank;

public class CompanyUpdateInput {
    @NotBlank(message = "Você precisa especificar um nome válido")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
