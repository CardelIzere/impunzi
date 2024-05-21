package com.refugietransaction.services.auth;

import com.refugietransaction.model.User;
import com.refugietransaction.dto.auth.UserDetailsImpl;
import com.refugietransaction.repository.AdminRepository;
import com.refugietransaction.repository.MagasinierRepository;
import com.refugietransaction.repository.SuperadminRepository;
import com.refugietransaction.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService implements UserDetailsService {
	
	private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final SuperadminRepository superadminRepository;
    private final MagasinierRepository magasinierRepository;

    @Autowired
    public UserInfoService(UserRepository userRepository, AdminRepository adminRepository, SuperadminRepository superadminRepository, MagasinierRepository magasinierRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.superadminRepository = superadminRepository;
        this.magasinierRepository = magasinierRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        UserDetailsImpl userDetails = new UserDetailsImpl(user);

        // Fetch additional info based on user role
        switch (user.getUserRoleEnum().toString()) {
            case "SUPERADMIN":
                userDetails.setSuperadmin(superadminRepository.findByUser(user));
                break;
            case "ADMIN":
                userDetails.setAdmin(adminRepository.findByUser(user));
                break;
            case "MAGASINIER":
                userDetails.setMagasinier(magasinierRepository.findByUser(user));
                break;
            default:
                // No additional info for default role
                break;
        }

        return userDetails;
    }

}
