package com.kowshik.auth_app_springboot_backend.repository;

import com.kowshik.auth_app_springboot_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
