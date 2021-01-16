package com.company.online_library.online_library.interfaces;


import com.company.online_library.online_library.damain.User;

public interface IUserServices {
    User createUser(User user);
    User findById(long id);
    User updateUsername(String email,String username);
    User updatePassword(String email,String password);
    void deleteUserById(long id);
    User findUserByEmail(String email);
    boolean userExist(String user);
}
