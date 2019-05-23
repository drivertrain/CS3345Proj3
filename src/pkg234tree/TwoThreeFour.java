package pkg234tree;

import java.util.ArrayList;

/**
 *
 * @author Chase Uphaus
 */
public class TwoThreeFour
{
    public class TreeNode
    {
        private TreeNode [] children;
        private TreeNode parent;
        private int[] keys;
        private int amtKeys;
        
        public TreeNode()
        {
            children = new TreeNode[4];
            keys = new int[3];
            amtKeys = 0;
        }//End default constructor
        
        //Returns -1 if d is not in keys
        public int contains(int d)
        {
            int idx = -1;
            for (int i = 0; i < keys.length; i++)
                if (keys[i] == d)
                    idx = i;
            return idx;
        }//End data Search
        //Removes last item in keys array and returns it
        public int remove()
        {
            amtKeys--;
            int temp = keys[amtKeys];
            keys[amtKeys] = 0;
            return temp;
        }
        public void link(TreeNode node, int i)
        {
            children[i] = node;
            if (node != null)
                node.parent = this;
        }
        public TreeNode unlink(int i)
        {
            TreeNode get = getChildIdx(i);
            children[i] = null;
            return get;
        }
        public int get(int index){return keys[index];}
        //Add data to node and return index where data was inserted
        public int add(int data)
        {
            amtKeys++;
            //Handles the case in which the node had no keys
            if (keys[0] == 0)
            {
                keys[0] = data;
                return 0;
            }//end empty node case
            //Handles the case where there is already one element
            else if (keys[1] == 0)
            {
                if (data < keys[0])
                {
                    int temp = keys[0];
                    keys[0] = data;
                    keys[1] = temp;
                    return 0;
                }
                else
                {
                    keys[1] = data;
                    return 1;
                }
            }//End one element case
            //Handles the case where there are 2 keys in the node
            else
            {
                //Handles case where item inserted need to be at front of the array
                if (data < keys[0])
                {
                    int temp = keys[1];
                    keys[1] = keys[0];
                    keys[2] = temp;
                    keys[0] = data;
                    return 0;
                }
                //Handle case where key needs to be in middle pos
                else if (data < keys[1])
                {
                    int temp = keys[1];
                    keys[1] = data;
                    keys[2] = temp;
                    return 1;
                }
                else
                {
                    keys[2] = data;
                    return 2;
                }
            }
            
            
            
        }
        @Override
        public String  toString() 
        {
		String str = "[";
		for (int i = 0; i< getNumKeys(); i++)
                    str+= keys[i] + "|";
                char[] string = str.toCharArray();
                string[string.length - 1] = ']';
                return String.copyValueOf(string);
	}
        public boolean isLeaf(){return (children[0] == null);}
        public TreeNode getParent(){return parent;}
        public TreeNode getFirstChild(){return children[0];}
        public TreeNode getChildIdx(int i){return children[i];}
        public boolean isFull(){return (amtKeys == 3);}
        public int getNumKeys(){return amtKeys;}
    }//End class Tree Node
    
    
    
    
    private TreeNode root;
    //ArrayLists to help the treeDisp function
    private ArrayList<String> treeDisp0;
    private ArrayList<String> treeDisp1;
    private ArrayList<String> treeDisp2;
    
    
    public TwoThreeFour()
    {
        root = null;
        treeDisp0 = new ArrayList<>();
        treeDisp1 = new ArrayList<>();
        treeDisp2 = new ArrayList<>();
    }
    private TreeNode getSibling(TreeNode parent, int i)
    {
        int numKeys = parent.getNumKeys();
        for (int index = 0; index < numKeys; index++)
        {
            if (i < parent.get(index))
                return parent.getChildIdx(index);
        }
        return parent.getChildIdx(numKeys);
    }
    private void split(TreeNode node)
    {
        int rightKey = node.remove();
        int midKey = node.remove();
        TreeNode child2 = node.unlink(2);
        TreeNode child3 = node.unlink(3);
        TreeNode parent;
        if (root == node)
        {
            root = new TreeNode();
            parent = root;
            root.link(node, 0);
        }
        else
            parent = node.getParent();
        int idxAdded = parent.add(midKey);
        int numKeys = parent.getNumKeys();
        for (int i = numKeys - 1; i > idxAdded; i--)
        {
            TreeNode temp = parent.unlink(i);
            parent.link(temp, i+1);
        }
        TreeNode right = new TreeNode();
        parent.link(right, idxAdded+1);
        right.add(rightKey);
        right.link(child2, 0);
        right.link(child3, 1);
    }
    //Assume no duplicate values are entered
    public boolean add(int data)
    {
        if (root == null)
            root = new TreeNode();
        TreeNode current = root;
        int temp = data;
        do
        {
            if (current.isFull())
            {
                split(current);
                current = getSibling(current.parent, data);
                continue;
            }
            if (current.isLeaf())
                break;
            current = getSibling(current, data);
        }while(true);
        current.add(data);
        return true;
    }
    //Prints out contents of node along with which child it is and which level it belongs to
    @Override
    public String toString()
    {
        return nodeString(root, 0, 0);
    }
    private String nodeString(TreeNode node, int level, int child)
    {
        String str = "Level:" + (level + 1) + " Child:" + (child + 1) + " " + node.toString() + "\n";
        int n = node.getNumKeys() + 1;
        TreeNode childNode;
        for (int i=0; i < n; i++) 
        {
                childNode = node.getChildIdx(i);
                if (childNode != null) 
                     str += nodeString(childNode, level + 1, i);

        }
        return str;
    }
    //This function prints a visualization of the tree as long as it is less than 4 levels deeps
    public void treeDisp()
    {
        specNodeString(root, 0);
        System.out.print("Level 1:");
        System.out.println("                         " + treeDisp0.get(0) + "\n");
        //Loop to print level 2
        System.out.print("Level 2:");
        for (int i = 0; i < treeDisp1.size(); i++)
            System.out.print("           " + treeDisp1.get(i));
        System.out.println("\n");
        System.out.print("Level 3:");
        for (int i = 0; i < treeDisp2.size(); i++)
            System.out.print("   " + treeDisp2.get(i));
        System.out.println();
        
    }
    //specNodeString is a helper function for treeDisp
    private String specNodeString(TreeNode node, int level)
    {
        String str = node.toString();
        if (level == 0)
            treeDisp0.add(str);
        if (level == 1)
            treeDisp1.add(str);
        if (level == 2)
            treeDisp2.add(str);
        int n = node.getNumKeys() + 1;
        TreeNode child;
        for (int i = 0; i < n; i++) 
        {
                child = node.getChildIdx(i);
                if (child != null) 
                     specNodeString(child, level + 1);

        }
        return str;
    }
    
}
