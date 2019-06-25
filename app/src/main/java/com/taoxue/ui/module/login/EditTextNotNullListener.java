package com.taoxue.ui.module.login;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;

import com.taoxue.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2017/8/11.
 */

public class EditTextNotNullListener {
    List<EditText>lists;
    private  View targetView;

    public EditTextNotNullListener() {
        lists=new ArrayList<>();
    }

    TextChange textChange = new TextChange();

    //EditText的监听器
    class TextChange implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            for (int i=0;i<lists.size();i++){
               if (lists.get(i).length()<=0){
                   onEditExitNull();
                   return;
               }
            }

            onEdtAllNotNull();
        }
    }

    public   void  onEditExitNull(){
       targetView.setEnabled(false);
    };
    public   void onEdtAllNotNull(){
       targetView .setFocusable(true);
        targetView.setEnabled(true);
    };

    public  Builder from(View targetView){
    this.targetView=targetView;
        return new Builder(targetView);
    }
    public class  Builder{
        private  View view;
        private  EditText nameEdt;
        private  EditText mobileEdt;
        private  EditText mobileCodeEdt;
        private  EditText  passwordEdt;
        private  EditText  newPwEdt;//确认密码

        public Builder(View view) {
            LogUtils.D("VIEW--->"+view);
            this.view = view;
        }

        public Builder name(EditText nameEdt){
             this.nameEdt=nameEdt;
             lists.add(nameEdt);
             return this;
         }

        public Builder mobile(EditText mobileEdt){
            this.mobileEdt=mobileEdt;
            lists.add(mobileEdt);
            return this;
        }
        public Builder mobileCode(EditText mobileCodeEdt){
            this.mobileCodeEdt=mobileCodeEdt;
            lists.add(mobileCodeEdt);
            return this;
        }
        public Builder password(EditText passwordEdt){
            this.passwordEdt=passwordEdt;
            lists.add(passwordEdt);
            return this;
        }
        public Builder newPw(EditText newPwEdt){
            this.newPwEdt=newPwEdt;
            lists.add(newPwEdt);
            return this;
        }
        public Builder addEdt(EditText newEdt){
            lists.add(newEdt);
            return this;
        }
        public  void load(){
            for (int i=0;i<lists.size();i++){
                lists.get(i).addTextChangedListener(textChange);
            }
        }
    }
}
