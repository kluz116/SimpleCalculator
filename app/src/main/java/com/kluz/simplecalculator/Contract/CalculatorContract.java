package com.kluz.simplecalculator.Contract;

import com.kluz.simplecalculator.Model.Calculator;

import java.util.HashMap;
import java.util.List;

public interface CalculatorContract {

    interface CalculatorView{
        void getDataResults(List<Calculator> calculatorList);
        void  getError(String message);
        void setMessage(String response);
        void showDialog();
        void hideDialog();

    }

    interface CalculatorPresenter{
        void getCalculatorData(HashMap data, String numberone, String numbertwo);
        HashMap getExpression(String Operations,String num_one,String num_two);
    }

}
