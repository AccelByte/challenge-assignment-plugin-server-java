// Copyright (c) 2024 AccelByte Inc. All Rights Reserved.
// This is licensed software from AccelByte Inc, for limitations
// and restrictions contact your company contract manager.

package net.accelbyte.challenge.assignment.instrumentation;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig;
import org.springframework.stereotype.Component;

@Component
public class CallMeterFilter implements MeterFilter {
    @Override
    public DistributionStatisticConfig configure(Meter.Id id, DistributionStatisticConfig config) {
        if (id.getName().equals("grpc.server.calls")) {
            return DistributionStatisticConfig.builder().percentiles(.95, .99).build().merge(config);
        }
        return config;
    }
}