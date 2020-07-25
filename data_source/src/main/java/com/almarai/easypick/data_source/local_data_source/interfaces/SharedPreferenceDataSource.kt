package com.almarai.easypick.data_source.local_data_source.interfaces

import java.lang.reflect.Type

interface SharedPreferenceDataSource {
    /**
     * Function to save [Int] value to preference
     *
     * @param key   [AppPreference] Preference Key of particular preference
     * @param value [Int] value to save in preference
     */
    fun setSharedPreference(key: String?, value: Int)

    /**
     * Function to save [Long] value to preference
     *
     * @param key   [AppPreference] Preference Key of particular preference
     * @param value [Long] value to save in preference
     */
    fun setSharedPreference(key: String?, value: Long)

    /**
     * Function to save [Boolean] value to preference
     *
     * @param key   [AppPreference] Preference Key of particular preference
     * @param value [Boolean] value to save in preference
     */
    fun setSharedPreference(key: String, value: Boolean)

    /**
     * Function to save [String] value to preference
     *
     * @param key   [AppPreference] Preference Key of particular preference
     * @param value [String] value to save in preference
     */
    fun setSharedPreference(key: String?, value: String?)

    /**
     * Function to save Json String value to preference
     *
     * @param key   [AppPreference] Preference Key of particular preference
     * @param value Json [String] value to save in preference
     */
    fun setSharedPreferenceJson(key: String, objectType: Any?)

    /**
     * Function to get [Int] value from preference
     *
     * @param key [AppPreference] Preference Key of particular preference
     * @return [int] preference value is returned. By default it returns 0 if preference key does not exist
     */
    fun getSharedPreferenceInt(key: String?): Int

    /**
     * Function to get [Long] value from preference
     *
     * @param key [AppPreference] Preference Key of particular preference
     * @return [Long] preference value is returned. By default it returns 0 if preference key does not exist
     */
    fun getSharedPreferenceLong(key: String?): Long

    /**
     * Function to get [Boolean] value from preference
     *
     * @param key [AppPreference] Preference Key of particular preference
     * @return Returns the preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a boolean.
     */
    fun getSharedPreferenceBoolean(key: String?): Boolean

    /**
     * Function to get [String] value from preference
     *
     * @param key [AppPreference] Preference Key of particular preference
     * @return Returns the preference value if it exists, or defValue(is empty string). Throws ClassCastException if there is a preference with this name that is not a String.
     */
    fun getSharedPreferenceString(key: String?): String?

    /**
     * Function to get Json [String] value from preference
     *
     * @param key [AppPreference] Preference Key of particular preference
     * @return Returns the preference value if it exists, or defValue(is empty string). Throws ClassCastException if there is a preference with this name that is not a String.
     */
    fun <T>getSharedPreferenceJsonObject(key: String?, objectType: Type?): T?
}