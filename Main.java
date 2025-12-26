import functions.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== TESTING POINT METHODS ===\n");
        
        // 1. Create initial function y = x^2
        System.out.println("1. CREATING INITIAL FUNCTION y = x^2:");
        TabulatedFunction func = new TabulatedFunction(0, 4, 5);
        
        for (int i = 0; i < func.getPointsCount(); i++) {
            double x = func.getPointX(i);
            func.setPointY(i, x * x);
        }
        
        printPoints("   Initial function points:", func);
        
        // 2. Test addPoint method
        System.out.println("\n2. TEST addPoint():");
        System.out.println("   Adding point (2.5; 6.25) in the middle:");
        System.out.println("   Expected: point inserted between (2.0; 4.0) and (3.0; 9.0)");
        func.addPoint(new FunctionPoint(2.5, 6.25));
        printPoints("   After addPoint(2.5; 6.25):", func);
        
        // 3. Test addPoint with existing X (update Y)
        System.out.println("\n3. TEST addPoint() WITH EXISTING X (update Y):");
        System.out.println("   Adding point (2.5; 100.0):");
        System.out.println("   Expected: Y of point (2.5; 6.25) changes to 100.0");
        func.addPoint(new FunctionPoint(2.5, 100.0));
        printPoints("   After addPoint(2.5; 100.0):", func);
        
        // 4. Test deletePoint method
        System.out.println("\n4. TEST deletePoint():");
        System.out.println("   Deleting point at index 2 (value (2.0; 4.0)):");
        System.out.println("   Expected: point removed, others shifted");
        func.deletePoint(2);
        printPoints("   After deletePoint(2):", func);
        
        // 5. Test setPoint method (correct replacement)
        System.out.println("\n5. TEST setPoint() - CORRECT REPLACEMENT:");
        System.out.println("   Replacing point at index 1 (1.0; 1.0) with (1.5; 2.25):");
        System.out.println("   Expected: point changed, order preserved");
        System.out.printf("   Before: point [1] = (%.2f; %.2f)%n", 
            func.getPointX(1), func.getPointY(1));
        func.setPoint(1, new FunctionPoint(1.5, 2.25));
        System.out.printf("   After: point [1] = (%.2f; %.2f)%n", 
            func.getPointX(1), func.getPointY(1));
        printPoints("   Final state after setPoint(1, (1.5; 2.25)):", func);
        
        // 6. Test setPoint method (incorrect - should not change)
        System.out.println("\n6. TEST setPoint() - INCORRECT REPLACEMENT:");
        System.out.println("   Trying to replace point [1] with (3.0; 9.0):");
        System.out.println("   Expected: DOES NOT CHANGE (X=3.0 violates order)");
        System.out.printf("   Before replacement: point [1] = (%.2f; %.2f)%n", 
            func.getPointX(1), func.getPointY(1));
        func.setPoint(1, new FunctionPoint(3.0, 9.0));
        System.out.printf("   After attempt: point [1] = (%.2f; %.2f)%n", 
            func.getPointX(1), func.getPointY(1));
        
        // 7. Final result
        System.out.println("\n=== FINAL FUNCTION STATE ===");
        printPoints("   Function after all operations:", func);
        
        System.out.println("\n=== ALL TESTS COMPLETED ===");
    }
    
    private static void printPoints(String message, TabulatedFunction func) {
        System.out.println(message);
        for (int i = 0; i < func.getPointsCount(); i++) {
            System.out.printf("   [%d] (%.2f; %.2f)%n", 
                i, func.getPointX(i), func.getPointY(i));
        }
    }
}