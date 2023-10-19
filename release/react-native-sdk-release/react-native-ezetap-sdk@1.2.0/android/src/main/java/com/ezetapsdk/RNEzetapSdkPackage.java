package com.ezetapsdk;

import androidx.annotation.NonNull;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import java.util.Collections;
import java.util.List;

/**
 * @author TIJO THOMAS
 * @since 19/04/23
 */

/**
 * @author TIJO THOMAS
 * @since 19/04/23
 */
public class RNEzetapSdkPackage implements ReactPackage {

    @NonNull
    @Override
    public List<NativeModule> createNativeModules(
        @NonNull ReactApplicationContext reactApplicationContext) {
        return Collections.singletonList(new RNEzetapSdkModule(reactApplicationContext));
    }

    @NonNull
    @Override
    public List<ViewManager> createViewManagers(
        @NonNull ReactApplicationContext reactApplicationContext) {
        return Collections.emptyList();
    }
}