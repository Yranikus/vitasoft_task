package com.example.vitasoft_task.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity(name = "users")
@Setter
@NoArgsConstructor
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    @Getter
    private Long id;
    @Getter
    @Column(unique = true, nullable = false, length = 50)
    private String name;
    @Getter
    @Column(nullable = false, length = 60)
    private String password;
    @Getter
    @ManyToMany(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Set<RoleEntity> roles;
    @Column(nullable = false)
    @ColumnDefault("true")
    private boolean isNonExpired;
    @Column(nullable = false)
    @ColumnDefault("true")
    private boolean isNonLocked;
    @Column(nullable = false)
    @ColumnDefault("true")
    private boolean isCredentialsNonExpired;
    @Column(nullable = false)
    @ColumnDefault("true")
    private boolean isEnabled;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
