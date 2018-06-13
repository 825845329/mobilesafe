package ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.mobilesafe.R;

public class SettingView extends RelativeLayout {
    private TextView tv_setting_title;
    private TextView tv_setting_desc;
    private CheckBox cb_setting_update;

    public SettingView(Context context) {
        super(context);
        init();
    }

    public SettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SettingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.settingview, this);
        tv_setting_title = (TextView) view.findViewById(R.id.tv_setting_title);
        tv_setting_desc = (TextView) view.findViewById(R.id.tv_setting_desc);
        cb_setting_update = (CheckBox) view.findViewById(R.id.cb_setting_update);

    }

    public void setTitle(String title) {
        tv_setting_title.setText(title);
    }

    public void setDesc(String desc) {
        tv_setting_desc.setText(desc);
    }

    public void setChecked(boolean checked) {
        cb_setting_update.setChecked(checked);
    }

    /**
     * 获取checkBox的状态
     *
     */
    public boolean isChecked() {
        return cb_setting_update.isChecked();
    }
}
