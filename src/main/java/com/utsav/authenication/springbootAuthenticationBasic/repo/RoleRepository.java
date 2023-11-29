package com.utsav.authenication.springbootAuthenticationBasic.repo;

import com.utsav.authenication.springbootAuthenticationBasic.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
