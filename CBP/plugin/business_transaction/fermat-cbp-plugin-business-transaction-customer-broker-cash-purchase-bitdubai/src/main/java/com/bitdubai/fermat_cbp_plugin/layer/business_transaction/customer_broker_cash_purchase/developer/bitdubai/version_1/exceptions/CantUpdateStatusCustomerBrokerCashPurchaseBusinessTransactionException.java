package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_cash_purchase.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 04.10.15.
 */
public class CantUpdateStatusCustomerBrokerCashPurchaseBusinessTransactionException extends FermatException {
    public CantUpdateStatusCustomerBrokerCashPurchaseBusinessTransactionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}