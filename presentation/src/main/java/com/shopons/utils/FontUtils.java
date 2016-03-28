package com.shopons.utils;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by deepali on 19/3/16.
 */

public class FontUtils {

    private static Map<String, Typeface> TYPEFACE = new HashMap<String, Typeface>();

    public static Typeface getFonts(Context context, String name) {
        Typeface typeface = TYPEFACE.get(name);
        if (typeface == null) {
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/"
                    + name);
            TYPEFACE.put(name, typeface);
        }
        return typeface;
    }
}
