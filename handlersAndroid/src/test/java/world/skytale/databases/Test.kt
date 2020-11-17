package world.skytale.databases

import android.os.Build.VERSION_CODES.LOLLIPOP
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricGradleTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config


@RunWith(RobolectricGradleTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(LOLLIPOP), packageName = "your.package.name")
class Test {

    lateinit var dbHelper: SkyTaleDatabaseHandler // Your DbHelper class

    @Before
    fun setup() {
     //   dbHelper = SkyTaleDatabaseHandler(RuntimeEnvironment.application , )

    }
}