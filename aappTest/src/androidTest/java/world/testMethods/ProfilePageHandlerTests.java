package world.testMethods;

import world.database.DatabaseHandler;
import world.database.ItemNotFoundException;
import world.skytale.model.ProfilePage;

import static org.junit.Assert.assertEquals;

public class ProfilePageHandlerTests {


    public static void veryfyProfilePageIsInDatbase(ProfilePage profilePage, DatabaseHandler databaseHandler) throws ItemNotFoundException {
        ProfilePage profilePage1 = databaseHandler.getProfilePageHandler().getProfilePage(profilePage.getConstactID());

        assertEquals(profilePage.getDescription(),profilePage1.getDescription());
        //assertEquals(profilePage.getProfileLinks(), profilePage1.getProfileLinks());
        assertEquals(profilePage.getUsername(), profilePage1.getUsername());
        assertEquals(profilePage.getProfilePicture(), profilePage.getProfilePicture());

    }
}
