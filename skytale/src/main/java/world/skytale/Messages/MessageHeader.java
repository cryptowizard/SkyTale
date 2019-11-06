package world.skytale.Messages;

import world.skytale.model.PublicKeyId;

public class MessageHeader {

    public static final String TITLE_TAG = "SkyTale";
    public static final String SPLIT_STRING = ";";

    private  String messageType;
    private PublicKeyId senderID;
    private long time;


    public static MessageHeader parseTitle(String mailTitle) throws MessageProcessingException {
        String [] split  = mailTitle.split(SPLIT_STRING);
        if(split.length<4)
        {
            throw new MessageProcessingException("Title Parsing failed, title too short");
        }

        String messageType = split[1];
        PublicKeyId publicKeyId = new PublicKeyId(split[2]);
        long time = Long.parseLong(split[3]);

        return  new MessageHeader(messageType,publicKeyId,time);
    }


    public String makeTitle()
    {
        String title = TITLE_TAG + SPLIT_STRING
                + messageType +SPLIT_STRING
                + senderID.toString() + SPLIT_STRING
                + time ;

        return title;
    }

    public MessageHeader(String messageType, PublicKeyId senderID, long time) {
        this.messageType = messageType;
        this.senderID = senderID;
        this.time = time;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public PublicKeyId getSenderID() {
        return senderID;
    }

    public void setSenderID(PublicKeyId senderID) {
        this.senderID = senderID;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }



}