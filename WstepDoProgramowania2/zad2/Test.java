public class Test {
    public static void main(String args[]) {
        int n= Integer.parseInt(args[0]);
        if (n>=0) {
            int k;
            LiczbyPierwsze pierwsze = new LiczbyPierwsze(n);
            for(int i=1; i < args.length; i++){
                try {
                    k= Integer.parseInt(args[i]);
                    if(k<0)
                        System.out.println(args[i] + ("; Nie jest dodatnia"));
                    else if (k >= pierwsze.s)
                        System.out.println(args[i] + ("; Liczba spoza zakresu"));
                    else
                        System.out.println(args[i] + ("; ") + pierwsze.Liczba(k));
                }
                catch(NumberFormatException ex) {
                    System.out.println(args[i] + ("; Nie jest liczba calkowita"));
                }
             }
        }
        else if (n<0) {
            try {
                n= Integer.parseInt(args[0]);
                System.out.println(args[0] + ("; Nie jest dodatnia"));
                return;
            }
            catch(NumberFormatException ex) {
                System.out.println(args[0] + ("; Nie jest liczba calkowita"));
                return;
            }
        }
    }
}
