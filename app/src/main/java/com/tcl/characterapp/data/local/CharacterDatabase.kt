package com.tcl.characterapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tcl.characterapp.domain.model.CharactersDomain

@Database(entities = [CharactersDomain::class], version = 1, exportSchema = false)
abstract class CharacterDatabase : RoomDatabase() {

    abstract val characterDao: CharacterDao
}