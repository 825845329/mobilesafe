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

public class SettingView extends RelativeLayout {
    private String desc_off;
    private String desc_on;
    private String title;
    private TextView tv_setting_title;
    private TextView tv_setting_desc;
    private CheckBox cb_setting_update;
    private SharedPreferences sp;

    public SettingView(Context context) {
        super(context);
        init();
    }

    public SettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

      title = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.example.administrator.mobilesafe","title");
      desc_on = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.example.administrator.mobilesafe","desc_on");
      desc_off = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.example.administrator.mobilesafe","desc_off");

      sp = getContext().getSharedPreferences("config",MODE_PRIVATE);

      tv_setting_title.setText(title);
      if(sp.getBoolean("update",true)){
          tv_setting_desc.setText(desc_on);
          cb_setting_update.setChecked(true);
      }else{
          tv_setting_desc.setText(desc_off);
          cb_setting_update.setChecked(false);
      }
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
        if(checked){
            tv_setting_desc.setText(desc_on);
            cb_setting_update.setChecked(true);
        }else{
            tv_setting_desc.setText(desc_off);
            cb_setting_update.setChecked(false);
        }
    }

    /**
     * 获取checkBox的状态
     *
     */
    public boolean isChecked() {
        return cb_setting_update.isChecked();
    }
}
