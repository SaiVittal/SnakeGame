import javax.swing.JFrame;


public class snakeframe extends JFrame {
    snakeframe(){
        this.add(new panel());
        this.setTitle("snakegame");
        //ensuirng the sizze of teh frame window cannot be changed
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        //spawning the frmae in the ceneter of teh screeen
        this.setLocationRelativeTo(null);



    }
}
