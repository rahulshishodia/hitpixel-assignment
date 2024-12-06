package net.rahuls.hitpixel.api.resources;

import net.rahuls.hitpixel.BaseIntegrationTest;
import net.rahuls.hitpixel.api.ResponsePayload;
import net.rahuls.hitpixel.api.dto.TransactionDto;
import net.rahuls.hitpixel.core.literals.PaymentMethod;
import net.rahuls.hitpixel.core.literals.TransactionStatus;
import net.rahuls.hitpixel.data.entity.Transaction;
import net.rahuls.hitpixel.data.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentResourceTest extends BaseIntegrationTest {

    User saveUser(String email) {
        User user = new User();
        user.setFullName("Full Name");
        user.setUsername("username" + (int)(Math.random()*100));
        user.setPassword("password");
        user.setEmail(email);
        return userRepository.save(user);
    }

    Transaction saveTransaction(User user) {
        Transaction transaction = new Transaction();
        transaction.setAmount(new BigDecimal("100.00"));
        transaction.setCurrency(Currency.getInstance("EUR"));
        transaction.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setUser(user);
        return transactionRepository.save(transaction);
    }

    @Test
    void invalidEmail() {
        saveUser("invalid.email@test.com");
        String token = jwtUtil.generateToken("invalid.email2@test.com");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        HttpEntity<TransactionDto> request = new HttpEntity<>(headers);

        ResponseEntity<ResponsePayload> response = restTemplate.exchange("/api/payments", HttpMethod.POST, request, ResponsePayload.class);

        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode().value());
    }

    @Test
    void invalidHeaderName() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Auth", "Bearer token");

        HttpEntity<TransactionDto> request = new HttpEntity<>(headers);

        ResponseEntity<ResponsePayload> response = restTemplate.exchange("/api/payments", HttpMethod.POST, request, ResponsePayload.class);

        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode().value());
    }

    @Test
    void invalidHeaderValue() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Auth", "token");

        HttpEntity<TransactionDto> request = new HttpEntity<>(headers);

        ResponseEntity<ResponsePayload> response = restTemplate.exchange("/api/payments", HttpMethod.POST, request, ResponsePayload.class);

        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode().value());
    }

    @Test
    void missingAuthHeader() {
        ResponseEntity<ResponsePayload> response = restTemplate.postForEntity("/api/payments", null, ResponsePayload.class);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode().value());
    }

    @Test
    void addTransaction() {
        String userEmailId = "test.add.txn@test.com";

        saveUser(userEmailId);

        String token = jwtUtil.generateToken(userEmailId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setAmount(new BigDecimal("100"));
        transactionDto.setCurrency("EUR");
        transactionDto.setPaymentMethod("Credit Card");

        HttpEntity<TransactionDto> request = new HttpEntity<>(transactionDto, headers);

        ResponseEntity<ResponsePayload> response = restTemplate.exchange("/api/payments", HttpMethod.POST, request, ResponsePayload.class);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        Map<String, String> data = (LinkedHashMap<String, String>) response.getBody().data();
        assertEquals(TransactionStatus.PENDING.name(), data.get("status"));
    }

    @Test
    void getTransactionDetails() {
        String userEmailId = "test.txn.details@test.com";

        User user = saveUser(userEmailId);
        Transaction transaction = saveTransaction(user);

        String token = jwtUtil.generateToken(userEmailId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        HttpEntity<TransactionDto> request = new HttpEntity<>(headers);

        ResponseEntity<ResponsePayload> response = restTemplate.exchange("/api/payments/{transactionId}/status", HttpMethod.GET, request, ResponsePayload.class, transaction.getId());

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        Map<String, String> data = (LinkedHashMap<String, String>) response.getBody().data();
        assertEquals(transaction.getStatus().name(), data.get("status"));
    }

    @Test
    void getTransactionHistory() {
        String userEmailId = "test.history@test.com";

        User user = saveUser(userEmailId);
        Transaction transaction = saveTransaction(user);

        String token = jwtUtil.generateToken(userEmailId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        HttpEntity<TransactionDto> request = new HttpEntity<>(headers);

        ResponseEntity<ResponsePayload> response = restTemplate.exchange("/api/payments/history", HttpMethod.GET, request, ResponsePayload.class, transaction.getId());

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        ArrayList<LinkedHashMap<String, String>> data = ((LinkedHashMap<String, ArrayList<LinkedHashMap<String, String>>>) response.getBody().data()).get("transactions");
        assertEquals(transaction.getStatus().name(), data.get(0).get("status"));
    }

    @Test
    void transactionRefund() {
        String userEmailId = "test.refund@test.com";

        User user = saveUser(userEmailId);
        Transaction transaction = saveTransaction(user);

        String token = jwtUtil.generateToken(userEmailId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        HttpEntity<TransactionDto> request = new HttpEntity<>(headers);

        ResponseEntity<ResponsePayload> response = restTemplate.exchange("/api/payments/{transactionId}/refund", HttpMethod.POST, request, ResponsePayload.class, transaction.getId());

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
    }
}
