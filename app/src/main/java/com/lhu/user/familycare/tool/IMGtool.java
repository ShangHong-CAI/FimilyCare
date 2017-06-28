package com.lhu.user.familycare.tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

/**
 * Created by 尚宏 on 2017/5/22.
 */

public class IMGtool {
    private static Bitmap bitmap;
    public static Bitmap readBitMap(Context context, int resId){
        if(bitmap!=null){
            bitmap = null;
        }
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inSampleSize = 3;
        //獲取資源圖片
        InputStream is = context.getResources().openRawResource(resId);
        bitmap=BitmapFactory.decodeStream(is,null,opt);
        return bitmap;
    }
}
