package Tools;

import com.sun.org.apache.xerces.internal.xs.StringList;
import javafx.util.Pair;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import javax.swing.*;

import Tools.gamesField;


public class battleField extends JPanel{

    public enum stateBox {
        E_EMPTY,
        E_CHECKED_EMPTY,
        E_SHIP,
        E_DESTROYED_SHIP
    }

    public enum typeField {
        E_ENEMY,
        E_PLAYER
    }

    public enum stateField {
        E_WAIT_SHOT,
        E_TAKE_SHOT,
        E_WAIT_SET_SHIP,
        E_ENABLE,
        E_DISABLE
    }

    private typeField typeThisField = typeField.E_ENEMY;
    private stateField stateThisField = stateField.E_ENABLE;

    private stateBox[][] battleMatrix = new stateBox[10][10];;
    private JButton[][] buttonsMatrix = new JButton[10][10];

    private JButton buttonoo = new JButton("one");
    private JTextField textinput = new JTextField("", 5);
    private JLabel labelmm = new JLabel("Input");
    private JRadioButton radio1 = new JRadioButton("select this");
    private JRadioButton radio2 = new JRadioButton("select that");
    private JCheckBox checkb = new JCheckBox("check it or not", false);

    private JLabel labelNameField = new JLabel("Мое поле");


    private gamesField myparent = null;

    private int getCountAliveShip = 0;
    //private HashMap<Pair<Integer, Integer>, JButton> mapPositiontButton = new HashMap<>();


    public battleField(typeField typeField, gamesField parent )
    {
        //super("Sea Battle");
        super();

        myparent = parent;

        typeThisField = typeField;

        initField();

        updateStateField();







        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /*
        Container container = this.getContentPane();

        container.setLayout( new GridLayout(3,2,2,2));
        container.add(labelmm);
        container.add(textinput);

        ButtonGroup groupR = new ButtonGroup();
        groupR.add(radio1);
        groupR.add(radio2);

        container.add(radio1);
        container.add(radio2);
        radio1.setSelected(true);

        container.add(checkb);

        // Button
        buttonoo.addActionListener(new listenerField() );

        container.add(buttonoo);
*/


    }

    public void initField()
    {
        for(int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                battleMatrix[i][j] = stateBox.E_EMPTY;
            }
        }


        // Устанавливаем размер полю
        //this.setBounds(100, 100, 500, 50);
        /*Dimension newSize = new Dimension(400,400);
        this.setMinimumSize(newSize);
        this.setMaximumSize(newSize);
        this.setPreferredSize(newSize);*/


        // Устанавливаем layout и размещение элементов
        this.setLayout( new GridBagLayout());

        JPanel panelBoxes = new JPanel();

        Dimension newSize2 = new Dimension(420,420);
        panelBoxes.setMinimumSize(newSize2);
        panelBoxes.setMaximumSize(newSize2);
        panelBoxes.setPreferredSize(newSize2);

        panelBoxes.setLayout(new GridLayout(11,11,0,0));
        this.add(panelBoxes);

        JLabel firstBoxText = new JLabel("");
        JPanel firstBox = new JPanel();
        firstBox.setBackground(typeThisField == typeField.E_PLAYER ? Color.green : Color.red);
        firstBox.add(firstBoxText);

        panelBoxes.add( firstBox );

        // Заполняем поле кнопками
        String headTop = "ABCDEFGHIJ";
        for (int i = 0; i < 10; i++)
        {
            JLabel labelLett = new JLabel(Character.toString(headTop.charAt(i)));
            //labelLett.setForeground( typeThisField == typeField.E_PLAYER ? Color.green : Color.red);

            JPanel titlePanel = new JPanel();
            titlePanel.setBackground(typeThisField == typeField.E_PLAYER ? Color.green : Color.red);
            titlePanel.add(labelLett);

            panelBoxes.add( titlePanel );
        }

        for(int i = 0; i < 10; i++) {
            JLabel labelNum = new JLabel(Integer.toString( i + 1));
            //labelNum.setForeground(typeThisField == typeField.E_PLAYER ? Color.green : Color.red);

            JPanel titlePanel = new JPanel();
            titlePanel.setBackground(typeThisField == typeField.E_PLAYER ? Color.green : Color.red);
            titlePanel.add(labelNum);

            panelBoxes.add( titlePanel );

            for (int j = 0; j < 10; j++) {

                buttonsMatrix[i][j] = new JButton("");
                buttonsMatrix[i][j].addActionListener(new listenerField() );

                Dimension newSize3 = new Dimension(20,20);
                buttonsMatrix[i][j].setMinimumSize(newSize3);
                buttonsMatrix[i][j].setMaximumSize(newSize3);
                panelBoxes.add( buttonsMatrix[i][j] );

                buttonsMatrix[i][j].setActionCommand( Integer.toString(i) + "_" + Integer.toString(j));
                //mapPositiontButton.put(new Pair<Integer, Integer>(i,j), buttonsMatrix[i][j]);
            }
        }




        GridBagConstraints constraints = new GridBagConstraints();
        // По умолчанию натуральная высота, максимальная ширина
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.5;
        constraints.gridy   = 0  ;  // нулевая ячейка таблицы по вертикали

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;      // нулевая ячейка таблицы по горизонтали
        add(labelNameField, constraints);

        JLabel labeltest1 = new JLabel("Тестовое");
        //labeltest1.setForeground( Color.green );
        //labeltest1.setBackground( Color.green );

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;      // первая ячейка таблицы по горизонтали
        add(labeltest1, constraints);

        JLabel labeltest2 = new JLabel("Количество ходов");
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 2;      // вторая ячейка таблицы по горизонтали
        add(labeltest2, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        //constraints.ipady     = 45;   // кнопка высокая
        constraints.weightx   = 0.0;
        constraints.gridwidth = 3;    // размер кнопки в две ячейки
        constraints.gridx     = 0;    // нулевая ячейка по горизонтали
        constraints.gridy     = 1;    // первая ячейка по вертикали
        add(panelBoxes, constraints);

    }

    // Устанавливаем тип кнопкам в записимости от состояния
    public void updateStateField() {

        if(typeThisField == typeField.E_ENEMY){
            labelNameField.setText("Поле противника");

            for(int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    String nameBox = "";
                    Color colorBut = Color.lightGray;
                    switch (battleMatrix[i][j]) {
                        case E_EMPTY: colorBut = Color.lightGray ; break;
                        case E_CHECKED_EMPTY: colorBut = Color.blue ; break;
                        //case E_SHIP: colorBut = Color.lightGray ; break;
                        case E_SHIP: colorBut = Color.black ; break;
                        case E_DESTROYED_SHIP: colorBut = Color.pink ; break;
                    }
                    //buttonsMatrix[i][j].setText(nameBox);
                    buttonsMatrix[i][j].setBackground(colorBut);
                }
            }
        }
        else if (typeThisField == typeField.E_PLAYER){
            labelNameField.setText("Мое поле");

            for(int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    String nameBox = "";
                    Color colorBut = Color.lightGray;
                    switch (battleMatrix[i][j]) {
                        case E_EMPTY: colorBut = Color.lightGray ; break;
                        case E_CHECKED_EMPTY: colorBut = Color.blue ; break;
                        case E_SHIP: colorBut = Color.black ; break;
                        case E_DESTROYED_SHIP: colorBut = Color.pink ; break;
                    }
                    //buttonsMatrix[i][j].setText(nameBox);
                    buttonsMatrix[i][j].setBackground(colorBut);
                }
            }

        }
    }

    public void printFieldConsole()
    {
        for(int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(battleMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void setShipInBox(int row, int column) {
        battleMatrix[row][column] = stateBox.E_SHIP;
        updateStateField();
        getCountAliveShip++;
    }

    public stateBox getStateBox(int row, int column) {
        return battleMatrix[row][column];
    }

    public void setShotInBox(int row, int column) {
        if(battleMatrix[row][column] == stateBox.E_EMPTY ) {
            battleMatrix[row][column] = stateBox.E_CHECKED_EMPTY;
        }
        else if(battleMatrix[row][column] == stateBox.E_SHIP) {
            battleMatrix[row][column] = stateBox.E_DESTROYED_SHIP;
            getCountAliveShip--;
        }

        myparent.haveShot( typeThisField );
        updateStateField();
    }

    public int getCountAliveShip() {
        return getCountAliveShip;
    }

    public typeField getTypeThisField() {
        return typeThisField;
    }

    public void setStateField(stateField state ) {
        stateThisField = state;
    }

    public void clearField() {
        for(int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                battleMatrix[i][j] = stateBox.E_EMPTY;
            }
        }

        getCountAliveShip = 0;
        updateStateField();
    }



    // Listener class
    class listenerField implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            JButton button = (JButton) e.getSource();
            String command = button.getActionCommand();
            String[] listInt = command.split("_");

            int row = Integer.parseInt(listInt[0]);
            int column = Integer.parseInt(listInt[1]);

            if(stateThisField == stateField.E_ENABLE){

            }
            else if(stateThisField == stateField.E_DISABLE) {

            }
            else if(stateThisField == stateField.E_WAIT_SHOT) {
                setShotInBox(row,column );
            }
            else if(stateThisField == stateField.E_WAIT_SET_SHIP) {
                setShipInBox(row,column );

            }
            else if(stateThisField == stateField.E_TAKE_SHOT) {
                myparent.haveShot( typeThisField );
            }





            /*
            String message = "HIII\n";
            message += "Maaan - " + textinput.getText() + "\n";
            message += (radio1.isSelected() ? "radio 1" : "radio2");
            message += "Checkbox is " + (checkb.isSelected() ? "checked" : "unchecked");

            JOptionPane.showMessageDialog(null, message, "Output", JOptionPane.PLAIN_MESSAGE);
            */


        }

    }
}
