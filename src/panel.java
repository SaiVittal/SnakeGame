import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;

public class panel extends JPanel implements ActionListener {
    static int width = 1200;
    static int height = 600;
    static  int unit = 50;
    //timer to constantly check on the state of teh game
    Timer timer;
    Random random;

    //coordinates for the food
    int fx, fy;

    int foodeaten;

    //
    int body = 3;

    boolean flag = false;
    char dir = 'R';

    static  int Delay = 160;
    static int grid_size = (width*height)/(unit*unit);

    int x_snake[] = new int[grid_size];
    int y_snake[] = new int[grid_size];

    panel(){
        this.setPreferredSize(new Dimension(width,height));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKey());
        random = new Random();
        Game_start();

    }
    public void Game_start(){
        spawnfood();
        flag = true;
        timer = new Timer(Delay,this);
        timer.start();
    }

    public void spawnfood(){
        fx = random.nextInt((int)(width/unit) )*unit;
        fy = random.nextInt((int)(height/unit))*unit;

    }
    public void paintcomponent(Graphics graphics){
        super.paintComponent(graphics);
        draw(graphics);
    }
    public void draw(Graphics graphics){
        if(flag){
            graphics.setColor(Color.orange);
            graphics.fillOval(fx,fy,unit, unit);

            for(int i=0; i<body; i++){
                if(i==0){
                    graphics.setColor(Color.red);
                    graphics.fillOval(x_snake[0], y_snake[0], unit, unit);}


                    else{
                    graphics.setColor(Color.green);
                    graphics.fillRect(x_snake[i],y_snake[i],unit, unit);}
            }

            //score display
            graphics.setColor(Color.cyan);
            graphics.setFont(new Font("comic sans", Font.BOLD, 40));
            FontMetrics fm = getFontMetrics(graphics.getFont());
            graphics.drawString("score"+foodeaten,(width-fm.stringWidth("score: "+foodeaten))/2, graphics.getFont().getSize());
        }
        else{
            gameover(graphics);
        }
    }
    public void gameover(Graphics graphics){
        graphics.setColor(Color.red);
        graphics.setFont(new Font("comic sans", Font.BOLD, 40));
        FontMetrics fm2 = getFontMetrics(graphics.getFont());
        graphics.drawString("Game Over"+foodeaten,(width-fm2.stringWidth("Game Over: "))/2, height/2);

        //gameover display
        graphics.setColor(Color.red);
        graphics.setFont(new Font("comic sans", Font.BOLD, 80));
        FontMetrics fm3 = getFontMetrics(graphics.getFont());
        graphics.drawString("Press R to replay"+foodeaten,(width-fm3.stringWidth("Press R to Replay: "))/2, height/2-150);
    }

    public void move(){
        //updating the body
        for(int i =body; i>0; i--){
            x_snake[i] = x_snake[i-1];
            y_snake[i] = y_snake[i-1];


        }

        //update the head

        switch(dir){
            case 'U':
                y_snake[0] = y_snake[0]-unit;
                break;
            case 'D':
                y_snake[0] = y_snake[0]+unit;
                break;
            case 'L':
                y_snake[0] = y_snake[0]-unit;
                break;
            case 'R':
                y_snake[0] = y_snake[0]+unit;
                break;
        }
    }
    public void check(){
        for(int i= body; i>0; i--){
            if((x_snake[0] == x_snake[i]) && (y_snake[0] == y_snake[i])){
                flag = false;
            }
        }
        //to chedck hit with the walls
        if(x_snake[0]<0){
            flag = false;
        }
        else if(x_snake[0]>width){
            flag = false;
        }
        else if (y_snake[0]<0){
            flag = false;
        }
        else if(y_snake[0]>height){
            flag = false;
        }
        if(!flag){
            timer.stop();
        }
    }

    public void food(){
        if((x_snake[0] == fx) && (y_snake[0] == fy)){
            body++;
            foodeaten++;
            spawnfood();
        }
    }


    public class MyKey extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(dir!='R'){
                        dir ='L';
                    }
                    break;

                case KeyEvent.VK_UP:
                    if(dir!='D'){
                        dir ='U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(dir!='U'){
                        dir ='D';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(dir!='L'){
                        dir ='R';
                    }
                    break;
                case KeyEvent.VK_R:
                    if(!flag){
                        foodeaten=0;
                        body =3;
                        dir = 'R';
                        Arrays.fill(x_snake,0);
                        Arrays.fill(y_snake,0);
                        Game_start();
                    }
                    break;
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(flag){
            move();
            food();
            check();
        }
        repaint();

    }



}
