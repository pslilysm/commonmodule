package pers.cxd.corelibrary.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import pers.cxd.corelibrary.util.ScreenUtil;

/**
 * A Base Dialog implements UiComponent, DialogInterface.OnDismissListener.
 * Easy to design various dialogs
 *
 * @author pslilysm
 * @since 1.1.0
 */
public abstract class BaseDialog implements UiComponent, DialogInterface.OnDismissListener {

    protected final UiComponent mBase;

    protected final AlertDialog.Builder mBuilder;
    private final View mContentView;

    protected AlertDialog mDialog;

    protected BaseDialog(UiComponent component){
        mBase = component;
        mContentView = getLayoutInflater().inflate(getLayoutId(), null);
        mBuilder = new AlertDialog.Builder(component.getContext())
                .setView(mContentView)
                .setCancelable(true)
                .setOnDismissListener(this);
        setUp(null);
    }

    public <V extends View> V findViewById(int id){
        return mContentView.findViewById(id);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        performOnDestroy();
    }

    public void show(){
        if (mDialog == null) {
            mDialog = mBuilder.create();
        }
        mDialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(mDialog.getWindow().getAttributes());
        lp.width = ScreenUtil.getWidth() - ScreenUtil.dip2px(36 * 2);
        mDialog.getWindow().setAttributes(lp);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void dismiss(){
        if (mDialog != null){
            mDialog.dismiss();
        }
    }
    
    public void showSoftKeyboard(EditText editText) {
        editText.postDelayed(() -> {
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
            InputMethodManager imm = (InputMethodManager) mBase.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText,  InputMethodManager.SHOW_IMPLICIT);
            editText.setSelection(editText.getText().length());
        }, 200);
    }

    public void hideSoftKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) mBase.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    @Override
    public Context getContext() {
        return mBase.getContext();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        mBase.startActivityForResult(intent, requestCode);
    }

    @Override
    public LayoutInflater getLayoutInflater() {
        return mBase.getLayoutInflater();
    }

    @Override
    public void startActivity(Intent intent) {
        mBase.startActivity(intent);
    }

    @Override
    public void registerActivityResultCallback(OnActivityResultCallback callback) {
        mBase.registerActivityResultCallback(callback);
    }

}
