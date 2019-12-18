package dede.ugurcan.dovizapi;

/**
 * @author: Ugurcan Dede
 * @date: 13.12.2019
 * @description: API implementation using fixer.io by Ugurcan Dede
 * @project-url: https://github.com/ugurcandede/DovizAPI
 */


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

                String turkLirasi = jsonObject1.getString("TRY");
                tryText.setText(turkLirasi);

                String abdDolari = jsonObject1.getString("USD");
                usdText.setText(abdDolari);

                String audDolari = jsonObject1.getString("AUD");
                audText.setText(audDolari);

                String cadDolari = jsonObject1.getString("CAD");
                cadText.setText(cadDolari);

                String jpyDolari = jsonObject1.getString("JPY");
                jpyText.setText(jpyDolari);


            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }
}
