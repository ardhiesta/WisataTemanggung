package com.wisatatmg;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;

public class WisataTabActivity extends ActionBarActivity {
	public static final String M_CURRENT_TAB = "M_CURRENT_TAB";
	private TabHost mTabHost;
	private String mCurrentTab;

	public static final String TAB_LOKASI = "LOKASI"; // tab yang akan dibentuk
	public static final String TAB_KATEGORI = "KATEGORI";
	public static final String TAB_CARI = "CARI";

	Fragment lokasiFragment = new LokasiFragment();
	Fragment kategoriFragment = new KategoriFragment();
	Fragment cariFragment = new CariFragment();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wisata_tab);

		// setup tabHost
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();

		if (savedInstanceState != null) {
			mCurrentTab = savedInstanceState.getString(M_CURRENT_TAB);
			initializeTabs();
			mTabHost.setCurrentTabByTag(mCurrentTab);
			/*
			 * when resume state it's important to set listener after
			 * initializeTabs
			 */
			mTabHost.setOnTabChangedListener(listener);
		} else {
			mTabHost.setOnTabChangedListener(listener);
			initializeTabs();
		}
	}

	public void initializeTabs() {
		TabHost.TabSpec spec;
		
		spec = mTabHost.newTabSpec(TAB_KATEGORI);
		spec.setContent(new TabHost.TabContentFactory() {
			public View createTabContent(String tag) {
				return findViewById(R.id.realtabcontent);
			}
		});
		spec.setIndicator("Kategori");
		// spec.setIndicator(createTabView(R.drawable.amarok, "search"));
		mTabHost.addTab(spec);

		spec = mTabHost.newTabSpec(TAB_LOKASI);
		spec.setContent(new TabHost.TabContentFactory() {
			public View createTabContent(String tag) {
				return findViewById(R.id.realtabcontent);
			}
		});
		spec.setIndicator("Lokasi");
		// spec.setIndicator(createTabView(R.drawable.amarok, "wisata"));
		mTabHost.addTab(spec);

		spec = mTabHost.newTabSpec(TAB_CARI);
		spec.setContent(new TabHost.TabContentFactory() {
			public View createTabContent(String tag) {
				return findViewById(R.id.realtabcontent);
			}
		});
		spec.setIndicator("Cari");
		// spec.setIndicator(createTabView(R.drawable.amarok, "search"));
		mTabHost.addTab(spec);
	}

	TabHost.OnTabChangeListener listener = new TabHost.OnTabChangeListener() {
		public void onTabChanged(String tabId) {
			mCurrentTab = tabId;

			if (tabId.equals(TAB_LOKASI)) {
				pushFragments(lokasiFragment, false, false, null);
			} else if (tabId.equals(TAB_KATEGORI)) {
				pushFragments(kategoriFragment, false, false, null);
			} else if (tabId.equals(TAB_CARI)) {
				pushFragments(cariFragment, false, false, null);
			}
		}

	};

	public void pushFragments(android.support.v4.app.Fragment fragment,
			boolean shouldAnimate, boolean shouldAdd, String tag) {
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		if (shouldAnimate) {
			// ft.setCustomAnimations(R.animator.fragment_slide_left_enter,
			// R.animator.fragment_slide_left_exit,
			// R.animator.fragment_slide_right_enter,
			// R.animator.fragment_slide_right_exit);
		}
		ft.replace(R.id.realtabcontent, fragment, tag);

		if (shouldAdd) {
			/*
			 * here you can create named backstack for realize another logic.
			 * ft.addToBackStack("name of your backstack");
			 */
			ft.addToBackStack(null);
		} else {
			/*
			 * and remove named backstack:
			 * manager.popBackStack("name of your backstack",
			 * FragmentManager.POP_BACK_STACK_INCLUSIVE); or remove whole:
			 * manager.popBackStack(null,
			 * FragmentManager.POP_BACK_STACK_INCLUSIVE);
			 */
			manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		}
		ft.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wisata_tab, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
