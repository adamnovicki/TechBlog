package pl.ing.techblog.dynamic

/**
 * Created by adamnowicki on 2019-09-03.
 */
object FeatureDynamic: FeatureModule {
    override val name = "feature_one"
    override val injectorName = "pl.ing.techblog.feature.dagger.FeatureInjector"
}