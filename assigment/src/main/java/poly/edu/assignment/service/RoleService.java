package poly.edu.assignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poly.edu.assignment.entity.Role;
import poly.edu.assignment.repository.RoleRepository;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}