package sudoku;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class StopWatch implements ActionListener {
    
    JButton timeLabel = new JButton();
    int elapsedTime = 0;
    int miliseconds = 0;
    int seconds = 0;
    int minutes = 0;

    boolean start = false;
    String miliseconds_string  = String.format("%02d", miliseconds);
    String seconds_string  = String.format("%02d", seconds);
    String minutes_string  = String.format("%02d", minutes);

    Timer timer = new Timer(1, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            elapsedTime+=1000;
            minutes = (elapsedTime/3600000) % 60;
            seconds = (elapsedTime/60000) % 60;
            miliseconds = (elapsedTime/1000) % 60;
            
            miliseconds_string  = String.format("%02d", miliseconds);
            seconds_string  = String.format("%02d", seconds);
            minutes_string  = String.format("%02d", minutes);        
            timeLabel.setText(minutes_string+":"+seconds_string+":"+miliseconds_string);          
        }
    });

    StopWatch(){
        timeLabel.setText(minutes_string+":"+seconds_string+":"+miliseconds_string);
        timeLabel.setFont(new Font("ROG Fonts", 0, 20));
        timeLabel.setHorizontalAlignment(JLabel.CENTER);
        timeLabel.setEnabled(true);
        timeLabel.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){

    }

    void start(){
        timer.start();
    }

    void stop(){
        timer.stop();
    }

    String returnTime(){
        miliseconds_string  = String.format("%02d", miliseconds);
        seconds_string  = String.format("%02d", seconds);
        minutes_string  = String.format("%02d", minutes);        
        return minutes_string+":"+seconds_string+":"+miliseconds_string;     
    }
}
