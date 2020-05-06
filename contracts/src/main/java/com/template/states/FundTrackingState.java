package com.template.states;

import com.template.contracts.TemplateContract;
import net.corda.core.contracts.BelongsToContract;
import net.corda.core.contracts.ContractState;
import net.corda.core.identity.AbstractParty;
import net.corda.core.contracts.BelongsToContract;
import net.corda.core.contracts.LinearState;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import net.corda.core.schemas.MappedSchema;
import net.corda.core.schemas.PersistentState;
import net.corda.core.schemas.QueryableState;
import java.util.Arrays;
import java.util.List;
import java.util.Date;
// *********
// * State *
// *********
@BelongsToContract(TemplateContract.class)
public class FundTrackingState implements ContractState {

    private final Party Company;
    private final Party FundManager;
    private final Date date;

    public FundTrackingState(Party company, Party fundManager, Date date, double value) {
        Company = company;
        FundManager = fundManager;
        this.date = date;
        this.value = value;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "FundTrackingState{" +
                "Company=" + Company +
                ", FundManager=" + FundManager +
                ", date=" + date +
                ", value=" + value +
                '}';
    }

    public Party getCompany() {
        return Company;
    }

    public Party getFundManager() {
        return FundManager;
    }

    public Date getDate() {
        return date;
    }

    public double getValue() {
        return value;
    }

    private final double value;

    public FundTrackingState() {

    }

    @Override
    public List<AbstractParty> getParticipants() {
        return Arrays.asList(FundManager,Company);
    }
}