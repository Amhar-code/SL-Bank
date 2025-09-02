package slbank.web.app.repository;

import slbank.web.app.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transactions, String> {
}
