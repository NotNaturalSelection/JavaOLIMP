public class Summator implements ISummator{
    private float[] x;

    public void setArr(float[] arr){
        x = arr;
    }

    public void sum(int c){
        float r = 0;
        for (int i =0; i < c; i++) {
            r+=x[i];
            if(x[c-1] != x[i]){
                System.out.print(x[i]+" + ");
            } else {
                System.out.print(x[i]+" = ");
            }
        }
        System.out.print(r);
    }
}
