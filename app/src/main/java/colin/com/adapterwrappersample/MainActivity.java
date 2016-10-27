package colin.com.adapterwrappersample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import colin.com.adapterwrapper.WrapperAdapter;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rv;
    private Button btnClear;
    private Button btnAdd;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = (RecyclerView) findViewById(R.id.rv);
        btnClear = (Button) findViewById(R.id.btn_clear);
        btnAdd = (Button) findViewById(R.id.btn_add);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeData();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();
            }
        });

        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter();

        WrapperAdapter wrapper = new WrapperAdapter(adapter);
        TextView header = (TextView) LayoutInflater.from(this).inflate(R.layout.layout_header_1, null);
        wrapper.addHeaderView(header);
        wrapper.setEmptyView(R.layout.layout_empty);
        rv.setAdapter(wrapper);

        addData();
    }

    private void addData() {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add("Item " + i);
        }
        adapter.addData(datas);
        adapter.notifyDataSetChanged();
    }

    private void removeData() {
        adapter.setData(new ArrayList<String>());
        adapter.notifyDataSetChanged();
    }
}
