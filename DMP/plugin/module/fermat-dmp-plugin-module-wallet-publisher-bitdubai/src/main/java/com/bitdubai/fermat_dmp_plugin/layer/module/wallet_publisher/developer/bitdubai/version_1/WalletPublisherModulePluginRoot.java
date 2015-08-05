/*
 * @#WalletPublisherModulePluginRoot.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.module.wallet_publisher.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectProposal;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.exceptions.CantCheckPublicationException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.exceptions.CantGetPublishedWalletsInformationException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.exceptions.CantPublishWalletException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces.DealsWithWalletPublisher;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces.DealsWithWalletPublisherMiddleware;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces.WalletPublishedInformation;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces.WalletPublisherManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces.WalletPublisherMiddlewareManager;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.WalletPublisherMiddlewarePluginRoot</code> is
 * the responsible to communicate the user interface whit the middleware layer.
 * <p/>
 *
 * Created by loui on 05/02/15.
 * Update by Roberto Requena - (rart3001@gmail.com) on 04/08/2015
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletPublisherModulePluginRoot implements Service, DealsWithWalletPublisherMiddleware, DealsWithEvents, DealsWithErrors, DealsWithLogger, LogManagerForDevelopers, Plugin, WalletPublisherManager {

    /**
     * Represent the logManager
     */
    private LogManager logManager;

    /**
     * Represent the newLoggingLevel
     */
    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

    /**
     * Represent the errorManager
     */
    private ErrorManager errorManager;

    /**
     * DealWithEvents Interface member variables.
     */
    private EventManager eventManager;

    /**
     * Represent the plugin id
     */
    private UUID pluginId;

    /**
     * Represent the status of the service
     */
    private ServiceStatus serviceStatus;

    /**
     * Represent the listenersAdded
     */
    private List<EventListener>  listenersAdded;

    /**
     * Represent the walletPublisherMiddlewareManager
     */
    private WalletPublisherMiddlewareManager walletPublisherMiddlewareManager;

    /**
     * Constructor
     */
    public WalletPublisherModulePluginRoot() {
        serviceStatus = ServiceStatus.CREATED;
        listenersAdded = new ArrayList<>();
    }

    /**
     * (non-Javadoc)
     * @see Service#start()
     */
    @Override
    public void start() {
        /**
         * I will initialize the handling of com.bitdubai.platform events.
         */

        EventListener eventListener;
        EventHandler eventHandler;

        this.serviceStatus = ServiceStatus.STARTED;

    }

    /**
     * (non-Javadoc)
     * @see Service#pause()
     */
    @Override
    public void pause() {

        this.serviceStatus = ServiceStatus.PAUSED;

    }

    /**
     * (non-Javadoc)
     * @see Service#resume()
     */
    @Override
    public void resume() {

        this.serviceStatus = ServiceStatus.STARTED;

    }

    /**
     * (non-Javadoc)
     * @see Service#stop()
     */
    @Override
    public void stop() {

        /**
         * I will remove all the event listeners registered with the event manager.
         */

        for (EventListener eventListener : listenersAdded) {
            eventManager.removeListener(eventListener);
        }

        listenersAdded.clear();
        this.serviceStatus = ServiceStatus.STOPPED;

    }

    /**
     * (non-Javadoc)
     * @see Service#getStatus()
     */
    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }


    /**
     * DealWithEvents Interface implementation.
     */

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * (non-Javadoc)
     * @see DealsWithErrors#setErrorManager(ErrorManager)
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {

    }

    /**
     * (non-Javadoc)
     * @see Plugin#setId(UUID)
     */
    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }


    /**
     * (non-Javadoc)
     * @see DealsWithLogger#setLogManager(LogManager)
     */
    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    /**
     * (non-Javadoc)
     * @see LogManagerForDevelopers#getClassesFullPath()
     */
    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.module.wallet_publisher.developer.bitdubai.version_1.WalletPublisherModulePluginRoot");

        /**
         * I return the values.
         */
        return returnedClasses;
    }

    /**
     * (non-Javadoc)
     * @see LogManagerForDevelopers#setLoggingLevelPerClass(Map<String, LogLevel>)
     */
    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        /**
         * I will check the current values and update the LogLevel in those which is different
         */

        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
            /**
             * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
             */
            if (WalletPublisherModulePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                WalletPublisherModulePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                WalletPublisherModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                WalletPublisherModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }

    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherMiddlewareManager#showPublishedWallets()
     */
    @Override
    public Map<String, List<WalletPublishedInformation>> showPublishedWallets() throws CantGetPublishedWalletsInformationException {
        return walletPublisherMiddlewareManager.showPublishedWallets();
    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherMiddlewareManager#canBePublished(WalletFactoryProjectProposal)
     */
    @Override
    public boolean canBePublished(WalletFactoryProjectProposal walletFactoryProjectProposal) throws CantCheckPublicationException {
        return walletPublisherMiddlewareManager.canBePublished(walletFactoryProjectProposal);
    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherMiddlewareManager#publishWallet(WalletFactoryProjectProposal)
     */
    @Override
    public void publishWallet(WalletFactoryProjectProposal walletFactoryProjectProposal) throws CantPublishWalletException {

    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherMiddlewareManager#publishSkin(WalletFactoryProjectProposal)
     */
    @Override
    public void publishSkin(WalletFactoryProjectProposal walletFactoryProjectProposal) throws CantPublishWalletException {

    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherMiddlewareManager#publishLanguage(WalletFactoryProjectProposal)
     */
    @Override
    public void publishLanguage(WalletFactoryProjectProposal walletFactoryProjectProposal) throws CantPublishWalletException {

    }

    /**
     * (non-Javadoc)
     * @see DealsWithWalletPublisherMiddleware#setWalletPublisherMiddlewareManager(WalletPublisherMiddlewareManager)
     */
    @Override
    public void setWalletPublisherMiddlewareManager(WalletPublisherMiddlewareManager walletPublisherMiddlewareManager) {
        this.walletPublisherMiddlewareManager = walletPublisherMiddlewareManager;
    }
}

