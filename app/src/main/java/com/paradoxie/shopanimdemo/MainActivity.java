package com.paradoxie.shopanimdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.paradoxie.shopanimlibrary.AniManager;
import com.paradoxie.shopanimlibrary.BadgeView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn1, btn2, btn3, btn4, btn5;
    private ImageView car;//购物车图标
    private ImageView buyImg;// 界面上跑的小图片
    private BadgeView buyNumView;//购物车上的数量标签
    private AniManager mAniManager;
    private int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);
        btn5 = (Button) findViewById(R.id.button5);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);

        mAniManager = new AniManager();//动画实例

        car = (ImageView) findViewById(R.id.car);

        buyNumView = new BadgeView(this, car);//把这个数字标签放在购物车图标上
        buyNumView.setBadgePosition(BadgeView.POSITION_CENTER);//放在图标中心
        buyNumView.setTextColor(Color.WHITE);//数字颜色
        buyNumView.setBadgeBackgroundColor(Color.BLUE);//背景颜色
        buyNumView.setTextSize(9);//数字大小
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                startAnim(v);
                break;
            case R.id.button2:
                startAnim(v);
                break;
            case R.id.button3:
                startAnim(v);
                break;
            case R.id.button4:
                startAnim(v);
                break;
            case R.id.button5:
                startAnim(v);
                break;
            default:
                break;
        }
    }

    //启动动画
    public void startAnim(View v) {
        int[] end_location = new int[2];
        int[] start_location = new int[2];
        v.getLocationInWindow(start_location);// 获取购买按钮的在屏幕的X、Y坐标（动画开始的坐标）
        car.getLocationInWindow(end_location);// 这是用来存储动画结束位置，也就是购物车图标的X、Y坐标
        buyImg = new ImageView(this);// buyImg是动画的图片
        buyImg.setImageResource(R.mipmap.sign);// 设置buyImg的图片

        //        mAniManager.setTime(5500);//自定义时间
        mAniManager.setAnim(this, buyImg, start_location, end_location);// 开始执行动画

        mAniManager.setOnAnimListener(new AniManager.AnimListener() {
            @Override
            public void setAnimBegin(AniManager a) {
                //动画开始时的监听
            }

            @Override
            public void setAnimEnd(AniManager a) {
                //动画结束后的监听
                num += 5;
                buyNumView.setText(num + "");
                buyNumView.show();
            }
        });
    }
}
