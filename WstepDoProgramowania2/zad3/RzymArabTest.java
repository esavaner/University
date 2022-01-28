public class RzymArabTest {
    public static void main(String[] args) {
        for(int i=0; i < args.length; i++) {
            try {
                int n = Integer.parseInt(args[i]);
                System.out.println(args[i] + "; " + RzymArab.arab2rzym(n));
            }
            catch (NumberFormatException e) {
                try {
                    String rzym = args[i];
                    System.out.println(rzym.toUpperCase() + "; " + RzymArab.rzym2arab(rzym));
                }
                catch (IllegalArgumentException ex) {            
                    System.out.println(args[i] +"; Złe dane");
                }
            }
        }
    }
}

