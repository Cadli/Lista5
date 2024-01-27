package pl.edu.uwr.pum.lista5

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SubjectViewModel(application: Application) : ViewModel() {

    private val repository: SubjectRepository
    private val _subjectsState = MutableStateFlow<List<Subject>>(emptyList())
    val subjectsState: StateFlow<List<Subject>>
        get() = _subjectsState

    init {
        val db = SubjectDatabase.getDatabase(application)
        val dao = db.subjectDao()
        repository = SubjectRepository(dao)

        fetchSubjects()
    }

    private fun fetchSubjects() {
        viewModelScope.launch {
            repository.getSubjects().collect { subject ->
                _subjectsState.value = subject
            }
        }
    }

    fun addSubject(subject: Subject) {
        viewModelScope.launch {
            repository.add(subject)
        }
    }

    fun getSubjectById(subjectId: String): Flow<Subject?> {
        return repository.getSubjectById(subjectId)
    }

    fun updateSubject(updatedSubject: Subject) {
        viewModelScope.launch {
            repository.update(updatedSubject)
        }
    }

    fun deleteSubject(subjectId: String){
        viewModelScope.launch {
            repository.delete(subjectId)
        }
    }

}