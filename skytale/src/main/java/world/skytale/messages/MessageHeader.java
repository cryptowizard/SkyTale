package world.skytale.messages;

import world.skytale.converters.LongConverter;
import world.skytale.MessageProcessingException;
import world.skytale.model.ID;

public class MessageHeader {

    public static final String TITLE_TAG = "SkyTale";
    public static final String SPLIT_STRING = ";";

    private  String messageType;
    private ID senderID;
    private long time;


    public static MessageHeader parseTitle(String mailTitle) throws MessageProcessingException {
        String [] split  = mailTitle.split(SPLIT_STRING);
        if(split.length<4)
        {
            throw new MessageProcessingException("Title Parsing failed, title too short");
        }
        String messageType = split[1];
        ID uniqueID = new ID(split[2]);
        long time = LongConverter.fromBase32String(split[3]);
        return  new MessageHeader(messageType, uniqueID,time);
    }


    public String makeTitle()
    {
        String title = TITLE_TAG + SPLIT_STRING
                + messageType +SPLIT_STRING
                + senderID.toString() + SPLIT_STRING
                + LongConverter.toBase32String(time);
        return title;
    }

    public MessageHeader(String messageType, ID senderID, long time) {
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

    public ID getSenderID() {
        return senderID;
    }

    public void setSenderID(ID senderID) {
        this.senderID = senderID;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

}