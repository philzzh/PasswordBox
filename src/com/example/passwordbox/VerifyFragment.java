package com.example.passwordbox;
import org.apache.commons.codec4android.digest.DigestUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bean.PasswordEntity;

public class VerifyFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
//    private static final String ARG_SECTION_NUMBER = "section_number";
	
	
	private TextView nameView;
    private EditText passwordEdit;
    private TextView descriptionView;
    
    private Button verifyButton;
    private Button deleteButton;
    
    private static PasswordEntity entity;
	
	private static final String ARG_ENTITY = "entity";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static  VerifyFragment newInstance(PasswordEntity passwordEntity) {
    	System.out.println("PlaceholderFragment newInstance entity"+passwordEntity);
    	entity = passwordEntity;
        VerifyFragment fragment = new VerifyFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ENTITY, passwordEntity);
        fragment.setArguments(args);
        return fragment;
    }

    public VerifyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
    	
    	System.out.println("PlaceholderFragment onCreateView entity"+entity);
    	View rootView = inflater.inflate(R.layout.fragment_verify_item, container, false);
    	nameView = (TextView)rootView.findViewById(R.id.nameView);
    	nameView.setText(entity.getEntityName());
        passwordEdit = (EditText)rootView.findViewById(R.id.passwordEdit);
        descriptionView = (TextView)rootView.findViewById(R.id.descriptionView);
        descriptionView.setText(entity.getEntityDiscription());
        verifyButton = (Button)rootView.findViewById(R.id.verifyButton);
        deleteButton = (Button)rootView.findViewById(R.id.deleteButton);
        verifyButton.setOnClickListener(new myClickListener());
        deleteButton.setOnClickListener(new myClickListener());
        return rootView;
    }

    
    class myClickListener implements View.OnClickListener{

		@Override
		public void onClick(View view) {
			if (view == verifyButton){
				String pwd = passwordEdit.getText().toString();
            	if(pwd.equals("")) {
            		Toast.makeText(getActivity(),"please Enter password!",Toast.LENGTH_SHORT).show();
            	}
            	else {
            		if(DigestUtils.md5Hex(pwd).equals(entity.getEntityPassword())) {
            			Toast.makeText(getActivity(),"Yes your memory is good!",Toast.LENGTH_SHORT).show();
            		}
            		else {
            			Toast.makeText(getActivity(),"That's not correct! try another one.",Toast.LENGTH_SHORT).show();
            		}
            	}	
			}
			if (view == deleteButton){
				System.out.println("deleteButton");
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setMessage(
						"Are you sure you want to delete?");
				builder.setPositiveButton(
								"Yes",
								new DialogInterface.OnClickListener() {

									public void onClick(
											DialogInterface dialog,
											int id) {
										((MainActivity) getActivity()).deleteEntity(entity);
//										((MainActivity) getActivity()).setContentView(R.layout.activity_main);
//										//flush plan view
//										DatabaseOperator operator = new DatabaseOperator(planDao , dutyDao);
//										MainActivity.this.setListAdapter(new MainAdapter(operator.queryPlanobjList(null), MainActivity.this));

									}

								});
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

									public void onClick(
											DialogInterface dialog,
											int id) {
										}
				});
				builder.create().show();
				System.out.println("deleteButton end");
			}
		}
    	
    }
    
    
    
    /*@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }*/
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(((PasswordEntity)
                getArguments().getSerializable(ARG_ENTITY)).getEntityName());
    }
   
}