package Tools;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

import Tools.battleField;
import com.sun.jmx.remote.internal.ArrayQueue;

public class gamesField extends JFrame{

    private JButton buttonStartGame = new JButton("Start game");
    private JButton buttonEndGame = new JButton("End game");
    private JButton randomSetShips = new JButton("Random Ship");
    private JButton aboutAutor = new JButton("About");
    private JButton backOnStartMenu = new JButton("Back");
    private JLabel labelmm = new JLabel("Start game...");

    private battleField myField = new battleField( battleField.typeField.E_PLAYER, this );
    private battleField enemyField = new battleField( battleField.typeField.E_ENEMY, this);

    //private HashMap<String, Integer> mapCreateShip = new HashMap<>();
    private ArrayList<Integer> listCreateShip = new ArrayList<>();
    private int indexCreateShip = 0;


    private JLabel infoLabel = new JLabel("Начни игру");
    private JLabel addInfoLabel = new JLabel("");
    private JLabel fannyPhraseLabel = new JLabel("");
    private JLabel allPlayerShotsLabel = new JLabel("");
    private JLabel currentTimeLabel = new JLabel("");

    private JLabel countPlayerWinLabel = new JLabel("");
    private JLabel countEnemyWinLabel = new JLabel("");

    String aboutString = "<html>This is sea battle <br> " +
            "and you need to be careful <br><br> " +
            "Developer: Sergey Smirnov  ∑<br><br>" +
            "v.0.2   06.2019<br>" +
            "<br><br></html>";
    private JLabel aboutThisGameLabel = new JLabel(aboutString);
    private JButton aboutBrownBox = new JButton();
    private JButton aboutRedBox = new JButton();
    private JButton aboutBlueBox = new JButton();


    private int allPlayerShots = 0;
    long dateTimeStartGame = 0;

    private int countPlayerWin = 0;
    private int countEnemyWin = 0;



    JPanel panelCenterGame = new JPanel();
    JPanel panelCurrentGames = new JPanel();
    JPanel panelStartGames = new JPanel();
    JPanel panelAboutGame = new JPanel();

    //JPanel panelStartGames2 = new JPanel();




    // add me please
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

    Timer TimerForTimeGame = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            long msecCurr = System.currentTimeMillis();
            currentTimeLabel.setText( "Время игры:   " + Long.toString((msecCurr - dateTimeStartGame)/1000) + " сек");

            /*
            int ttt = (int)(Math.random()*3);
            //panelCenterGame.removeAll();
            if(ttt == 0 ) {
                //panelCenterGame.add(panelStartGames);
                panelStartGames.setVisible(true);
                panelStartGames2.setVisible(false);
            }
            else if(ttt == 1) {
                //panelCenterGame.add(panelAboutGame);
            } else {
                //panelCenterGame.add(panelStartGames2);
                panelStartGames.setVisible(false);
                panelStartGames2.setVisible(true);
            }
*/

        }
    });

    public gamesField() {
        super("Sea Battle");
        dateTimeStartGame = System.currentTimeMillis();
        aboutThisGameLabel.setForeground(Color.PINK);


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
        container.setBackground(new Color(255,255,204));
        //container.setLayout( new GridLayout(3,1,10,10));
        container.setLayout( new FlowLayout(FlowLayout.LEFT, 10, 10));

        //panelTools.setVisible(false);

        // Создаем панель с настройками, где мы будем видеть время, очки, количество живых кораблей и тд
        panelCurrentGames.setLayout( new GridLayout(10,1,40,10));
        panelCurrentGames.setBorder( BorderFactory.createEmptyBorder(10, 10, 10, 10) );
        panelCurrentGames.add(infoLabel);
        panelCurrentGames.add(addInfoLabel);
        panelCurrentGames.add(new JLabel(""));
        panelCurrentGames.add(currentTimeLabel);
        panelCurrentGames.add(allPlayerShotsLabel);
        panelCurrentGames.add(new JLabel(""));
        panelCurrentGames.add(buttonEndGame);
        panelCurrentGames.add(randomSetShips);

        panelCurrentGames.setMinimumSize(new Dimension(200,400));
        panelCurrentGames.setMaximumSize(new Dimension(200,700));
        panelCurrentGames.setPreferredSize(new Dimension(200,400));



        // Создаем панель со стартовым интерфейсом - пока только кнопка "Начать игру"
        panelStartGames.setLayout( new GridLayout(10,1,5,10));
        panelStartGames.setBorder( BorderFactory.createEmptyBorder(10, 10, 10, 10) );
        panelStartGames.setMinimumSize(new Dimension(200,400));
        panelStartGames.setMaximumSize(new Dimension(200,700));
        panelStartGames.setPreferredSize(new Dimension(200,400));

        panelStartGames.add(buttonStartGame);
        panelStartGames.add(aboutAutor);
        panelStartGames.add(new JLabel(""));
        panelStartGames.add(countPlayerWinLabel);
        panelStartGames.add(countEnemyWinLabel);

        //panelStartGames.add(new JLabel(""));
        //panelStartGames.add(new JLabel(""));


        // Панель с информацией об игре
        aboutBrownBox = new JButton();
        aboutBrownBox.setBackground(new Color(102, 51 , 0));
        aboutBrownBox.setPreferredSize(new Dimension(40,40));
        aboutRedBox = new JButton();
        aboutRedBox.setBackground(Color.pink);
        aboutRedBox.setPreferredSize(new Dimension(40,40));
        aboutBlueBox = new JButton();
        aboutBlueBox.setBackground(new Color(51,204,255));
        aboutBlueBox.setPreferredSize(new Dimension(40,40));

        //panelAboutGame.setLayout( new GridLayout(5,1,5,10));
        panelAboutGame.setLayout(new FlowLayout());
        panelAboutGame.setBorder( BorderFactory.createEmptyBorder(10, 10, 10, 10) );
        panelAboutGame.setMinimumSize(new Dimension(200,400));
        panelAboutGame.setMaximumSize(new Dimension(200,700));
        panelAboutGame.setPreferredSize(new Dimension(200,400));
        panelAboutGame.add(aboutThisGameLabel);
        panelAboutGame.add(aboutBrownBox);
        JLabel aboutBox = new JLabel(" - ship");
        aboutBox.setPreferredSize(new Dimension(100,20));
        aboutBox.setForeground(Color.PINK);
        panelAboutGame.add(aboutBox);
        panelAboutGame.add(aboutRedBox);
        aboutBox = new JLabel(" - die ship");
        aboutBox.setForeground(Color.PINK);
        aboutBox.setPreferredSize(new Dimension(100,20));
        panelAboutGame.add(aboutBox);
        panelAboutGame.add(aboutBlueBox);
        aboutBox = new JLabel(" - empty");
        aboutBox.setForeground(Color.PINK);
        aboutBox.setPreferredSize(new Dimension(100,20));
        panelAboutGame.add(aboutBox);

        aboutBox = new JLabel("");
        aboutBox.setPreferredSize(new Dimension(200,50));
        panelAboutGame.add(aboutBox);
        panelAboutGame.add(backOnStartMenu);
        //panelAboutGame.add(new JLabel("hhhhh"));




        aboutAutor.setMaximumSize(new Dimension(10,10));
        aboutAutor.setMinimumSize(new Dimension(10,10));
        aboutAutor.setPreferredSize(new Dimension(10,10));
        aboutAutor.setSize(new Dimension(10,10));

        countPlayerWinLabel.setText("Всего ваших побед: " + Integer.toString(countPlayerWin));
        countEnemyWinLabel.setText("Всего побед соперника: " + Integer.toString(countEnemyWin));


        // Создаем среднее поле с настройками
        Dimension newSize2 = new Dimension(200,420);
        panelCenterGame.setMinimumSize(new Dimension(200,400));
        panelCenterGame.setMaximumSize(new Dimension(200,700));
        panelCenterGame.setPreferredSize(new Dimension(200,400));
        panelCenterGame.setLayout (new FlowLayout(FlowLayout.CENTER));


        panelCenterGame.setBackground(new Color(255,255,153));
        panelStartGames.setBackground(new Color(255,255,153));
        panelCurrentGames.setBackground(new Color(255,255,153));
        panelAboutGame.setBackground(new Color(102,100,153));

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
        randomSetShips.addActionListener(new listenerGamesField() );
        aboutAutor.addActionListener(new listenerGamesField() );
        backOnStartMenu.addActionListener(new listenerGamesField() );

        buttonStartGame.setActionCommand("Start game");
        buttonEndGame.setActionCommand("End game");
        randomSetShips.setActionCommand("randomSetShips");
        aboutAutor.setActionCommand("aboutAutor");
        backOnStartMenu.setActionCommand("backOnStartMenu");

        panelCenterGame.add(panelStartGames);
        panelCenterGame.add(panelCurrentGames);
        panelCenterGame.add(panelAboutGame);
        panelStartGames.setVisible(true);
        panelCurrentGames.setVisible(false);
        panelAboutGame.setVisible(false);

        //container.add(buttonoo);
    }

    private void setRandomShipForField(battleField field) {
        field.clearField();
        field.setStateField(battleField.stateField.E_WAIT_SET_SHIP);

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
                if (field.getStateBox(row[0], column[0]) == battleField.stateBox.E_SHIP ) {
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

                            if (field.getStateBox(row[i] + n, column[i] + m) == battleField.stateBox.E_SHIP ) {
                                flagContinue = true;
                            }

                        }
                    }

                }

                if(!flagContinue) {
                    for(int i = 0; i < nextShip; i++ ) {
                        //System.out.println(row[i] + " " + column[i]);
                        field.setShipInBox(row[i], column[i]);
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

            field.setShipInBox( row , column);
        }*/


        field.setStateField(battleField.stateField.E_DISABLE);
    }

    private void timerForMakeShot() {
        //timerForShot.schedule(timerTaskForShot, 1000);
        timerForShot.start();
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
            countPlayerWin++;
        }
        else if(win == battleField.typeField.E_ENEMY){
            infoLabel.setText("Вы проиграли!");
            countEnemyWin++;
        }

        enemyField.setStateField(battleField.stateField.E_DISABLE);
        myField.setStateField(battleField.stateField.E_DISABLE);

        buttonStartGame.setText("Start game");
        TimerForTimeGame.stop();

        countPlayerWinLabel.setText("Всего ваших побед: " + Integer.toString(countPlayerWin));
        countEnemyWinLabel.setText("Всего побед соперника: " + Integer.toString(countEnemyWin));

    }

    private void makeShotForEnemy() {
        //myField.setStateField(battleField.stateField.E_WAIT_SHOT);


            int row = (int)(Math.random()*10);
            int column = (int)(Math.random()*10);

            myField.setShotInBox( row , column);


        //myField.setStateField(battleField.stateField.E_DISABLE);
    }

    public void haveShot(battleField.typeField typeField) {
        if(checkAliveShipOnFields())
            return;

        if(typeField == battleField.typeField.E_PLAYER) {
            enemyField.setStateField(battleField.stateField.E_WAIT_SHOT);
            myField.setStateField(battleField.stateField.E_DISABLE);

            infoLabel.setText("Твой ход");
            funnyFunction();
        }
        else if(typeField == battleField.typeField.E_ENEMY) {
            myField.setStateField(battleField.stateField.E_WAIT_SHOT);
            enemyField.setStateField(battleField.stateField.E_DISABLE);

            infoLabel.setText("Ход противника...");
            addInfoLabel.setText( "" );
            allPlayerShots++;
            allPlayerShotsLabel.setText( "Выстрелов:   " + Integer.toString(allPlayerShots));

            timerForMakeShot();
        }

    }

    // Все корабли расставлены
    public void haveAllShipsOnField(battleField.typeField typeField) {
        if(typeField == battleField.typeField.E_PLAYER) {
            letStartGame();
        }
        else if(typeField == battleField.typeField.E_ENEMY) {

        }
    }

    private void letStartGame() {
        randomSetShips.setVisible(false);
        infoLabel.setText("Твой ход");

    }

    String[] funnyPhrases = {"- 'Заставь его страдать'", "- 'Бей бей бей'", "- 'мама...'"
            ,"- 'Ну все'", "- 'Кончай с ним'", "- 'Взорви его корабли!'","- 'Давай уже'"
            , "- 'Ты что застыл, сосунок?'", "- 'Покажи ему, кто тут Альфа!'", "- 'Kill him'" };

    public void funnyFunction() {
        int numPhrase = (int)(Math.random()* funnyPhrases.length);
        addInfoLabel.setText( funnyPhrases[numPhrase] );
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
                setRandomShipForField();
                */
/*
                panelCenterGame.removeAll();
                panelCenterGame.add(panelCurrentGames);*/

                panelStartGames.setVisible(false);
                panelCurrentGames.setVisible(true);
                panelAboutGame.setVisible(false);

                //buttonStartGame.setText("put a ships");
                infoLabel.setText("Поставь корабль: ");
                addInfoLabel.setText(Integer.toString( listCreateShip.get(indexCreateShip) ) + " квадрат");

                myField.setStateField(battleField.stateField.E_WAIT_SET_SHIP);
                setRandomShipForField(enemyField);
                repaint();

                enemyField.setStateField(battleField.stateField.E_WAIT_SHOT);
                myField.setStateField(battleField.stateField.E_DISABLE);
                //buttonStartGame.setText("Твой ход");


                dateTimeStartGame = System.currentTimeMillis();
                TimerForTimeGame.start();

                //SimpleDateFormat formatForDateNow = new SimpleDateFormat("hh:mm:ss");
                //currentTimeLabel.setText(formatForDateNow.format(dateCur));
            }
            else if( command == "TTT" )
            {
                /*
                indexCreateShip++;
                if(indexCreateShip < listCreateShip.size() )
                {
                    infoLabel.setText("Поставь корабль: ");
                    addInfoLabel.setText(Integer.toString( listCreateShip.get(indexCreateShip) ) + " квадрат");

                    return;
                }*/

                enemyField.setStateField(battleField.stateField.E_WAIT_SHOT);
                myField.setStateField(battleField.stateField.E_DISABLE);
                buttonStartGame.setText("Твой ход");
            }
            else if( command == "End game"  )
            {
                enemyField.setStateField(battleField.stateField.E_DISABLE);
                myField.setStateField(battleField.stateField.E_DISABLE);

                /*panelCenterGame.removeAll();
                panelCenterGame.add(panelStartGames);*/

                panelStartGames.setVisible(true);
                panelCurrentGames.setVisible(false);
                panelAboutGame.setVisible(false);

                allPlayerShots = 0;
                allPlayerShotsLabel.setText("");

                myField.clearField();
                enemyField.clearField();

                randomSetShips.setVisible(true);

                TimerForTimeGame.stop();

                repaint();
            }
            else if( command == "randomSetShips") {
                setRandomShipForField(myField);
                randomSetShips.setVisible(false);
            }
            else if( command == "aboutAutor") {
                /*panelCenterGame.removeAll();
                panelCenterGame.add(panelAboutGame);
                //panelCenterGame.add(panelCurrentGames);
                repaint();*/

                panelStartGames.setVisible(false);
                panelCurrentGames.setVisible(false);
                panelAboutGame.setVisible(true);


            }
            else if( command == "backOnStartMenu") {
                /*
                panelCenterGame.removeAll();
                //panelCenterGame.add(panelStartGames);
                panelCenterGame.add(panelCurrentGames);
                repaint();
                */
                /*panelCenterGame.removeAll();
                panelCenterGame.add(panelCurrentGames);*/

                panelStartGames.setVisible(true);
                panelCurrentGames.setVisible(false);
                panelAboutGame.setVisible(false);
            }




        }
    }

}
