package invoiceManagementBackend.repository;

import invoiceManagementBackend.entity.Invoice;
import invoiceManagementBackend.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    Payment findByInvoice(Invoice invoice);
}
