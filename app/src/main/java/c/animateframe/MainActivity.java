package c.animateframe;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int screenWidth = (int) (width / density);  // 屏幕宽度(dp)
        int screenHeight = (int) (height / density);// 屏幕高度(dp)


        Log.d("TAG", "屏幕宽度（像素）：" + width);
        Log.d("TAG", "屏幕高度（像素）：" + height);
        Log.d("TAG", "屏幕密度（0.75 / 1.0 / 1.5）：" + density);
        Log.d("TAG", "屏幕密度dpi（120 / 160 / 240）：" + densityDpi);
        Log.d("TAG", "屏幕宽度（dp）：" + screenWidth);
        Log.d("TAG", "屏幕高度（dp）：" + screenHeight);
    }
}
