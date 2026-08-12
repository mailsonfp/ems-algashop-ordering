package com.ems.algaworks.algashop.ordering.domain.utility;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochRandomGenerator;
import io.hypersistence.tsid.TSID;
import io.hypersistence.tsid.TSID.Factory;

import java.util.UUID;

public class IdGenerator {

    private static final TimeBasedEpochRandomGenerator timeBasedEpochRandomGenerator
            = Generators.timeBasedEpochRandomGenerator();

    private IdGenerator() {
    }

    private static final TSID.Factory tsidFactory = Factory.INSTANCE;

    public static UUID generateTimeBasedUUID() {
        return timeBasedEpochRandomGenerator.generate();
    }

    public static TSID generateTSID() {
        return tsidFactory.generate();
    }
}
