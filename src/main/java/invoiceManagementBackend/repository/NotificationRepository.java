package invoiceManagementBackend.repository;

import invoiceManagementBackend.entity.Biller;
import invoiceManagementBackend.entity.Notification;
import invoiceManagementBackend.entity.Payer;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    Notification findById(int id);

    List<Notification> findAllByBillerAndPayer(Biller biller, Payer payer, Sort sort);

    List<Notification> findAllByPayer(Payer payer, Sort sort);

    List<Notification> findAllByBiller(Biller biller, Sort sort);
}