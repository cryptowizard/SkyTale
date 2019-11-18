package world.skytale;

import world.skytale.messages.MessageHeader;

public class VeryfiedMessage {

    private final MessageHeader messageHeader;
    private  final byte [] messageBytes;


    /**
     *  contactType is either recipients type when sending new message
     *  or sendersType when processing incoming message
     */
    private int contactType;



    public VeryfiedMessage(MessageHeader messageHeader, byte[] messageBytes) {
        this.messageHeader = messageHeader;
        this.messageBytes = messageBytes;
    }

    public VeryfiedMessage(MessageHeader messageHeader, byte[] messageBytes, int contactType) {
        this.messageHeader = messageHeader;
        this.messageBytes = messageBytes;
        this.contactType = contactType;
    }

    public MessageHeader getMessageHeader() {
        return messageHeader;
    }

    public byte[] getMessageBytes() {
        return messageBytes;
    }

    public int getContactType() {
        return contactType;
    }

    public void setContactType(int contactType) {
        this.contactType = contactType;
    }
}