package net.kreddo.notes.service.impl;


import static java.util.Collections.emptyList;

import lombok.RequiredArgsConstructor;
import net.kreddo.notes.repository.model.UserEntity;
import net.kreddo.notes.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    var user = userRepository.findByEmail(email).orElseThrow(() -> {
      throw new UsernameNotFoundException(email);
    });
    return new User(user.getEmail(), user.getPassword(), emptyList());
  }
}
