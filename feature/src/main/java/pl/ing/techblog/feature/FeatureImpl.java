package pl.ing.techblog.feature;

import pl.ing.techblog.dynamic.Feature;

/**
 * Created by adamnowicki on 2019-08-23.
 */
public class FeatureImpl implements Feature {

    @Override
    public String getFeatureString() {
        return "HelloFeature";
    }
}
