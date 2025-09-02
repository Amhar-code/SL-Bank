package slbank.web.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import slbank.web.app.entity.Account;

public interface AccountRepository extends JpaRepository<Account, String> {

}
