package com.template.flows;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.flows.FlowException;
import net.corda.core.flows.FlowLogic;
import net.corda.core.flows.*;
import net.corda.core.flows.FlowSession;
import net.corda.core.flows.InitiatedBy;
import java.util.Date;

// ******************
// * Responder flow *
// ******************
@InitiatedBy(FundTrackingFlow.class)
public class FundTrackingResponderFlow extends FlowLogic<Void> {
    private FlowSession FundMangerSession;

    public FundTrackingResponderFlow(FlowSession counterpartySession) {
        this.FundMangerSession = counterpartySession;
    }

    @Suspendable
    @Override
    public Void call() throws FlowException {
        // Responder flow logic goes here.
        subFlow(new ReceiveFinalityFlow(FundMangerSession));


        return null;
    }
}
