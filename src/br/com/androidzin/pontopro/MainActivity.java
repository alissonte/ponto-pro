package br.com.androidzin.pontopro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;

public class MainActivity extends SherlockFragmentActivity {

	private ViewPager mViewPager;
	private PagerAdapter mPagerAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mPagerAdapter = new CheckinHistoryAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mPagerAdapter);
	}
	
	@Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
        }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private class CheckinHistoryAdapter extends FragmentPagerAdapter
	{
		Fragment screens[];
		public CheckinHistoryAdapter(FragmentManager fm) {
			super(fm);
			screens = new Fragment[2];
			screens[0] = new CheckinFragement();
			screens[1] = new HistoryFragment();
		}

		@Override
		public Fragment getItem(int index) {
			if(index <= screens.length)
			{
				return screens[index];
			}
			return null;
		}

		@Override
		public int getCount() {
			return screens.length;
		}
	}
	

}
