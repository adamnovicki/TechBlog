package pl.ing.techblog

import androidx.lifecycle.ViewModel
import pl.ing.techblog.data.AppRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repo: AppRepository
): ViewModel() {

    fun getData(): String {
        return repo.getData()
    }
}
