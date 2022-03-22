package invoiceManagementBackend.repository;

import invoiceManagementBackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);
//    @Query(value = "select * from user where username =?1", nativeQuery = true)
//    User findByUsername(String username);

}
