package world.skytale.database;

import world.skytale.model2.ID;
import world.skytale.model2.ProfilePage;

public interface ProfilePageHandler {

    /**
     *
     * @param profilePage
     * @return true if added successfully false if couldnt add profilePage to database
     */
    boolean addProfilePage(ProfilePage profilePage);
    boolean updateProfilePage(ProfilePage profilePage) throws ItemNotFoundException;
    ProfilePage getProfilePage(ID id) throws ItemNotFoundException;
    boolean deleteProfilePage(ID contactID);

}
