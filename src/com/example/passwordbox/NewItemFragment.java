package com.example.passwordbox;

import org.apache.commons.codec4android.digest.DigestUtils;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bean.PasswordEntity;
import com.j256.ormlite.dao.RuntimeExceptionDao;

public class NewItemFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    
    
    private RuntimeExceptionDao<PasswordEntity, Integer> entityDao;
    
    //use for check duplicates while add a entity.
    PasswordEntity passwordEntityCopy;
    
    private EditText nameEdit;
    private EditText passwordEdit;
    private EditText descriptionEdit;
    
    private Button saveButton;
//    private Button updateButton;
//    private Button deleteButton;
    public NewItemFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity)activity).onSectionAttached("New Password Item");
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
//        updateButton = (Button)rootview.findViewById(R.id.updateButton);
//        updateButton.setOnClickListener(new myClickListener());
//        deleteButton = (Button)rootview.findViewById(R.id.deleteButton);
//        deleteButton.setOnClickListener(new myClickListener());
        return rootview;
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
        if(passwordEntityCopy!=null&&compareEntityFromView()) {
        	((MainActivity) getActivity()).showToast(getActivity(), getString(R.string.check_unchanged));
//        	Toast.makeText(getActivity(),"Unchanged!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    
    public PasswordEntity getEntityFromView() {
    	PasswordEntity passwordEntity = new PasswordEntity();
    	passwordEntity.setEntityName(nameEdit.getText().toString());
    	passwordEntity.setEntityPassword(DigestUtils.md5Hex(passwordEdit.getText().toString()));
    	passwordEntity.setEntityDiscription(descriptionEdit.getText().toString());
		return passwordEntity;
    }
    
    public boolean compareEntityFromView() {
		if(passwordEntityCopy.getEntityDiscription().equals(descriptionEdit.getText().toString())
				&&passwordEntityCopy.getEntityName().equals(nameEdit.getText().toString())
				&&passwordEntityCopy.getEntityPassword().equals(DigestUtils.md5Hex(passwordEdit.getText().toString()))){
			return true;
		}
		else
			return false;
    	
    }

    class myClickListener implements View.OnClickListener{


        @Override
		public void onClick(View view) {
            if (view == saveButton){
                if(checkLogic()) {
                	entityDao = ((MainActivity) getActivity()).getHelper()
            				.getEntityDao();
                	PasswordEntity passwordEntity = getEntityFromView();
                	entityDao.create(passwordEntity);
                	passwordEntityCopy = passwordEntity;
                	((MainActivity) getActivity()).showToast(getActivity(), getString(R.string.add_tips));
                	 clearView();
//                	Toast.makeText(getActivity(),"A new Entity added!",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void clearView() {
    	nameEdit.setText("");
        passwordEdit.setText("");
        descriptionEdit.setText("");
    }
    
    public static NewItemFragment newInstance(int sectionNumber){
    	NewItemFragment fragment = new NewItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER,sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
}
