package ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.mobilesafe.R;

import static android.content.Context.MODE_PRIVATE;

public class SettingClickView extends RelativeLayout {
    private TextView tv_setting_title;
    private TextView tv_setting_desc;
    private SharedPreferences sp;

    public SettingClickView(Context context) {
        super(context);
        init();
    }

    public SettingClickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SettingClickView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.settingclickview, this);
        tv_setting_title = (TextView) view.findViewById(R.id.tv_setting_title);
        tv_setting_desc = (TextView) view.findViewById(R.id.tv_setting_desc);

    }

    public void setTitle(String title) {
        tv_setting_title.setText(title);
    }

    public void setDesc(String desc) {
        tv_setting_desc.setText(desc);
    }

}
