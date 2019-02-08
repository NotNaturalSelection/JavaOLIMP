import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        Summator s = new Summator();
        float[] x = new float[1000];
        float p;
        int i = 0;
        do {
            p = sc.nextFloat();
            x[i] = p;
            i++;
        } while(p != 0);
        i--;
        s.setArr(x);
        s.sum(i);
    }
}
