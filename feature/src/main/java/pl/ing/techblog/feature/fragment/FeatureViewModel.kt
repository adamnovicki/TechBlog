package pl.ing.techblog.feature.fragment

import androidx.lifecycle.ViewModel
import pl.ing.techblog.data.AppRepository
import javax.inject.Inject

class FeatureViewModel @Inject constructor(
    private val repo: AppRepository
) : ViewModel() {
    fun getData(): String {
        return repo.getData()
    }
}
