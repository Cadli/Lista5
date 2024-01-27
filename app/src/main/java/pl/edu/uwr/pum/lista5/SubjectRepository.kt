package pl.edu.uwr.pum.lista5

import kotlinx.coroutines.flow.Flow

class SubjectRepository(private val subjectDao: SubjectDao) {

    fun getSubjects(): Flow<List<Subject>> {
        return subjectDao.getSubjects()
    }

    suspend fun add(subject: Subject) {
        subjectDao.insert(subject)
    }

    suspend fun clear() {
        subjectDao.deleteAll()
    }

    suspend fun update(subject: Subject) {
        subjectDao.update(subject)
    }

    suspend fun delete(subjectId: String) {
        subjectDao.deleteById(subjectId)
    }

    fun getSubjectById(subjectId: String): Flow<Subject?> {
        return subjectDao.getSubjectById(subjectId)
    }

}
