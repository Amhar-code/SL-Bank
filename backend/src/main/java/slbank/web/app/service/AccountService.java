package slbank.web.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import slbank.web.app.dto.AccountDto;
import slbank.web.app.entity.Account;
import slbank.web.app.entity.User;
import slbank.web.app.repository.AccountRepository;
import slbank.web.app.service.helper.AccountHelper;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountHelper accountHelper;

    public Account createAccount(AccountDto accountDto, User user) throws Exception {
        return accountHelper.createAccount(accountDto, user);
    }

    public List<Account> getUserAccounts(UUID uid) {
        return accountRepository.findAllByOwnerUid(uid);
    }
}