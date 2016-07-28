package com.shopons.utils;

/**
 *
 */

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;


public class Typefaces {
    /**
     * TAG for logging
     */
    private static final String TAG = "###Typefaces###";

    /**
     * Enum for typefaces
     */
    public enum Type {
        PROXIMA_NOVA("fonts/proxima-nova.otf"),
        PROXIMA_NOVA_BOLD("fonts/proxima-nova-bold.otf"),
        PROXIMA_NOVA_LIGHT("fonts/proxima-nova-light.otf"),
        PROXIMA_NOVA_SEMI_BOLD("fonts/proxima-nova-semi-bold.otf");

        /**
         * Asset path for typeface type
         */
        private String mAssetPath;

        /**
         * Constructor
         *
         * @param assetPath asset path
         */
        Type(final String assetPath) {
            mAssetPath = assetPath;
        }

        /**
         * Getter for @mAssetPath
         *
         * @return value of asset path
         */
        String getAssetPath() {
            return mAssetPath;
        }

    }

    /**
     * Hash map for assets
     */
    private ConcurrentHashMap<String, Typeface> mCache;
    /**
     * Flag to check if its initialized or not
     */
    private AtomicBoolean mIsInitialized = new AtomicBoolean(false);
    /**
     * Private instance of the class
     */
    private static Typefaces mInstance;

    /**
     * Constructor
     */
    private Typefaces() {
        mCache = new ConcurrentHashMap<String, Typeface>();
    }

    private static Typefaces getInstance() {
        if (mInstance == null) {
            mInstance = new Typefaces();
        }
        return mInstance;
    }

    /**
     * Initialize the cache
     *
     * @param context context
     */
    public static void Init(final Context context) {
        if (!getInstance().mIsInitialized.get())
            mInstance.pushAllTypefacesTypes(context);
    }

    private void pushAllTypefacesTypes(final Context context) {
        for (Type type : Type.values()) {
            try {
                Log.d(TAG, "Adding " + type.getAssetPath());
                Typeface typeface = Typeface.createFromAsset(context.getAssets(),
                        type.getAssetPath());
                mCache.putIfAbsent(type.getAssetPath(), typeface);
            } catch (final Exception e) {
                Log.e(TAG, "Could not get typeface '" + type.getAssetPath()
                        + "' because " + e.getMessage(), e);
                // push default typeface
                mCache.putIfAbsent(type.getAssetPath(), Typeface.SANS_SERIF);
            }
        }
        mIsInitialized.set(true);
    }

    public static Typeface get(final Type typefacesType) {
        Typefaces instance = getInstance();
        if (!instance.mIsInitialized.get())
            throw new IllegalStateException("Typefaces are not initialized before using them");
        if (instance.mCache.containsKey(typefacesType.getAssetPath())) {
            return instance.mCache.get(typefacesType.getAssetPath());
        }
        return Typeface.SANS_SERIF;
    }
}