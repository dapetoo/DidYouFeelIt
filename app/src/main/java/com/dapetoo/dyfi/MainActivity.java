package com.dapetoo.dyfi;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    /** URL for earthquake data from the USGS dataset */
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2017-05-02&minfelt=50&minmagnitude=5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create AsyncTask Object to perform an HTTP request to the given URL
        //on a background thread.  When the result is received then update the UI
        EarthQuakeAsyncTask task = new EarthQuakeAsyncTask();
        task.execute(USGS_REQUEST_URL);

    }

    /**
     * Update the UI with the given earthquake information.
     */
    private void updateUi(Event earthquake) {
        TextView titleTextView = (TextView) findViewById(R.id.title);
        titleTextView.setText(earthquake.title);

        TextView tsunamiTextView = (TextView) findViewById(R.id.number_of_people);
        tsunamiTextView.setText(getString(R.string.num_people_felt_it, earthquake.numOfPeople));

        TextView magnitudeTextView = (TextView) findViewById(R.id.perceived_magnitude);
        magnitudeTextView.setText(earthquake.perceivedStrength);
    }


    private class EarthQuakeAsyncTask extends AsyncTask<String, Void, Event> {

        //This method is invoked in the background thread so that we can make network request
        @Override
        protected Event doInBackground(String... urls) {
            // Perform the HTTP request for earthquake data and process the response.
            Event result = Utils.fetchEarthquakeData(urls[0]);
            return result;
        }

        //This will update the UI of the app after the background process
        @Override
        protected void onPostExecute(Event result) {
            updateUi(result);
        }
    }
}
