package org.mayur.WalletService.Consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mayur.Utils.CommonConstants;
import org.mayur.Utils.UserIdentifier;
import org.mayur.WalletService.model.Wallet;
import org.mayur.WalletService.repository.WalletRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserCreatedConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WalletRepository walletRepository;

    @KafkaListener(topics = CommonConstants.USER_CREATION_TOPIC, groupId ="wallet-group")
    public void createWallet(String msg) throws JsonProcessingException {
        JSONObject jsonObject=  objectMapper.readValue(msg, JSONObject.class);
        String contact = (String) jsonObject.get(CommonConstants.USER_CREATION_TOPIC_CONTACT);
        Integer userId = (Integer) jsonObject.get(CommonConstants.USER_CREATION_TOPIC_USER_ID);
        String userIdentifier = (String) jsonObject.get(CommonConstants.USER_CREATION_TOPIC_USERIDENTIFIER);
        String userIdentifierValue = (String) jsonObject.get(CommonConstants.USER_CREATION_TOPIC_USERIDENTIFIER_VALUE);

        Wallet wallet = Wallet.builder().
                userId(userId).
                contact(contact).
                userIdentifier(UserIdentifier.valueOf(userIdentifier)).
                userIdentifierValue(userIdentifierValue).
                balance(20.0).build();

        walletRepository.save(wallet);


    }
}
