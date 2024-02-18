import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class OyunEkrani extends JFrame {
    public OyunEkrani(String title) throws HeadlessException {
        super(title);
    }

    public static void main(String[] args)  throws IOException{

        OyunEkrani ekran= new OyunEkrani("Oyun Ekrani");
        ekran.setResizable(false);
        ekran.setFocusable(false);//ekrandan focusu aldik, asagidaki satirlarda oyuna verdik

        ekran.setSize(800,600);
        ekran.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Oyun oyun = new Oyun();

        oyun.requestFocus();//o ekrana klavye odagini verir

        oyun.addKeyListener(oyun);//bu kod klavye hareketlerini dinler

        oyun.setFocusable(true); // focusuoyuna verdik
        oyun.setFocusTraversalKeysEnabled(false);
        //klavye islemlerinin jpaneli tarafindan anlasilabilmesi icin


        ekran.add(oyun);
        ekran.setVisible(true);


    }

}
