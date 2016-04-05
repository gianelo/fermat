package com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantCreateSelfIdentityException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteContactConnectionException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteGroupException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteGroupMemberException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetChatUserIdentityException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetContactConnectionException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetGroupException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetNetworkServicePublicKeyException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetOwnIdentitiesException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantListGroupException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantListGroupMemberException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantNewEmptyChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantNewEmptyContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantNewEmptyMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveContactConnectionException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveGroupException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveGroupMemberException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSendChatMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.SendStatusUpdateMessageNotificationException;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.ChatUserIdentity;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Contact;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.ContactConnection;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Group;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.GroupMember;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Message;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 06/01/16.
 * Updated by Jose Cardozo josejcb (josejcb89@gmail.com) on 16/03/16.
 */
public interface ChatManager {
    //TODO: Implementar los metodos que necesiten manejar el module
    //Documentar
    List<Chat> getChats() throws CantGetChatException;

    Chat getChatByChatId(UUID chatId) throws CantGetChatException;

    Chat newEmptyInstanceChat() throws CantNewEmptyChatException;

    void saveChat(Chat chat) throws CantSaveChatException;

    void deleteChat(Chat chat) throws CantDeleteChatException;

    void deleteChats() throws CantDeleteChatException;

    void deleteMessagesByChatId(UUID chatId) throws CantDeleteMessageException;

    List<Message> getMessages() throws CantGetMessageException;

    List<Message> getMessagesByChatId(UUID chatId) throws CantGetMessageException;

    Message getMessageByChatId(UUID chatId) throws CantGetMessageException;

    int getCountMessageByChatId(UUID chatId) throws CantGetMessageException;

    Message getMessageByMessageId(UUID messageId) throws CantGetMessageException;

    Message newEmptyInstanceMessage() throws CantNewEmptyMessageException;

    void saveMessage(Message message) throws CantSaveMessageException;

    void deleteMessage(Message message) throws CantDeleteMessageException;

    Chat getChatByRemotePublicKey(String publicKey) throws CantGetChatException;

    void sendReadMessageNotification(Message message) throws SendStatusUpdateMessageNotificationException;

    List<Contact> getContacts() throws CantGetContactException;

    Contact getContactByContactId(UUID contactId) throws CantGetContactException;

    Contact newEmptyInstanceContact() throws CantNewEmptyContactException;

    void saveContact(Contact contact) throws CantSaveContactException;

    void deleteContact(Contact contact) throws CantDeleteContactException;

    List<ContactConnection> discoverActorsRegistered() throws CantGetContactConnectionException;

    String getNetworkServicePublicKey() throws CantGetNetworkServicePublicKeyException;

    /**
     * This method return a HashMap with the possible self identities.
     * The HashMap contains a Key-value like PlatformComponentType-ActorPublicKey.
     * If there no identities created in any platform, this hashMaps contains the public chat Network
     * Service.
     * @return
     */
    HashMap<PlatformComponentType, Object> getSelfIdentities() throws CantGetOwnIdentitiesException;

    /**
     * This method returns the contact id by local public key.
     * @param localPublicKey
     * @return
     * @throws CantGetContactException
     */
    Contact getContactByLocalPublicKey(String localPublicKey) throws CantGetContactException;

    void createSelfIdentities() throws CantCreateSelfIdentityException;

    boolean isIdentityDevice() throws CantGetChatUserIdentityException;

    List<ChatUserIdentity> getChatUserIdentities() throws CantGetChatUserIdentityException;

    ChatUserIdentity getChatUserIdentity(String publicKey) throws CantGetChatUserIdentityException;

    void saveContactConnection(ContactConnection contactConnection) throws CantSaveContactConnectionException;

    //void deleteContactConnections( ) throws CantDeleteContactConnectionException;

    void deleteContactConnection(ContactConnection chatUserIdentity) throws CantDeleteContactConnectionException;

    List<ContactConnection> getContactConnections() throws CantGetContactConnectionException;

    ContactConnection getContactConnectionByContactId(UUID contactId) throws CantGetContactConnectionException;

    /**
     * This method sends the message through the Chat Network Service
     * @param createdMessage
     * @throws CantSendChatMessageException
     */
    void sendMessage(Message createdMessage) throws CantSendChatMessageException;

    void saveGroup(Group group) throws CantSaveGroupException;

    void deleteGroup(Group group) throws CantDeleteGroupException;

    List<Group> getGroups() throws CantListGroupException;
    Group getGroup(UUID groupId) throws CantGetGroupException;

    void saveGroupMember(GroupMember groupMember) throws CantSaveGroupMemberException;

    void deleteGroupMember(GroupMember groupMember) throws CantDeleteGroupMemberException;

    List<GroupMember> getGroupMembersByGroupId(UUID groupId)throws CantListGroupMemberException;

    void clearChatMessageByChatId(UUID chatId) throws CantDeleteMessageException, CantGetMessageException;

}
