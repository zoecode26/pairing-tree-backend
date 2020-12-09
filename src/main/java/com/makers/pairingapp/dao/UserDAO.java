package com.makers.pairingapp.dao;

import com.makers.pairingapp.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDAO extends CrudRepository<User, Long> {
}
