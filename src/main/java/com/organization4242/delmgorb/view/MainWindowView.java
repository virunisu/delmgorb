package com.organization4242.delmgorb.view;

import com.organization4242.delmgorb.controller.MainWindowController;
import com.organization4242.delmgorb.model.Angle;
import com.organization4242.delmgorb.model.IntegrationMethods;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents main window with all UI controls.
 * It uses 5 panels to place UI controls ans then place all panel inside the main panel.
 * Also it adds focus listener in order to select all text in text fields when it gains focus.
 *
 * All controls are empty in the resulting window, use
 * {@link com.organization4242.delmgorb.controller.MainWindowController} to set all values.
 * 
 * @author Murzinov Ilya
 */
public class MainWindowView extends AbstractView {
    private static final int NUMBER_OF_EQUATION_PARAMETERS = 14;
    private static final int HEIGHT = 650;
    private static final int WIDTH = 400;
    private static final int TEXT_FIELD_MIN_WIDTH = 4;
    private static final Insets DEFAULT_INSETS = new Insets(5,5,5,5);
    private static final Insets EMPTY_INSETS = new Insets(0,0,0,0);

    private JFrame frame = new JFrame();
    //Menu
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menu = new JMenu("File");
    private JMenuItem importDataMenuItem = new JMenuItem("Import Data and Config");
    private JMenuItem exportDataMenuItem = new JMenuItem("Export Data and Config");

    //Internal panels
    private JPanel equationParametersPanel = new JPanel();
    private JPanel integrationParametersPanel = new JPanel();
    private JPanel interpolationParametersPanel = new JPanel();
    private JPanel initialConditionsPanel = new JPanel();
    private JPanel buttonsPanel = new JPanel();
    private GridBagLayout gridBagLayout = new GridBagLayout();

    //Labels
    private JLabel integrationMethodLabel = new JLabel("Method:");
    private JLabel numLabel = new JLabel("Number of points:");
    private JLabel timeLabel = new JLabel("Time step:");
    private JLabel boundsLabel = new JLabel("Bounds:");
    private JLabel xLabel = new JLabel("Delta:");
    private JLabel yLabel = new JLabel("Epsilon:");
    private JLabel xToLabel = new JLabel("to");
    private JLabel yToLabel = new JLabel("to");
    private JLabel numberOfSpheresLabel = new JLabel("Number of spheres:");
    private JLabel angleLabel = new JLabel("Angle:");
    private JLabel timePeriodLabel = new JLabel("Time period:");
    private JLabel phiLabel = new JLabel(Angle.PHI + "(0):");
    private JLabel psiLabel = new JLabel(Angle.PSI + "(0):");
    private JLabel thetaLabel = new JLabel(Angle.THETA + "(0):");

    //UI Controls
    private JTextField[] textFields = new JTextField[NUMBER_OF_EQUATION_PARAMETERS];
    private JTextField xMinTextField = new JTextField(TEXT_FIELD_MIN_WIDTH);
    private JTextField xMaxTextField = new JTextField(TEXT_FIELD_MIN_WIDTH);
    private JTextField yMinTextField = new JTextField(TEXT_FIELD_MIN_WIDTH);
    private JTextField yMaxTextField = new JTextField(TEXT_FIELD_MIN_WIDTH);
    private JTextField numberOfPointsTextField;
    private JTextField timeStepTextField;
    private JTextField timePeriodTextField;
    private JComboBox<IntegrationMethods> integrationMethodsComboBox;
    private JComboBox<Angle> angleComboBox;
    private JTextField numberOfSpheresTextField;
    private JTextField phiTextField;
    private JTextField psiTextField;
    private JTextField thetaTextField;
    private JButton drawButton;
    private JButton resetButton;

    private Logger logger = Logger.getLogger("Delmgorb.logger");

    //Accessors
    public JFrame getFrame() {
        return frame;
    }

    public JMenuItem getImportDataMenuItem() {
        return importDataMenuItem;
    }

    public JMenuItem getExportDataMenuItem() {
        return exportDataMenuItem;
    }

    public JButton getDrawButton() {
        return drawButton;
    }

    public JButton getResetButton() {
        return resetButton;
    }

    public JTextField getxMinTextField() {
        return xMinTextField;
    }

    public JTextField[] getTextFields() {
        return textFields;
    }

    public JTextField getxMaxTextField() {
        return xMaxTextField;
    }

    public JTextField getyMinTextField() {
        return yMinTextField;
    }

    public JTextField getyMaxTextField() {
        return yMaxTextField;
    }

    public JTextField getNumberOfPointsTextField() {
        return numberOfPointsTextField;
    }

    public JComboBox<IntegrationMethods> getIntegrationMethodsComboBox() {
        return integrationMethodsComboBox;
    }

    public JTextField getTimePeriodTextField() {
        return timePeriodTextField;
    }

    public JTextField getTimeStepTextField() {
        return timeStepTextField;
    }

    public JComboBox<Angle> getAngleComboBox() {
        return angleComboBox;
    }

    public JTextField getPhiTextField() {
        return phiTextField;
    }

    public JTextField getPsiTextField() {
        return psiTextField;
    }

    public JTextField getThetaTextField() {
        return thetaTextField;
    }

    /**
    * Initializes main window and creates its structure without showing it.
    */
    public MainWindowView() {
        //Setting window parameters
        frame.setTitle("Delmgorb v1.0");
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Setting theme
        try
        {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch(Exception ex) {
            logger.log(Level.WARNING, ex.getMessage());
        }

        init();
        addMenu();
        createPanelStructure();
        placeControls();
        addActionListeners();
    }

    private void addMenu() {
        menu.add(importDataMenuItem);
        menu.add(new JSeparator());
        menu.add(exportDataMenuItem);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
    }

    /**
     * Initializes all controls in the window.
     */
    private void init() {

        for (int i=0; i< NUMBER_OF_EQUATION_PARAMETERS; i++) {
            textFields[i] = new JTextField(TEXT_FIELD_MIN_WIDTH);
        }

        numberOfPointsTextField = new JTextField(TEXT_FIELD_MIN_WIDTH);
        timeStepTextField = new JTextField(TEXT_FIELD_MIN_WIDTH);
        timePeriodTextField = new JTextField(TEXT_FIELD_MIN_WIDTH);
        numberOfSpheresTextField = new JTextField(TEXT_FIELD_MIN_WIDTH);
        phiTextField = new JTextField(TEXT_FIELD_MIN_WIDTH);
        psiTextField = new JTextField(TEXT_FIELD_MIN_WIDTH);
        thetaTextField = new JTextField(TEXT_FIELD_MIN_WIDTH);

        drawButton = new JButton("Draw!");
        resetButton = new JButton("Reset");
        integrationMethodsComboBox = new JComboBox<IntegrationMethods>(IntegrationMethods.values());
        integrationMethodsComboBox.setEditable(false);
        angleComboBox = new JComboBox<Angle>(Angle.values());
        angleComboBox.setEditable(false);
    }

    /**
     * Creates main panel, internal panels and places internal panels inside main panel.
     */
    private void createPanelStructure() {
        JPanel mainPanel = new JPanel();
        //Set main panel layout
        mainPanel.setLayout(gridBagLayout);
        GridBagConstraints constraints = new GridBagConstraints();

        //Add groupboxes
        equationParametersPanel.setBorder(BorderFactory.createTitledBorder("Equation parameters"));
        integrationParametersPanel.setBorder(BorderFactory.createTitledBorder("Integration parameters"));
        initialConditionsPanel.setBorder(BorderFactory.createTitledBorder("Initial conditions (in fractions of Pi)"));
        interpolationParametersPanel.setBorder(BorderFactory.createTitledBorder("Interpolation parameters"));

        //Set GridBagConstraints
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = DEFAULT_INSETS;
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
        gridBagLayout.setConstraints(initialConditionsPanel, constraints);
        constraints.gridy++;
        gridBagLayout.setConstraints(interpolationParametersPanel, constraints);
        constraints.gridy++;
        gridBagLayout.setConstraints(buttonsPanel, constraints);

        //Add all internal panels to main panel
        mainPanel.add(equationParametersPanel);
        mainPanel.add(integrationParametersPanel);
        mainPanel.add(interpolationParametersPanel);
        mainPanel.add(initialConditionsPanel);
        mainPanel.add(buttonsPanel);

        //Add main panel to window
        frame.getContentPane().add(mainPanel);
    }

    /**
     * Places all UI controls to equation parameters panel.
     */
    private void placeEquationParameters() {
        equationParametersPanel.setLayout(gridBagLayout);

        //Create GridBagConstraints
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = DEFAULT_INSETS;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        for (int i = 0; i < NUMBER_OF_EQUATION_PARAMETERS /2; i++) {
            constraints.gridx = i;
            gridBagLayout.setConstraints(textFields[i], constraints);
            equationParametersPanel.add(textFields[i]);
        }
        constraints.gridy++;
        for (int i = NUMBER_OF_EQUATION_PARAMETERS /2; i < NUMBER_OF_EQUATION_PARAMETERS; i++) {
            constraints.gridx = i - NUMBER_OF_EQUATION_PARAMETERS /2;
            gridBagLayout.setConstraints(textFields[i], constraints);
            equationParametersPanel.add(textFields[i]);
        }

        JPanel internalPane = new JPanel();
        internalPane.setLayout(gridBagLayout);
        constraints.gridx = 0;
        constraints.gridy++;
        constraints.insets = EMPTY_INSETS;
        constraints.gridwidth = NUMBER_OF_EQUATION_PARAMETERS/2;
        gridBagLayout.setConstraints(internalPane, constraints);

        constraints.insets = DEFAULT_INSETS;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        gridBagLayout.setConstraints(boundsLabel, constraints);

        constraints.gridy++;
        gridBagLayout.setConstraints(xLabel, constraints);
        constraints.gridx++;
        gridBagLayout.setConstraints(xMinTextField, constraints);
        constraints.gridx++;
        gridBagLayout.setConstraints(yToLabel, constraints);
        constraints.gridx++;
        gridBagLayout.setConstraints(xMaxTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy++;
        gridBagLayout.setConstraints(yLabel, constraints);
        constraints.gridx++;
        gridBagLayout.setConstraints(yMinTextField, constraints);
        constraints.gridx++;
        gridBagLayout.setConstraints(xToLabel, constraints);
        constraints.gridx++;
        gridBagLayout.setConstraints(yMaxTextField, constraints);

        internalPane.add(xMinTextField);
        internalPane.add(xMaxTextField);
        internalPane.add(yMinTextField);
        internalPane.add(yMaxTextField);
        internalPane.add(xLabel);
        internalPane.add(yLabel);
        internalPane.add(xToLabel);
        internalPane.add(yToLabel);
        internalPane.add(boundsLabel);

        equationParametersPanel.add(internalPane);
    }

    /**
     * Places all UI controls to integration parameters panel.
     */
    private void placeIntegrationParameters() {
        //Set layout to all internal panels
        integrationParametersPanel.setLayout(gridBagLayout);

        buttonsPanel.setLayout(gridBagLayout);

        //Create GridBagConstraints
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = DEFAULT_INSETS;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        //Set constraints to all UI controls
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
        gridBagLayout.setConstraints(timePeriodLabel, constraints);
        constraints.gridx++;
        gridBagLayout.setConstraints(timePeriodTextField, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        gridBagLayout.setConstraints(integrationMethodLabel, constraints);
        constraints.gridx++;
        constraints.gridwidth = 4;
        gridBagLayout.setConstraints(integrationMethodsComboBox, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        gridBagLayout.setConstraints(angleLabel, constraints);
        constraints.gridx++;
        constraints.gridwidth = 4;
        gridBagLayout.setConstraints(angleComboBox, constraints);

        integrationParametersPanel.add(numLabel);
        integrationParametersPanel.add(numberOfPointsTextField);
        integrationParametersPanel.add(timeLabel);
        integrationParametersPanel.add(timeStepTextField);
        integrationParametersPanel.add(timePeriodLabel);
        integrationParametersPanel.add(timePeriodTextField);
        integrationParametersPanel.add(integrationMethodLabel);
        integrationParametersPanel.add(integrationMethodsComboBox);
        integrationParametersPanel.add(angleLabel);
        integrationParametersPanel.add(angleComboBox);
    }

    /**
     * Places all UI controls to interpolation parameters panel.
     */
    private void placeInterpolationParameters() {
        interpolationParametersPanel.setLayout(gridBagLayout);

        //Create GridBagConstraints
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = DEFAULT_INSETS;
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

    /**
     * Places all UI controls to initial conditions panel.
     */
    private void placeInitialConditions() {
        initialConditionsPanel.setLayout(gridBagLayout);

        //Create GridBagConstraints
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = DEFAULT_INSETS;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        constraints.gridy++;
        constraints.gridx = 0;
        gridBagLayout.setConstraints(phiLabel, constraints);
        constraints.gridx++;
        gridBagLayout.setConstraints(phiTextField, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        gridBagLayout.setConstraints(psiLabel, constraints);
        constraints.gridx++;
        gridBagLayout.setConstraints(psiTextField, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        gridBagLayout.setConstraints(thetaLabel, constraints);
        constraints.gridx++;
        gridBagLayout.setConstraints(thetaTextField, constraints);

        initialConditionsPanel.add(phiLabel);
        initialConditionsPanel.add(phiTextField);
        initialConditionsPanel.add(psiLabel);
        initialConditionsPanel.add(psiTextField);
        initialConditionsPanel.add(thetaLabel);
        initialConditionsPanel.add(thetaTextField);
    }

    /**
     * Places all UI controls to buttons panel.
     */
    private void placeButtonControls() {
        JPanel internalPanel = new JPanel();
        internalPanel.setLayout(gridBagLayout);
        buttonsPanel.setLayout(gridBagLayout);

        //Create GridBagConstraints
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.SOUTHEAST;
        constraints.insets = DEFAULT_INSETS;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        constraints.fill = GridBagConstraints.NONE;
        constraints.gridy++;
        gridBagLayout.setConstraints(resetButton, constraints);
        constraints.gridx++;
        gridBagLayout.setConstraints(drawButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = EMPTY_INSETS;
        gridBagLayout.setConstraints(internalPanel, constraints);

        internalPanel.add(drawButton);
        internalPanel.add(resetButton);
        buttonsPanel.add(internalPanel);
    }

    /**
    * Combines all controls in main window panel.
    */
    private void placeControls() {
        placeEquationParameters();
        placeIntegrationParameters();
        placeInterpolationParameters();
        placeInitialConditions();
        placeButtonControls();
    }

    /**
    * When a text field gets focus, all text should be selected.
    */
    private void addActionListeners() {
        ItemListener itemListener = new ItemListener() {
            String oldValue = null;
            String newValue = null;
            @Override
            public void itemStateChanged(final ItemEvent e) {
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    oldValue = e.getItem().toString();
                } else if (e.getStateChange() == ItemEvent.SELECTED) {
                    newValue = e.getItem().toString();
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            String propertyName = "";
                            if (e.getSource().equals(integrationMethodsComboBox)) {
                                propertyName = MainWindowController.INTEGRATION_METHOD;
                            } else if (e.getSource().equals(angleComboBox)) {
                                propertyName = MainWindowController.ANGLE;
                            }
                            firePropertyChange(propertyName, oldValue, newValue);
                        }
                    });
                }
            }
        };

        MainWindowView.this.getIntegrationMethodsComboBox().addItemListener(itemListener);
        MainWindowView.this.getAngleComboBox().addItemListener(itemListener);

        FocusListener focusListener = new FocusAdapter() {
            Object oldValue;
            @Override
            public void focusGained(final FocusEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        JTextField tf = (JTextField) e.getComponent();
                        tf.selectAll();
                        oldValue = tf.getText();
                    }
                });
            }

            @Override
            public void focusLost(final FocusEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        JTextField tf = (JTextField) e.getComponent();
                        tf.select(0, 0);
                        String propertyName = "";
                        if (e.getSource().equals(numberOfPointsTextField)) {
                            propertyName = MainWindowController.NUMBER_OF_POINTS;
                        } else if (e.getSource().equals(timeStepTextField)) {
                            propertyName = MainWindowController.TIME_STEP;
                        } else if (e.getSource().equals(timePeriodTextField)) {
                            propertyName = MainWindowController.TIME_PERIOD;
                        } else if (e.getSource().equals(xMinTextField)) {
                            propertyName = MainWindowController.X_MIN;
                        } else if (e.getSource().equals(xMaxTextField)) {
                            propertyName = MainWindowController.X_MIN;
                        } else if (e.getSource().equals(yMinTextField)) {
                            propertyName = MainWindowController.Y_MIN;
                        } else if (e.getSource().equals(yMaxTextField)) {
                            propertyName = MainWindowController.Y_MAX;
                        } else if (e.getSource().equals(phiTextField)) {
                            propertyName = MainWindowController.PHI;
                        } else if (e.getSource().equals(psiTextField)) {
                            propertyName = MainWindowController.PSI;
                        } else if (e.getSource().equals(thetaTextField)) {
                            propertyName = MainWindowController.THETA;
                        } else if (e.getSource().equals(numberOfSpheresTextField)) {
                            propertyName = MainWindowController.NUMBER_OF_SPHERES;
                        }
                        firePropertyChange(propertyName, oldValue, tf.getText());
                    }
                });
            }
        };

        //Add focus listener to all text fields using reflection
        for (Field f : MainWindowView.this.getClass().getDeclaredFields()) {
            if (f.getName().contains("TextField")) {
                try {
                    ((JTextField) f.get(MainWindowView.this)).addFocusListener(focusListener);
                } catch (IllegalAccessException e) {
                    logger.severe(e.getMessage());
                }
            }
        }
    }

    /**
    * Shows main window.
    */
    public void display() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.setVisible(true);
            }
        });    
    }

    /**
     * Handles event sent by {@link com.organization4242.delmgorb.model.MainWindowModel}
     *
     * @param pce event
     */
    @Override
    public void modelPropertyChange(PropertyChangeEvent pce) {
        if (pce.getPropertyName().equals(MainWindowController.NUMBER_OF_POINTS)) {
            numberOfPointsTextField.setText(pce.getNewValue().toString());
        } else if (pce.getPropertyName().equals(MainWindowController.TIME_STEP)) {
            timeStepTextField.setText(pce.getNewValue().toString());
        } else if (pce.getPropertyName().equals(MainWindowController.TIME_PERIOD)) {
            timePeriodTextField.setText(pce.getNewValue().toString());
        } else if (pce.getPropertyName().equals(MainWindowController.INTEGRATION_METHOD)) {
            integrationMethodsComboBox.setSelectedItem(pce.getNewValue());
        } else if (pce.getPropertyName().equals(MainWindowController.ANGLE)) {
            angleComboBox.setSelectedItem(pce.getNewValue());
        } else if (pce.getPropertyName().equals(MainWindowController.X_MIN)) {
            xMinTextField.setText(pce.getNewValue().toString());
        } else if (pce.getPropertyName().equals(MainWindowController.X_MAX)) {
            xMaxTextField.setText(pce.getNewValue().toString());
        } else if (pce.getPropertyName().equals(MainWindowController.Y_MIN)) {
            yMinTextField.setText(pce.getNewValue().toString());
        } else if (pce.getPropertyName().equals(MainWindowController.Y_MAX)) {
            yMaxTextField.setText(pce.getNewValue().toString());
        } else if (pce.getPropertyName().equals(MainWindowController.PHI)) {
            phiTextField.setText(pce.getNewValue().toString());
        } else if (pce.getPropertyName().equals(MainWindowController.PSI)) {
            psiTextField.setText(pce.getNewValue().toString());
        } else if (pce.getPropertyName().equals(MainWindowController.THETA)) {
            thetaTextField.setText(pce.getNewValue().toString());
        } else if (pce.getPropertyName().equals(MainWindowController.NUMBER_OF_SPHERES)) {
            numberOfSpheresTextField.setText(pce.getNewValue().toString());
        }
    }
}