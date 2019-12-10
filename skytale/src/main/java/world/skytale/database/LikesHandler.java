package world.skytale.database;

import world.skytale.model.sendable.Like;

public interface LikesHandler {
    boolean addLike(Like like);
}
