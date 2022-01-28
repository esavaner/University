public class LiczbyPierwsze {
    int i,j,d,s=0;
    int pierwsze[];
    public LiczbyPierwsze(int n) {
        pierwsze= new int[n];
        for(i=2; i < n; i++) {
            d=0;
            for(j=1; j < i; j++){
                if(i%j==0)
                    d++;
            }
            if(d==1) {
                pierwsze[s]=i;
                s++;
            }                
        }
    }
    public int Liczba(int k) {
        return pierwsze[k];
    }
}

