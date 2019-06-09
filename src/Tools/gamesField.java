package Tools;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import javax.swing.*;

import Tools.battleField;
import com.sun.jmx.remote.internal.ArrayQueue;

public class gamesField extends JFrame{

    private JButton buttonoo = new JButton("Start game...");
    private JLabel labelmm = new JLabel("Start game...");

    private battleField myField = new battleField( battleField.typeField.E_PLAYER, this );
    private battleField enemyField = new battleField( battleField.typeField.E_ENEMY, this);

    //private HashMap<String, Integer> mapCreateShip = new HashMap<>();
    private ArrayList<Integer> listCreateShip = new ArrayList<>();
    private int indexCreateShip = 0;


    private JLabel infoLabel = new JLabel("Начни игру");
    private JLabel addInfoLabel = new JLabel("");

    public gamesField() {
        super("Sea Battle");

        this.setBounds(100, 100, 1120, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        listCreateShip.add(4);
        listCreateShip.add(3);
        listCreateShip.add(3);
        listCreateShip.add(2);
        listCreateShip.add(2);
        listCreateShip.add(2);
        listCreateShip.add(1);
        listCreateShip.add(1);
        listCreateShip.add(1);
        listCreateShip.add(1);




        Container container = this.getContentPane();
        //container.setLayout( new GridLayout(3,1,10,10));
        container.setLayout( new FlowLayout(FlowLayout.LEFT, 10, 10));


        container.add(myField);

        JPanel panelTools = new JPanel();
        panelTools.setLayout( new GridLayout(4,2,5,10));
        panelTools.add(infoLabel);
        panelTools.add(addInfoLabel);
        panelTools.add(new JLabel("Время игры"));
        panelTools.add(new JLabel("..."));
        panelTools.add(new JLabel("Выстрелов"));
        panelTools.add(new JLabel("..."));
        panelTools.add(buttonoo);
        //container.getLayout().
        container.add(panelTools);
        //container.add(labelmm);

        //container.add(new JLabel("oneeee"));
        //container.add(new JLabel("oneeee"));
        //container.add(new JLabel("oneeee"));


        container.add(enemyField);

        buttonoo.addActionListener(new listenerGamesField() );

        //container.add(buttonoo);
    }

    private void setRandomShipForEnemy() {
        enemyField.setStateField(battleField.stateField.E_WAIT_SET_SHIP);

        for(int i = 0; i < 10; i++)
        {
            int row = (int)(Math.random()*10);
            int column = (int)(Math.random()*10);

            enemyField.setShipInBox( row , column);
        }


        enemyField.setStateField(battleField.stateField.E_DISABLE);
    }

    private void makeShotForEnemy() {
        myField.setStateField(battleField.stateField.E_WAIT_SHOT);


            int row = (int)(Math.random()*10);
            int column = (int)(Math.random()*10);

            myField.setShotInBox( row , column);


        myField.setStateField(battleField.stateField.E_DISABLE);
    }

    public void haveShot(battleField.typeField typeField) {
        if(typeField == battleField.typeField.E_PLAYER) {
            enemyField.setStateField(battleField.stateField.E_WAIT_SHOT);
            myField.setStateField(battleField.stateField.E_DISABLE);
        }
        else if(typeField == battleField.typeField.E_ENEMY) {
            myField.setStateField(battleField.stateField.E_WAIT_SHOT);
            enemyField.setStateField(battleField.stateField.E_DISABLE);
            makeShotForEnemy();
        }

    }

    class listenerGamesField implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //labelmm.setText("put your ships on field...");
            if(buttonoo.getText().indexOf("Start game") != -1 )
            {
                buttonoo.setText("put a ships");
                infoLabel.setText("Поставь корабль: ");
                addInfoLabel.setText(Integer.toString( listCreateShip.get(indexCreateShip) ) + " квадрат");

                myField.setStateField(battleField.stateField.E_WAIT_SET_SHIP);
                setRandomShipForEnemy();
            }
            else if( buttonoo.getText().indexOf("put a ships") != -1)
            {
                indexCreateShip++;
                if(indexCreateShip < listCreateShip.size() )
                {
                    infoLabel.setText("Поставь корабль: ");
                    addInfoLabel.setText(Integer.toString( listCreateShip.get(indexCreateShip) ) + " квадрат");

                    return;
                }

                enemyField.setStateField(battleField.stateField.E_WAIT_SHOT);
                myField.setStateField(battleField.stateField.E_DISABLE);
                buttonoo.setText("Твой ход");
            }




        }
    }

}
