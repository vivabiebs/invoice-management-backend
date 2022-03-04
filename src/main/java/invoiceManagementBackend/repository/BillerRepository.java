package invoiceManagementBackend.repository;

import invoiceManagementBackend.entity.Biller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillerRepository extends JpaRepository<Biller, Integer> {
    Biller findById(int id);

    Biller findByCitizenId(String citizenId);

    Biller findByUsername(String username);

}
