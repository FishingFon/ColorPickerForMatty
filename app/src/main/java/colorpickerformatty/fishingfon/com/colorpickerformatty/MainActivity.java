package colorpickerformatty.fishingfon.com.colorpickerformatty;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;



public class MainActivity extends AppCompatActivity
        implements ColorPickerFragment.OnFragmentInteractionListener, MultiColorPickerFragment.OnFragmentInteractionListener, SavedColorsFragments.OnFragmentInteractionListener, ColorChangeSelectedListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private long timeOfLastHttpRequest;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timeOfLastHttpRequest = 0;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public void onColorChanged(int[] color) {

        /*
        int A = (color[0] >> 24) & 0xff; // or color >>> 24
        int R = (color[0] >> 16) & 0xff;
        int G = (color[0] >>  8) & 0xff;
        int B = (color[0]      ) & 0xff;
        Log.v("Color in ARGB: ", "(" + A + ", " + R + ", " + G + ", " + B + ")");
        */
        Log.v("Time: ", "" + (System.currentTimeMillis() > timeOfLastHttpRequest + 200));
        if (System.currentTimeMillis() > timeOfLastHttpRequest + 200){
            timeOfLastHttpRequest = System.currentTimeMillis();
            Log.v("MainColor", Arrays.toString(color));
            //TODO add socket stuff
            //new ColorRequest().execute(color);
        }


    }

    class ColorRequest extends AsyncTask<int[], Void, String>{

        @Override
        protected String doInBackground(int[]... color) {

            String responseString = null;
            try {
                URL url = new URL("URL HERE");
                HttpURLConnection urlConnection =(HttpURLConnection) url.openConnection();
                //urlConnection.setDoOutput(true);
                urlConnection.setChunkedStreamingMode(0);
                Log.v("Testing", Arrays.toString(color[0]));
               // OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("User-Agent", "color_picker_1.0");
                urlConnection.setRequestProperty("Content-Length", "0");
                urlConnection.setRequestProperty("Lc", Arrays.toString(color[0]));
                // TEST IM TODO DELETE LATER
                int responseCode = urlConnection.getResponseCode();
                Log.v(" TEST IN 2: ", "\nSending 'GET' request to URL : " + url);
                Log.v(" TEST IN 3: ", "Response Code : " + responseCode);

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //print result
                Log.v(" HTTPRESPONSE: ", response.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position){
                case 0:
                    return ColorPickerFragment.newInstance();
                case 1:
                    return MultiColorPickerFragment.newInstance();
                case 2:
                    return SavedColorsFragments.newInstance();

            }
            return null;
            //return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Colour Picker";
                case 1:
                    return "MultiColour Picker";
                case 2:
                    return "Saved Colours";
            }
            return null;
        }
    }
}
