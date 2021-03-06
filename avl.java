/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kevinkwok
 */
import java.util.Queue;
import java.util.LinkedList;
import java.util.Random;
public class avl {

    class Node{
        Node left;
        Node right;
        int value;
        int height;
        Node(int new_value){
          left = right = null;
          value = new_value;
        }
    }
    Node root;
    avl(){
        root = null;
    }
    void insert(int data)
    {
 
        root = sub_insert(root,data);
    }
   
    Node sub_insert(Node root, int key)
    {
        if (root == null)
         {   
            root = new Node (key);
            return root;
         }
        if (root.value > key)
            root.left =  sub_insert(root.left,key);
        
        else if (root.value < key)
             root.right = sub_insert(root.right,key);

        return sub_balance(root);
    }
    
    
    void remove(int data)
    {
        root = sub_delete(root,data);
    }
    
    Node sub_delete(Node root, int key)
    {
        if (root == null)
            return root;

        else if (root.value > key)
        {
            root.left = sub_delete(root.left,key);
        }

        else if (root.value < key)
        {
            root.right = sub_delete(root.right,key);
        }

        else if (root.left != null && root.right != null)
        {
            root.value = find_min(root.right);
            root.right = sub_delete(root.right,root.value);
        }

        else
        {
            // Look for the case that has no child or one child
            root = (root.left != null) ? root.left : root.right;
        }

        return sub_balance(root);    
    }
    
    int find_min (Node root)
    {
        int min_value = root.value;
        while(root.left != null)
        {
            root = root.left;
            min_value = root.value;
        }
        return min_value;
    }

    int find_max (Node root)
    {
        int max_value = root.value;
        while (root.right != null)
        {
            root = root.right;
            max_value = root.value;
        }
        return max_value;
    }

    void print_max()
    {
        int max_value = find_max(root);
        System.out.println("Max value is " + max_value);
    }
    boolean contain( Node root, int key)
    {
        if (root == null)
            return false;
        if (root.value > key)
            return contain(root.left,key);
        else if (root.value < key)
            return contain(root.right,key);
        else
            return true;
    }
   
    int height_tree(Node root)
    {
        if (root == null)
            return 0;
        else{
            //return 1+Math.max(height_tree(root.left),height_tree(root.right));
            int l = height_tree(root.left);
            int r = height_tree(root.right);
            
            if (l > r)
                return l + 1;
            else
                return r + 1;
        }
    }

    // This is will be the first version to print the level by level 
    // with O(n^2)
    void print()
    {
        print_level(root);
    }

    void print_BFS()
    {
        BFS(root);
    }
    void print_level(Node root)
    {
        int h = height_tree(root);
        for (int i = 1; i <= h; i++)
        {
            System.out.print("Level "+ i +":");
            print_sub_level(root,i);
             System.out.println("");
          
        }
    }

    void print_sub_level(Node root, int height)
    {
        if (root == null)
            return;
        if (height == 1)
            System.out.print(" " + root.value);
        else
        {
            print_sub_level(root.left,height - 1);
            print_sub_level(root.right,height - 1);
        }
    }

    void check_balance()
    {
        boolean flag = sub_check_balance(root);
        if (flag)
            System.out.println("Balanced");
        else
            System.out.println("Unbalanced");
    }
    boolean sub_check_balance(Node root)
    {
        if (root == null)
            return true;
        else
        {
            int left_root = height_tree(root.left);
            int right_root = height_tree(root.right);
            int difference = left_root - right_root;
            if (Math.abs(difference) > 1)
                return false;
            else
            {
                return (sub_check_balance(root.left) && sub_check_balance(root.right));
            }
        }

    }
    
    void balance()
    {
        root = sub_balance(root);
    }
    
    Node sub_balance(Node root)
    {
        if (root == null)
            return root;
        if (height_tree(root.left) - height_tree(root.right) > 1)
        {
            if(height_tree(root.left.left) >= height_tree(root.left.right) )
                root = singleRightrotation(root);
            else
                root = doubleLeftRightrotation(root);
        }
        else if (height_tree(root.right) - height_tree(root.left) > 1)
        {
            if (height_tree(root.right.right) >= height_tree(root.right.left) )
                root = singleLeftrotation(root);
            else
                root = doubleRightLeftRotation(root);
        }
        root.height = Math.max(height_tree(root.left), height_tree(root.right)) + 1;
        return root;
    }

    Node singleRightrotation(Node root)
    {
        Node temp = root.left;
        root.left = temp.right;
        temp.right = root;
        root.height = Math.max( height_tree( root.left ), height_tree( root.right ) )+ 1;
        temp.height = Math.max( height_tree( temp.left ), root.height) + 1;
        return temp;
    }

    Node singleLeftrotation(Node root)
    {
        Node temp = root.right;
        root.right = temp.left;
        temp.left = root;
        root.height = Math.max( height_tree( root.left ),height_tree( root.right ))+1;
        temp.height = Math.max( height_tree( temp.left ), root.height)+1;
        return temp;
    }

    Node doubleLeftRightrotation(Node root)
    {
        root.left = singleLeftrotation(root.left);
        return singleRightrotation(root);
    }

    Node doubleRightLeftRotation(Node root)
    {
        root.right = singleRightrotation(root.right);
        return singleLeftrotation(root);
    }

    void BFS(Node root)
    {
        Queue<Node> q = new LinkedList<Node>();
        q.add(root);
        while(!q.isEmpty())
        {
            Node temp = q.poll();
            System.out.println( temp.value);
            if (temp.left != null)
            q.add(temp.left);
            if (temp.right !=null)
            q.add(temp.right);
        }
    }
    public static void main(String[] args) {
        // Node tree = new Node(10);
        // tree.left
        Random rand = new Random();
        
        avl tree = new avl();
       for (int i = 0; i < 10; i++)
       {
           int n = rand.nextInt(50);
           tree.insert(n);
       }
       tree.print();
       tree.print_BFS();
    } 
}
