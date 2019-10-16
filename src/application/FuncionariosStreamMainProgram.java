package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import model.entities.Funcionarios;

public class FuncionariosStreamMainProgram {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		System.out.print("Entre com o caminho e nome arquivo a ser lido:");
		String path = sc.next();

		System.out.print("Informe o valor do salário a ser comparado:");
		Double pSalario = sc.nextDouble();

		List<Funcionarios> funcionarios = new ArrayList<Funcionarios>();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String linha = br.readLine();
			while (linha != null) {
				String[] fields = linha.split(",");
				String nome = fields[0];
				String email = fields[1];
				Double salario = Double.parseDouble(fields[2]);
				funcionarios.add(new Funcionarios(nome, email, salario));
				linha = br.readLine();
			}
			Comparator<String> pFunc = (f1, f2) -> f1.compareTo(f2);
			List<String> emails = funcionarios.stream().filter(p -> p.getSalario() > pSalario).map(p -> p.getEmail())
					.sorted(pFunc).collect(Collectors.toList());
			System.out.println("Email dos funcionários cujo salário seja Maior que " + String.format("%.2f", pSalario));

			double soma = funcionarios.stream().filter(p -> p.getNome().substring(0, 1).charAt(0) == 'M')
					.map(p -> p.getSalario()).reduce(0.00, (x, y) -> x + y);

			for (String e : emails) {
				System.out.println(e);
			}
			System.out.println();
			System.out.println(
					"Soma dos salários das pessoas cuja nome inicie com a letra 'M': " + String.format("%.2f", soma));
		} catch (IOException e) {
			System.out.println();
		}

	}

}
