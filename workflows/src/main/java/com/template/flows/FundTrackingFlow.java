package com.template.flows;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.flows.FlowException;
import net.corda.core.flows.FlowLogic;
import net.corda.core.flows.InitiatingFlow;
import net.corda.core.flows.StartableByRPC;
import net.corda.core.utilities.ProgressTracker;

// ******************
// * Initiator flow *
// ******************
@InitiatingFlow
@StartableByRPC
public class FundTrackingFlow extends FlowLogic<Void> {
    private final Party FundManager;
    private final  int dailyvalue;
    private final Date date;
    private final ProgressTracker progressTracker = new ProgressTracker();

    public FundTrackingFlow(Party fundManager, int dailyvalue, Date date) {
        FundManager = fundManager;
        this.dailyvalue = dailyvalue;
        this.date = date;
    }

    @Override
    public ProgressTracker getProgressTracker() {
        return progressTracker;
    }

    @Suspendable
    @Override
    public Void call() throws FlowException {
        // We retrieve the notary identity from the network map.
        Party notary = getServiceHub().getNetworkMapCache().getNotaryIdentities().get(0);
        // We create the transaction components.

        FundTrackingState trackingState = new FundTrackingState(getOurIdentity(),FundManager,date,dailyvalue)
        Command command = new Command<>(new TemplateContract.Commands.Action(), getOurIdentity().getOwningKey());

        // We create a transaction builder and add the components.
        TransactionBuilder txBuilder = new TransactionBuilder(notary)
                .addOutputState(outputState, TemplateContract.ID)
                .addCommand(command);

        // Signing the transaction.
        SignedTransaction signedTx = getServiceHub().signInitialTransaction(txBuilder);

        // Creating a session with the FundManager party.
        FlowSession FundManagerSession = initiateFlow(FundManager);

        // We finalise the transaction and then send it to the counterparty.
        subFlow(new FinalityFlow(signedTx, FundManagerSession));


        return null;
    }
}
