//package com.refugietransaction.services.auth;
//import com.refugietransaction.exceptions.EntityNotFoundException;
//import com.refugietransaction.repository.UtilisateurRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//public class ApplicationUserDetailsService implements UserDetailsService {
//
//    private final UtilisateurRepository utilisateurRepository;
//
//    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//
//        return utilisateurRepository.findByEmail(email)
//                .orElseThrow(() -> new EntityNotFoundException("No user was found with the provided email"));
//    }
//}
