package world.database.Tables;

import android.Manifest;
import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;

import world.database.DatabaseHandler;
import world.skytale.databases.SkyTaleDatabaseHandler;
import world.skytale.databases.UserAccount;
import world.skytale.model.Account;

public class DatabaseHandlerTest {
    private final DatabaseHandler databaseHandler;

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE);

    public DatabaseHandlerTest()
    {
        Context context  =  InstrumentationRegistry.getTargetContext();
        Account user = UserAccount.makeNewAccount("MSD@xfreemedia.com");
        this.databaseHandler = new SkyTaleDatabaseHandler(context, user);

    }

    public DatabaseHandler getDatabaseHandler()
    {
        return databaseHandler;
    }


}
