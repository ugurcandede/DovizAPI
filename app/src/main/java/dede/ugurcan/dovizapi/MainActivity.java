package dede.ugurcan.dovizapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView tryText;
    TextView usdText;
    TextView audText;
    TextView cadText;
    TextView jpyText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tryText = findViewById(R.id.TRYtext);
        usdText = findViewById(R.id.USDtext);
        audText = findViewById(R.id.AUDtext);
        cadText = findViewById(R.id.CADtext);
        jpyText = findViewById(R.id.JPYtext);

    }


    public void verileriGetir(View v) {

        DownloadData downloadData = new DownloadData();
        try {

            String accessKey = "PUT HERE YOUR ACCESS KEY";
            String url = "http://data.fixer.io/api/latest?access_key=' + accessKey + '";
            downloadData.execute(url);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class DownloadData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;

            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);

                int data = reader.read();
                while (data > 0) {
                    char karakter = (char) data;
                    result += karakter;
                    data = reader.read();
                }
                return result;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            System.out.println("Alınan veri: " + s);
            try {

                JSONObject jsonObject = new JSONObject(s);
                String base = jsonObject.getString("base");
                System.out.println(base);


                String rates = jsonObject.getString("rates");
                JSONObject jsonObject1 = new JSONObject(rates);

                String turkLirası = jsonObject1.getString("TRY");
                tryText.setText(turkLirası);

                String abdDoları = jsonObject1.getString("USD");
                usdText.setText(abdDoları);

                String audDoları = jsonObject1.getString("AUD");
                audText.setText(audDoları);

                String cadDoları = jsonObject1.getString("CAD");
                cadText.setText(cadDoları);

                String jpyDoları = jsonObject1.getString("JPY");
                jpyText.setText(jpyDoları);


            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }
}
