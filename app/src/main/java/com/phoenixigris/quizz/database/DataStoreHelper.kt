package com.phoenixigris.quizz.database

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

const val DATA_STORE_NAME = "Quizz Datastore"
private val Context.dataStore by preferencesDataStore(DATA_STORE_NAME)

class DataStoreHelper @Inject constructor(@ApplicationContext appContext: Context) {

    private val dataStore = appContext.dataStore
}