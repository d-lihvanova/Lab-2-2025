import functions.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== TEST LINEAR FUNCTION ===");
        
        // Linear function y = 2x + 1
        System.out.println("\n1. Linear function y = 2x + 1 on [0, 4]:");
        TabulatedFunction func = new TabulatedFunction(0, 4, 5);
        
        for (int i = 0; i < func.getPointsCount(); i++) {
            double x = func.getPointX(i);
            func.setPointY(i, 2*x + 1);
        }
        
        System.out.println("   Points:");
        for (int i = 0; i < func.getPointsCount(); i++) {
            System.out.printf("   [%d] (%.1f; %.1f)%n", 
                i, func.getPointX(i), func.getPointY(i));
        }
        
        System.out.println("\n2. Linear interpolation check:");
        System.out.println("   Exact f(0.5) = 2*0.5 + 1 = 2.0");
        System.out.printf("   Computed: f(0.5) = %.3f%n", func.getFunctionValue(0.5));
        
        System.out.println("\n   Exact f(1.5) = 2*1.5 + 1 = 4.0");
        System.out.printf("   Computed: f(1.5) = %.3f%n", func.getFunctionValue(1.5));
        
        System.out.println("\n   Exact f(2.7) = 2*2.7 + 1 = 6.4");
        System.out.printf("   Computed: f(2.7) = %.3f%n", func.getFunctionValue(2.7));
        
        System.out.println("\n   Exact f(3.8) = 2*3.8 + 1 = 8.6");
        System.out.printf("   Computed: f(3.8) = %.3f%n", func.getFunctionValue(3.8));
        System.out.println("\n=== TEST COMPLETED ===");
    }
}