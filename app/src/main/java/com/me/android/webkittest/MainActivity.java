package com.me.android.webkittest;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private WebView mWeBView;
    private AlertDialog mAlertDialog;
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initWebView();
            }
        });
    }

    public void initWebView(){
        mWeBView=(WebView)findViewById(R.id.webview);
        mWeBView.getSettings().setJavaScriptEnabled(true);
        mProgressDialog=ProgressDialog.show(this,null,"please wait...");
        mAlertDialog=new AlertDialog.Builder(this).create();
        EditText editText=(EditText)findViewById(R.id.editText);
        mWeBView.loadUrl(editText.getText().toString());
        mWeBView.setWebViewClient(new MyWebViewClient());

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK&&mWeBView.canGoBack()){
            mWeBView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public class MyWebViewClient extends WebViewClient{

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
          view.loadUrl(url);
            return  true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if(mProgressDialog.isShowing()){
                mProgressDialog.dismiss();
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Toast.makeText(MainActivity.this,"网页加载错误！",Toast.LENGTH_LONG).show();
            mAlertDialog.setTitle("ERROR");
            mAlertDialog.setMessage(description);
            mAlertDialog.setButton("ok",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            mAlertDialog.show();
        }
    }

}
