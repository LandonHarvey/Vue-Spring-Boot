package com.codeup.weekndr.repositories;

import com.codeup.weekndr.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
