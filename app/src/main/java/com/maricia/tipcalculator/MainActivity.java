package com.maricia.tipcalculator;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.MessageFormat;

import java.util.HashMap;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "";

    private EditText getBillAmount; //enter money
    private TextView t10amount; //tip 10%
    private TextView t15amount; // tip 15%
    private TextView t20amount; // tip 20%
    private TextView to10amount; //total 10%
    private TextView to15amount; //total 15%
    private TextView to20amount; //total 20%
    private SeekBar seekBar; //seekba23.00r value
    private TextView ctip; //custom tip
    private TextView ctotal; // custom total
    private TextView cuamount; //stoppedon
    private EditText howManyPeople; //how many people
    private TextView splitamount; //amount each should pay
    private TextView disclaimer; //warning about bill splitter text

    private Map<TextView, Double> amounts = new HashMap<TextView, Double>(); //make HashMap to hold results

    private double billAmount;  //holds money
    private double splitTotal;  //total split bill

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GetViewFields();
        GetTextListener();

    }//end onCreate

    private void GetViewFields()
    {
        //gets all the view fields objects by id
        getBillAmount =  this.findViewById(R.id.getBillAmount);
        t10amount = this.findViewById(R.id.t10amount);
        t15amount = this.findViewById(R.id.t15amount);
        t20amount = this.findViewById(R.id.t20amount);
        to10amount = this.findViewById(R.id.to10amount);
        to15amount = this.findViewById(R.id.to15amount);
        to20amount = this.findViewById((R.id.to20amount));
        ctip = this.findViewById(R.id.ctip);
        ctotal = this.findViewById(R.id.ctotal);
        cuamount = this.findViewById(R.id.cuamount);
        seekBar = (SeekBar) findViewById(R.id.seekBar);//set up seekbar
        howManyPeople = this.findViewById(R.id.howManyPeople);
        splitamount = this.findViewById(R.id.splitamount);
        disclaimer = this.findViewById(R.id.disclaimer);
        disclaimer.setText("Bill splitter is only active with the custom slider");

        howManyPeople.setEnabled(false);
        seekBar.setEnabled(false);



    }//end GetViewFields

    private void GetTextListener() {
        //adds a textListener to the bill amount field
        getBillAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    //gets number that is entered into field makes it a double
                    billAmount = Double.parseDouble(getBillAmount.getText().toString());
                    DoTips(billAmount);
                    SetSeekBar();
                    GetPeopleListener(billAmount);

                } catch (NumberFormatException e) {
                    e.printStackTrace();

                    ClearFields();
                }
            }
        });//end addTextChangedListener
    }//end GetTextListener

    private void DoTips(double billAmount)
    {
        //enables seekbar, do tip math and save it display results enable bill splitter
        seekBar.setEnabled(true);
        TenPercent(billAmount, amounts);
        FifteenPercent(billAmount, amounts);
        TwentyPercent(billAmount, amounts);
        DisplayAmounts2(amounts);

        howManyPeople.setEnabled(true);
        //   GetPeopleListener(billAmount);
    }//end DoTips

    private void GetPeopleListener(final double billAmount) {
        howManyPeople.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Toast.makeText(getApplicationContext(),"Bill splitter is only active with the custom slider", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {
                double splitPeople = 0;

                try {
                    splitPeople = Double.parseDouble(howManyPeople.getText().toString());
                    splitTotal = billAmount / splitPeople;
                    splitamount.setText(MessageFormat.format("${0}{1}", String.format("%.2f", splitTotal )," each"));
                    SetSeekBar();
                    DoTips(splitTotal);

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    splitamount.setText("0.00");
                    DoTips(billAmount);
                    splitTotal = 0;
                    //ClearFields();
                }
            }//end afterTextChanged
        });
    }

    private Map<TextView, Double> TwentyPercent(double billAmount, Map<TextView, Double> amounts) {
        double tp20 = billAmount * .20;
        amounts.put(t20amount,tp20);
        double tt20 = tp20 + billAmount;
        amounts.put(to20amount,tt20);
        return amounts;
    }

    private Map<TextView, Double> FifteenPercent(double billAmount, Map<TextView, Double> amounts) {
        double tp15 = billAmount * .15;
        amounts.put(t15amount,tp15);
        double tt15 = tp15 + billAmount;
        amounts.put(to15amount,tt15);
        return amounts;
    }

    private Map<TextView, Double> TenPercent(double billAmount, Map<TextView, Double> amounts) {
        double tp10 = billAmount * .10;
        amounts.put(t10amount, tp10);
        double tt10 = tp10 + billAmount;
        amounts.put(to10amount,tt10);
        return amounts;
    }


    private void DisplayAmounts2(Map<TextView, Double> amounts) {

        for(Map.Entry<TextView,Double> entry : amounts.entrySet()){
            entry.getKey().setText(MessageFormat.format("${0}",String.format("%.2f", entry.getValue()) ));
        }

        /*
        //lamdba I am not sure why this will not work, guess I am using wrong version of java maybe
        amounts.forEach((TextView k, Double v) ->k.setText(MessageFormat.format("${0}", String.format("%.2f", v))));
        */
    }//end DisplayAmounts2



    private void SetSeekBar() {


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            double changes = 0; //for seekbar how much it changes
            int stoppedon = 0;  //display of change amount seekbar
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                changes = (double)progress;
                stoppedon = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                changes = changes / 100;
                FindCustom(changes, splitTotal != 0 ? splitTotal : billAmount, stoppedon);


            }//end onStopTrackingTouch
        });//end setOnSeekBarChangeListener
    }//end SetSeekBar

    private void FindCustom(double changes, double billAmount, int stoppedon)
    {
        double totalmoney = changes*billAmount ;//*
        double  grandtotal = totalmoney + billAmount;
        SetCustomText(totalmoney, grandtotal, stoppedon);
    }
    private void SetCustomText(double totalmoney, double grandtotal, int stoppedon)
    {
        ctip.setText(MessageFormat.format("${0}",String.format("%.2f",totalmoney)));
        ctotal.setText(MessageFormat.format("${0}",String.format("%.2f", grandtotal)));
        cuamount.setText(MessageFormat.format("{0}%",String.format("%d", stoppedon)));
    }

    private void ClearFields()
    {
        seekBar.setEnabled(false);
        t10amount.setText("0.00");
        to10amount.setText("0.00");
        t15amount.setText("0.00");
        to15amount.setText("0.00");
        t20amount.setText("0.00");
        to20amount.setText("0.00");
        ctip.setText("0.00");
        ctotal.setText("0.00");
        cuamount.setText("%");
        howManyPeople.setEnabled(false);
        splitamount.setText("0.00");
        splitTotal = 0;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_about)
        {
            //TODO do something when clicked
            Intent intent =  new Intent (this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
