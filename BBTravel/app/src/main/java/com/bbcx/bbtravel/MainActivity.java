package com.bbcx.bbtravel;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.layout_back)
    LinearLayout layoutBack;

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitle() {
        layoutBack.setVisibility(View.INVISIBLE);
        titleTv.setText("便便出行");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @OnClick({R.id.bt_car_rental,R.id.bt_ll_gc})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.bt_car_rental:
                intent = new Intent(MainActivity.this, CarRentalActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_open,R.anim.activity_stay);
                break;
            case R.id.bt_ll_gc:
                intent = new Intent(MainActivity.this, BookingCarActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_open,R.anim.activity_stay);
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
