package com.asd.task2;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerSource, spinnerDestination;
    private EditText inputValue;
    private TextView resultText;
    private Button convertButton;

    private Map<String, Double> lengthConversions;
    private Map<String, Double> weightConversions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        spinnerSource = findViewById(R.id.spinner_source);
        spinnerDestination = findViewById(R.id.spinner_destination);
        inputValue = findViewById(R.id.input_value);
        resultText = findViewById(R.id.result_text);
        convertButton = findViewById(R.id.convert_button);

        // Populate conversion categories
        String[] units = {"Inch", "Foot", "Yard", "Mile", "Pound", "Ounce", "Ton", "Celsius", "Fahrenheit", "Kelvin"};

        // Set up Spinners with Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSource.setAdapter(adapter);
        spinnerDestination.setAdapter(adapter);

        // Define conversion factors
        initializeConversions();

        // Button Click Listener
        convertButton.setOnClickListener(v -> convertUnits());
    }

    private void initializeConversions() {
        lengthConversions = new HashMap<>();
        lengthConversions.put("Inch", 2.54);
        lengthConversions.put("Foot", 30.48);
        lengthConversions.put("Yard", 91.44);
        lengthConversions.put("Mile", 1609.34);

        weightConversions = new HashMap<>();
        weightConversions.put("Pound", 0.453592);
        weightConversions.put("Ounce", 28.3495);
        weightConversions.put("Ton", 907.185);
    }

    private void convertUnits() {
        String sourceUnit = spinnerSource.getSelectedItem().toString();
        String destinationUnit = spinnerDestination.getSelectedItem().toString();
        String inputText = inputValue.getText().toString();

        if (inputText.isEmpty()) {
            resultText.setText("Please enter a value.");
            return;
        }

        double inputValue = Double.parseDouble(inputText);
        double convertedValue = 0.0;

        // Length Conversions
        if (lengthConversions.containsKey(sourceUnit) && lengthConversions.containsKey(destinationUnit)) {
            convertedValue = (inputValue * lengthConversions.get(sourceUnit)) / lengthConversions.get(destinationUnit);
        }
        // Weight Conversions
        else if (weightConversions.containsKey(sourceUnit) && weightConversions.containsKey(destinationUnit)) {
            convertedValue = (inputValue * weightConversions.get(sourceUnit)) / weightConversions.get(destinationUnit);
        }
        // Temperature Conversions
        else {
            convertedValue = convertTemperature(sourceUnit, destinationUnit, inputValue);
        }

        // Display Result
        resultText.setText("Converted Value: " + convertedValue);
    }

    private double convertTemperature(String source, String destination, double value) {
        if (source.equals("Celsius") && destination.equals("Fahrenheit")) {
            return (value * 1.8) + 32;
        } else if (source.equals("Fahrenheit") && destination.equals("Celsius")) {
            return (value - 32) / 1.8;
        } else if (source.equals("Celsius") && destination.equals("Kelvin")) {
            return value + 273.15;
        } else if (source.equals("Kelvin") && destination.equals("Celsius")) {
            return value - 273.15;
        } else if (source.equals("Fahrenheit") && destination.equals("Kelvin")) {
            return (value - 32) * 5 / 9 + 273.15;
        } else if (source.equals("Kelvin") && destination.equals("Fahrenheit")) {
            return (value - 273.15) * 9 / 5 + 32;
        }
        return value; // Default return in case of identical units
    }
}
