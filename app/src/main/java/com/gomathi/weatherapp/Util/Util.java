package com.gomathi.weatherapp.Util;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

public class Util {
    public static Drawable getDrawable(String name, Resources resources, String packageName) {
        final int resourceId = resources.getIdentifier(name, "drawable", packageName);
        return resources.getDrawable(resourceId);
    }
}
