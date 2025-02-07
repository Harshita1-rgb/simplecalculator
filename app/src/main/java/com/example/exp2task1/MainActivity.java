package com.example.exp2task1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText number1, number2;
    private TextView result;
    private Spinner operators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        number1 = findViewById(R.id.number1);
        number2 = findViewById(R.id.number2);
        Button equal = findViewById(R.id.equal);
        result = findViewById(R.id.result);
        operators = findViewById(R.id.operators);

        ArrayList<String> operatorList = new ArrayList<>();
        operatorList.add("+");
        operatorList.add("-");
        operatorList.add("*");
        operatorList.add("/");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, operatorList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        operators.setAdapter(adapter);

        equal.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                hideKeyboard(MainActivity.this);

                String num1 = number1.getText().toString();
                String num2 = number2.getText().toString();
                String operator = operators.getSelectedItem().toString();

                if (TextUtils.isEmpty(num1) || TextUtils.isEmpty(num2)) {
                    Toast.makeText(MainActivity.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                    if (TextUtils.isEmpty(num1)) {
                        number1.setError("Field cannot be empty");
                    }
                    if (TextUtils.isEmpty(num2)) {
                        number2.setError("Field cannot be empty");
                    }
                    return;
                }

                int num1Int;
                int num2Int;

                try {
                    num1Int = Integer.parseInt(num1);
                    num2Int = Integer.parseInt(num2);
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Please enter valid integers", Toast.LENGTH_SHORT).show();
                    return;
                }

                int resultInt;
                try {
                    switch (operator) {
                        case "+":
                            resultInt = num1Int + num2Int;
                            break;
                        case "-":
                            resultInt = num1Int - num2Int;
                            break;
                        case "*":
                            resultInt = num1Int * num2Int;
                            break;
                        case "/":
                            if (num2Int == 0) {
                                Toast.makeText(MainActivity.this, "Cannot divide by zero", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            resultInt = num1Int / num2Int;
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid operator");
                    }
                    result.setText(String.valueOf(resultInt));
                } catch (ArithmeticException | IllegalArgumentException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void hideKeyboard(Context context) {
        View view = ((Activity) context).getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}
