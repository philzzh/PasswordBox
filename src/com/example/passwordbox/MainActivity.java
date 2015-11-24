package com.example.passwordbox;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bean.PasswordEntity;
import com.example.db.DatabaseHelper;
import com.example.db.OrmLiteActionBarActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;


@SuppressLint("NewApi")
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
//        System.out.println("onCreate start"+mNavigationDrawerFragment );
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, new WelcomeFragment())
                .commit();
//        System.out.println("onCreate end"+mNavigationDrawerFragment );
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
    
    public void updateEntity(PasswordEntity passwordEntity) {
    	getRuntimeExceptionDao().update(passwordEntity);
    }
    
    public void showToast(Context context ,String msg) {
    	Toast toast = new Toast(context);
//    	toast.setText(msg);
    	LinearLayout layout = new LinearLayout(context);  
        TextView textView = new TextView(context);  
        textView.setText(msg);  
        textView.setTextSize(24);  
        layout.addView(textView);  
        toast.setView(layout);
    	toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER, 50, 50);
    	toast.setDuration(Toast.LENGTH_SHORT);
    	toast.show();
    }

   /**
     * A placeholder fragment containing a simple view.
     */
	public static class WelcomeFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */

    	
        public WelcomeFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
           View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        	
            return rootView;
        }
        @Override
        public void onAttach(Activity activity) {
        	super.onAttach(activity);
        	((MainActivity) activity).onSectionAttached("Welcome");
        }
       
    }

}
