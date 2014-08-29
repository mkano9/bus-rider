package com.migapro.busrider.ui;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.widget.ArrayAdapter;

import com.astuetz.PagerSlidingTabStrip;
import com.migapro.busrider.R;
import com.migapro.busrider.models.Bus;
import com.migapro.busrider.parser.BusXmlPullParser;

public class MainActivity extends Activity implements OnNavigationListener {

	private ViewPager mViewPager;
	private BusXmlPullParser mParser;
	private ArrayList<String> mBusNames;
	private Bus mCurrentBus;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		try {
			mParser = new BusXmlPullParser();
			mBusNames = mParser.readBusNames(getAssets().open("data/bus_data.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_title, mBusNames);
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		actionBar.setListNavigationCallbacks(spinnerAdapter, this);
		
		mViewPager = (ViewPager) findViewById(R.id.view_pager);
		mViewPager.setAdapter(new ViewPagerAdapter(getFragmentManager()));
		
		PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		tabs.setViewPager(mViewPager);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long timeId) {
		try {
			mCurrentBus = mParser.readABusData(getAssets().open("data/bus_data.xml"), 0);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		
		return false;
	}

}
