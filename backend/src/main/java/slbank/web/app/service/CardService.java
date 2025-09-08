package slbank.web.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import slbank.web.app.entity.*;
import slbank.web.app.repository.AccountRepository;
import slbank.web.app.repository.CardRepository;
import slbank.web.app.repository.TransactionRepository;
import slbank.web.app.service.helper.AccountHelper;
import slbank.web.app.util.RandomUtil;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final AccountHelper accountHelper;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public Card getCard(User user) {
        return cardRepository.findByOwnerUid(user.getUid()).orElseThrow();
    }

    public Card createCard(double amount, User user) throws Exception {
        if(amount < 2){
            throw new IllegalArgumentException("Minimum amount to create a card is $2");
        }
        if(!accountRepository.existsByCodeAndOwnerUid("USD", user.getUid())){
            throw new IllegalArgumentException("USD Account not found for this user so Card cannot be created");
        }
        var usdAccount = accountRepository.findByCodeAndOwnerUid("USD", user.getUid()).orElseThrow();
        accountHelper.validateSufficientFunds(usdAccount, amount);
        usdAccount.setBalance(usdAccount.getBalance()-amount);
        long cardNumber;
        do{
            cardNumber = generateCardNumber();
        } while (cardRepository.existsByCardNumber(cardNumber));
        Card card = Card.builder()
                .cardHolder(user.getFirstname()+" "+user.getLastname())
                .cardNumber(cardNumber)
                .exp(LocalDateTime.now().plusYears(3))
                .cvv(new RandomUtil().generateRandom(3).toString())
                .balance(amount - 1)
                .build();
        card = cardRepository.save(card);
        accountHelper.createAccountTransaction(1, Type.WITHDRAWAL, 0.00, user, usdAccount);
        accountHelper.createAccountTransaction(amount-1, Type.WITHDRAWAL, 0.00, user, usdAccount);
        createCardTransaction(amount, Type.CREDIT, 0.00, user, card);
        accountRepository.save(usdAccount);
        return card;
     }

     private long generateCardNumber(){
        return new RandomUtil().generateRandom(16);
     }

    public Transactions creditCard(double amount, User user) {
        var usdAccount = accountRepository.findByCodeAndOwnerUid("USD", user.getUid()).orElseThrow();
        usdAccount.setBalance(usdAccount.getBalance() - amount);
        accountHelper.createAccountTransaction(amount, Type.WITHDRAWAL, 0.00, user, usdAccount);
        var card = user.getCard();
        card.setBalance(card.getBalance() + amount );
        return createCardTransaction(amount, Type.CREDIT, 0.00, user, card);
    }

    public Transactions debitCard(double amount, User user) {
        var usdAccount = accountRepository.findByCodeAndOwnerUid("USD", user.getUid()).orElseThrow();
        usdAccount.setBalance(usdAccount.getBalance() + amount);
        accountHelper.createAccountTransaction(amount, Type.DEPOSIT, 0.00, user, usdAccount);
        var card = user.getCard();
        card.setBalance(card.getBalance() - amount );
        return createCardTransaction(amount, Type.DEBIT, 0.00, user, card);
    }

    private Transactions createCardTransaction(double amount, Type type, double txfee, User user, Card card){
        var tx = Transactions.builder()
                .txFee(txfee)
                .amount(amount)
                .type(type)
                .status(Status.COMPLETED)
                .owner(user)
                .card(card)
                .build();
        return transactionRepository.save(tx);
    }
}
