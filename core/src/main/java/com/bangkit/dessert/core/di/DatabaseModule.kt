package com.bangkit.dessert.core.di

import android.content.Context
import androidx.room.Room
import com.bangkit.dessert.core.data.source.local.room.DessertBriefDao
import com.bangkit.dessert.core.data.source.local.room.DessertDao
import com.bangkit.dessert.core.data.source.local.room.DessertDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): DessertDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("DessertMeCipher".toCharArray())
        val factory = SupportFactory(passphrase)

        return Room.databaseBuilder(context, DessertDatabase::class.java, "Dessert.db")
            .openHelperFactory(factory)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideDessertDao(database: DessertDatabase): DessertDao {
        return database.dessertDao()
    }

    @Provides
    fun provideDessertBriefDao(database: DessertDatabase): DessertBriefDao {
        return database.dessertBriefDao()
    }
}