// Copyright (c) 2024 AccelByte Inc. All Rights Reserved.
// This is licensed software from AccelByte Inc, for limitations
// and restrictions contact your company contract manager.

package net.accelbyte.challenge.assignment.service;

import java.util.Random;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;

import net.accelbyte.challenge.assignmentFunction.AssignmentFunctionGrpc;
import net.accelbyte.challenge.assignmentFunction.AssignmentRequest;
import net.accelbyte.challenge.assignmentFunction.AssignmentResponse;
import net.accelbyte.challenge.assignmentFunction.Goal;

@Slf4j
@GRpcService
public class ChallengeAssignmentService extends AssignmentFunctionGrpc.AssignmentFunctionImplBase {

    @Override
    public void assign(AssignmentRequest request, StreamObserver<AssignmentResponse> responseObserver) {
        log.info("Received assign request");

        final AssignmentResponse.Builder responseBuilder = AssignmentResponse.newBuilder();

        if (request.getGoalsCount() == 0) {
            Status status = Status.INVALID_ARGUMENT.withDescription("No active goals is available to be assigned.");
            responseObserver.onError(status.asRuntimeException());
        }

        int randomIndex = new Random().nextInt(request.getGoalsCount());
        Goal goal = request.getGoals(randomIndex);

        responseBuilder.setNamespace(request.getNamespace());
        responseBuilder.setUserId(request.getUserId());
        responseBuilder.addAssignedGoals(goal);

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }
}
