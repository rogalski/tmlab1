package lab.tm.rogalski.lab1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    String lastOperation = "";
    float accumulator = 0.0f;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setOnClickCallbacksForNumbers();
        setOnClickCallbacksForActions();
    }

    private void setOnClickCallbacksForActions() {
        int[] identifiers = {R.id.BTN_PLUS, R.id.BTN_MINUS, R.id.BTN_MULT, R.id.BTN_DIV, R.id.BTN_EQ};
        setOnClickListenersForEach(identifiers, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button source = (Button) v;
                if (lastOperation.isEmpty() && source.getId() == R.id.BTN_EQ) {
                    return;
                }
                calcAccumulator();
                clearDisplay();
                if (source.getId() == R.id.BTN_EQ) {
                    updateDisplay(String.valueOf(accumulator));
                }
                lastOperation = source.getText().toString();
            }
        });
    }

    private void setOnClickCallbacksForNumbers() {
        int[] identifiers = {R.id.BTN_0, R.id.BTN_1, R.id.BTN_2,
                R.id.BTN_3, R.id.BTN_4, R.id.BTN_5, R.id.BTN_6,
                R.id.BTN_7, R.id.BTN_8, R.id.BTN_9, R.id.BTN_DOT
        };
        setOnClickListenersForEach(identifiers, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button source = (Button) v;
                if (lastOperation.equals("=")) {
                    clearCalc();
                }
                appendToDisplay(source.getText().toString());
            }
        });
        Button clearButton = (Button) findViewById(R.id.BTN_C);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCalc();
            }
        });
    }

    private void setOnClickListenersForEach(int[] identifiers, View.OnClickListener listener) {
        for (int id : identifiers) {
            Button button = (Button) findViewById(id);
            button.setOnClickListener(listener);
        }
    }

    private float getDisplayedNumber() {
        TextView display = (TextView) findViewById(R.id.OUTPUT);
        try {
            return Float.parseFloat(display.getText().toString());
        } catch (NumberFormatException e) {
            return 0.0f;
        }
    }

    private void clearDisplay() {
        TextView display = (TextView) findViewById(R.id.OUTPUT);
        display.setText("");
    }

    private void appendToDisplay(String s) {
        TextView display = (TextView) findViewById(R.id.OUTPUT);
        display.append(s);
    }

    private void updateDisplay(String s) {
        TextView display = (TextView) findViewById(R.id.OUTPUT);
        display.setText(s);
    }

    private void calcAccumulator() {
        switch (lastOperation) {
            case "+":
                accumulator = accumulator + getDisplayedNumber();
                break;
            case "-":
                accumulator = accumulator - getDisplayedNumber();
                break;
            case "*":
                accumulator = accumulator * getDisplayedNumber();
                break;
            case "/":
                accumulator = accumulator / getDisplayedNumber();
                break;
            default:
                accumulator = getDisplayedNumber();
        }
    }

    private void clearCalc() {
        clearDisplay();
        accumulator = 0.0f;
        lastOperation = "";
    }
}
