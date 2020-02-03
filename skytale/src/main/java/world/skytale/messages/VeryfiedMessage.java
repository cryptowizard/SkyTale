package world.skytale.messages;

import world.skytale.model.Contact;

public class VeryfiedMessage {

    private final MessageHeader messageHeader;
    private  final byte [] messageBytes;
    private final Contact sender;






    public VeryfiedMessage(MessageHeader messageHeader, byte[] messageBytes, Contact sender) {
        this.messageHeader = messageHeader;
        this.messageBytes = messageBytes;
        this.sender = sender;

    }

    public VeryfiedMessage(MessageHeader messageHeader, byte[] messageBytes, int contactType, Contact sender) {
        this.messageHeader = messageHeader;
        this.messageBytes = messageBytes;
        this.sender = sender;
    }

    public MessageHeader getMessageHeader() {
        return messageHeader;
    }

    public byte[] getMessageBytes() {
        return messageBytes;
    }

  public Contact getSender()
  {
      return sender;
  }
}
