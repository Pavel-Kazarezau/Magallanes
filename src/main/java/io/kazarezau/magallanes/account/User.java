package io.kazarezau.magallanes.account;

import io.kazarezau.magallanes.core.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.ValueObject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@AggregateRoot
@Getter
@Setter
public class User extends BaseEntity<User.UserId> implements UserDetails {

    private String username;

    private Email email;

    private String password;

    private Role role;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;

    public User(final String username,
                final String email,
                final String password) {
        super(new UserId(UUID.randomUUID()));
        this.username = username;
        this.email = new Email(email);
        this.password = password;
        this.role = Role.ROLE_USER;
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    public record UserId(UUID id) {
    }

    @ValueObject
    public record Name(String firstName, String lastName) {
    }

    @ValueObject
    public record Email(String email) {
        public Email(final String email) {
            this.email = Objects.requireNonNull(email, "Email must not be empty");
        }
    }
}
