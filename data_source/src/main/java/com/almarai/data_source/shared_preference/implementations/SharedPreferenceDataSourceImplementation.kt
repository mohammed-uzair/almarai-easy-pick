package com.almarai.data_source.shared_preference.implementations

import android.content.Context
import android.content.SharedPreferences
import com.almarai.data_source.shared_preference.interfaces.SharedPreferenceDataSource

class SharedPreferenceDataSourceImplementation(private val context: Context) :
    SharedPreferenceDataSource {
    /**
     * Function to save [int] value to preference
     *
     * @param key   [AppPreference] Preference Key of particular preference
     * @param value [int] value to save in preference
     */
    override fun setSharedPreference(key: String?, value: Int) {
        editor.edit().putInt(key, value).apply()
    }

    /**
     * Function to save [boolean] value to preference
     *
     * @param key   [AppPreference] Preference Key of particular preference
     * @param value [boolean] value to save in preference
     */
    override fun setSharedPreference(key: String?, value: Boolean) {
        editor.edit().putBoolean(key, value).apply()
    }

    /**
     * Function to save [String] value to preference
     *
     * @param key   [AppPreference] Preference Key of particular preference
     * @param value [String] value to save in preference
     */
    override fun setSharedPreference(key: String?, value: String?) {
        editor.edit().putString(key, value).apply()
    }

    /**
     * Function to get [int] value from preference
     *
     * @param key [AppPreference] Preference Key of particular preference
     * @return [int] preference value is returned. By default it returns 0 if preference key does not exist
     */
    override fun getSharedPreferenceInt(key: String?): Int {
        return editor.getInt(key, 0)
    }

    /**
     * Function to get [boolean] value from preference
     *
     * @param key [AppPreference] Preference Key of particular preference
     * @return Returns the preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a boolean.
     */
    override fun getSharedPreferenceBoolean(key: String?): Boolean {
        return editor.getBoolean(key, false)
    }

    /**
     * Function to get [String] value from preference
     *
     * @param key [AppPreference] Preference Key of particular preference
     * @return Returns the preference value if it exists, or defValue(is empty string). Throws ClassCastException if there is a preference with this name that is not a String.
     */
    override fun getSharedPreferenceString(key: String?): String? {
        return editor.getString(key, "")
    }

    private val editor: SharedPreferences
        get() {
            return context.getSharedPreferences(
                ALM_PREF_KEY,
                ALM_PREFERENCE_MODE
            )
        }

    companion object {
        /* Operating mode of preference*/
        const val ALM_PREFERENCE_MODE: Int = Context.MODE_PRIVATE

        /*App preference file name*/
        const val ALM_PREF_KEY: String = "App_Preferences"
    }

}