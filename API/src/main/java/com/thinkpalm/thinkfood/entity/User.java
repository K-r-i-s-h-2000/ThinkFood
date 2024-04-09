package com.thinkpalm.thinkfood.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * <p> User entity class representing user information.</p>
 *
 * @author ajay.S
 * @version 2.0
 * @since 31/10/2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
@Builder
public class User extends EntityDoc implements UserDetails {

    /**
     * The first name of the user.
     */
    private String firstname;
    /**
     * The last name of the user.
     */
    private String lastname;
    /**
     * The email address of the user, which serves as their unique identifier.
     */
    private String email;
    /**
     * The user's password for authentication.
     */
    private String password;
    @Column(name="authentication_token")
    private String authenticationToken;

    @Builder.Default
    @Column(name="is_active")
    private Boolean isActive=false;


    /**
     * The role of the user.
     */
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * Transient field representing the role value.
     */
    @Transient
    private int roleValue;

    /**
     * The associated customer information.
     */
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL) //  fetch = FetchType.EAGER
    private Customer customer;

    /**
     * The associated delivery information.
     */
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL ) // fetch = FetchType.EAGER
    private Delivery delivery;

    /**
     * The associated restaurant information.
     */
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL ) // fetch = FetchType.EAGER
    private Restaurant restaurant ;

    public User(String username, String oldPassword, Object o) {
        super();
    }

    /**
     * Returns the authorities granted to the user. Cannot return <code>null</code>.
     *
     * @return the authorities, sorted by natural key (never <code>null</code>)
    //     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_" + Role.fromRoleId(roleValue).name()));
//        return authorities;
//    }

    /**
     * Returns the username used to authenticate the user. Cannot return
     * <code>null</code>.
     *
     * @return the username (never <code>null</code>)
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Indicates whether the user's account has expired. An expired account cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user's account is valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) has expired. Expired
     * credentials prevent authentication.
     *
     * @return <code>true</code> if the user's credentials are valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
