package pkg234tree;

import java.util.Scanner;

/**
 *
 * @author Chase Uphaus
 */
public class Main
{

    
    public static void main(String[] args)
    {
        System.out.println("Inserting all values...");
        int[] valuesToInsert = {1, 12, 8, 2, 25, 6, 14, 28, 17, 7, 52, 16, 48, 68, 3, 26, 29, 53, 55, 45};
        TwoThreeFour tree = new TwoThreeFour();
        for (int i = 0; i < valuesToInsert.length; i++)
        {
            tree.add(valuesToInsert[i]);
        }
        System.out.println(tree.toString());
        tree.treeDisp();
        try
        {
            Scanner input = new Scanner(System.in);
            String choice = "";
            
            do
            {
                System.out.println("Enter 1 to add an item, 2 to print tree, or z to exit");
                choice = input.next();
                if (choice.compareTo("1") == 0)
                {
                    System.out.println("Enter an integer greater than 0 to add");
                    choice = input.next();
                    tree.add(Integer.parseInt(choice));
                }
                else if (choice.compareTo("2") == 0)
                    System.out.println(tree.toString());
            } while (choice.compareTo("z") != 0);
                
            
        }
        catch (Exception e){e.printStackTrace();}
    }
    
}
