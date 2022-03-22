package invoiceManagementBackend.repository;

import invoiceManagementBackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

//    User findByUsername(String username);
//    @Query(value = "select * from user where username =?1", nativeQuery = true)
//    User findByUsername(String username);

}
