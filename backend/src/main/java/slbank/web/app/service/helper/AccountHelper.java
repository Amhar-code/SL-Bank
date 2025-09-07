package slbank.web.app.service.helper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import slbank.web.app.dto.AccountDto;
import slbank.web.app.dto.ConvertDto;
import slbank.web.app.entity.*;
import slbank.web.app.repository.AccountRepository;
import slbank.web.app.repository.TransactionRepository;
import slbank.web.app.service.ExhangeRateService;
import slbank.web.app.util.RandomUtil;

import javax.naming.OperationNotSupportedException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Getter
@Setter
public class AccountHelper {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final ExhangeRateService exhangeRateService;

    private final Map<String, String> CURRENCIES = Map.of(
            "USD", "United States Dollar",
            "EUR", "Euro",
            "GBP", "British Pound",
            "JPY", "japanese Yen",
            "LKR", "Sri Lankan Rupee"
    );

    public Account createAccount(AccountDto accountDto, User user) throws Exception {
        long accountNumber;
        validateAccountNonExistsForUser(accountDto.getCode(), user.getUid());
        do {
            accountNumber = new RandomUtil().generateRandom(10);
        } while (accountRepository.existsByAccountNumber(accountNumber));
        var account = Account.builder()
                .accountNumber(accountNumber)
                .accountName(user.getFirstname() + " " + user.getLastname())
                .balance(1000)
                .owner(user)
                .code(accountDto.getCode())
                .symbol(accountDto.getSymbol())
                .label(CURRENCIES.get(accountDto.getCode()))
                .build();
        return accountRepository.save(account);
    }


    public Transactions performTransfer(Account senderAccount, Account receiverAccount, double amount, User user) throws Exception {
        validateSufficientFunds(senderAccount, (amount*1.01));
        senderAccount.setBalance(senderAccount.getBalance() - amount * 1.01);
        receiverAccount.setBalance(receiverAccount.getBalance() * amount);
        accountRepository.saveAll(List.of(senderAccount, receiverAccount));
        var senderTransaction = Transactions.builder()
                .account(senderAccount)
                .status(Status.COMPLETED)
                .type(Type.WITHDRAWAL)
                .txFee(amount * 0.01)
                .amount(amount)
                .owner(senderAccount.getOwner())
                .build();

        var recipientTransaction = Transactions.builder()
                .account(receiverAccount)
                .status(Status.COMPLETED)
                .type(Type.DEPOSIT)
                .amount(amount)
                .owner(receiverAccount.getOwner())
                .build();

        return transactionRepository.saveAll(List.of(senderTransaction, recipientTransaction)).getFirst();
    }

    public void validateAccountNonExistsForUser(String code, UUID uid) throws Exception {
        if (accountRepository.existsByCodeAndOwnerUid(code, uid)) {
            throw new Exception("Account of this type already exists for this user.");
        }
    }

    public void validateAccountOwner(Account account, User user)throws OperationNotSupportedException{
        if(!account.getOwner().getUid().equals(user.getUid())){
            throw new OperationNotSupportedException("Invalid account owner");
        }
    }

    public void validateSufficientFunds(Account account, double amount)throws Exception{
        if(account.getBalance() < amount){
            throw new OperationNotSupportedException("Insufficient funds in the account");
        }
    }

    public void valdateAmount(double amount) throws Exception{
        if(amount <= 0){
            throw new IllegalArgumentException("Invalid amount");
        }
    }

    public void validateDifferentCurrencyType(ConvertDto convertDto)throws Exception{
        if(convertDto.getToCurrency().equals(convertDto.getFromCurrency())){
            throw new IllegalArgumentException("Conversion between same currency types is not allowed");
        }
    }

    public void validateAccountOwnership(ConvertDto convertDto, UUID uid)throws Exception{
        accountRepository.findByCodeAndOwnerUid(convertDto.getFromCurrency(), uid).orElseThrow();
        accountRepository.findByCodeAndOwnerUid(convertDto.getToCurrency(), uid).orElseThrow();
    }

    public void validateConversion(ConvertDto convertDto, UUID uid)throws Exception{
        validateDifferentCurrencyType(convertDto);
        validateAccountOwnership(convertDto, uid);
        valdateAmount(convertDto.getAmount());
        validateSufficientFunds(accountRepository.findByCodeAndOwnerUid(convertDto.getFromCurrency(), uid).get(), convertDto.getAmount());
    }

    public Transactions convertCurrency(ConvertDto convertDto, User user) throws Exception {
        validateConversion(convertDto, user.getUid());
        var rates = exhangeRateService.getRates();
        var sendingRates = rates.get(convertDto.getFromCurrency());
        var receivingRates = rates.get(convertDto.getToCurrency());
        var computedAmount = (receivingRates / sendingRates) * convertDto.getAmount();
        var fromAccount = accountRepository.findByCodeAndOwnerUid(convertDto.getFromCurrency(), user.getUid()).get();
        var toAccount = accountRepository.findByCodeAndOwnerUid(convertDto.getToCurrency(), user.getUid()).get();
        fromAccount.setBalance(fromAccount.getBalance() - (convertDto.getAmount() * 1.01));
        toAccount.setBalance(toAccount.getBalance() + computedAmount);
        accountRepository.saveAll(List.of(fromAccount, toAccount));


        var transaction = Transactions.builder()
                .owner(user)
                .amount(convertDto.getAmount())
                .txFee(convertDto.getAmount() * 0.01)
                .account(fromAccount)
                .status(Status.COMPLETED)
                .type(Type.DEPOSIT)
                .build();
        return transactionRepository.save(transaction);
    }
}