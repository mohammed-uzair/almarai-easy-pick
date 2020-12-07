package com.almarai.easypick.voice

@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class VoiceCommands {
    companion object {
        const val ChangeTheme = "theme"
        const val NavigateScreens = "move"
        const val PickProduct = "pick"
        const val ServeRoute = "serve route"
        const val Filter = "filter"
        const val Update = "update"
        const val Search = "search"
        const val Halloween = "halloween"
    }
}

sealed class VoiceCommandsState {
    object Invalid : VoiceCommandsState()

    object NavigateScreen : VoiceCommandsState()
    object DataConfigurationScreen : VoiceCommandsState()
    object NetworkConfigurationScreen : VoiceCommandsState()
    object GenerateTicket : VoiceCommandsState()

    object CheckForUpdates : VoiceCommandsState()

    object ChangeTheme : VoiceCommandsState()
    object ChangeLanguage : VoiceCommandsState()

    object ServerRoute : VoiceCommandsState()
    object SearchRoute : VoiceCommandsState()

    object SearchProduct : VoiceCommandsState()
    object PickProduct : VoiceCommandsState()

    object Halloween : VoiceCommandsState()
}

val VOICE_COMMANDS = listOf(
    "Move To NetworkConfiguration",
    "Move To DataConfiguration",
    "Generate Ticket",

    "Check For Updates",

    "Change Theme To Light",
    "Change Theme To Dark",
    "Change Theme To Night",

    "Change Language",

    "Serve Route",
    "Search",

    "Search",
    "Pick",

    "Halloween"
)
