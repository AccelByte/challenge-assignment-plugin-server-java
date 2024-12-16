// Copyright (c) 2024 AccelByte Inc. All Rights Reserved.
// This is licensed software from AccelByte Inc, for limitations
// and restrictions contact your company contract manager.

package net.accelbyte.challenge.assignment.config;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import net.accelbyte.sdk.core.AccelByteSDK;

@TestConfiguration
public class MockedAppConfiguration {

    @Bean
    @Primary
    public AccelByteSDK accelbyteSdk() {
        return Mockito.mock(AccelByteSDK.class);
    }
}
