package A2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Client {


	public static void main(String[] args) throws Exception {
		Scanner in = new Scanner(System.in);
		System.out.println("enter file name");
		String fileName = in.nextLine();
		FileWriter outFile = new FileWriter("outFile.txt");
		PrintWriter out = new PrintWriter(outFile);
		Scanner inFile = new Scanner(new File(fileName));
		StringTokenizer line;
		StringTokenizer transaction;
				
		binaryTree Tree  = new binaryTree();
		int records = Integer.parseInt(inFile.nextLine());
		System.out.println("There were " + records + " records found in " + fileName);
		System.out.println("Populating tree, please wait.");
		
		for (int i = 0; i< records; i++){

			line = new StringTokenizer(inFile.nextLine());
			int index = Integer.parseInt(line.nextToken());
			int leftIndex = Integer.parseInt(line.nextToken());
			int rightIndex = Integer.parseInt(line.nextToken());
			String name = line.nextToken("\n").trim();
			Tree.insert(index, leftIndex, rightIndex, name);
			//System.out.println(index);
			//System.out.println(leftIndex);
			//System.out.println(rightIndex);
			//System.out.println(name);
		}
		//System.out.println("Is this a binary tree? " + Tree.isBinaryTree());
		//if (Tree.isBinaryTree == false){
		//	break;
		//}
		//else {
			System.out.print("inorder [ ");
			Tree.inorder(Tree.root);
			System.out.println(" ]");
			Tree.topDown();
			if (Tree.isBinarySearch(Tree.root) == false)
				System.out.println("Not a binary search tree, transforming");
			else
				System.out.println("This a binary search tree");
			while (inFile.hasNext()){
				transaction = new StringTokenizer(inFile.nextLine());
				String command = transaction.nextToken();
				String name = transaction.nextToken("\n").trim();
				if (command.equals("search")){
					if (Tree.search(name) == false)
						System.out.println(name + " does not exist.");
					else
						System.out.println(name + " was found.");
				}
				if (command.equals("insert")){
					if(Tree.insertBST(name) == false)
						System.out.println(name + " cannot be inserted.");
					else
						System.out.println(name + " was inserted.");
				}
				if (command.equals("delete")){
					
					if(Tree.delete(name) == false)
						System.out.println(name + " cannot be removed.");
					else
						System.out.println(name + " was removed.");
				}
			}
		System.out.print("inorder [ ");
		Tree.inorder(Tree.root);
		System.out.println(" ]");
		Tree.topDown();
	}
		//System.out.println(Tree.size);
		//Tree.inorder(Tree.root);
		//System.out.println(Tree.isBinarySearch(Tree.root));
		//Tree.topDown();
	}

//}

 
