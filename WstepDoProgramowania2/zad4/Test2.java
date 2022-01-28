public class Wyjatek extends Exception {
    Wyjatek (String message) {
        super(message);
    }
}
abstract class Figury {
}
abstract class Czworokat extends Figury {
}
class Kolo extends Figury {
    public double pole(double r) { return r*r*Math.PI; };
    public double obw(double r) { return 2*r*Math.PI; };
}
class Pieciokat extends Figury {
    public double obw(double bok) { return bok*5; };
    public double pole(double bok) { return bok*bok*Math.pow(25 + 10*Math.pow(5,(1/2)),(1/2))/4; };
}
class Szesciokat extends Figury {
    public double obw(double bok) { return bok*6; };
    public double pole(double bok) { return 6*bok*Math.pow(3,(1/2))/4; };
}
class Kwadrat extends Czworokat {
    public double obw(double bok1) { return 4*bok1; };
    public double pole(double bok1) { return bok1*bok1; };
}
class Prostokat extends Czworokat {
    public double obw(double bok1, double bok3) { return 2*bok1 + 2*bok3; };
    public double pole(double bok1, double bok3) { return bok1*bok3; };
}
class Romb extends Czworokat {
    public double obw(double bok1) { return 4*bok1; };
    public double pole(double bok1, double kat) { return bok1*bok1*Math.sin(Math.toRadians(kat)); };
}
public class Test {
    public static void main(String[] args) {
        String lit = args[0];
        int j=1;
        for(int i=0; i < lit.length(); i++) {
            try {
                char ch = lit.charAt(i);
                if(ch=='o') {
                    double r = Double.parseDouble(args[j]);
                    if(r <= 0)
                        System.out.println(ch + ";" + r + " Musi byc wieksza od 0");
                    else {    
                        Kolo o = new Kolo();
                        System.out.println("Kolo; Pole=" + o.pole(r) + "; Obwód=" + o.obw(r));
                    }
                    j++;
                    if(j >= args.length) {
                        System.out.println("Za mało danych");
                        break;
                    }
                }
                else if (ch=='p') {
                    double bok = Double.parseDouble(args[j]);
                    if(bok <= 0)
                        System.out.println(ch + "; " + bok + " Musi byc wieksza od 0"); 
                    else {   
                        Pieciokat p = new Pieciokat();
                        System.out.println("Pieciokat; Pole=" + p.pole(bok) + "; Obwód=" + p.obw(bok));
                    }
                    j++;
                    if(j >= argv.length()) {
                        System.out.println("Za mało danych");
                        break;
                    }
                }
                else if (ch=='s') {
                    double bok = Double.parseDouble(args[j]);
                    if(bok <= 0)
                        System.out.println(ch + "; " + bok + " Musi byc wieksza od 0");
                    else {    
                        Szesciokat s = new Szesciokat();
                        System.out.println("Szesciokat; Pole=" + s.pole(bok) + "; Obwód=" + s.obw(bok));
                    }
                    j++;
                    if(j >= args.length) {
                        System.out.println("Za mało danych");
                        break;
                    }
                }
                else if (ch=='c') {
                    double bok1 = Double.parseDouble(args[j]);
                        j++;
                        if(j >= args.length) {
                            System.out.println("Za mało danych");
                            break;
                        }
                        if(bok1<=0)
                            System.out.println(ch + "; " + bok1 + " Bok musi byc wiekszy od 0");
                    double bok2 = Double.parseDouble(args[j]);
                        j++;
                        if(j >= args.length) {
                            System.out.println("Za mało danych");
                            break;
                        }
                        if(bok2<=0)
                            System.out.println(ch + "; " + bok2 + " Bok musi byc wiekszy od 0");
                    double bok3 = Double.parseDouble(args[j]);
                        j++;
                        if(j >= args.length) {
                            System.out.println("Za mało danych");
                            break;
                        }
                        if(bok3<=0)
                            System.out.println(ch + "; " + bok3 + " Bok musi byc wiekszy od 0");
                    double bok4 = Double.parseDouble(args[j]);
                        j++;
                        if(j >= args.length) {
                            System.out.println("Za mało danych");
                            break;
                        }
                        if(bok4<=0)
                            System.out.println(ch + "; " + bok4 + " Bok musi byc wiekszy od 0");
                    double kat = Double.parseDouble(args[j]);
                        j++;
                        if(j >= args.length) {
                            System.out.println("Za mało danych");
                            break;
                        }
                        if(kat<=0)
                            System.out.println(ch + "; " + kat + " Kat musi byc wiekszy od 0");
                        else if (kat > 180)
                            System.out.println(ch + "; " + kat + " Kat musi byc mniejszy od 180");
                    if((bok1==bok2) && (bok2==bok3) && (bok3==bok4)) {
                        Romb ro = new Romb();
                        System.out.println("Romb; Pole=" + ro.pole(bok1,kat) + "; Obwód=" + ro.obw(bok1));
                    }
                    else if((kat == 90) && (bok1==bok2) && (bok2==bok3) && (bok3==bok4)) {
                        Kwadrat kw = new Kwadrat();
                        System.out.println("Kwadrat; Pole=" + kw.pole(bok1) + "; Obwód=" + kw.obw(bok1));
                    }
                    else if((kat == 90) && (bok1==bok2) && (bok3==bok4)) {
                        Prostokat pr = new Prostokat();
                        System.out.println("Prostokat; Pole=" + pr.pole(bok1,bok3) + "; Obwód=" + pr.obw(bok1,bok3));
                    }
                    else
                        System.out.println(ch + "; Brak figury dla tych danych");
                }
                else
                    System.out.println(ch + "; Złe dane");
            }
            catch (IllegalArgumentException ax) {
                System.out.println(args[i] + "; Złe dane");
            }
        }        
    }
}
