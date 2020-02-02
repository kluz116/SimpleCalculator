package com.kluz.simplecalculator.Presenter;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kluz.simplecalculator.Contract.CalculatorContract;
import com.kluz.simplecalculator.Model.Calculator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CalculatorPresenter implements CalculatorContract.CalculatorPresenter {

    private CalculatorContract.CalculatorView view;
    private Context context;
    List<Calculator> calculatorList = new ArrayList<>();

    public CalculatorPresenter(CalculatorContract.CalculatorView view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void getCalculatorData( HashMap data, final String numberone, final String numbertwo) {
            String url = "http://api.mathjs.org/v4/";
            view.setMessage("Please wait..");
            view.showDialog();
            RequestQueue requstQueue = Volley.newRequestQueue(context);

            JsonObjectRequest jsonobj = new JsonObjectRequest(Request.Method.POST, url,new JSONObject(data),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                int result  = response.getInt("result");
                                int operation_response = 0;


                                Calculator a;
                                Random rand = new Random();
                                float rand_int1 = Math.round(rand.nextFloat());
                                if(rand_int1 == 1){
                                    float rand_int2 = rand.nextFloat();
                                    operation_response = (int) Math.ceil(rand_int2 * 4000);
                                    calculatorList.add(new Calculator(numberone,numbertwo,operation_response,result,"No"));

                                }else{
                                    operation_response = result;
                                    calculatorList.add(new Calculator(numberone,numbertwo,operation_response,result,"Yes"));

                                }

                                view.getDataResults(calculatorList);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            view.hideDialog();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            view.getError("Error : "+error);
                            view.hideDialog();
                        }
                    }
            ){
                //here I want to post data to sever
            };
            requstQueue.add(jsonobj);

        }

    @Override
    public HashMap getExpression(String Operations,String num_one,String num_two) {
        //Operational signs variable
        String operation_add = "+";
        String operation_sub = "-";
        String operation_div = "/";
        String operation_mul = "*";

        //Operations Variables
        String Add = "Add";
        String Subtract = "Subtract";
        String Multply ="Multply";
        String expr;

        //Generate expression from here based on which operation
        if(Operations.equals(Add)){
            expr = num_one+ " " + operation_add + " "+ num_two;
        }else if(Operations.equals(Subtract)){
            expr = num_one+ " " + operation_sub + " "+ num_two;
        }else if(Operations.equals(Multply)){
            expr = num_one+ " " + operation_mul + " "+ num_two;
        }else {
            expr = num_one+ " " + operation_div + " "+ num_two;
        }


        HashMap data = new HashMap();
        data.put("expr",expr);
        return data;
    }

}
