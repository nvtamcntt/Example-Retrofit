package com.example.retrofitexample;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Stack;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends ListActivity implements Callback<StackOverflowQuestions> {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hiển thị title loading
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        requestWindowFeature(Window.FEATURE_PROGRESS);

        // Set Adapter mặc định cho Activity
        ArrayAdapter<Question> arrayAdapter =
                new ArrayAdapter<Question>(this,
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        new ArrayList<Question>());
        setListAdapter(arrayAdapter);

        // Visible loading
        setProgressBarIndeterminateVisibility(true);
        setProgressBarVisibility(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Gán Menu cho Activity
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Khi lựa chọn Load Data từ Menu
        setProgressBarIndeterminateVisibility(true);

        // Khởi tạo Retrofit để gán API ENDPOINT (Domain URL) cho Retrofit 2.0
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.stackexchange.com")
                // Sử dụng GSON cho việc parse và maps JSON data tới Object
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Khởi tạo các cuộc gọi cho Retrofit 2.0
        StackOverflowAPI stackOverflowAPI = retrofit.create(StackOverflowAPI.class);

        Call<StackOverflowQuestions> call = stackOverflowAPI.loadQuestions("android");
        // Cuộc gọi bất đồng bọ (chạy dưới background)
        call.enqueue(this);

        // Nếu bạn muốn chạy đồng bồ trên main thread sử dụng phương thức execute
        // call.execute()

        // Để Cancel request:
        // call.cancel();

        // Có thể clone một cuộc gọi trước đó
        //Call<StackOverflowQuestions> c = call.clone();
        //c.enqueue(this);

        return true;
    }

    // Phương thức được triển khai khi implement interface  Callback
    @Override
    public void onResponse(Response<StackOverflowQuestions> response, Retrofit retrofit) {
        // Visible Loading bar
        setProgressBarIndeterminateVisibility(false);

        // Gán giá trị cho Adapter để fill tới List
        ArrayAdapter<Question> adapter = (ArrayAdapter<Question>) getListAdapter();
        adapter.clear();
        // Lấy dữ liệu trả về từ Response qua Body()
        adapter.addAll(response.body().items);
    }

    @Override
    public void onFailure(Throwable t) {
        // Khi có vấn đề từ Server như các lỗi 4xx 5xx ....
        Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }
}