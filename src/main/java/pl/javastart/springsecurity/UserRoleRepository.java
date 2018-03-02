package pl.javastart.springsecurity;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository <UserRole, Long> {

    UserRole getUserRoleByUsername(String username);
}
