package com.a78.audio.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.a78.audio.R;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class VoiceChangeActivity extends BasePlayActivity {

    @InjectView(R.id.play_btn)
    Button playBtn;
    @InjectView(R.id.vc_luoli_btn)
    Button vcLuoliBtn;
    @InjectView(R.id.vc_jingsong_btn)
    Button vcJingsongBtn;
    @InjectView(R.id.vc_dashu_btn)
    Button vcDashuBtn;
    @InjectView(R.id.vc_gaoguai_btn)
    Button vcGaoguaiBtn;
    @InjectView(R.id.vc_kongling_btn)
    Button vcKonglingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_change);
        ButterKnife.inject(this);

    }



    @OnClick({R.id.play_btn, R.id.vc_luoli_btn, R.id.vc_jingsong_btn, R.id.vc_dashu_btn, R.id.vc_gaoguai_btn, R.id.vc_kongling_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.play_btn:
                break;
            case R.id.vc_luoli_btn:
                break;
            case R.id.vc_jingsong_btn:
                break;
            case R.id.vc_dashu_btn:
                break;
            case R.id.vc_gaoguai_btn:
                break;
            case R.id.vc_kongling_btn:
                break;
        }
    }
}
