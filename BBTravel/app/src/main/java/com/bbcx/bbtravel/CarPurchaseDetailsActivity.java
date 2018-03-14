package com.bbcx.bbtravel;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;

public class CarPurchaseDetailsActivity extends BaseActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitle() {
        titleTv.setText("详情");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_car_purchase_details;
    }


    @OnClick({R.id.layout_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.layout_back:
                finish();
                break;
        }
    }


}
