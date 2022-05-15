package com.github.smkozh.restaurantvoting.to;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.smkozh.restaurantvoting.HasIdAndEmail;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserTo extends NamedTo implements HasIdAndEmail {
    @Email
    @NotBlank
    @Size(max = 100)
    String email;

    @NotBlank
    @Size(min = 5, max = 32)
    String password;

    @JsonCreator
    public UserTo(@JsonProperty("id") Integer id, @JsonProperty("name") String name, @JsonProperty("email") String email, @JsonProperty("password") String password) {
        super(id, name);
        this.email = email;
        this.password = password;
    }
}
