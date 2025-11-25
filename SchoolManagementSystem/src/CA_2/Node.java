package CA_2;

//Represents a Node in the Binary Tree
public class Node {
    Applicant data; // The employee kept in this Node
    Node left;
    Node right;
    
    public Node(Applicant data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
    
}