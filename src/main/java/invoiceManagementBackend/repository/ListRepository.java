package invoiceManagementBackend.repository;

import invoiceManagementBackend.entity.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListRepository extends JpaRepository<List, Integer> {
    List findById(int id);
}
