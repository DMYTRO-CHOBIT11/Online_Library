package com.company.online_library.online_library.implements_;

import com.company.online_library.online_library.damain.Role;
import com.company.online_library.online_library.damain.User;
import com.company.online_library.online_library.interfaces.IUserServices;
import com.company.online_library.online_library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServices implements IUserServices,UserDetailsService {
    private UserRepository repository;
    @Autowired
    public UserServices(UserRepository repository) {
        this.repository = repository;
    }

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User createUser(User user) {
        user.setEnabled(true);
        user.setRoles(Collections.singleton(Role.ROLE_USER));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @Override
    public User findById(long id) {
        return repository.findById(id).get();
    }

    @Override
    public User updateUsername(String email,String username) {
        User updateUser=repository.findUserByEmail(email);
        updateUser.setUsername(username);
        return repository.save(updateUser);
    }

    @Override
    public User updatePassword(String email,String password) {
        User updateUser=repository.findUserByEmail(email);
        updateUser.setPassword(bCryptPasswordEncoder.encode(password));
        return repository.save(updateUser);
    }

    @Override
    public void deleteUserById(long id) {
    }


    @Override
    public User findUserByEmail(String email) {
        return repository.findUserByEmail(email);
    }

    @Override
    public boolean userExist(String email) {
        User userFromDB=repository.findUserByEmail(email);
        if(userFromDB==null){
            return false;
        }else return true;
    }

//    @Override
//    public User findUserByUsername(String username) {
//        return repository.findUserByUsername(username);
//    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return (UserDetails) repository.findUserByEmail(email);
    }
}
