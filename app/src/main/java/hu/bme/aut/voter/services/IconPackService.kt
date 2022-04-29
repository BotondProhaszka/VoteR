package hu.bme.aut.voter.services

import android.content.Context
import android.util.Log
import com.maltaisn.icondialog.pack.IconPack
import com.maltaisn.icondialog.pack.IconPackLoader
import com.maltaisn.iconpack.defaultpack.createDefaultIconPack
import hu.bme.aut.voter.R
import hu.bme.aut.voter.activities.MainActivity
import java.util.*

class IconPackService(private val context: Context) {
    var iconPack: IconPack
    init {
        val loader = IconPackLoader(context)
        val defaultPack = createDefaultIconPack(loader)
        defaultPack.loadDrawables(loader.drawableLoader)
        this.iconPack = loader.load(R.xml.icons, R.xml.tags, listOf(Locale.ENGLISH), defaultPack)
        Log.i(MainActivity.TAG_BUGFIX, "Iconpack set")
    }
}