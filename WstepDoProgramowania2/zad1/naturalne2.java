public class naturalne2 {
    public static int div(int n) {
        int d=1;
        for(int i=1; i < (n-1); i++) {       
            if(n%i == 0)
                d=i;
        }
        return d;
    }
    public static void main(String args[]) {
        int n;
        for(int j=0; j < args.length; j++) {
            try {
                n= Integer.parseInt(args[j]);
                System.out.println(args[j] + ("; ") + div(n));
            }
            catch(NumberFormatException ex) {
                System.out.println(args[j] + ("; Nie jest liczba calkowita"));
            }
        }
    }
}
