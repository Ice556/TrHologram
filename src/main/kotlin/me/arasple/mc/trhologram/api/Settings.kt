package me.arasple.mc.trhologram.api

import taboolib.common5.Baffle
import taboolib.common5.FileWatcher
import taboolib.module.configuration.Config
import taboolib.module.configuration.SecuredFile
import java.util.concurrent.TimeUnit

/**
 * @author Arasple
 * @date 2021/2/11 10:07
 */
class Settings {

    companion object {

        @Config("settings.yml", migrate = true)
        lateinit var CONF: SecuredFile
            private set

        internal var INSTANCE = Settings()

        fun init() {
//            CONF.listener { onSettingsReload() }.also { onSettingsReload() }
            FileWatcher.INSTANCE.addSimpleListener(CONF.file) {
                onSettingsReload()
            }
        }

        fun onSettingsReload() {
            INSTANCE = Settings()
        }

    }

    val loadPaths: List<String> by lazy {
        CONF.getStringList("Loader.Hologram-Files")
    }

    val interactDelay by lazy {
        Baffle.of(CONF.getLong("Hologram.Interact-Min-Delay").coerceAtLeast(100), TimeUnit.MILLISECONDS)
    }

    val lineSpacing by lazy {
        CONF.getDouble("Hologram.Options.Default-Line-Spacing", 0.25)
    }

    val viewDistance by lazy {
        CONF.getDouble("Hologram.Options.Default-View-Distance", 20.0)
    }

    val viewCondition by lazy {
        CONF.getString("Hologram.Options.Default-View-Condition", "")
    }

    val refershCondition by lazy {
        CONF.getLong("Hologram.Options.Default-Refresh-Condition", -1)
    }

}