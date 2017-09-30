package com.mylibrary.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.mylibrary.zxing.activity.CaptureActivity;
import com.mylibrary.zxing.encoding.EncodingHandler;

/**
 * Created by Administrator on 2017/9/29.
 */

public class MyOrCodeUtils extends Activity  {

    //打开扫描界面请求码
    private int REQUEST_CODE = 0x01;
    //扫描成功返回码
    private int RESULT_OK = 0xA1;
    private Button openQrCodeScan;
    private EditText enter;
    private Button createQrCode;
    private ImageView qrCode;
    private TextView qrCodeText;
    private Activity context;

    //扫描二维码
    public  void openQrCodeScan (final Activity context, Button openQrCodeScan, TextView qrCodeText) {
        this.context=context;
        this.openQrCodeScan=openQrCodeScan;
        this.qrCodeText=qrCodeText;
        openQrCodeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开二维码扫描界面
                if(CommonUtil.isCameraCanUse()){
                context.startActivityForResult(new Intent(context, CaptureActivity.class), REQUEST_CODE);
                }else{
                    Toast.makeText(context,"请打开此应用的摄像头权限！",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //生成二维码
    public  void CreateQrCodeScan (final Activity context, final EditText  enter, Button createQrCode, final ImageView qrCode) {
        this.context =context;
        this.createQrCode=createQrCode;
        this.enter=enter;
        this.qrCode=qrCode;
        createQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //获取输入的文本信息
                    String str = enter.getText().toString().trim();
                    if(str != null && !"".equals(str.trim())){
                        //根据输入的文本生成对应的二维码并且显示出来
                        Bitmap mBitmap = EncodingHandler.createQRCode(enter.getText().toString(), 500);
                        if(mBitmap != null){
                            Toast.makeText(context,"二维码生成成功！",Toast.LENGTH_SHORT).show();
                            qrCode.setImageBitmap(mBitmap);
                        }
                    }else{
                        Toast.makeText(context,"文本信息不能为空！",Toast.LENGTH_SHORT).show();
                    }
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //扫描结果回调
        if (resultCode == RESULT_OK) { //RESULT_OK = -1
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("qr_scan_result");
            //将扫描出的信息显示出来
            qrCodeText.setText(scanResult);
        }
    }
}
