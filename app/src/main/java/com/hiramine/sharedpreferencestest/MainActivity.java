package com.hiramine.sharedpreferencestest;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity
{
	CheckBox checkBoxSetting;
	Spinner  spinnerSetting;
	EditText editTextSetting1;
	EditText editTextSetting2;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );

		// チェックボックス（boolean型設定用）
		checkBoxSetting = (CheckBox)findViewById( R.id.checkBoxSetting );

		// スピナー（int型設定用）
		spinnerSetting = (Spinner)findViewById( R.id.spinnerSetting );
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( this, R.array.number_array, android.R.layout.simple_spinner_item );
		adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
		spinnerSetting.setAdapter( adapter );

		// テキストビュー（string型設定用）
		editTextSetting1 = (EditText)findViewById( R.id.editTextSetting1 );

		// テキストビュー（float型設定用）
		editTextSetting2 = (EditText)findViewById( R.id.editTextSetting2 );

		// 前回設定の読み込み
		updateSettings( false );
	}

	// 別のアクティビティ（か別のアプリ）に移行したことで、バックグラウンドに追いやられた時
	@Override
	protected void onPause()
	{
		if( isFinishing() )
		{    // アクティビティは終了しようとしている（※強制終了時は、ここは処理されない）
			Log.d( getString( R.string.app_name ), "Finish Application" );
		}

		// 設定値の保存
		updateSettings( true );

		super.onPause();
	}

	// 設定値の読み書き
	private void updateSettings( boolean bSave )
	{
		SharedPreferences        sharedpreferences = PreferenceManager.getDefaultSharedPreferences( this );
		SharedPreferences.Editor editor            = sharedpreferences.edit();

		if( bSave )
		{
			boolean bValue = checkBoxSetting.isChecked();
			editor.putBoolean( "checkBoxSetting", bValue );
			int iValue = Integer.parseInt( (String)spinnerSetting.getSelectedItem() );
			editor.putInt( "spinnerSetting", iValue );
			String strValue = editTextSetting1.getText().toString();
			editor.putString( "editTextSetting1", strValue );
			float fValue = Float.parseFloat( editTextSetting2.getText().toString() );
			editor.putFloat( "editTextSetting2", fValue );
		}
		else
		{
			boolean bValue = sharedpreferences.getBoolean( "checkBoxSetting", false );
			checkBoxSetting.setChecked( bValue );
			int iValue = sharedpreferences.getInt( "spinnerSetting", 0 );
			spinnerSetting.setSelection( iValue );
			String strValue = sharedpreferences.getString( "editTextSetting1", "" );
			editTextSetting1.setText( strValue );
			float fValue = sharedpreferences.getFloat( "editTextSetting2", 0.0f );
			editTextSetting2.setText( String.valueOf( fValue ) );
		}

		// 設定値の書き込みの実施
		if( bSave )
		{
			editor.apply();
		}
	}
}
