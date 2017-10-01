package waveequation;


public class Particle {

    private static final double strength = 0.2, damp = 0.001;
    double x, y, vx, vy;

    public Particle(double x, double vy){
        this.x = x;
        y = 0;
        vx = 0;
        this.vy = vy;
    }
    
    public void mouseAt(int mx, int my){
        double dx = mx - x;
        double dy = my - y;
        vy += 0.001 * dy / Math.abs(dx);
    }
    
    public void interact(double dt, Particle prev, Particle next){
        double average = (prev.y + next.y) / 2;
        
        double k1 = y - average;
//        double k2 = (y + k1 * dt * dt * strength / 16 + vy * dt / 2) - average;
//        double k3 = (y + k2 * dt * dt * strength / 16 + vy * dt / 2) - average;
//        double k4 = (y + k3 * dt * dt * strength / 2 + vy * dt) - average;
        
        vy -= (strength + damp * Math.signum(k1) * vy) * dt * k1;// / 6 * (k1 + 2 * k2 + 2 * k3 + k4);
    }
    
    public void move(double t){
        x += vx * t;
        y += vy * t;
    }
}
