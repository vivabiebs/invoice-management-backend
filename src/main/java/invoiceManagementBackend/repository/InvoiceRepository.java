package invoiceManagementBackend.repository;

import invoiceManagementBackend.entity.Biller;
import invoiceManagementBackend.entity.Invoice;
import invoiceManagementBackend.entity.Payer;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    Invoice findById(int id);

    List<Invoice> findAllByPayerAndStatus(Payer payer, String status, Sort sort);

    List<Invoice> findAllByPayer(Payer payer, Sort sort);

    List<Invoice> findAllByPayer(Payer payer);

    List<Invoice> findAllByBillerAndStatus(Biller biller, String status, Sort sort);

    List<Invoice> findAllByBiller(Biller biller, Sort sort);

    List<Invoice> findAllByBiller(Biller biller);

    List<Invoice> findAllByPayerAndPaidAtBetween(Payer payer, Timestamp dateFrom, Timestamp dateTo);
}
