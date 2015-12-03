package com.example.json_to_server;

import java.util.HashMap;

/**
 * Created by saoirse on 30/11/2015.
 */
public class Loan_inputs {

    public HashMap<String, String> getInputmap() {
        return inputmap;
    }

    public void setInputmap(HashMap<String, String> inputmap) {
        this.inputmap = inputmap;
    }

    public HashMap<String, String> inputmap;


    public Loan_inputs(String amount, String interest, String period)
    {
        inputmap  = new HashMap<String, String>();
        inputmap.put("number1" , amount);
        inputmap.put("number2" , interest);
        inputmap.put("number3" , period);



    }




}
