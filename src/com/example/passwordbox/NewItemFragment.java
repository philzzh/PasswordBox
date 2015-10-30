package com.example.passwordbox;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class NewItemFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    
    private EditText nameEdit;
    private EditText passwordEdit;
    private EditText descriptionEdit;
    
    private Button saveButton;
    private Button updateButton;
    private Button deleteButton;
    private boolean flag = false;
    public NewItemFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity)activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_new_item,container,false);
        nameEdit = (EditText)rootview.findViewById(R.id.nameEdit);
        passwordEdit = (EditText)rootview.findViewById(R.id.passwordEdit);
        descriptionEdit = (EditText)rootview.findViewById(R.id.descriptionEdit);
        saveButton = (Button)rootview.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new myClickListener());
        updateButton = (Button)rootview.findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new myClickListener());
        deleteButton = (Button)rootview.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new myClickListener());
        return rootview;
    }

    class myClickListener implements View.OnClickListener{


        public void onClick(View view) {
           /* if (view == initButton){
                //��ʼ����Կ
                String desKey = "";
                try {
                    desKey = des.initKey();
                }catch (Exception e){
                    e.printStackTrace();
                }
                miyaoEdit.setText(desKey);
            }
            if (view == jiamiButton){
                String desKey = "";
                desKey = miyaoEdit.getText().toString();
                if (desKey.equals("")){
                    Toast.makeText(getActivity(),"���ȳ�ʼ����Կ",Toast.LENGTH_SHORT).show();
                    return;
                }

                String inputStr = "";
                inputStr = mingEdit.getText().toString();
                if (inputStr.equals("")){
                    Toast.makeText(getActivity(),"���������ݲ���Ϊ��",Toast.LENGTH_SHORT).show();
                    return;
                }
                byte[] inputData = inputStr.getBytes();
                byte[] outputData = new byte[0];
                String outputStr = "";
                try {
                    outputData = des.encrypt(inputData,desKey);
                    outputStr = des.encryptBASE64(outputData);
                }catch (Exception e){
                    e.printStackTrace();
                }
                miEdit.setText(outputStr);
            }
            if (view == jiemiButton){
                String desKey = "";
                desKey = miyaoEdit.getText().toString();
                if (desKey.equals("")){
                    Toast.makeText(getActivity(),"���ȳ�ʼ����Կ�����м���",Toast.LENGTH_SHORT).show();
                    return;
                }
                String outputStr = "";
                outputStr = miEdit.getText().toString();
                if(outputStr.equals("")){
                    Toast.makeText(getActivity(),"���Ĳ���Ϊ��,���ȼ��ܲ�������",Toast.LENGTH_SHORT).show();
                    return;
                }
                byte[] outputData = new byte[0];
                try {
                    outputData = des.decrypt(des.decryptBASE64(outputStr),desKey);
                }catch (Exception e){
                    e.printStackTrace();
                }
                String jiemiStr = new String(outputData);
                if(jiemiStr.equals("")){
                    Toast.makeText(getActivity(),"��Կ��ƥ�䣬����ʧ��",Toast.LENGTH_SHORT).show();
                    ming2Edit.setText("");
                    return;
                }
                ming2Edit.setText(new String(outputData));
            }*/
        }
    }

    public static NewItemFragment newInstance(int sectionNumber){
    	NewItemFragment fragment = new NewItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER,sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
}
