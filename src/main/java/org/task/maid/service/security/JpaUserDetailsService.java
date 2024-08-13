package org.task.maid.service.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.task.maid.entity.User;
import org.task.maid.repository.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User name not found: " + username));
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("USER"));
        return new org.springframework.security.core.userdetails.
                User(user.getUsername(), user.getPassword(), true, true, true, true, authorities);

    }
}
