plugins {

}
object Constants {
    const val KOTLIN_LANGUAGE_VERSION = "2.2.0-Beta2"
    const val KOTLIN_PLUGIN_VERSION = "2.2.0-Beta2"
}

dependencyLocking {
    lockMode = LockMode.DEFAULT // Not every configuration can be locked so can't use strict :'(
    lockAllConfigurations()
}

