package com.kluz.simplecalculator;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kluz.simplecalculator.Adapter.CalculatorAdapter;
import com.kluz.simplecalculator.Contract.CalculatorContract;
import com.kluz.simplecalculator.Model.Calculator;
import com.kluz.simplecalculator.Presenter.CalculatorPresenter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements CalculatorContract.CalculatorView, AdapterView.OnItemSelectedListener {



    //Views variable
    EditText number_one,number_two;
    private Spinner spinner1;
    Button calculate;
    ProgressDialog progressDialog;

    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;

    String Operations = "";
    CalculatorPresenter presenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Binding views
        recyclerView = findViewById(R.id.recycleview);
        number_one = findViewById(R.id.number_one);
        number_two = findViewById(R.id.number_two);
        calculate = findViewById(R.id.cal);
        spinner1 = findViewById(R.id.spinner1);

        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        presenter = new CalculatorPresenter(this,this);

        recyclerView = findViewById(R.id.recycleview);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num_one = number_one.getText().toString();
                String num_two = number_two.getText().toString();

                if(num_one.isEmpty() && num_two.isEmpty()){
                    Toast.makeText(getApplicationContext(), " Please select numbers.", Toast.LENGTH_SHORT).show();
                }else{
                    presenter.getCalculatorData(presenter.getExpression(Operations,num_one,num_two),num_one,num_two);
                }


            }
        });
        spinner1.setOnItemSelectedListener(this);



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
    public void getDataResults(List<Calculator> calculatorList) {
        CalculatorAdapter adapter = new CalculatorAdapter(this, calculatorList);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void getError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void setMessage(String response) {
        progressDialog.setMessage(response);
    }

    @Override
    public void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    @Override
    public void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Operations = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
