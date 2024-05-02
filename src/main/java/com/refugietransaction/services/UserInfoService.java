//package com.refugietransaction.services;
//
//import com.refugietransaction.model.Utilisateur;
//import com.refugietransaction.repository.UtilisateurRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class UserInfoService implements UserDetailsService {
//
//    @Autowired
//    private UtilisateurRepository utilisateurRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//
//        Optional<Utilisateur> userInfo = utilisateurRepository.findByEmail(email);
//        return userInfo.map(UserInfoDetails::new)
//                .orElseThrow(()-> new UsernameNotFoundException("User not found"+email));
//    }
//    public List<Utilisateur> getAllUser(){
//        return utilisateurRepository.findAll();
//    }
//}
