package com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_csh_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.interfaces.CashMoneyBalance;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.interfaces.CashMoneyBalanceRecord;

/**
 * Created by francisco on 14/10/15.
 */
public class ImplementCashMoneyBalance implements CashMoneyBalance{
    @Override
    public double getBalance() throws CantCalculateBalanceException {
        return 0;
    }

    @Override
    public void debit(CashMoneyBalanceRecord CashMoneyBalanceRecord, BalanceType balanceType) throws CantRegisterDebitException {

    }

    @Override
    public void credit(CashMoneyBalanceRecord CashMoneyBalanceRecord, BalanceType balanceType) throws CantRegisterCreditException {

    }
}
