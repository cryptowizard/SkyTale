package world.database;

import world.skytale.model.ID;
import world.skytale.model.ProfilePage;

public interface ProfilePageHandler {

    /**
     *  Adds profile Page to the databa
     * @param profilePage
     * @return
     */
    boolean addProfilePage(ProfilePage profilePage);
    boolean updateProfilePage(ProfilePage profilePage) throws ItemNotFoundException;
    ProfilePage getProfilePage(ID id) throws ItemNotFoundException;
    boolean deleteProfilePage(ID contactID);

}
