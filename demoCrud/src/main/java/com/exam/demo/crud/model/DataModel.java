package com.exam.demo.crud.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DataModel {
    private Long id;
    private String name;
    private String lastName;
    private String telephone;
    private String address;

    @Override
    public String toString() {
        return "{\"id\": " + this.id + ",\"name\": \"" + this.name + "\",\"lastName\": \"" + this.lastName + "\",\"telephone\": \"" + this.telephone + "\"," +
                "\"address\": \"" + this.address +
                "\"}";
    }
}
