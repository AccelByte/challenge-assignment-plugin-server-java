// Copyright (c) 2024 AccelByte Inc. All Rights Reserved.
// This is licensed software from AccelByte Inc, for limitations
// and restrictions contact your company contract manager.

package net.accelbyte.challenge.assignment.service;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.MetadataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lognet.springboot.grpc.context.LocalRunningGrpcPort;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import net.accelbyte.sdk.core.AccelByteSDK;

import net.accelbyte.challenge.assignment.config.MockedAppConfiguration;
import net.accelbyte.challenge.assignmentFunction.AssignmentFunctionGrpc;
import net.accelbyte.challenge.assignmentFunction.AssignmentRequest;
import net.accelbyte.challenge.assignmentFunction.AssignmentResponse;
import net.accelbyte.challenge.assignmentFunction.Goal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@ActiveProfiles("test")
@SpringBootTest(
        classes = MockedAppConfiguration.class,
        properties = "spring.main.allow-bean-definition-overriding=true"
)
public class ChallengeAssignmentServiceTests {
    private ManagedChannel channel;

    private final Metadata header = new Metadata();

    @LocalRunningGrpcPort
    int port;

    @Autowired
    AccelByteSDK sdk;

    @BeforeEach
    public void init() {
        channel = ManagedChannelBuilder.forAddress("localhost", port)
                .usePlaintext()
                .build();
    }

    @Test
    public void assignWithNoGoals() {
        Mockito.reset(sdk);
        Mockito.when(sdk.validateToken(any(), any(), anyInt())).thenReturn(true);

        final AssignmentFunctionGrpc.AssignmentFunctionBlockingStub stub = AssignmentFunctionGrpc.newBlockingStub(channel)
                .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(header));

        StatusRuntimeException exception = assertThrows(StatusRuntimeException.class, () ->
                stub.assign(AssignmentRequest.newBuilder().build()));

        assertEquals("INVALID_ARGUMENT: No active goals is available to be assigned.", exception.getMessage());
    }

    @Test
    public void assign() {
        Mockito.reset(sdk);
        Mockito.when(sdk.validateToken(any(), any(), anyInt())).thenReturn(true);

        final AssignmentFunctionGrpc.AssignmentFunctionBlockingStub stub = AssignmentFunctionGrpc.newBlockingStub(channel)
                .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(header));

        AssignmentRequest.Builder requestBuilder = AssignmentRequest.newBuilder()
                .addGoals(Goal.newBuilder().setCode("goal1").build());
        AssignmentResponse response = stub.assign(requestBuilder.build());
        assertEquals("goal1", response.getAssignedGoals(0).getCode());
    }
}
