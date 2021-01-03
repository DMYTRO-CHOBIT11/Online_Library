package com.company.online_library.online_library.repositories;

import com.company.online_library.online_library.damain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
//    User findUserByEmailAndPassword(String email,String password);
    User findUserByEmail(String email);
}
