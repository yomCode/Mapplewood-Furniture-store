package com.decagon.OakLandv1be.config.userDetails;


import com.decagon.OakLandv1be.entities.Person;
import com.decagon.OakLandv1be.repositries.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {
    private final PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person dbUser = personRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Not Found"));
        return AppUserDetails.builder()
                .username(dbUser.getEmail())
                .password(passwordEncoder.encode(dbUser.getPassword()))
                .grantedAuthorities(dbUser.getRole().getGrantedAuthorities())
                .build();
    }
}
