package com.article.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.article.R;

/**
 * @ClassName: LoginActivity.java
 * @author zcs
 * @version V1.0
 * @Date 2017年3月27日 上午10:31:40
 * @Description: 用户登录界面
 */
public class LoginActivity extends BaseActivity {

	@Override
	protected void onCreateView(Bundle savedInstanceState) {
		createView(R.layout.activity_login);
		setOnClick(R.id.btn_login);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:// 登录
			login(null,null);
			break;

		default:
			break;
		}
		super.onClick(v);
	}
	
	
	private void login(String name,String pass){
		if(isLoginOk(name,pass)){
			startActivity(new Intent(this, HomeActivity.class));
		}
	}

	private boolean isLoginOk(String name, String pass) {
		// TODO Auto-generated method stub
		return true;
	}
}
