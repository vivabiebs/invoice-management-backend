package invoiceManagementBackend.repository;

import invoiceManagementBackend.entity.Payer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayerRepository extends JpaRepository<Payer, Integer> {
    Payer findById(int id);

    Payer findByCitizenId(String citizenId);

}
