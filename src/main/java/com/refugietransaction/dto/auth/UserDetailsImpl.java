package com.refugietransaction.dto.auth;

import com.refugietransaction.model.Admin;
import com.refugietransaction.model.Magasinier;
import com.refugietransaction.model.Superadmin;
import com.refugietransaction.model.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UserDetailsImpl implements UserDetails {
	
	private final Long id;
    private final String username;
    private final String password;
    private final String userfullname;
    private final String userphonenumber;
    private final String userrole;
    private final boolean isuseractive;
    private final Set<GrantedAuthority> authorities;
    private Admin admin;
    private Superadmin superadmin;
    private Magasinier magasinier;

    public UserDetailsImpl(User user) {
        this.id = user.getId();
        this.username = user.getUserEmail();
        this.password = user.getUserPassword();
        this.userfullname = user.getUserFullName();
        this.userphonenumber = user.getUserPhoneNumber();
        this.userrole = user.getUserRoleEnum().toString();
        this.isuseractive = user.getIsUserActive();
        this.authorities = new HashSet<>();
        this.authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getUserRoleEnum().toString().toUpperCase()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isuseractive;
    }

    // Setter methods for additional details


    public Long getId() {
        return id;
    }

    public String getUserfullname() {
        return userfullname;
    }

    public String getUserphonenumber() {
        return userphonenumber;
    }

    public String getUserrole() {
        return userrole;
    }

    public boolean isIsuseractive() {
        return isuseractive;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Superadmin getSuperadmin() {
        return superadmin;
    }

    public void setSuperadmin(Superadmin superadmin) {
        this.superadmin = superadmin;
    }

    public Magasinier getMagasinier() {
        return magasinier;
    }

    public void setMagasinier(Magasinier magasinier) {
        this.magasinier = magasinier;
    }

}
