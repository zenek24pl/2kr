package pl.noskilljustfun.dwakrokistad.model;

import android.graphics.drawable.Drawable;

/**
 * Created by zeno on 2016-05-06.
 */
public class ClueItem {
    public final Drawable icon;
    public final String title;
    public final String desc;

    public ClueItem(Drawable icon,String title,String desc){
        this.icon=icon;
        this.title=title;
        this.desc=desc;
    }

    public Drawable getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }
}
