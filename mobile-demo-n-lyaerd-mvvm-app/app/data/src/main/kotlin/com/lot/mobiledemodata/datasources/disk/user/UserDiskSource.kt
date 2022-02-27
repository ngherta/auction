package com.lot.mobiledemodata.datasources.disk.user

import android.content.SharedPreferences
import com.google.gson.Gson
import com.lot.mobiledemodata.datasources.disk.SharedPreferencesSource
import javax.inject.Inject

class UserDiskSource @Inject constructor(
    sharedPreferences: SharedPreferences,
    gson: Gson
) : SharedPreferencesSource<UserDiskModel>(
    UserDiskModel::class.java,
    sharedPreferences,
    gson,
    id = "user_cache"
)
