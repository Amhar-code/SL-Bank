package slbank.web.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import slbank.web.app.entity.Card;

public interface CardRepository extends JpaRepository<Card, String> {
}
