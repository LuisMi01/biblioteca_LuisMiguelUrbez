package io.uax.biblioteca.repos;

import io.uax.biblioteca.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdminRepository extends JpaRepository<Admin, Integer> {
}
