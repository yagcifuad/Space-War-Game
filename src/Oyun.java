
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

class Ates{ //ates class'i olusturuyoruz
    private int x;
    private int y;

    public Ates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
public class Oyun extends JPanel implements KeyListener, ActionListener {
    //klavye hareketleri icin KeyListener,
    //Action listeneri action perform methodu icin implement ettik,
    //bu syede oynun icinde timer her calistiginda action perform harekete gececek

    //5---------------------------------------------------------------------------------------------------------------------
    //5- timer olusturma

    Timer timer = new Timer(5, this);
    //1. deger timerin kac milisaniyede bir calisacagi
    //ikinci deger ise actionlistener
    // interfacsini implemente eden bir obje
    //comstractor basladiginda timeri baslatmak icin, constractorun icine yazdik.

    //1-oyunda gecen sureyi anlamak icin
    private int gecen_sure=0;
    private int harcanan_ates=0;


    private BufferedImage image;

    //uzay gemisi gorseli icin class olusturacagiz.


    // bu da onu import etmek icin

    //atilan atislari tutmak icin arraylist yazacagiz
    private ArrayList<Ates> atesler = new ArrayList<Ates>();//yukarida ates classi olusturduk

    private int atesdirY = 1;//her action perform oldugunda bu ates classinin
    // y cordinatoruna eklenecek ve ates her adimda daha yukari gidecek

    private int topX=0;//bu top x de saga ve sola gitmeyi ayarlayacak
    //ilk basta 0a 0 dan baslayacak daha sonra surekli hareket edecek

    private int topdirX=2;
    //bunu surekli topX e ekleyecegiz ve top saga dogru gidecek

    private int uzayGemisiX=0;
    //uzay gemisinin nereden basladiginin koordinati

    private int dirUzayx=20;//klavyede saga basilinca saga 20,
    // sola basinca sola 20 kaydirmak icin

    //12---------------------------------------------------------------------------------------------------------------------
    public  boolean kontrolEt(){

        for(Ates ates: atesler){
            if(new Rectangle(ates.getX(),ates.getY(),10,20).intersects(new Rectangle(topX,0,20,20))){
                return true;
            }

        }
        return false;
    }



    public Oyun()  {
        try {
            //DIKKAT
            //the FileImageInputStream class reads files as absolute paths, not relative paths.
            //yani bu yetmez (DefaultPackage/spaceShip.png), tam address lazim
           image = ImageIO.read(new FileImageInputStream(new File("/Users/fuadyagci/NetBeansProjects/SpaceGame/src/spacegame/spaceShip.png")));
            //gemi = ImageIO.read(new FileImageInputStream(new File("DefaultPackage/spaceShip.png")));

            //bu constractorun icinde bufferimage nin
            // icini png dosyasi ile doldurmamiz gerekiyor

        }catch (IOException ex){
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        }

        setBackground(Color.BLACK);//arka plani siyah yapar

        timer.start();

    }
    @Override
    public void paint(Graphics g) {

        super.paint(g);
        //11---------------------------------------------------------------------------------------------------------------------
        gecen_sure+=5;// gecen sureye 5 milisaniye eklemis oluyoruz


        g.setColor(Color.red);//top icin
        //1-simdi topu cizmemiz lazim, peki nasil cizecegiz

        //2-
        g.fillOval(topX,0,20,20);
        //3---------------------------------------------------------------------------------------------------------------------
        //3- baslangic noktasi topx , y de ise 0,
        //buyukluk 20 ye 20

        //4---------------------------------------------------------------------------------------------------------------------
        //4- image = uzay gemisi zaten, 490 en alt kisim,
        //en sondaki kisim buyukluke ilgili
        //en sondaki this jpaneline objeyi vermek icin

        //g.fillOval(uzayGemisiX,490,20,20);
         g.drawImage(image,uzayGemisiX,490,image.getWidth()/10,image.getHeight()/10,this);

        //10---------------------------------------------------------------------------------------------------------------------
        for(Ates ates: atesler){//j frame den cikan atesleri silmek icin
            if(ates.getY()<0){
                atesler.remove(ates);
            }
        }
        g.setColor(Color.blue);//ateslerin rengi mavi yapildi
        //atesleri cizmeye basliyoruz
        for(Ates ates: atesler){
            g.fillRect(ates.getX(),ates.getY(),10,20);
            //cizilecek atesin bilgileri
        }

        //13---------------------------------------------------------------------------------------------------------------------
        if (kontrolEt()){
            timer.stop();
            String message = "Kazandiniz...\n"+
                    "Harcanan ates: "+harcanan_ates+
                    "\nGecen sure "+ gecen_sure/1000.0+"milisaniye!";
            JOptionPane.showMessageDialog(this,message);
            System.exit(0);
        }
    }

    @Override
    public void repaint() {
        //bunu her cagirdigimizda java aslinda paintide cagirir
        //bunu oyunlarda yazmamiz gerekiyor

        //ileride action perfomu cagirdigimizda en son repainti cagirip paint
        // islemlerinin yeniden yapilmasini isteyecegiz
        super.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //7---------------------------------------------------------------------------------------------------------------------
        int c= e.getKeyCode();
        //bununla birlikte saga basilinca saga basma doner,
        // sola basilinca sola basma olarak doner

        if (c==KeyEvent.VK_LEFT){
            if(uzayGemisiX<=0){uzayGemisiX=0;
            }else {uzayGemisiX-=dirUzayx;//her adimda kaydirmak icin
            }
        }else if(c==KeyEvent.VK_RIGHT) {
            if(uzayGemisiX>=750){
                uzayGemisiX=750;
            } else {uzayGemisiX+=dirUzayx;}
        }else if (c==KeyEvent.VK_CONTROL){//8---------------------------------------------------------------------------------------------------------------------

            atesler.add(new Ates(uzayGemisiX+17,475));
            harcanan_ates++;
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //9---------------------------------------------------------------------------------------------------------------------
        for(Ates ates:atesler){
            ates.setY(ates.getY()-atesdirY);
            //her action perform calistiginda ateslerin
            // y coordinattaki yeri degismis olacak

            //action perdorm en sonda repainti cagiriyot
            //repaintte painti cagiriyor, yani simdi atesi
            // paintin icinde cizmemiz lazim


        }
        //6---------------------------------------------------------------------------------------------------------------------
        topX += topdirX;

        if (topX>=780){
            topdirX= -topdirX;
        //topu geriye dogru yollamak icin
        }
        if (topX<=0){

            topdirX=-topdirX;
        }
        repaint();

    }
}
