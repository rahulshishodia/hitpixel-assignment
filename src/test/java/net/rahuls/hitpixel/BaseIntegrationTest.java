package net.rahuls.hitpixel;

import net.rahuls.hitpixel.config.security.JwtUtil;
import net.rahuls.hitpixel.data.repository.TransactionRepository;
import net.rahuls.hitpixel.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestConstructor;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseIntegrationTest {

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected TransactionRepository transactionRepository;

    @Autowired
    protected JwtUtil jwtUtil;

}
