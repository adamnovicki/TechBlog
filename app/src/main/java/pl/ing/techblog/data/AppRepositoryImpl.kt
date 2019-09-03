package pl.ing.techblog.data

/**
 * Created by adamnowicki on 2019-08-26.
 */
class AppRepositoryImpl : AppRepository {
    override fun getData(): String {
        return "this is data from app module"
    }
}