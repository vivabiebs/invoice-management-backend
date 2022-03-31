package invoiceManagementBackend.repository;

import invoiceManagementBackend.entity.Biller;
import invoiceManagementBackend.entity.Payer;
import invoiceManagementBackend.entity.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, Integer> {
    Relationship findById(int id);

    List<Relationship> findAllByBiller(Biller biller);

    List<Relationship> findAllByPayer(Payer payer);

    Relationship findByBillerAndPayer(Biller biller, Payer payer);
}

