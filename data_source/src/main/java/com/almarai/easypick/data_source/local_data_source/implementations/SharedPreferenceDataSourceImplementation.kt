package com.almarai.easypick.data_source.local_data_source.implementations

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import com.almarai.easypick.data_source.BuildConfig
import com.almarai.easypick.data_source.interfaces.SharedPreferenceDataSource
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.squareup.moshi.Moshi
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferenceDataSourceImplementation
@Inject constructor(
    private val context: Context,
    private val moshi: Moshi
) : SharedPreferenceDataSource {
    /**
     * Function to save [int] value to preference
     *
     * @param key   [AppPreference] Preference Key of particular preference
     * @param value [int] value to save in preference
     */
    override fun setSharedPreference(key: String?, value: Int) =
        editor.edit().putInt(key, value).apply()

    override fun setSharedPreference(key: String?, value: Long) =
        editor.edit().putLong(key, value).apply()

    /**
     * Function to save [String] value to preference
     *
     * @param key   [AppPreference] Preference Key of particular preference
     * @param value [String] value to save in preference
     */
    override fun setSharedPreference(key: String, value: Boolean) =
        editor.edit().putBoolean(key, value).apply()

    override fun setSharedPreference(key: String?, value: String?) =
        editor.edit().putString(key, value).apply()

    /**
     * Function to save Json String value to preference
     *
     * @param key   [AppPreference] Preference Key of particular preference
     * @param value Json [String] value to save in preference
     */
    override fun setSharedPreferenceJson(key: String, objectType: Any?) {
//        try {
//            if (objectType == null) {
//                editor.edit().putString(key, "").apply()
////            } else editor.edit().putString(key, moshi.adapter<Any>(objectType)).apply()
//        } catch (exception: JsonSyntaxException) {
//            Log.e(TAG, "Could not parse json string", exception)
//        }
    }

    /**
     * Function to get [int] value from preference
     *
     * @param key [AppPreference] Preference Key of particular preference
     * @return [int] preference value is returned. By default it returns 0 if preference key does not exist
     */
    override fun getSharedPreferenceInt(key: String?) = editor.getInt(key, 0)

    override fun getSharedPreferenceLong(key: String?) = editor.getLong(key, 0)

    /**
     * Function to get [boolean] value from preference
     *
     * @param key [AppPreference] Preference Key of particular preference
     * @return Returns the preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a boolean.
     */
    override fun getSharedPreferenceBoolean(key: String?) = editor.getBoolean(key, false)

    /**
     * Function to get [String] value from preference
     *
     * @param key [AppPreference] Preference Key of particular preference
     * @return Returns the preference value if it exists, or defValue(is empty string). Throws ClassCastException if there is a preference with this name that is not a String.
     */
    override fun getSharedPreferenceString(key: String?) = editor.getString(key, "")

    /**
     * Function to get Json [String] value from preference
     *
     * @param key [AppPreference] Preference Key of particular preference
     * @return Returns the preference value if it exists, or defValue(is empty string). Throws ClassCastException if there is a preference with this name that is not a String.
     */
    override fun <T> getSharedPreferenceJsonObject(key: String?, objectType: Type?): T? {
        var result: T? = null
        val jsonString = editor.getString(key, "")
        try {
//            result = gson.fromJson<Any>(jsonString, objectType) as T
        } catch (exception: JsonSyntaxException) {
            Log.e(TAG, "Could not parse json string", exception)
        } catch (exception: ClassCastException) {
            Log.e(TAG, "Cannot cast type", exception)
        }
        return result
    }

    private val editor: SharedPreferences
        get() {
            return context.getSharedPreferences(
                ALM_PREF_KEY,
                ALM_PREFERENCE_MODE
            )
        }

    companion object {
        const val TAG = "SharedPrefsDSImpl"

        /* Operating mode of preference*/
        const val ALM_PREFERENCE_MODE: Int = Context.MODE_PRIVATE

        /*App preference file name*/
        const val ALM_PREF_KEY: String = "com.almarai.easypick_preferences"
    }
}