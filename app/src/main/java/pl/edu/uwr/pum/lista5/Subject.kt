package pl.edu.uwr.pum.lista5

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subject_table")
data class Subject(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val grade: Float
)