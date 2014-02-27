package com.organization4242.delmgorb.View;

import com.organization4242.delmgorb.Model.IntegrationMethods;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ilya-murzinov on 22.02.14.
 */
public class MainWindowView extends JPanel{
    //Window
    private JFrame jf = new JFrame("Delmgorb v1.0");

    //Internal panels
    private JPanel equationParametersPanel = new JPanel();
    private JPanel integrationParametersPanel = new JPanel();
    private JPanel interpolationParametersPanel = new JPanel();
    private JPanel buttonsPanel = new JPanel();
    GridBagLayout gridBagLayout = new GridBagLayout();

    //Labels
    private JLabel integrationMethodLabel = new JLabel("Method:");
    private JLabel numLabel = new JLabel("Number of points:");
    private JLabel timeLabel = new JLabel("Time step:");
    private JLabel boundsLabel = new JLabel("Bounds:");
    private JLabel xLabel = new JLabel("delta = ");
    private JLabel yLabel = new JLabel("epsilon = ");
    private JLabel xToLabel = new JLabel("...");
    private JLabel yToLabel = new JLabel("...");
    private JLabel numberOfSpheresLabel = new JLabel("Number of spheres:");
    private JLabel brightnessLabel = new JLabel("Brightness:");

    //UI Controls
    private int parametersNumber = 14;
    private JTextField[] textFields = new JTextField[parametersNumber];
    private JTextField[] boundsTextFields = new JTextField[4];
    private JTextField numberOfPointsTextField;
    private JTextField timeStepTextField;
    private JComboBox<IntegrationMethods> integrationMethodsComboBox;
    private JTextField numberOfSpheresTextField;
    private JTextField brightnessTextField;
    private JProgressBar progressBar;
    private JButton button;

    //Getters
    public JTextField[] getTextFields() {
        return textFields;
    }

    public JTextField[] getBoundsTextFields() {
        return boundsTextFields;
    }

    public JTextField getNumberOfPoints() {
        return numberOfPointsTextField;
    }

    public JTextField getTimeStep() {
        return timeStepTextField;
    }

    public JTextField getNumberOfSpheresTextField() {
        return numberOfSpheresTextField;
    }

    public JButton getButton() {
        return button;
    }

    public JComboBox<IntegrationMethods> getIntegrationMethodsComboBox() {
        return integrationMethodsComboBox;
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public MainWindowView() {
        //Setting window parameters
        jf.setSize(400, 500);
        jf.setResizable(true);
        jf.setMaximumSize(new Dimension(800, 600));
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Setting theme
        try
        {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch(Exception e){
            System.out.println(e);
        }

        init();

        createPanelStructure();

        placeControls();
    }

    /**
     * Initializes all controls in the window
     */
    private void init() {

        for (int i=0; i<parametersNumber; i++) {
            textFields[i] = new JTextField(4);
            textFields[i].setText("0");
        }
        for (int i=0; i<4; i++) {
            boundsTextFields[i] = new JTextField(4);
        }
        boundsTextFields[0].setText("1");
        boundsTextFields[1].setText("2");
        boundsTextFields[2].setText("0.05");
        boundsTextFields[3].setText("1");

        numberOfPointsTextField = new JTextField(4);
        numberOfPointsTextField.setText("4");
        timeStepTextField = new JTextField(4);
        timeStepTextField.setText("0.5");
        numberOfSpheresTextField = new JTextField(4);
        numberOfSpheresTextField.setText("100");
        brightnessTextField = new JTextField(4);
        brightnessTextField.setText("1");

        button = new JButton("Draw!");
        integrationMethodsComboBox = new JComboBox<IntegrationMethods>(IntegrationMethods.values());
        integrationMethodsComboBox.setSelectedItem(IntegrationMethods.DormandPrince8);
        integrationMethodsComboBox.setEditable(false);
        progressBar = new JProgressBar();
    }

    /**
     * Creates main panel, internal panels and places internal panels inside main
     */
    private void createPanelStructure() {
        JPanel mainPanel = new JPanel();
        //Set main panel layout
        mainPanel.setLayout(gridBagLayout);
        GridBagConstraints constraints = new GridBagConstraints();

        //Add groupboxes
        equationParametersPanel.setBorder(BorderFactory.createTitledBorder("Equation parameters"));
        integrationParametersPanel.setBorder(BorderFactory.createTitledBorder("Integration parameters"));
        interpolationParametersPanel.setBorder(BorderFactory.createTitledBorder("Interpolation parameters"));

        //Set GridBagConstraints
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = new Insets(5,5,5,5);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;

        //Apply constraints to all internal panels
        gridBagLayout.setConstraints(equationParametersPanel, constraints);
        constraints.gridy++;
        gridBagLayout.setConstraints(integrationParametersPanel, constraints);
        constraints.gridy++;
        gridBagLayout.setConstraints(interpolationParametersPanel, constraints);
        constraints.gridy++;
        gridBagLayout.setConstraints(buttonsPanel, constraints);

        //Add all internal panels to main panel
        mainPanel.add(equationParametersPanel);
        mainPanel.add(integrationParametersPanel);
        mainPanel.add(interpolationParametersPanel);
        mainPanel.add(buttonsPanel);

        //Add main panel to window
        jf.getContentPane().add(mainPanel);
    }

    /**
     * Places all UI controls to equation parameters panel
     */
    private void placeEquationParameters() {
        equationParametersPanel.setLayout(gridBagLayout);

        //Create GridBagConstraints
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = new Insets(5,5,5,5);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        for (int i = 0; i < parametersNumber/2; i++) {
            constraints.gridx = i;
            gridBagLayout.setConstraints(textFields[i], constraints);
            equationParametersPanel.add(textFields[i]);
        }
        constraints.gridy++;
        for (int i = parametersNumber/2; i < parametersNumber; i++) {
            constraints.gridx = i - parametersNumber/2;
            gridBagLayout.setConstraints(textFields[i], constraints);
            equationParametersPanel.add(textFields[i]);
        }
    }

    /**
     * Places all UI controls to integration parameters panel
     */
    private void placeIntegrationParameters() {
        //Set layout to all internal panels
        integrationParametersPanel.setLayout(gridBagLayout);

        buttonsPanel.setLayout(gridBagLayout);

        //Create GridBagConstraints
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = new Insets(5,5,5,5);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        //Set constraints to all UI controls
        constraints.gridx = 0;
        constraints.gridy = 0;
        gridBagLayout.setConstraints(boundsLabel, constraints);
        constraints.gridy++;
        gridBagLayout.setConstraints(xLabel, constraints);
        constraints.gridx++;
        gridBagLayout.setConstraints(boundsTextFields[0], constraints);
        constraints.gridx++;
        gridBagLayout.setConstraints(yToLabel, constraints);
        constraints.gridx++;
        gridBagLayout.setConstraints(boundsTextFields[1], constraints);

        constraints.gridx = 0;
        constraints.gridy++;
        gridBagLayout.setConstraints(yLabel, constraints);
        constraints.gridx++;
        gridBagLayout.setConstraints(boundsTextFields[2], constraints);
        constraints.gridx++;
        gridBagLayout.setConstraints(xToLabel, constraints);
        constraints.gridx++;
        gridBagLayout.setConstraints(boundsTextFields[3], constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        gridBagLayout.setConstraints(numLabel, constraints);
        constraints.gridx++;
        gridBagLayout.setConstraints(numberOfPointsTextField, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        gridBagLayout.setConstraints(timeLabel, constraints);
        constraints.gridx++;
        gridBagLayout.setConstraints(timeStepTextField, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        gridBagLayout.setConstraints(integrationMethodLabel, constraints);
        constraints.gridx++;
        constraints.gridwidth = 4;
        gridBagLayout.setConstraints(integrationMethodsComboBox, constraints);

        integrationParametersPanel.add(xLabel);
        integrationParametersPanel.add(yLabel);
        integrationParametersPanel.add(xToLabel);
        integrationParametersPanel.add(yToLabel);
        integrationParametersPanel.add(boundsLabel);
        for (int i = 0; i < 4; i++) {
            integrationParametersPanel.add(boundsTextFields[i]);
        }
        integrationParametersPanel.add(numLabel);
        integrationParametersPanel.add(numberOfPointsTextField);
        integrationParametersPanel.add(timeLabel);
        integrationParametersPanel.add(timeStepTextField);
        integrationParametersPanel.add(integrationMethodLabel);
        integrationParametersPanel.add(integrationMethodsComboBox);
    }

    /**
     * Places all UI controls to interpolation parameters panel
     */
    private void placeInterpolationParameters() {
        interpolationParametersPanel.setLayout(gridBagLayout);

        //Create GridBagConstraints
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = new Insets(5,5,5,5);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        constraints.gridy++;
        constraints.gridx = 0;
        gridBagLayout.setConstraints(numberOfSpheresLabel, constraints);
        constraints.gridx++;
        gridBagLayout.setConstraints(numberOfSpheresTextField, constraints);

        interpolationParametersPanel.add(numberOfSpheresLabel);
        interpolationParametersPanel.add(numberOfSpheresTextField);
    }

    private void placeButtonControls() {
        buttonsPanel.setLayout(gridBagLayout);

        //Create GridBagConstraints
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.SOUTHEAST;
        constraints.insets = new Insets(5,5,5,5);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.NONE;

        gridBagLayout.setConstraints(button, constraints);

        buttonsPanel.add(button);
    }

    private void placeControls() {
        placeEquationParameters();
        placeIntegrationParameters();
        placeInterpolationParameters();
        placeButtonControls();
    }

    public void display() {
        jf.setVisible(true);
    }
}
