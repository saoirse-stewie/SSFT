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
        inputmap.put("amount" , amount);
        inputmap.put("rate" , interest);
        inputmap.put("months" , period);



    }




}
