package pl.edu.uwr.pum.lista5

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface SubjectDao {
    @Query("SELECT * FROM subject_table ORDER BY id")
    fun getSubjects(): Flow<List<Subject>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(subject: Subject)

    @Update
    suspend fun update(subject: Subject)

    @Query("DELETE FROM subject_table WHERE id = :subjectId")
    suspend fun deleteById(subjectId: String)

    @Query("SELECT * FROM subject_table WHERE id = :subjectId")
    fun getSubjectById(subjectId: String): Flow<Subject?>

    @Query("DELETE FROM subject_table")
    suspend fun deleteAll()
}