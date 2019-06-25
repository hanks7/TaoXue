package com.taoxue.ui.module.mine;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.ui.model.BindSuceessInfo;
import com.taoxue.ui.module.classification.CommitContent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SeccessBindCardActivity extends BaseActivity{


    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.reader_code_tv)
    TextView readerCodeTv;
    @BindView(R.id.reader_guan_tv)
    TextView readerGuanTv;
    @BindView(R.id.unbind_BTN)
    RelativeLayout unbindBTN;
    private BindSuceessInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seccess_bind_card);
        ButterKnife.bind(this);
        getIntentData();
    }

    private void getIntentData() {
        info = (BindSuceessInfo) getIntent().getSerializableExtra(CommitContent.BIND_READER_CARD_ID_SUCCESS);
        if (info != null) {
         readerCodeTv.setText(info.getReader_card_id());
            readerGuanTv.setText(info.getMyLib_name());
            nameTv.setText(info.getReaderName());
        }
    }


    @OnClick(R.id.unbind_BTN)
    public void onClick() {
    }
}
