package com.example.accounts

import com.example.accounts.services.AccountsServiceImplTest
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [AccountsServiceImplTest::class])
class AccountsApplicationTests {

	@Test
	fun contextLoads() {
	}

}
