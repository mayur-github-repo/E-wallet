package org.mayur.UserService.repository;

import org.mayur.UserService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByContact(String contact);
}
