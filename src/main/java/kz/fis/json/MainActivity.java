package kz.fis.json;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Spinner spinner;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);
        textView = findViewById(R.id.textView);
        textView.setTextIsSelectable(true);
        imageView = findViewById(R.id.imageView);
        JSONUpdateTask task = new JSONUpdateTask();
        task.execute(spinner.getSelectedItem().toString());
    }

    // Кнопка "Обновить"
    public void onClick(View view) {
        new JSONUpdateTask().execute(spinner.getSelectedItem().toString());
    }

    private class JSONUpdateTask extends AsyncTask<String, Void, Json> {


        @Override
        protected Json doInBackground(String... params) {
            return JsonBuilder.buildJson(params[0]);
        }


        @Override
        protected void onPostExecute(final Json json) {
            super.onPostExecute(json);
            //  Используем синхронизацию с UI
            imageView.post(() -> {

                if (json.getIcon() != null) {
                    imageView.setImageBitmap(json.getIcon());
                } else {
                    imageView.setImageResource(R.mipmap.ic_launcher); // Установка своей иконки при ошибке
                }
                imageView.invalidate(); // Принудительная прорисовка картинки на всякий случай
            });
            //вывод подписок
            //  с использованием синхронизации UI
            textView.post(() -> {
                textView.setText("");
                if (json.getName() != null) {
                    textView.append("ID: " + json.getId() + "\n");
                    textView.append("Login: " + json.getName() + "\n");
                    textView.append("Ссылка: " + json.getUserURL() + "\n");
                    textView.append("Ссылка на репозитории: " + json.getRepository());
                } else {
                    textView.append("Нет данных" + "\n");
                    textView.append("Проверьте подключение с интернетом");
                }
            });
        }
    }
}