package PayrollCalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class PayrollCalculator implements ActionListener {

    private JFrame frame;
    private JPanel panel;
    private JLabel nameLabel, hoursLabel, wageLabel, provinceLabel, resultLabel;
    private JTextField nameField, hoursField, wageField, provinceField;
    private JButton calculateButton;
    private JTextArea outputArea;

    private static final Map<String, String> PROVINCE_MAP = new HashMap<>(); // map to convert province name to the correct format

    static {
        PROVINCE_MAP.put("AB", "Alberta");
        PROVINCE_MAP.put("BC", "British Columbia");
        PROVINCE_MAP.put("MB", "Manitoba");
        PROVINCE_MAP.put("NB", "New Brunswick");
        PROVINCE_MAP.put("NL", "Newfoundland and Labrador");
        PROVINCE_MAP.put("NT", "Northwest Territories");
        PROVINCE_MAP.put("NS", "Nova Scotia");
        PROVINCE_MAP.put("NU", "Nunavut");
        PROVINCE_MAP.put("ON", "Ontario");
        PROVINCE_MAP.put("PE", "Prince Edward Island");
        PROVINCE_MAP.put("QC", "Quebec");
        PROVINCE_MAP.put("SK", "Saskatchewan");
        PROVINCE_MAP.put("YT", "Yukon");
    }

    public PayrollCalculator() {
        frame = new JFrame("Payroll Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 350);
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;

        nameLabel = new JLabel("Employee name:");
        c.gridx = 0;
        c.gridy = 0;
        panel.add(nameLabel, c);

        nameField = new JTextField(15); // reduce the number of columns to 15
        c.gridx = 1;
        c.gridy = 0;
        panel.add(nameField, c);

        hoursLabel = new JLabel("Hours worked:");
        c.gridx = 0;
        c.gridy = 1;
        panel.add(hoursLabel, c);

        hoursField = new JTextField(5); // reduce the number of columns to 5
        c.gridx = 1;
        c.gridy = 1;
        panel.add(hoursField, c);

        wageLabel = new JLabel("Hourly wage:");
        c.gridx = 0;
        c.gridy = 2;
        panel.add(wageLabel, c);

        wageField = new JTextField(5); // reduce the number of columns to 5
        c.gridx = 1;
        c.gridy = 2;
        panel.add(wageField, c);

        provinceLabel = new JLabel("Province of employment:");
        c.gridx = 0;
        c.gridy = 3;
        panel.add(provinceLabel, c);

        provinceField = new JTextField(15); // reduce the number of columns to 15
        c.gridx = 1;
        c.gridy = 3;
        panel.add(provinceField, c);

        calculateButton = new JButton("Calculate");
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2; // set the width of the button to span 2 columns
        panel.add(calculateButton, c);
        calculateButton.addActionListener(this);

        resultLabel = new JLabel("");
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2; // set the width of the label to span 2 columns
        panel.add(resultLabel, c);

        // Create the output area
        outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        c.gridx = 2; // set the x coordinate to 2 to place the output area to the right of the other components
        c.gridy = 0;
        c.gridwidth = 1; // reset the width to 1
        c.gridheight = 6; // set the height to span all 6 rows
        c.fill = GridBagConstraints.BOTH;
        panel.add(scrollPane, c);
        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new PayrollCalculator();
    }

    public void actionPerformed(ActionEvent e) {
        // Calculate the gross pay, taxes, and net pay
        String name = nameField.getText();
        double hours = Double.parseDouble(hoursField.getText());
        double wage = Double.parseDouble(wageField.getText());
        String provinceCode = provinceField.getText().toUpperCase();
        String province = PROVINCE_MAP.getOrDefault(provinceCode, "Invalid province code");

        double grossPay = hours * wage;
        double federalTax = grossPay * 0.15;
        double provincialTax = 0;
        if (provinceCode.equals("AB")) {
            provincialTax = grossPay * 0.1;
        } else if (provinceCode.equals("BC")) {
            provincialTax = grossPay * 0.12;
        } else if (provinceCode.equals("SK")) {
            provincialTax = grossPay * 0.11;
        } else if (provinceCode.equals("MB")) {
            provincialTax = grossPay * 0.1;
        } else if (provinceCode.equals("ON")) {
            provincialTax = grossPay * 0.13;
        } else if (provinceCode.equals("QC")) {
            provincialTax = grossPay * 0.15;
        } else if (provinceCode.equals("NB") || provinceCode.equals("NL") || provinceCode.equals("NS")
                || provinceCode.equals("PE")) {
            provincialTax = grossPay * 0.14;
        } else if (provinceCode.equals("NT") || provinceCode.equals("NU") || provinceCode.equals("YT")) {
            provincialTax = grossPay * 0.12;
        } else {
            resultLabel.setText("Invalid province code");
            return;
        }

        double netPay = grossPay - federalTax - provincialTax;

        // Display the results in the JTextArea
        outputArea.setText(String.format("Payroll Summary for %s in %s:\n\n"
                + "Gross Pay: $%.2f\n"
                + "Federal Tax: $%.2f\n"
                + "%s Tax: $%.2f\n"
                + "Net Pay: $%.2f",
                name, province, grossPay, federalTax, province, provincialTax, netPay));

    }
}
