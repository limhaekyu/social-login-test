package com.example.sociallogintest.user.repository;

import com.example.sociallogintest.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
