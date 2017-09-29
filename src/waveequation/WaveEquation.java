package waveequation;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.*;

public class WaveEquation extends JPanel implements Runnable, MouseListener, MouseMotionListener{

    private static final int WIDTH = 800, HEIGHT = 600, N = 800;
    private Particle[] particles = new Particle[N];
    private int mouseX, mouseY;
    private boolean mouseActive = false;
    
    public WaveEquation(){
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        
        addMouseListener(this);
        
        for(int i = 0; i < N; i++)
            particles[i] = new Particle(i * 1.0 / (N - 1) * WIDTH, 0);
        int speed = 10;
        if(true){
            particles[100].vy = speed;
            particles[600].vy = -speed;
            particles[605].vy = speed;
        }
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("It's all wiggily!!!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        WaveEquation panel = new WaveEquation();
        frame.add(panel);
        
        frame.pack();
        //position frame to the center of screen
        java.awt.Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds((int)(screen.getWidth() / 2 - frame.getWidth() / 2), 
                        (int)(screen.getHeight() / 2 - frame.getHeight() / 2),
                        frame.getWidth(), frame.getHeight());
        frame.setVisible(true);
        
        new Thread(panel).start();
        
        while(true){
            panel.repaint();
            try{
                Thread.sleep(50);
            }catch(InterruptedException e){}
        }
    }

    @Override
    public void run(){
        System.out.println("Starting loop");
        double dt = 1;
        double t = 0;
        while(true){
            for(int i = 1; i < N - 1; i++)
                particles[i].interact(dt, particles[i-1], particles[i+1]);
            if(mouseActive)
                for(int i = 0; i < N; i++)
                    particles[i].mouseAt(mouseX, mouseY - HEIGHT / 2);
            
            particles[N-1].y = 20 * Math.sin(t * 0.012);
            
            for(int i = 1; i < N; i++)
                particles[i].move(dt);
            
            t += dt;
            try{
                Thread.sleep(1);
            }catch(InterruptedException e){}
        }
    }
    
    @Override
    public void paint(Graphics g){
        
        g.clearRect(0, 0, WIDTH, HEIGHT);
        
        g.setColor(Color.red);
        for(int i = 1; i < N; i++)
            g.drawLine((int)particles[i-1].x, (int)particles[i-1].y + HEIGHT / 2,
                        (int)particles[i].x, (int)particles[i].y + HEIGHT / 2);
    }



    @Override
    public void mousePressed(MouseEvent e){
        mouseX = e.getX();
        mouseY = e.getY();
        mouseActive = true;
    }
    
    @Override
    public void mouseDragged(MouseEvent e){
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e){
        mouseActive = false;
    }

    @Override
    public void mouseClicked(MouseEvent e){
    }
    @Override
    public void mouseEntered(MouseEvent e){
    }
    @Override
    public void mouseExited(MouseEvent e){
    }
    @Override
    public void mouseMoved(MouseEvent e){
    }
}
