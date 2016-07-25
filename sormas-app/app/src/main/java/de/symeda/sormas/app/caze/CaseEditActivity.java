package de.symeda.sormas.app.caze;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import de.symeda.sormas.api.caze.CaseDataDto;
import de.symeda.sormas.app.R;
import de.symeda.sormas.app.SurveillanceActivity;

/**
 * Created by Stefan Szczesny on 21.07.2016.
 */
public class CaseEditActivity extends Activity {

    //private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.case_edit_layout);


        final Button button = (Button) findViewById(R.id.button_back);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showCasesView();            }
        });


        /*TabHost tabHost = (TabHost) findViewById(R.id.tab_host);

        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("simple").setIndicator("Simple"),
                FragmentStackSupport.CountingFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("contacts").setIndicator("Contacts"),
                LoaderCursorSupport.CursorLoaderListFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("custom").setIndicator("Custom"),
                LoaderCustomSupport.AppListFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("throttle").setIndicator("Throttle"),
                LoaderThrottleSupport.ThrottledLoaderListFragment.class, null);



        TabSpec tab1 = tabHost.newTabSpec("First Tab");
        tab1.setIndicator("Tab1");
        tab1.setContent(new Intent(this, CaseDataActivity.class));
        TabSpec tab2 = tabHost.newTabSpec("Second Tab");
        tab2.setIndicator("Tab2");
        tab2.setContent(new Intent(this, CasePersonActivity.class));

        // Add the tabs  to the TabHost to display.
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);*/


    }

    public void setData(CaseDataDto dto) {
        //populateFormView(dto);
    }

    public void showCasesView() {
        Intent intent = new Intent(this, SurveillanceActivity.class);
        startActivity(intent);
    }


}
