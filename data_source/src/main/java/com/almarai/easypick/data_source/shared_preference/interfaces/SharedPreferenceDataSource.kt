package com.almarai.easypick.data_source.shared_preference.interfaces

interface SharedPreferenceDataSource {
    /**
     * Function to save [int] value to preference
     *
     * @param key   [AppPreference] Preference Key of particular preference
     * @param value [int] value to save in preference
     */
    fun setSharedPreference(key: String?, value: Int)

    /**
     * Function to save [boolean] value to preference
     *
     * @param key   [AppPreference] Preference Key of particular preference
     * @param value [boolean] value to save in preference
     */
    fun setSharedPreference(key: String?, value: Boolean)

    /**
     * Function to save [String] value to preference
     *
     * @param key   [AppPreference] Preference Key of particular preference
     * @param value [String] value to save in preference
     */
    fun setSharedPreference(key: String?, value: String?)

    /**
     * Function to get [int] value from preference
     *
     * @param key [AppPreference] Preference Key of particular preference
     * @return [int] preference value is returned. By default it returns 0 if preference key does not exist
     */
    fun getSharedPreferenceInt(key: String?): Int

    /**
     * Function to get [boolean] value from preference
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
}