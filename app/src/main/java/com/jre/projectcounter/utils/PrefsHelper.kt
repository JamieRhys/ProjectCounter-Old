package com.jre.projectcounter.utils

import android.content.Context
import android.content.SharedPreferences
import com.jre.projectcounter.PREF_RECYCLER_VIEW_GRID

const val ITEM_TYPE_KEY = "item_type_key"

class PrefsHelper {
    companion object {
        private fun preferences(context: Context): SharedPreferences = context.getSharedPreferences("default", 0)

        /** Sets the Preference item type key to the designated type.
         * @param context The [Fragment] [Context] that this is called from
         * @param key The key that we're using in to label the value.
         * @param value The [String] we are storing.
         */
        fun setItemType(context: Context, key: String, value: String) {
            preferences(context).edit()
                .putString(key, value)
                .apply()
        }

        /** Returns the item value from our Preference file.
         * @return String
         */
        fun getItemType(context: Context, preference: String): String = preferences(context).getString(preference, "default")!!
    }
}