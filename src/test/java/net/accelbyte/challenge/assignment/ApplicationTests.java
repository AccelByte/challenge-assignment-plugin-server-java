// Copyright (c) 2024 AccelByte Inc. All Rights Reserved.
// This is licensed software from AccelByte Inc, for limitations
// and restrictions contact your company contract manager.

package net.accelbyte.challenge.assignment;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import net.accelbyte.challenge.assignment.config.MockedAppConfiguration;

@ActiveProfiles("test")
@SpringBootTest(
	classes = MockedAppConfiguration.class,
	properties = "spring.main.allow-bean-definition-overriding=true"
)
class ApplicationTests {
	@Test
	void contextLoads() {
	}
}
