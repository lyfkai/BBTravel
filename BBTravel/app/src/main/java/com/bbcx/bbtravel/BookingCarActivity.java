package com.bbcx.bbtravel;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;

import butterknife.BindView;
import butterknife.OnClick;

public class BookingCarActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.ll_sd1)
    LinearLayout llSd1;
    @BindView(R.id.ll_sd_image)
    ImageView llSdImage;
    @BindView(R.id.ll_sd2)
    LinearLayout llSd2;
    @BindView(R.id.ll_xh1)
    LinearLayout llXh1;
    @BindView(R.id.ll_xh_image)
    ImageView llXhImage;
    @BindView(R.id.ll_xh2)
    LinearLayout llXh2;
    @BindView(R.id.ll_aq1)
    LinearLayout llAq1;
    @BindView(R.id.ll_aq_image)
    ImageView llAqImage;
    @BindView(R.id.ll_aq2)
    LinearLayout llAq2;
    @BindView(R.id.roundButton)
    Button roundButton;
    TranslateAnimation animation;

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitle() {
        titleTv.setText("购车");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onScaleAnimationBySpringWayOne();
            }
        }, 260);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_booking_car;
    }

    @OnClick({R.id.layout_back,R.id.roundButton})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.layout_back:
                finish();
                break;
            case R.id.roundButton:
                Intent intent = new Intent(BookingCarActivity.this,CarPurchaseDetailsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_open,R.anim.activity_stay);
                break;
        }
    }


    private void onScaleAnimationBySpringWayOne() {
        SpringSystem springSystem = SpringSystem.create();
        Spring spring = springSystem.createSpring();
        spring.setCurrentValue(0.8f);
        spring.setSpringConfig(new SpringConfig(50, 6));
        spring.addListener(new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                super.onSpringUpdate(spring);
                float currentValue = (float) spring.getCurrentValue();
                llSdImage.setScaleX(currentValue);
                llSdImage.setScaleY(currentValue);
            }
        });
        spring.setEndValue(2.4f);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onScaleAnimationBySpringWayTwo();
            }
        }, 260);
    }

    private void onScaleAnimationBySpringWayTwo() {
        SpringSystem springSystem = SpringSystem.create();
        Spring spring = springSystem.createSpring();
        spring.setCurrentValue(0.8f);
        spring.setSpringConfig(new SpringConfig(50, 6));
        spring.addListener(new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                super.onSpringUpdate(spring);
                float currentValue = (float) spring.getCurrentValue();
                llXhImage.setScaleX(currentValue);
                llXhImage.setScaleY(currentValue);
            }
        });
        spring.setEndValue(2.4f);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onScaleAnimationBySpringWayThree();
            }
        }, 260);
    }

    private void onScaleAnimationBySpringWayThree() {
        SpringSystem springSystem = SpringSystem.create();
        Spring spring = springSystem.createSpring();
        spring.setCurrentValue(0.6f);
        spring.setSpringConfig(new SpringConfig(50, 6));
        spring.addListener(new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                super.onSpringUpdate(spring);
                float currentValue = (float) spring.getCurrentValue();
                llAqImage.setScaleX(currentValue);
                llAqImage.setScaleY(currentValue);
            }
        });
        spring.setEndValue(2.4f);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                oninitAnimationOne();
            }
        }, 260);

    }

    private void oninitAnimationOne() {
        llSd1.setVisibility(View.VISIBLE);
        llSd2.setVisibility(View.VISIBLE);
        animation = new TranslateAnimation(-100.0f, 10.0f, 0.0f, 0.0f);
        animation.setDuration(300);
        llSd1.startAnimation(animation);
        animation = new TranslateAnimation(100.0f, 0.0f, 0.0f, 0.0f);
        animation.setDuration(200);
        llSd2.startAnimation(animation);
        SpringSystem springSystem = SpringSystem.create();
        Spring spring = springSystem.createSpring();
        spring.setCurrentValue(0.8f);
        spring.setSpringConfig(new SpringConfig(50, 6));

        spring.addListener(new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                float currentValue = (float) spring.getCurrentValue();
                llSd1.setScaleX(currentValue);
                llSd2.setScaleX(currentValue);
            }
        });
        spring.setEndValue(1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                oninitAnimationTwo();
            }
        }, 400);
    }


    private void oninitAnimationTwo() {
        llXh1.setVisibility(View.VISIBLE);
        llXh2.setVisibility(View.VISIBLE);
        animation = new TranslateAnimation(-100.0f, 10.0f, 0.0f, 0.0f);
        animation.setDuration(200);
        llXh1.startAnimation(animation);
        animation = new TranslateAnimation(100.0f, 0.0f, 0.0f, 0.0f);
        animation.setDuration(300);
        llXh2.startAnimation(animation);
        SpringSystem springSystem = SpringSystem.create();
        Spring spring = springSystem.createSpring();
        spring.setCurrentValue(0.8f);
        spring.setSpringConfig(new SpringConfig(50, 6));

        spring.addListener(new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                float currentValue = (float) spring.getCurrentValue();
                llXh1.setScaleX(currentValue);
                llXh2.setScaleX(currentValue);
            }
        });
        spring.setEndValue(1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                oninitAnimationThree();
            }
        }, 460);
    }


    private void oninitAnimationThree() {
        llAq1.setVisibility(View.VISIBLE);
        llAq2.setVisibility(View.VISIBLE);
        animation = new TranslateAnimation(-100.0f, 10.0f, 0.0f, 0.0f);
        animation.setDuration(300);
        llAq1.startAnimation(animation);
        animation = new TranslateAnimation(100.0f, 0.0f, 0.0f, 0.0f);
        animation.setDuration(200);
        llAq2.startAnimation(animation);
        SpringSystem springSystem = SpringSystem.create();
        Spring spring = springSystem.createSpring();
        spring.setCurrentValue(0.8f);
        spring.setSpringConfig(new SpringConfig(50, 6));

        spring.addListener(new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                float currentValue = (float) spring.getCurrentValue();
                llAq1.setScaleX(currentValue);
                llAq2.setScaleX(currentValue);
            }
        });
        spring.setEndValue(1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onAnimationBySpringBotton();
            }
        }, 520);
    }

    private void onAnimationBySpringBotton() {
        roundButton.setVisibility(View.VISIBLE);
        SpringSystem springSystem = SpringSystem.create();
        Spring spring = springSystem.createSpring();
        spring.setCurrentValue(0.9f);
        spring.setSpringConfig(new SpringConfig(50, 8));

        spring.addListener(new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                float currentValue = (float) spring.getCurrentValue();
                roundButton.setScaleX(currentValue);
            }
        });
        spring.setEndValue(1);
    }

}
