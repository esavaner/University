import java.util.Random;

public class RzymArabTest2 {
    public enum TestResult {SUCCES, FAILURE}
    public static TestResult testBidirectionalConversionForRandomIntegers(int sampleSize) throws IllegalSampleSizeException {
        if(sampleSize > 3999 || sampleSize < 1)
            throw new IllegalSampleSizeException(" RIP ");
        int[] tab = new int[sampleSize];
        Random rand = new Random();
        for(int i=0; i < sampleSize; i++) {
            tab[i]= rand.nextInt(3999) + 1;
            System.out.println(tab[i] + "; " + RzymArab.arab2rzym(tab[i]) + "; " + RzymArab.rzym2arab(RzymArab.arab2rzym(tab[i])));
            if(tab[i] != RzymArab.rzym2arab(RzymArab.arab2rzym(tab[i]))) {
                return TestResult.FAILURE;
            }
        }
        return TestResult.SUCCES;
    } 
    public static TestResult testBidirectionalConversionForNumbersWithReminder(int rem, int div) throws DivisionException { 
        if(rem <0)
            throw new DivisionException( " RIP2 ");
        if(div <=0)
            throw new DivisionException( " RIP3 ");
        if(div <= rem)
            throw new DivisionException( " RIP4 "); 
        for(int i=1; i< 4000; i++) {
            if(i % div == rem) {
                System.out.println(i + "; " + RzymArab.arab2rzym(i) + "; " + RzymArab.rzym2arab(RzymArab.arab2rzym(i)));  
                if(i != RzymArab.rzym2arab(RzymArab.arab2rzym(i)))
                    return TestResult.FAILURE;
            }
        }
        return TestResult.SUCCES;
    } 
    public static void main(String[] args) {
        try {
            int sampleSize = Integer.parseInt(args[0]);
            RzymArabTest2.testBidirectionalConversionForRandomIntegers(sampleSize);
        }
        catch(IllegalSampleSizeException ex) {
            System.out.println(" RIP99 ");
        }
        try {
            int rem = Integer.parseInt(args[1]);
            int div = Integer.parseInt(args[2]);
            RzymArabTest2.testBidirectionalConversionForNumbersWithReminder(rem, div);
        }
        catch(DivisionException ax) {
            System.out.println(" RIP11 ");
        }
    }
}
