package com.bitdubai.fermat_ccp_core.layer.network_service.crypto_transmission;

import com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractSubsystem;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by Matias Furszyfer - (matiasfurszyfer@gmail.com) on 09/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoTransmissionSubsystem extends AbstractSubsystem {

    @Override
    public void start() throws CantStartSubsystemException {
        try {
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            plugin = developerBitDubai.getPlugin();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException(e, null, null);
        }
    }

}