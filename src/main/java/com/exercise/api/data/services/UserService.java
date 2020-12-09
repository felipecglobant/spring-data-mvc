package com.exercise.api.data.services;

import com.exercise.api.data.domain.User;
import com.exercise.api.data.repositories.UserRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;
/*
  public List<UserDetails> getAll(){
    return userRepository. .getAll();
  }

 */

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
      Optional<User> optionalUser = userRepository.findByUsername(s);
      if (optionalUser.isPresent())
        return optionalUser.get();
      else
        throw new UsernameNotFoundException("User not found");

    }
/*
  public UserDetails findByAuthority(String authority){
    return userRepository.findByAuthority(authority).get();
  }
*/

}
