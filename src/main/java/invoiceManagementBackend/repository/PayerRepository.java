package invoiceManagementBackend.repository;

import invoiceManagementBackend.entity.Payer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayerRepository extends JpaRepository<Payer, Integer> {
    Payer findById(int id);
}
