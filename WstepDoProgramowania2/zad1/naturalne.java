public class naturalne {
    public static int div(int n) {
        int d=1;
        for(int i=1; i < (n-1); i++) {       
            if(n%i == 0)
                d=i;
        }
        return d;
    }
    public static void main(String args[]) {
        for(int j=0; j < args.length; j++) {
            int n= Integer.parseInt(args[j]);
            if(n>0)
                System.out.println(args[j] + ("; ") + div(n));
            else if (n==0) 
                System.out.println(args[j] + ("; 0 nie ma najwiekszego dzielnika"));
            else
                System.out.println(args[j] + ("; Nie jest to liczba naturalna"));
        }
    }
}
