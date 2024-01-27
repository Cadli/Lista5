package pl.edu.uwr.pum.lista5

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Subject::class], version = 1, exportSchema = false)
abstract class SubjectDatabase : RoomDatabase() {
    abstract fun subjectDao(): SubjectDao

    companion object {
        @Volatile
        private var Instance: SubjectDatabase? = null

        fun getDatabase(context: Context): SubjectDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, SubjectDatabase::class.java, "subject_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}