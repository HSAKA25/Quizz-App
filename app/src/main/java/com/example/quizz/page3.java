package com.example.quizz;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class page3 extends AppCompatActivity{
    GridView gridView;
    RelativeLayout relativeLayout;

    String[] items = {"Python","C","Cpp","java","kotlin","csharp"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_page3);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        gridView = findViewById(R.id.gv);
        relativeLayout = findViewById(R.id.main);

        gridView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return items.length;
            }

            @Override
            public Object getItem(int i) {
                return items[i];
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View convertView, ViewGroup parent) {
                View view = getLayoutInflater().inflate(R.layout.page2xmlfile, parent, false);

                TextView textView = view.findViewById(R.id.textView);
                textView.setText(items[i]);

                return view;
            }
        });

        gridView.setOnItemClickListener((adapterView, view, position, id) -> {
            String selectedItem = items[position];
            Toast.makeText(this, "" + items[position], Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, "Selected: " + items[position], Toast.LENGTH_SHORT).show();

            // Optional: Change background to a fixed color
            //relativeLayout.setBackgroundColor(Color.LTGRAY);

            if(selectedItem.equals("Python")) {
                Intent intent = new Intent(getApplicationContext(),Python.class);
                startActivity(intent);
            }
            if(selectedItem.equals("C")) {
                Intent intent = new Intent(getApplicationContext(),C.class);
                startActivity(intent);
            }
            if(selectedItem.equals("Cpp")) {
                Intent intent = new Intent(getApplicationContext(),Cpp.class);
                startActivity(intent);
            }
            if(selectedItem.equals("java")) {
                Intent intent = new Intent(getApplicationContext(),java.class);
                startActivity(intent);
            }
            if(selectedItem.equals("kotlin")) {
                Intent intent = new Intent(getApplicationContext(),kotlin.class);
                startActivity(intent);
            }
            if(selectedItem.equals("csharp")) {
                Intent intent = new Intent(getApplicationContext(),csharp.class);
                startActivity(intent);
            }
        });
    }
}
