package com.android.sample.test_download;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.framework.R;
import com.android.libcore.download.FileDownloadManager;
import com.android.libcore_ui.activity.BaseActivity;

/**
 * Description: 测试断点续传式下载
 *
 * @author zzp(zhao_zepeng@hotmail.com)
 * @since 2015-08-06
 */
public class DownloadActivity extends BaseActivity implements View.OnClickListener{
    ProgressBar pb_bar;
    TextView tv_progress;
    TextView tv_state;
    FileDownloadManager manager;

    @Override
    protected void initView() {
        setContentViewSrc(R.layout.activity_test_download);
        pb_bar = (ProgressBar) findViewById(R.id.pb_bar);
        tv_progress = (TextView) findViewById(R.id.tv_progress);
        tv_state = (TextView) findViewById(R.id.tv_state);
        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_stop).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        manager = new FileDownloadManager("http://gdown.baidu.com/data/wisegame/2a5353b54211f954/weixin_600.apk", "a.apk");
        manager.setListener(new FileDownloadManager.IDownloadProgressChangedListener() {
            @Override
            public void onProgressChanged(long completeSize, long totalSize) {
                pb_bar.setProgress((int) ((completeSize*1.0/totalSize*1.0)*100));
                tv_progress.setText(completeSize+"/"+totalSize);
            }

            @Override
            public void onStateChanged(int state) {
                tv_state.setText(state+"   ");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start:
                manager.start();
                break;
            case R.id.btn_stop:
                manager.stop();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        manager.stop();
        super.onDestroy();
    }
}
