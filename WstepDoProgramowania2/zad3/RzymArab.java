public class RzymArab {
    public static String[] rzymst= {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
    public static int arabtab[]= {1000,900,500,400,100,90,50,40,10,9,5,4,1};
    public static int rzym2arab(String rzym) {
        int i=0;
        int wynik=0;
        char[] chars = rzym.toCharArray();
        rzym = rzym.toUpperCase();
        int counter = 0;
        while(i < rzym.length()) {
            int n = rzymtoarab(rzym.charAt(i));
            if(n ==0)
                throw new IllegalArgumentException(); 
            i++;
            if(i==rzym.length())
                wynik = wynik + n;
            else {
                int nastepny = rzymtoarab(rzym.charAt(i));
                if(nastepny == n) {
                    counter += 1;
                    if(counter > 2)
                        throw new IllegalArgumentException();
                }
                else {
                    counter = 0;
                }
                if(nastepny > n) {
                    if(nastepny > 10*n)
                        throw new IllegalArgumentException();
                    wynik = wynik + nastepny - n;
                    i++;
                    if(i<rzym.length()) {
                        int nastepny2 = rzymtoarab(rzym.charAt(i));
                        if(nastepny2 == n)
                            throw new IllegalArgumentException();
                    }
                }
                else
                    wynik = wynik + n;
            }
        }
        if(wynik > 3999)
            throw new IllegalArgumentException();
        return wynik;
    }
    public static String arab2rzym(int n) {
        String wynik = "";
        if(n <= 3999) {
            int s=0;
            while(n > 0) {
                if(n >= arabtab[s]) {
                    wynik = wynik + rzymst[s];
                    n = n - arabtab[s];
                }
                else
                    if(s<12)
                    s++;
            }
        }
        else if(n > 0)
            throw new NumberFormatException();
        else 
            throw new NumberFormatException();
        return wynik;
    }
    public static int rzymtoarab(char r) {
        switch(r) {
            case 'M' : return 1000;
            case 'D' : return 500;
            case 'C' : return 100;
            case 'L' : return 50;
            case 'X' : return 10;
            case 'V' : return 5;
            case 'I' : return 1;
            default : return 0;
        }
    }
}


