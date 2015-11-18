package com.example.passwordbox;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bean.PasswordEntity;
import com.example.db.DatabaseHelper;
import com.example.db.OrmLiteActionBarActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;


public class MainActivity extends OrmLiteActionBarActivity<DatabaseHelper>
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);*/
        mTitle = getTitle();
        System.out.println("onCreate start"+mNavigationDrawerFragment );
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        System.out.println("onCreate end"+mNavigationDrawerFragment );
    }
    
	@Override
	public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
//    	System.out.println("click fragmentdrawer"+position);
//    	System.out.println("mDrawerListView "+((NavigationDrawerFragment)
//                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer)).getmDrawerListView().getAdapter().getItem(position));//null why?
    	PasswordEntity passwordEntity = (PasswordEntity) ((NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer)).getmDrawerListView().getAdapter().getItem(position);
//    	System.out.println("onNavigationDrawerItemSelected Entity name & password"+passwordEntity.getEntityName()+"  "+passwordEntity.getEntityPassword());
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, VerifyFragment.newInstance(passwordEntity))
                .commit();
    }

   /* public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }*/

	 public void onSectionAttached(String  entityName) {
	        
	                mTitle = entityName;
	    }
	
    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
        	FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, NewItemFragment.newInstance(1))
                    .commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private RuntimeExceptionDao<PasswordEntity, Integer> entityDao;
    
    public RuntimeExceptionDao<PasswordEntity, Integer> getRuntimeExceptionDao() {
    	if(this.entityDao==null) {
    		this.entityDao = getHelper().getEntityDao();
    	}
    	return this.entityDao;
    }
    
    /**
     * Get Entities from DB
     */
    public List<PasswordEntity> getEntityList() {
    	
		return getRuntimeExceptionDao().queryForAll();
    }
    
    public void deleteEntity(PasswordEntity passwordEntity) {
    	getRuntimeExceptionDao().delete(passwordEntity);
    }

   /* *//**
     * A placeholder fragment containing a simple view.
     *//*
    public static class VerifyFragment extends Fragment {
        *//**
         * The fragment argument representing the section number for this
         * fragment.
         *//*
//        private static final String ARG_SECTION_NUMBER = "section_number";
    	
		private RuntimeExceptionDao<PasswordEntity, Integer> entityDao;
    	
    	private TextView nameView;
        private EditText passwordEdit;
        private TextView descriptionView;
        
        private Button verifyButton;
        private Button deleteButton;
        
        private static PasswordEntity entity;
    	
    	private static final String ARG_ENTITY = "entity";

        *//**
         * Returns a new instance of this fragment for the given section
         * number.
         *//*
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
//            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        	
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
					AlertDialog.Builder builder = new AlertDialog.Builder(
							getActivity());
					builder.setMessage(
							"Are you sure you want to delete?");
					builder.setPositiveButton(
									"Yes",
									new DialogInterface.OnClickListener() {

										public void onClick(
												DialogInterface dialog,
												int id) {
											//deleteEntity(entity);
//											planDao = getHelper().getPlanDao();
//											dutyDao = getHelper().getDutyDao();
//											planDao.deleteById(planId);
//											DutyObject dutyObj = new DutyObject();
//											dutyObj.setPlanId(planId);
//											dutyDao.delete(dutyObj);
//											//flush plan view
//											DatabaseOperator operator = new DatabaseOperator(planDao , dutyDao);
//											MainActivity.this.setListAdapter(new MainAdapter(operator.queryPlanobjList(null), MainActivity.this));

										}

									});
					builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

										public void onClick(
												DialogInterface dialog,
												int id) {
											}
					});
					builder.create().show();
				}
			}
        	
        }
        
        
        
        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(((PasswordEntity)
                    getArguments().getSerializable(ARG_ENTITY)).getEntityName());
        }
       
    }*/

}
