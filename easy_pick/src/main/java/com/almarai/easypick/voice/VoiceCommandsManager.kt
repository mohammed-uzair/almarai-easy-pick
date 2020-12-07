package com.almarai.easypick.voice

import android.app.Activity
import android.widget.Toast
import com.almarai.easypick.voice.use_cases.ChangeThemes
import com.almarai.easypick.voice.use_cases.NavigateScreen
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VoiceCommandsManager @Inject constructor(
    private val changeThemes: ChangeThemes,
    private val navigationScreen: NavigateScreen
) {
    private var activity: Activity? = null
    private var voiceRecognitionServer: VoiceRecognitionServer? = null

    fun processCommands(
        activity: Activity?,
        commands: List<String>,
        voiceRecognitionServer: VoiceRecognitionServer
    ) {
        this.activity = activity
        this.voiceRecognitionServer = voiceRecognitionServer
        val mCommand = commands[0].toLowerCase(Locale.ROOT).replace(" ", "").trim()

        val command = parseCommands(mCommand)

        //Execute the command
        executeCommand(command, mCommand)
    }

    private fun executeCommand(command: VoiceCommandsState, userCommands: String) {
        when (command) {
            VoiceCommandsState.NetworkConfigurationScreen -> TODO()
            VoiceCommandsState.DataConfigurationScreen -> TODO()
            VoiceCommandsState.GenerateTicket -> TODO()
            VoiceCommandsState.CheckForUpdates -> TODO()
            is VoiceCommandsState.ChangeTheme -> {
                changeThemes.changeTheme(activity, voiceRecognitionServer, userCommands)
            }
            VoiceCommandsState.ChangeLanguage -> TODO()
            VoiceCommandsState.ServerRoute -> TODO()
            VoiceCommandsState.SearchRoute -> TODO()
            VoiceCommandsState.SearchProduct -> TODO()
            VoiceCommandsState.PickProduct -> TODO()
            VoiceCommandsState.Halloween -> {
                Toast.makeText(activity?.applicationContext, "Haloween", Toast.LENGTH_SHORT).show()
            }
            VoiceCommandsState.NavigateScreen -> navigationScreen.navigateToScreen(activity, userCommands)
            VoiceCommandsState.Invalid -> {
            }
        }
    }

    private fun parseCommands(command: String): VoiceCommandsState {
        if (command.isNotEmpty()) {
            var mCommand = command.toLowerCase(Locale.ROOT).replace(" ", "").trim()

            //Remove keyword from the word if it has it
            if (mCommand.contains(ACTIVATION_KEYWORD, true)) {
                mCommand = mCommand.removePrefix(ACTIVATION_KEYWORD).trim()
            }

            return when {
                mCommand.contains(VoiceCommands.ChangeTheme) -> return VoiceCommandsState.ChangeTheme
                mCommand.contains(VoiceCommands.NavigateScreens) -> return VoiceCommandsState.NavigateScreen
                mCommand.contains(VoiceCommands.ServeRoute) -> return VoiceCommandsState.ServerRoute
                mCommand.contains(VoiceCommands.PickProduct) -> return VoiceCommandsState.PickProduct
                mCommand.contains(VoiceCommands.Filter) -> return VoiceCommandsState.PickProduct
                mCommand.contains(VoiceCommands.Update) -> return VoiceCommandsState.CheckForUpdates
                mCommand.contains(VoiceCommands.Search) -> return VoiceCommandsState.SearchProduct
                mCommand.contains(VoiceCommands.Halloween) -> return VoiceCommandsState.Halloween
                else -> VoiceCommandsState.Invalid
            }
        }

        return VoiceCommandsState.Invalid
    }
}