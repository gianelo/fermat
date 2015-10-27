package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.developer_utils.mocks;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Genders;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 24/10/15.
 */
public class MockActorAssetUserForTesting implements ActorAssetUser {
    @Override
    public String getPublicKey() {
        return new ECCKeyPair().getPublicKey();
    }

    @Override
    public String getName() {
        return "Actor Asset User Patriotic Name";
    }

    @Override
    public String getAge() {
        return "90";
    }

    @Override
    public Genders getGender() {
        return Genders.MALE;
    }

    @Override
    public ConnectionState getConnectionState() {
        return ConnectionState.CONNECTED;
    }

    @Override
    public long getRegistrationDate() {
        return 0;
    }

    @Override
    public long getLastConnectionDate() {
        return 0;
    }

    @Override
    public Double getLocationLatitude() {
        return 24.846565;
    }

    @Override
    public Double getLocationLongitude() {
        return 1.054688;
    }

    @Override
    public byte[] getProfileImage() {
        return new byte[0];
    }

    @Override
    public CryptoAddress getCryptoAddress() {
        CryptoAddress actorUserCryptoAddress=new CryptoAddress("mqBuPbxaxKzni6uTQCyVxK2FRLbcmaDrsV", CryptoCurrency.BITCOIN);
        return actorUserCryptoAddress;
    }
}