package com.example.npod

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.npod.data.nasa.NasaRepositoryImpl
import com.example.npod.data.notes.NoteRepositoryImpl
import com.example.npod.domain.nasa.NasaRepository
import com.example.npod.domain.notes.NoteRepository

class App : Application() {
    lateinit var applicationDatabase: AppDatabase
    val noteRepository: NoteRepository by lazy { NoteRepositoryImpl(applicationDatabase.noteDAO()) }
    val nasaRepository: NasaRepository by lazy { NasaRepositoryImpl() }

    override fun onCreate() {
        super.onCreate()
        synchronized(AppDatabase::class.java) {
            applicationDatabase = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                AppDatabase.DB_NAME
            ).build()
        }
    }
}

fun Fragment.app(): App {
    return requireContext().applicationContext as App
}