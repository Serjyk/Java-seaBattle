package Tools;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import java.util.TimerTask;
import javax.swing.*;

import Tools.battleField;
import com.sun.jmx.remote.internal.ArrayQueue;

public class gamesField extends JFrame{

    private JButton buttonStartGame = new JButton("Start game.");
    private JButton buttonEndGame = new JButton("End game.");
    private JLabel labelmm = new JLabel("Start game...");

    private battleField myField = new battleField( battleField.typeField.E_PLAYER, this );
    private battleField enemyField = new battleField( battleField.typeField.E_ENEMY, this);

    //private HashMap<String, Integer> mapCreateShip = new HashMap<>();
    private ArrayList<Integer> listCreateShip = new ArrayList<>();
    private int indexCreateShip = 0;


    private JLabel infoLabel = new JLabel("Начни игру");
    private JLabel addInfoLabel = new JLabel("");

    JPanel panelCenterGame = new JPanel();
    JPanel panelCurrentGames = new JPanel();
    JPanel panelStartGames = new JPanel();

    /*
    java.util.Timer timerForShot = new java.util.Timer();
    TimerTask timerTaskForShot = new TimerTask() {
        @Override
        public void run() {
            makeShotForEnemy();
        }
    };*/

    Timer timerForShot = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            makeShotForEnemy();
            timerForShot.stop();
        }
    });

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

        //panelTools.setVisible(false);

        // Создаем панель с настройками, где мы будем видеть время, очки, количество живых кораблей и тд
        panelCurrentGames.setLayout( new GridLayout(10,1,5,10));
        panelCurrentGames.add(infoLabel);
        panelCurrentGames.add(addInfoLabel);
        panelCurrentGames.add(new JLabel("Время игры"));
        panelCurrentGames.add(new JLabel("..."));
        panelCurrentGames.add(new JLabel("Выстрелов"));
        panelCurrentGames.add(new JLabel("..."));
        panelCurrentGames.add(buttonEndGame);


        // Создаем панель со стартовым интерфейсом - пока только кнопка "Начать игру"
        panelStartGames.add(buttonStartGame);


        // Создаем среднее поле с настройками
        Dimension newSize2 = new Dimension(200,420);
        panelCenterGame.setMinimumSize(new Dimension(200,400));
        panelCenterGame.setMaximumSize(new Dimension(200,700));
        panelCenterGame.setPreferredSize(new Dimension(200,400));
        panelCenterGame.setLayout (new FlowLayout(FlowLayout.CENTER));


        panelCenterGame.add(panelStartGames);

        //container.getLayout().



        //buttonoo.addActionListener(new listenerGamesField() );

        //container.add(labelmm);

        //container.add(new JLabel("oneeee"));
        //container.add(new JLabel("oneeee"));
        //container.add(new JLabel("oneeee"));


        // Заполняем главное поле нашей игры ( наше поле, настройки, поле противника)
        container.add(myField);
        container.add(panelCenterGame);
        container.add(enemyField);


        buttonStartGame.addActionListener(new listenerGamesField() );
        buttonEndGame.addActionListener(new listenerGamesField() );

        buttonStartGame.setActionCommand("Start game");
        buttonEndGame.setActionCommand("End game");

        //container.add(buttonoo);
    }

    private void setRandomShipForEnemy() {
        enemyField.clearField();
        enemyField.setStateField(battleField.stateField.E_WAIT_SET_SHIP);

        ArrayList<Integer> listShipEnemy = new ArrayList<>();
        listShipEnemy.add(4);
        listShipEnemy.add(3);
        listShipEnemy.add(3);
        listShipEnemy.add(2);
        listShipEnemy.add(2);
        listShipEnemy.add(2);
        listShipEnemy.add(1);
        listShipEnemy.add(1);
        listShipEnemy.add(1);
        listShipEnemy.add(1);

        for(Integer nextShip : listShipEnemy) {
            while(true) {
                int row[] = new int[nextShip];
                int column[] = new int[nextShip];

                /*
                row[0] = (int)(Math.random()*10);
                column[0] = (int)(Math.random()*10);

                if(row[0] < 0 || row[0] >= 10 || column[0] < 0 || column[0] >= 10){
                    continue;
                }

                boolean flagContinue = false;
                if (enemyField.getStateBox(row[0], column[0]) == battleField.stateBox.E_SHIP ) {
                    continue;
                }
                */

                boolean flagContinue = false;

                for(int i = 0; i < nextShip; i++ ) {

                    if(i == 0) {
                        row[i] = (int)(Math.random()*10);
                        column[i] = (int)(Math.random()*10);
                    }
                    else{
                        while(true){
                            int rownOrColumnn = (int)(Math.random()*2);
                            int upOrDown = 1 - (int)(Math.random()*2)*2;

                            //System.out.println(rownOrColumnn + " " + upOrDown);

                            if(rownOrColumnn == 1) {
                                row[i] = row[i-1] + upOrDown;
                                column[i] = column[i-1];
                            }
                            else {
                                column[i] = column[i-1] + upOrDown;
                                row[i] = row[i-1];
                            }

                            if(row[i] >= 0 && row[i] < 10 && column[i] >= 0 && column[i] < 10){
                                boolean flagbr = false;
                                for(int y = 0; y < i; y++){
                                    if(row[y] == row[i] && column[y] == column[i])
                                    {
                                        flagbr = true;
                                    }
                                }

                                if(!flagbr)
                                    break;
                            }
                        }
                    }

                    for(int n = -1; n <= 1; n ++ ) {
                        for(int m = -1; m <=1; m++ ) {
                            if(row[i] + n < 0 || row[i] + n >= 10 || column[i] + m < 0 || column[i] + m >= 10){
                                continue;
                            }

                            if (enemyField.getStateBox(row[i] + n, column[i] + m) == battleField.stateBox.E_SHIP ) {
                                flagContinue = true;
                            }

                        }
                    }

                }

                if(!flagContinue) {
                    for(int i = 0; i < nextShip; i++ ) {
                        //System.out.println(row[i] + " " + column[i]);
                        enemyField.setShipInBox(row[i], column[i]);
                    }
                    //System.out.println();

                    break;
                }

            }
        }

        /*
        for(int i = 0; i < 10; i++)
        {
            int row = (int)(Math.random()*10);
            int column = (int)(Math.random()*10);

            enemyField.setShipInBox( row , column);
        }*/


        enemyField.setStateField(battleField.stateField.E_DISABLE);
    }

    private void timerForMakeShot() {
        //timerForShot.schedule(timerTaskForShot, 1000);
        timerForShot.start();
    }

    private void startGame() {

    }

    private boolean checkAliveShipOnFields() {
        if(myField.getCountAliveShip() > 0 && enemyField.getCountAliveShip() <= 0) {
            finishGame( myField.getTypeThisField() );
            return true;
        }
        else if(myField.getCountAliveShip() <= 0 && enemyField.getCountAliveShip() > 0){
            finishGame( enemyField.getTypeThisField() );
            return true;
        }

        return false;
    }

    private void finishGame(battleField.typeField win) {
        if(win == battleField.typeField.E_PLAYER) {
            infoLabel.setText("Вы выйграли!");
        }
        else if(win == battleField.typeField.E_ENEMY){
            infoLabel.setText("Вы проиграли!");
        }

        enemyField.setStateField(battleField.stateField.E_DISABLE);
        myField.setStateField(battleField.stateField.E_DISABLE);

        buttonStartGame.setText("Start game");

    }

    private void makeShotForEnemy() {
        myField.setStateField(battleField.stateField.E_WAIT_SHOT);


            int row = (int)(Math.random()*10);
            int column = (int)(Math.random()*10);

            myField.setShotInBox( row , column);


        myField.setStateField(battleField.stateField.E_DISABLE);
    }

    public void haveShot(battleField.typeField typeField) {
        if(checkAliveShipOnFields())
            return;

        if(typeField == battleField.typeField.E_PLAYER) {
            enemyField.setStateField(battleField.stateField.E_WAIT_SHOT);
            myField.setStateField(battleField.stateField.E_DISABLE);

            buttonStartGame.setText("Твой ход");
        }
        else if(typeField == battleField.typeField.E_ENEMY) {
            myField.setStateField(battleField.stateField.E_WAIT_SHOT);
            enemyField.setStateField(battleField.stateField.E_DISABLE);

            buttonStartGame.setText("Ход прот");

            timerForMakeShot();
        }

    }

    class listenerGamesField implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //labelmm.setText("put your ships on field...");

            JButton button = (JButton) e.getSource();
            String command = button.getActionCommand();

            if(command == "Start game" )
            {
                /*
                indexCreateShip = 0;
                buttonStartGame.setText("put a ships");
                infoLabel.setText("Поставь корабль: ");
                addInfoLabel.setText(Integer.toString( listCreateShip.get(indexCreateShip) ) + " квадрат");

                myField.setStateField(battleField.stateField.E_WAIT_SET_SHIP);
                setRandomShipForEnemy();
                */

                panelCenterGame.removeAll();
                panelCenterGame.add(panelCurrentGames);

                //buttonStartGame.setText("put a ships");
                infoLabel.setText("Поставь корабль: ");
                addInfoLabel.setText(Integer.toString( listCreateShip.get(indexCreateShip) ) + " квадрат");

                myField.setStateField(battleField.stateField.E_WAIT_SET_SHIP);
                setRandomShipForEnemy();
                repaint();
            }
            else if( command == "TTT" )
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
                buttonStartGame.setText("Твой ход");
            }
            else if( command == "End game"  )
            {
                panelCenterGame.removeAll();
                panelCenterGame.add(panelStartGames);
                repaint();
            }




        }
    }

}
