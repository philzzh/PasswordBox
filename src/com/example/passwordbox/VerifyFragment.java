package com.example.passwordbox;
import org.apache.commons.codec4android.digest.DigestUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bean.PasswordEntity;
import com.example.passwordbox.MainActivity.WelcomeFragment;

public class VerifyFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
//    private static final String ARG_SECTION_NUMBER = "section_number";
	
	
	private EditText nameEdit;
    private EditText passwordEdit;
    private EditText descriptionEdit;
    
    private Button verifyButton;
    private Button deleteButton;
    private Button updateButton;
    
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
    	nameEdit = (EditText)rootView.findViewById(R.id.nameEdit);
    	nameEdit.setText(entity.getEntityName());
        passwordEdit = (EditText)rootView.findViewById(R.id.passwordEdit);
        descriptionEdit = (EditText)rootView.findViewById(R.id.descriptionEdit);
        descriptionEdit.setText(entity.getEntityDiscription());
        verifyButton = (Button)rootView.findViewById(R.id.verifyButton);
        deleteButton = (Button)rootView.findViewById(R.id.deleteButton);
        updateButton = (Button)rootView.findViewById(R.id.updateButton);
        verifyButton.setOnClickListener(new myClickListener());
        deleteButton.setOnClickListener(new myClickListener());
        updateButton.setOnClickListener(new myClickListener());
        return rootView;
    }

    private boolean checkLogic() {
    	if(nameEdit.getText().toString().equals("")){
    		nameEdit.setError(getString(R.string.check_null_name));
//        	Toast.makeText(getActivity(),"please Enter name!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(passwordEdit.getText().toString().equals("")){
        	passwordEdit.setError(getString(R.string.check_null_password));
//        	Toast.makeText(getActivity(),"please Enter password!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(descriptionEdit.getText().toString().equals("")){
        	descriptionEdit.setError(getString(R.string.check_null_description));
//        	Toast.makeText(getActivity(),"please Enter description!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    
    class myClickListener implements View.OnClickListener{

		@Override
		public void onClick(View view) {
			if (view == verifyButton){
				
            	if(checkLogic()) {
//            		String pwd = passwordEdit.getText().toString();
            		if(DigestUtils.md5Hex(passwordEdit.getText().toString()).equals(entity.getEntityPassword())) {
//            			Toast.makeText(getActivity(),"Yes your memory is good!",Toast.LENGTH_SHORT).show();
            			((MainActivity) getActivity()).showToast(getActivity(), getString(R.string.verify_tips_yes));
            		}
            		else {
//            			Toast.makeText(getActivity(),"That's not correct! try another one.",Toast.LENGTH_SHORT).show();
            			((MainActivity) getActivity()).showToast(getActivity(), getString(R.string.verify_tips_no));
            		}
            	}	
			}
			if (view == deleteButton){
				System.out.println("deleteButton");
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setMessage(
						getString(R.string.delete_tips));
				builder.setPositiveButton(
								getString(R.string.dialog_yes),
								new DialogInterface.OnClickListener() {

									public void onClick(
											DialogInterface dialog,
											int id) {
										((MainActivity) getActivity()).deleteEntity(entity);
//										Toast.makeText(getActivity(),"Delete success!",Toast.LENGTH_SHORT).show();
										((MainActivity) getActivity()).showToast(getActivity(), "Delete success!");
//										((MainActivity) getActivity()).setContentView(R.layout.activity_main);
										FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
								        fragmentManager.beginTransaction()
						                .replace(R.id.container,new MainActivity.WelcomeFragment())
						                .commit();
//										//flush plan view
//										DatabaseOperator operator = new DatabaseOperator(planDao , dutyDao);
//										MainActivity.this.setListAdapter(new MainAdapter(operator.queryPlanobjList(null), MainActivity.this));

									}

								});
				builder.setNegativeButton(getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {

									public void onClick(
											DialogInterface dialog,
											int id) {
										}
				});
				builder.create().show();
				System.out.println("deleteButton end");
			}
			if (view == updateButton){
				
				if(checkLogic()) {
					String name = nameEdit.getText().toString();
					String description = descriptionEdit.getText().toString();
					String password = DigestUtils.md5Hex(passwordEdit.getText().toString());
					if(!name.equals(entity.getEntityName())||!description.equals(entity.getEntityDiscription())||!password.equals(entity.getEntityPassword())){
						entity.setEntityName(name);
						entity.setEntityDiscription(description);
						entity.setEntityPassword(password);
						((MainActivity) getActivity()).updateEntity(entity);
						((MainActivity) getActivity()).showToast(getActivity(), getString(R.string.update_done));
//						Toast.makeText(getActivity(),"Update done!.",Toast.LENGTH_SHORT).show();
					}
					else {
							((MainActivity) getActivity()).showToast(getActivity(), getString(R.string.check_unchanged));
	//						Toast.makeText(getActivity(),"Unchanged!!",Toast.LENGTH_SHORT).show();
					}
				}
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