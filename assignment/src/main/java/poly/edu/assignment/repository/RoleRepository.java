package poly.edu.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poly.edu.assignment.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}