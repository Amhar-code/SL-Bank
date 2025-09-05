package slbank.web.app.service.helper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import slbank.web.app.dto.AccountDto;
import slbank.web.app.entity.Account;
import slbank.web.app.entity.User;
import slbank.web.app.repository.AccountRepository;
import slbank.web.app.util.RandomUtil;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Getter
@Setter
public class AccountHelper {

    private final AccountRepository accountRepository;
    private final Map<String, String> CURRENCIES = Map.of(
            "USD", "United States Dollar",
            "EUR", "Euro",
            "GBP", "British Pound",
            "JPY", "japanese Yen",
            "SLR", "Sri Lankan Rupee"
    );

    public Account createAccount(AccountDto accountDto, User user) throws Exception {
        long accountNumber;
        validateAccountNonExistsForUser(accountDto.getCode(), user.getUid());
        do {
            accountNumber = new RandomUtil().generateRandom(10);
        } while (accountRepository.existsByAccountNumber(accountNumber));
        var account = Account.builder()
                .accountNumber(accountNumber)
                .balance(1000)
                .owner(user)
                .code(accountDto.getCode())
                .symbol(accountDto.getSymbol())
                .label(CURRENCIES.get(accountDto.getCode()))
                .build();
        return accountRepository.save(account);
    }

    public void validateAccountNonExistsForUser(String code, UUID uid) throws Exception {
        if (accountRepository.existsByCodeAndOwnerUid(code, uid)) {
            throw new Exception("Account of this type already exists for this user.");
        }
    }
}