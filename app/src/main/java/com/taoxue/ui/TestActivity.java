package com.taoxue.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.utils.AliPay.AliPayUtil;
import com.taoxue.utils.UtilToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends BaseActivity {

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.textView1)
    EditText textView1;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.textView2)
    EditText textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        textView1.setText(str);
    }

    String str = "alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2017041506732697&biz_content=%7B%22body%22%3A%22%E5%87%BA%E7%A7%9F%E8%BD%A6%E8%AE%A2%E5%8D%95%E6%95%B0%E6%8D%AE%22%2C%22out_trade_no%22%3A%22cz20170810144336y8yetqg0%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E4%B9%98%E5%AE%A2App%E6%94%AF%E4%BB%98%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.01%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fadmin.ananchuxing.com%2FtaxiOrder%2FcallBack.do&sign=grJ6HkpsFVZssXbtZMezyR6Kyv%2F9dMwn%2BoZh7pIWxCmRyitaizi2KKTtZyU5KxfdC3%2F9N9IVDp7Uhbf6xr9o2CIcaUv3e6MQxjn3vKezIJX%2BPStbloLCQyYwuRU9r2lzZX5vb5yPtHhoi4106CuWXzcPvUW9xLJdLnQa2VzmBd6%2FOuyRY3SP721PcmOT1QJhmPjEHuC8nf5bEWgJWfxkwO6uhrK4F%2FGytsxpyaUrzAivleibUOjHMC2rPKImVt2ob5KgzsNYeSMMet%2F%2F1NhJdQs9jAdFmdlO25gNIwQGRDH%2FqqqHUPGHq82X2au7SmcfYdJwhuU9t3iE9SWCy%2BSyFw%3D%3D&sign_type=RSA2&timestamp=2017-08-10+14%3A46%3A03&version=1.0";

    /**
     * 支付宝支付
     */
    private void pay(String str) {
        AliPayUtil.Builder builder = new AliPayUtil.Builder(this);
        builder.setPayCallBackListener(new AliPayUtil.Builder.PayCallBackListener() {
            @Override
            public void onPayCallBack(int status, String resultStatus, String resultInfo) {
                UtilToast.showText(resultInfo);
            }
        });
        builder.doPay(str);
    }

    @OnClick({R.id.button, R.id.button2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:
                pay(textView1.getText().toString().trim());
                break;
            case R.id.button2:
                pay(textView2.getText().toString().trim());
                break;
        }
    }
}
