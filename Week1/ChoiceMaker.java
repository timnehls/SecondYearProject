package Week1;
public class ChoiceMaker {
    private static final int[] visitors = {1400, 1200, 1000, 900, 600};

    public static void main(String[] args) {

        for(int i = 0; i < 5; i++) {
            for(int k = 0; k < 5; k++) {
                for(int j = 0; j < 5; j++) {
                    int res = payoff(k, j, i);
                    
                    System.out.print(res + "    ");
                }
                System.out.println();
            }
            System.out.printf("\n\n");
        }

    }


    public static int payoff(int x, int y, int z) {
        int result;
        
        if(x == y && x == z) {
            result = visitors[x]/3;
        } else if(x == y) {
            result = visitors[x]/2;
        } else if(y == z) {
            result = visitors[x];
        } else if(x == z) {
            result = visitors[x]/2;
        } 
        else {
            result = visitors[x];
        }

        return result;
    }
}
