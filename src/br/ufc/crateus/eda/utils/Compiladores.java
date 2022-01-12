package br.ufc.crateus.eda.utils;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

import br.ufc.crateus.eda.utils.ExprTree.Node;

public class Compiladores {
	private static Scanner scanner;

	static class Node {
		String ch;
		int valeu = 0;
		Node left, right;

		Node(String ch, int v) {
			this.ch = ch;
			this.valeu = v;
			right = left = null;
		}
	}

	private static boolean isOperator(String ch) {
		return ch == "+" || ch == "-" || ch == "*" || ch == "/";
	}

	public static void inorder(Node r) {
		if (r != null) {
			inorder(r.left);
			if (!isOperator(r.ch))
				System.out.print(r.ch);
			inorder(r.right);
		}
	}

	private static Node posfixToTree(String[] posfix) {
		Stack<Node> stack = new Stack<>();
		for (String s : posfix) {
			String ch = s;
			if (!isOperator(ch))
				stack.push(new Node(ch, 0));
			else {
				Node node = new Node(ch, 0);
				node.right = stack.pop();
				node.left = stack.pop();
				stack.push(node);
			}
		}
		return stack.pop();
	}

	private static String[] infixToPosfix(String[] infix) {
		String[] stack = new String[infix.length];
		String[] posfix = new String[infix.length];

		int t = 0, j = 0;
		stack[t++] = infix[0];

		for (String ch : infix) {
//			char ch = infix[i];
			if (ch.equals("("))
				stack[t++] = ch;
			else if (ch.equals(")")) {
				while (!stack[t - 1].equals("("))
					posfix[j++] = stack[--t];
				t--;
			} else if (ch.equals("+") || ch.equals("-")) {
				while (!stack[t - 1].equals("("))
					posfix[j++] = stack[--t];
				stack[t++] = ch;
			} else if (ch.equals("*") || ch.equals("/")) {
				while (!stack[t - 1].equals("(") && !stack[t - 1].equals("+") && !stack[t - 1].equals("-"))
					posfix[j++] = stack[--t];
				stack[t++] = ch;
			} else
				posfix[j++] = ch;
		}

		return Arrays.copyOf(posfix, j);
		
	}

//	private static void update(Node r, char key, int valor) {
//		if (r != null) {
//			if (key == r.ch)
//				r.valeu = valor;
//			update(r.left, key, valor);
//			update(r.right, key, valor);
//		}
//	}

//	private static Node Menu(char[] posfix) {
//
//		int value;
//		scanner = new Scanner(System.in);
//		Node r = posfixToTree(posfix);
//		for (int i = 0; i < posfix.length; i++) {
//			if (!isOperator(posfix[i])) {
//				System.out.println("Digite o valor a ser atribuido: " + posfix[i]);
//				value = scanner.nextInt();
//				update(r, posfix[i], value);
//			}
//		}
//		return r;
//	}

	private static int calculate(Node r) {
		int result = 0;
		if (r != null) {
			String ch = r.ch;
			if (isOperator(ch)) {
				switch (ch) {
				case "+":
					result = calculate(r.left) + calculate(r.right);
					break;
				case "-":
					result = calculate(r.left) - calculate(r.right);
					break;
				case "*":
					result = calculate(r.left) * calculate(r.right);
					break;
				case "/":
					int calcu = calculate(r.right);
					if (calcu != 0)
						result = calculate(r.left) / calcu;
					break;
				}
			} else
				return r.valeu;
		}
		return result;
	}

	public static void main(String[] args) {
//		String strInfix;
//		scanner = new Scanner(System.in);
//		System.out.println("Digite a expressão a ser calculada ENTRE ()");
//		strInfix = scanner.next();
//		Node r = Menu(infixToPosfix(strInfix.toCharArray()));
		String[] strinfix = {"+","c1","c2"};
		String[] d = infixToPosfix(strinfix);
		for (String p : d)
			System.out.println(p);
//		System.out.println("RESULTADO E:" + calculate(r));
	}

}
