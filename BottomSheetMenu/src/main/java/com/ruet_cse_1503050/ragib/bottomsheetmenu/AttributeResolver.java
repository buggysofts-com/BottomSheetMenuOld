package com.ruet_cse_1503050.ragib.bottomsheetmenu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import androidx.core.content.ContextCompat;

public class AttributeResolver {

    private final Context context;
    private final TypedValue typedValue;
    private final Resources.Theme theme;


    public AttributeResolver(Context context) {
        this.context = context;
        typedValue = new TypedValue();
        theme = context.getTheme();
    }

    public int getColor(int attrId) {
        theme.resolveAttribute(attrId, typedValue, true);
        return typedValue.data;
    }

    public String getColorInHex(int attrId) {
        theme.resolveAttribute(attrId, typedValue, true);
        int intColor = typedValue.data;
        return String.format("#%06X", (0xFFFFFF & intColor));
    }

    public Drawable getDrawable(int attrId) {
        theme.resolveAttribute(attrId, typedValue, true);
        return ContextCompat.getDrawable(context, typedValue.resourceId);
    }

}
