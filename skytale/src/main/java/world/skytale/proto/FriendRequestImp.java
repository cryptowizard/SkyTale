package world.skytale.proto;

import world.skytale.model.AvaiableMessages.FriendRequest;
import world.skytale.model.ID;

public class FriendRequestImp implements FriendRequest {

    private final ID sendersID;
    private final long time;
    private final String reciversEmail;

    public FriendRequestImp(ID sendersID, long time, String reciversEmail) {
        this.sendersID = sendersID;
        this.time = time;
        this.reciversEmail = reciversEmail;
    }

    @Override
    public ID getSendersID() {
        return sendersID;
    }

    @Override
    public long getTime() {
        return time;
    }

    @Override
    public String getReciversEmail() {
        return reciversEmail;
    }
}
