package com.example.mycalculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView display; // Displaying the result and input
    private String currentInput = "";
    private String fullExpression = "";
    private String operator = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.textViewResult); // Connects the TextView to display results

        setupNumberButtons();
        setupOperatorButtons();
    }

    private void setupNumberButtons() {
        int[] numberButtonIds = {
                R.id.button_0, R.id.button_1, R.id.button_2,
                R.id.button_3, R.id.button_4, R.id.button_5,
                R.id.button_6, R.id.button_7, R.id.button_8,
                R.id.button_9
        };

        for (int id : numberButtonIds) {
            findViewById(id).setOnClickListener(v -> {
                Button button = (Button) v;
                currentInput += button.getText().toString();
                fullExpression += button.getText().toString();
                display.setText(fullExpression);
            });
        }
    }

    private void setupOperatorButtons() {
        findViewById(R.id.button_Plus).setOnClickListener(v -> setOperator("+"));
        findViewById(R.id.button_Subtraction).setOnClickListener(v -> setOperator("-"));
        findViewById(R.id.button_multiplication).setOnClickListener(v -> setOperator("X"));
        findViewById(R.id.button_Division).setOnClickListener(v -> setOperator(":"));

        findViewById(R.id.button_Equal).setOnClickListener(v -> calculateResult());
        findViewById(R.id.button_AC).setOnClickListener(v -> clear());
    }

    private void setOperator(String op) {
        if (!currentInput.isEmpty()) {
            fullExpression += " " + op + " ";
            operator = op;
            currentInput = "";
            display.setText(fullExpression);
        }
    }

    private void calculateResult() {
        if (!fullExpression.isEmpty() && !currentInput.isEmpty()) {
            String[] parts = fullExpression.split(" ");
            if (parts.length >= 3) {
                double num1 = Double.parseDouble(parts[0]);
                double num2 = Double.parseDouble(currentInput);
                double result = 0;

                switch (operator) {
                    case "+":
                        result = num1 + num2;
                        break;
                    case "-":
                        result = num1 - num2;
                        break;
                    case "X":
                        result = num1 * num2;
                        break;
                    case ":":
                        if (num2 != 0) {
                            result = num1 / num2;
                        } else {
                            display.setText("Error");
                            return;
                        }
                        break;
                }

                // Checking if the result is an integer
                if (result == (int) result) {
                    display.setText(String.valueOf((int) result));
                } else {
                    display.setText(String.valueOf(result));
                }

                // Reset after displaying a result
                fullExpression = String.valueOf(result);
                currentInput = "";
                operator = "";
            }
        }
    }

    private void clear() {
        currentInput = "";
        fullExpression = "";
        operator = "";
        display.setText("");
    }
}
