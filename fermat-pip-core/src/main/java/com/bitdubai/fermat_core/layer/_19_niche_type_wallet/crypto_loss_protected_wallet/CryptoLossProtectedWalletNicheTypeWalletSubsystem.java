package com.bitdubai.fermat_core.layer._19_niche_type_wallet.crypto_loss_protected_wallet;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer._19_niche_type_wallet.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer._19_niche_type_wallet.NicheTypeWalletSubsystem;
import com.bitdubai.fermat_dmp_plugin.layer._19_niche_type_wallet.crypto_loss_protected_wallet.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by loui on 27/05/15.
 */
public class CryptoLossProtectedWalletNicheTypeWalletSubsystem  implements NicheTypeWalletSubsystem {

    Plugin plugin;





    @Override
    public Plugin getPlugin() {
        return plugin;
    }





    @Override
    public void start() throws CantStartSubsystemException {
        /**
         * I will choose from the different Developers available which implementation to use. Right now there is only
         * one, so it is not difficult to choose.
         */

        try {
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            plugin = developerBitDubai.getPlugin();
        }
        catch (Exception e)
        {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException();
        }
    }

}
